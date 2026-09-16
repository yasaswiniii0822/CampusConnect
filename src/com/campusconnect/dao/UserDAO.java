package com.campusconnect.dao;

import com.campusconnect.db.DBConnection;
import com.campusconnect.model.User;
import com.campusconnect.util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /** Creates a new user. Returns the new user's id, or -1 if the email is already taken. */
    public int createUser(String name, String email, String plainPassword,
                           String college, String department, String year) throws SQLException {
        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hash(plainPassword, salt);

        String sql = "INSERT INTO users (name, email, password_hash, password_salt, college, department, year, avatar_color) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String[] palette = {"#c1502f", "#5c6b47", "#a8763e", "#3c5a68", "#8a4b6b"};
        String color = palette[Math.abs(email.hashCode()) % palette.length];

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, hash);
            ps.setString(4, salt);
            ps.setString(5, college);
            ps.setString(6, department);
            ps.setString(7, year);
            ps.setString(8, color);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLIntegrityConstraintViolationException dup) {
            return -1; // email already registered
        }
        return -1;
    }

    /** Verifies credentials and returns the matching user, or null if invalid. */
    public User authenticate(String email, String plainPassword) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    String salt = rs.getString("password_salt");
                    if (PasswordUtil.matches(plainPassword, salt, storedHash)) {
                        return mapRow(rs);
                    }
                }
            }
        }
        return null;
    }

    public User getById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = mapRow(rs);
                    u.setSkills(getSkillsForUser(conn, id));
                    u.setInterests(getInterestsForUser(conn, id));
                    return u;
                }
            }
        }
        return null;
    }

    public void markOnboarded(int userId) throws SQLException {
        String sql = "UPDATE users SET onboarded = 1 WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    public void saveInterests(int userId, List<Integer> interestIds) throws SQLException {
        String del = "DELETE FROM user_interests WHERE user_id = ?";
        String ins = "INSERT INTO user_interests (user_id, interest_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement d = conn.prepareStatement(del)) {
                d.setInt(1, userId);
                d.executeUpdate();
            }
            try (PreparedStatement ins2 = conn.prepareStatement(ins)) {
                for (int interestId : interestIds) {
                    ins2.setInt(1, userId);
                    ins2.setInt(2, interestId);
                    ins2.addBatch();
                }
                ins2.executeBatch();
            }
        }
    }

    public void updateProfile(int userId, String bio, String department, String year) throws SQLException {
        String sql = "UPDATE users SET bio = ?, department = ?, year = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bio);
            ps.setString(2, department);
            ps.setString(3, year);
            ps.setInt(4, userId);
            ps.executeUpdate();
        }
    }

    public void addSkill(int userId, String skillName) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            int skillId;
            try (PreparedStatement find = conn.prepareStatement("SELECT id FROM skills WHERE name = ?")) {
                find.setString(1, skillName);
                try (ResultSet rs = find.executeQuery()) {
                    if (rs.next()) {
                        skillId = rs.getInt(1);
                    } else {
                        try (PreparedStatement ins = conn.prepareStatement(
                                "INSERT INTO skills (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
                            ins.setString(1, skillName);
                            ins.executeUpdate();
                            try (ResultSet keys = ins.getGeneratedKeys()) {
                                keys.next();
                                skillId = keys.getInt(1);
                            }
                        }
                    }
                }
            }
            try (PreparedStatement link = conn.prepareStatement(
                    "INSERT IGNORE INTO user_skills (user_id, skill_id) VALUES (?, ?)")) {
                link.setInt(1, userId);
                link.setInt(2, skillId);
                link.executeUpdate();
            }
        }
    }

    /** Simple people-search: matches name, department, or an attached skill/interest. */
    public List<User> search(String query, String department) throws SQLException {
        StringBuilder sql = new StringBuilder(
            "SELECT DISTINCT u.* FROM users u " +
            "LEFT JOIN user_skills us ON us.user_id = u.id " +
            "LEFT JOIN skills s ON s.id = us.skill_id " +
            "LEFT JOIN user_interests ui ON ui.user_id = u.id " +
            "LEFT JOIN interests i ON i.id = ui.interest_id " +
            "WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            sql.append("AND (u.name LIKE ? OR s.name LIKE ? OR i.name LIKE ?) ");
            String like = "%" + query.trim() + "%";
            params.add(like); params.add(like); params.add(like);
        }
        if (department != null && !department.trim().isEmpty()) {
            sql.append("AND u.department = ? ");
            params.add(department);
        }
        sql.append("ORDER BY u.created_at DESC LIMIT 40");

        List<User> results = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = mapRow(rs);
                    u.setSkills(getSkillsForUser(conn, u.getId()));
                    u.setInterests(getInterestsForUser(conn, u.getId()));
                    results.add(u);
                }
            }
        }
        return results;
    }

    public List<String> getAllDepartments() throws SQLException {
        List<String> depts = new ArrayList<>();
        String sql = "SELECT DISTINCT department FROM users WHERE department IS NOT NULL AND department <> '' ORDER BY department";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) depts.add(rs.getString(1));
        }
        return depts;
    }

    // --- helpers -----------------------------------------------------------

    private List<String> getSkillsForUser(Connection conn, int userId) throws SQLException {
        List<String> skills = new ArrayList<>();
        String sql = "SELECT s.name FROM skills s JOIN user_skills us ON us.skill_id = s.id WHERE us.user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) skills.add(rs.getString(1));
            }
        }
        return skills;
    }

    private List<String> getInterestsForUser(Connection conn, int userId) throws SQLException {
        List<String> interests = new ArrayList<>();
        String sql = "SELECT i.name FROM interests i JOIN user_interests ui ON ui.interest_id = i.id WHERE ui.user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) interests.add(rs.getString(1));
            }
        }
        return interests;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setCollege(rs.getString("college"));
        u.setDepartment(rs.getString("department"));
        u.setYear(rs.getString("year"));
        u.setBio(rs.getString("bio"));
        u.setAvatarColor(rs.getString("avatar_color"));
        u.setOnboarded(rs.getBoolean("onboarded"));
        return u;
    }
}
