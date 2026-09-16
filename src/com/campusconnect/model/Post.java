package com.campusconnect.model;

import java.sql.Timestamp;

public class Post {
    private int id;
    private int userId;
    private String authorName;
    private String authorInitial;
    private String authorColor;
    private String postType;
    private String content;
    private Timestamp createdAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorInitial() { return authorInitial; }
    public void setAuthorInitial(String authorInitial) { this.authorInitial = authorInitial; }

    public String getAuthorColor() { return authorColor; }
    public void setAuthorColor(String authorColor) { this.authorColor = authorColor; }

    public String getPostType() { return postType; }
    public void setPostType(String postType) { this.postType = postType; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
