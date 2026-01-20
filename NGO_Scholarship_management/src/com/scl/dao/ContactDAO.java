package com.scl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.scl.util.DBConnection;
import com.scl.model.Contact;

public class ContactDAO {

    // 1. Send Message (For Student/Member)
    public boolean submitMessage(Contact c) {
        String query = "INSERT INTO contacts (user_id, name, phone, subject, message) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setInt(1, c.getUserId());
            pst.setString(2, c.getName());
            pst.setString(3, c.getPhone());
            pst.setString(4, c.getSubject());
            pst.setString(5, c.getMessage());
            
            return pst.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 2. View All Messages (For Admin)
    public void viewAllMessages() {
        String query = "SELECT * FROM contacts ORDER BY created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            System.out.println("\n--- INBOX (USER MESSAGES) ---");
            System.out.printf("%-20s %-15s %-20s %-30s\n", "Name", "Phone", "Subject", "Message");
            System.out.println("-----------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-20s %-15s %-20s %-30s\n", 
                    rs.getString("name"), 
                    rs.getString("phone"), 
                    rs.getString("subject"), 
                    rs.getString("message"));
            }
            if (!found) System.out.println("No messages found.");
            System.out.println("-----------------------------------------------------------------------------------------");

        } catch (Exception e) { e.printStackTrace(); }
    }
}