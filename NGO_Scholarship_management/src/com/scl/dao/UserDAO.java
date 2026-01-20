package com.scl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLIntegrityConstraintViolationException; // New Import
import com.scl.util.DBConnection;
import com.scl.model.User;

public class UserDAO {

    public User loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getInt("is_active") == 0) return null; // Account Locked
                
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // 🔥 UPDATED REGISTER METHOD WITH DUPLICATE CHECK
    public boolean registerUser(User user, String fullName, String phone, String extra1, String extra2) {
        String userQuery = "INSERT INTO users (username, password, role, is_active) VALUES (?, ?, ?, ?)";
        
        Connection con = null;
        PreparedStatement pstUser = null;
        PreparedStatement pstRole = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // 🛑 Transaction Start

            // 1. Insert into USERS table
            int isActive = user.getRole().equalsIgnoreCase("STUDENT") ? 1 : 0;
            
            pstUser = con.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            pstUser.setString(1, user.getUsername());
            pstUser.setString(2, user.getPassword());
            pstUser.setString(3, user.getRole());
            pstUser.setInt(4, isActive);
            
            int rows = pstUser.executeUpdate();
            if (rows == 0) throw new Exception("User Insert Failed");

            ResultSet rs = pstUser.getGeneratedKeys();
            int newUserId = 0;
            if (rs.next()) newUserId = rs.getInt(1);

            // 2. Insert into Role Specific table
            if (user.getRole().equalsIgnoreCase("STUDENT")) {
                String studQuery = "INSERT INTO students (student_id, full_name, phone, course, address) VALUES (?, ?, ?, ?, ?)";
                pstRole = con.prepareStatement(studQuery);
                pstRole.setInt(1, newUserId);
                pstRole.setString(2, fullName);
                pstRole.setString(3, phone);
                pstRole.setString(4, extra1); 
                pstRole.setString(5, extra2); 
                
            } else if (user.getRole().equalsIgnoreCase("MEMBER")) {
                String memQuery = "INSERT INTO members (member_id, full_name, phone, designation) VALUES (?, ?, ?, ?)";
                pstRole = con.prepareStatement(memQuery);
                pstRole.setInt(1, newUserId);
                pstRole.setString(2, fullName);
                pstRole.setString(3, phone);
                pstRole.setString(4, extra1);
            }

            pstRole.executeUpdate();
            con.commit(); // ✅ Success
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle Duplicate Username gracefully
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            System.out.println("\n❌ REGISTRATION ERROR: The username '" + user.getUsername() + "' is already taken.");
            return false;
            
        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (Exception ex) {}
        }
    }
}