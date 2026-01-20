package com.scl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.scl.util.DBConnection;
import com.scl.model.Application;

public class ApplicationDAO {

    // 1. Submit Application (For Student)
    public boolean submitApplication(Application app) {
        String query = "INSERT INTO applications (student_id, subject, description, status) VALUES (?, ?, ?, 'PENDING')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, app.getStudentId());
            pst.setString(2, app.getSubject());
            pst.setString(3, app.getDescription());
            return pst.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 2. View My Applications (For Student) - [YE WALA METHOD MISSING THA]
    public void viewMyApplications(int studentId) {
        String query = "SELECT * FROM applications WHERE student_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();

            System.out.println("\n--- MY APPLICATIONS ---");
            System.out.printf("%-5s %-20s %-15s\n", "ID", "Subject", "Status");
            System.out.println("------------------------------------------");
            
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-5d %-20s %-15s\n", 
                    rs.getInt("app_id"), 
                    rs.getString("subject"), 
                    rs.getString("status"));
            }
            if (!found) System.out.println("No applications found.");
            System.out.println("------------------------------------------");

        } catch (Exception e) { e.printStackTrace(); }
    }

 // 3. View Pending Applications (Updated: Returns True/False)
    public boolean viewPendingApplications() {
        String query = "SELECT a.app_id, s.full_name, a.subject, a.description FROM applications a " +
                       "JOIN students s ON a.student_id = s.student_id " +
                       "WHERE a.status = 'PENDING'";
        
        boolean hasData = false; // Flag to track data

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            System.out.println("\n--- PENDING REQUESTS ---");
            System.out.printf("%-5s %-15s %-20s %-30s\n", "ID", "Student", "Subject", "Description");
            System.out.println("--------------------------------------------------------------------------");

            while (rs.next()) {
                hasData = true; // Data mil gaya!
                System.out.printf("%-5d %-15s %-20s %-30s\n", 
                    rs.getInt("app_id"), 
                    rs.getString("full_name"), 
                    rs.getString("subject"), 
                    rs.getString("description"));
            }
            
            if (!hasData) {
                System.out.println("No pending applications found.");
            }
            System.out.println("--------------------------------------------------------------------------");

        } catch (Exception e) { e.printStackTrace(); }
        
        return hasData; // Main file ko batao ki data tha ya nahi
    }

 // 4. Update Status (SAFE VERSION)
    public boolean updateStatus(int appId, String newStatus) {
        // Query change dekho: Hum check kar rahe hain ki wo pehle se PENDING ho
        String query = "UPDATE applications SET status = ? WHERE app_id = ? AND status = 'PENDING'";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setString(1, newStatus);
            pst.setInt(2, appId);
            
            int rows = pst.executeUpdate();
            
            // Agar rows > 0 hai, matlab update hua. 
            // Agar 0 hai, matlab ya to ID galat thi, ya wo Application PENDING nahi thi.
            return rows > 0; 
            
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
   
}