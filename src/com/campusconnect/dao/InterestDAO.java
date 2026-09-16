package com.campusconnect.dao;

import com.campusconnect.db.DBConnection;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InterestDAO {

    public static class Interest {
        public int id;
        public String name;
        public Interest(int id, String name) { this.id = id; this.name = name; }
        public int getId() { return id; }
        public String getName() { return name; }
    }

    /** Returns interests grouped by category, in the order Technology, Creative, Professional, Other. */
    public Map<String, List<Interest>> getAllGroupedByCategory() throws SQLException {
        Map<String, List<Interest>> grouped = new LinkedHashMap<>();
        for (String cat : new String[]{"Technology", "Creative", "Professional", "Other"}) {
            grouped.put(cat, new ArrayList<>());
        }
        String sql = "SELECT id, name, category FROM interests ORDER BY category, name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String category = rs.getString("category");
                grouped.computeIfAbsent(category, k -> new ArrayList<>())
                       .add(new Interest(rs.getInt("id"), rs.getString("name")));
            }
        }
        return grouped;
    }
}
