package com.scl.service;

import java.util.Scanner;
import com.scl.dao.AdminDAO;
import com.scl.dao.ContactDAO; // ✅ Added
import com.scl.model.User;
import com.scl.util.InputUtil;

public class AdminService {
    Scanner sc;
    AdminDAO adminDAO = new AdminDAO();
    ContactDAO contactDAO = new ContactDAO(); // ✅ DAO Init

    public AdminService(Scanner sc) {
        this.sc = sc;
    }

    public void showDashboard(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n=== SUPER ADMIN PANEL ===");
            System.out.println("1. View Pending Approvals");
            System.out.println("2. Approve User");
            System.out.println("3. View Inbox (Messages)"); // 🔥 New Feature
            System.out.println("4. Logout");
            System.out.print("Choice: ");
            
            int choice = InputUtil.readInt(sc); 

            switch (choice) {
                case 1:
                    adminDAO.viewPendingUsers();
                    break;
                    
                case 2:
                    System.out.print("Enter User ID to Approve: ");
                    int id = InputUtil.readInt(sc); 
                    
                    if (adminDAO.approveUser(id)) 
                        System.out.println("✅ User Approved!");
                    else 
                        System.out.println("❌ Failed.");
                    break;

                case 3: // 🔥 View Messages Logic
                    contactDAO.viewAllMessages();
                    break;
                    
                case 4:
                    loggedIn = false;
                    System.out.println("Logged out.");
                    break;
                    
                default:
                    System.out.println("Invalid Option.");
            }
        }
    }
}