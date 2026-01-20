package com.scl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.scl.util.DBConnection;

public class AdminDAO {

    // 1. Pending Users ki List dikhana (Jo Active nahi hain)
    public void viewPendingUsers() {
        // Query: Sirf unhe dikhao jo 'is_active = 0' (False) hain
        String query = "SELECT user_id, username, role FROM users WHERE is_active = 0"; 
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            System.out.println("\n--- PENDING APPROVAL REQUESTS ---");
            System.out.printf("%-10s %-20s %-15s\n", "User ID", "Username", "Role");
            System.out.println("------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-10d %-20s %-15s\n", 
                    rs.getInt("user_id"), 
                    rs.getString("username"), 
                    rs.getString("role"));
            }
            
            if (!found) {
                System.out.println("No pending requests found.");
            }
            System.out.println("------------------------------------------------");

        } catch (Exception e) { e.printStackTrace(); }
    }

    // 2. User ko Approve karna (Active karna)
    public boolean approveUser(int userId) {
        String query = "UPDATE users SET is_active = 1 WHERE user_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setInt(1, userId);
            int rows = pst.executeUpdate();
            return rows > 0; // True agar update ho gaya
            
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
