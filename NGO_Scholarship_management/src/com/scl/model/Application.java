package com.scl.model;

public class Application {
    private int appId;
    private int studentId;
    private String subject;
    private String description;
    private String status;

    // Constructors
    public Application() {}

    public Application(int studentId, String subject, String description) {
        this.studentId = studentId;
        this.subject = subject;
        this.description = description;
    }

   
    public int getAppId() { return appId; }
    public void setAppId(int appId) { this.appId = appId; }

    public void setStatus(String status) { this.status = status; }
    // --------------------------------------------------

    // Existing Getters
    public int getStudentId() { return studentId; }
    public String getSubject() { return subject; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
}