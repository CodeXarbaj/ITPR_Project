package com.scl.model;

public class Contact {
    private int userId;
    private String name;
    private String phone;
    private String subject;
    private String message;

    public Contact(int userId, String name, String phone, String subject, String message) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.subject = subject;
        this.message = message;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getSubject() { return subject; }
    public String getMessage() { return message; }
}