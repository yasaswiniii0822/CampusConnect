package com.campusconnect.dao;

import com.campusconnect.db.DBConnection;
import com.campusconnect.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public void createPost(int userId, String postType, String content) throws SQLException {
        String sql = "INSERT INTO posts (user_id, post_type, content) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, postType);
            ps.setString(3, content);
            ps.executeUpdate();
        }
    }

    /** Latest posts across campus, newest first. */
    public List<Post> getFeed(int limit) throws SQLException {
        String sql = "SELECT p.*, u.name AS author_name, u.avatar_color AS author_color " +
                     "FROM posts p JOIN users u ON u.id = p.user_id " +
                     "ORDER BY p.created_at DESC LIMIT ?";
        List<Post> posts = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Post p = new Post();
                    p.setId(rs.getInt("id"));
                    p.setUserId(rs.getInt("user_id"));
                    p.setAuthorName(rs.getString("author_name"));
                    p.setAuthorInitial(rs.getString("author_name").substring(0, 1).toUpperCase());
                    p.setAuthorColor(rs.getString("author_color"));
                    p.setPostType(rs.getString("post_type"));
                    p.setContent(rs.getString("content"));
                    p.setCreatedAt(rs.getTimestamp("created_at"));
                    posts.add(p);
                }
            }
        }
        return posts;
    }
}
