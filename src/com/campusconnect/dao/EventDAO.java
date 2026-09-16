package com.campusconnect.dao;

import com.campusconnect.db.DBConnection;
import com.campusconnect.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    /** All upcoming events, with registration counts, flagged for whether currentUserId has registered. */
    public List<Event> getUpcoming(int currentUserId) throws SQLException {
        String sql =
            "SELECT e.*, " +
            "  (SELECT COUNT(*) FROM event_registrations r WHERE r.event_id = e.id) AS reg_count, " +
            "  (SELECT COUNT(*) FROM event_registrations r2 WHERE r2.event_id = e.id AND r2.user_id = ?) AS is_registered " +
            "FROM events e " +
            "WHERE e.event_date >= CURDATE() OR e.event_date IS NULL " +
            "ORDER BY e.event_date ASC";
        List<Event> events = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, currentUserId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) events.add(mapRow(rs));
            }
        }
        return events;
    }

    public void createEvent(String title, String description, String organizer, String category,
                             Date eventDate, String eventTime, String venue, int capacity, int createdBy) throws SQLException {
        String sql = "INSERT INTO events (title, description, organizer, category, event_date, event_time, venue, capacity, created_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, organizer);
            ps.setString(4, category);
            ps.setDate(5, eventDate);
            ps.setString(6, eventTime);
            ps.setString(7, venue);
            ps.setInt(8, capacity);
            ps.setInt(9, createdBy);
            ps.executeUpdate();
        }
    }

    public void register(int eventId, int userId) throws SQLException {
        String sql = "INSERT IGNORE INTO event_registrations (event_id, user_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public void unregister(int eventId, int userId) throws SQLException {
        String sql = "DELETE FROM event_registrations WHERE event_id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    private Event mapRow(ResultSet rs) throws SQLException {
        Event e = new Event();
        e.setId(rs.getInt("id"));
        e.setTitle(rs.getString("title"));
        e.setDescription(rs.getString("description"));
        e.setOrganizer(rs.getString("organizer"));
        e.setCategory(rs.getString("category"));
        e.setEventDate(rs.getDate("event_date"));
        e.setEventTime(rs.getString("event_time"));
        e.setVenue(rs.getString("venue"));
        e.setCapacity(rs.getInt("capacity"));
        e.setRegisteredCount(rs.getInt("reg_count"));
        e.setCurrentUserRegistered(rs.getInt("is_registered") > 0);
        return e;
    }
}
