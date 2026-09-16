package com.campusconnect.model;

import java.sql.Date;

public class Event {
    private int id;
    private String title;
    private String description;
    private String organizer;
    private String category;
    private Date eventDate;
    private String eventTime;
    private String venue;
    private int capacity;
    private int registeredCount;
    private boolean currentUserRegistered;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getOrganizer() { return organizer; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Date getEventDate() { return eventDate; }
    public void setEventDate(Date eventDate) { this.eventDate = eventDate; }

    public String getEventTime() { return eventTime; }
    public void setEventTime(String eventTime) { this.eventTime = eventTime; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getRegisteredCount() { return registeredCount; }
    public void setRegisteredCount(int registeredCount) { this.registeredCount = registeredCount; }

    public boolean isCurrentUserRegistered() { return currentUserRegistered; }
    public void setCurrentUserRegistered(boolean currentUserRegistered) { this.currentUserRegistered = currentUserRegistered; }
}
