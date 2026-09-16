package com.campusconnect.model;

import java.util.List;

public class User {
    private int id;
    private String name;
    private String email;
    private String college;
    private String department;
    private String year;
    private String bio;
    private String avatarColor;
    private boolean onboarded;
    private List<String> skills;
    private List<String> interests;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCollege() { return college; }
    public void setCollege(String college) { this.college = college; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getAvatarColor() { return avatarColor; }
    public void setAvatarColor(String avatarColor) { this.avatarColor = avatarColor; }

    public boolean isOnboarded() { return onboarded; }
    public void setOnboarded(boolean onboarded) { this.onboarded = onboarded; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }

    /** First name only, used in headers/greetings. */
    public String getFirstName() {
        if (name == null) return "";
        int space = name.indexOf(' ');
        return space > 0 ? name.substring(0, space) : name;
    }

    /** Single-letter initial for avatar bubbles. */
    public String getInitial() {
        return (name != null && !name.isEmpty()) ? name.substring(0, 1).toUpperCase() : "?";
    }
}
