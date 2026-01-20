package com.scl.service;

import java.util.Scanner;
import com.scl.dao.ApplicationDAO;
import com.scl.dao.ContactDAO; // ✅ Added
import com.scl.model.User;
import com.scl.model.Application;
import com.scl.model.Contact; // ✅ Added
import com.scl.util.InputUtil;

public class StudentService {
    Scanner sc;
    ApplicationDAO appDAO = new ApplicationDAO();
    ContactDAO contactDAO = new ContactDAO(); 

    public StudentService(Scanner sc) {
        this.sc = sc;
    }

    public void showDashboard(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- STUDENT DASHBOARD ---");
            System.out.println("1. Apply for Help");
            System.out.println("2. Check Application Status");
            System.out.println("3. Contact Admin (Send Message)"); // 🔥 New Feature
            System.out.println("4. Logout");
            System.out.print("Choice: ");
            
            int choice = InputUtil.readInt(sc); 

            switch (choice) {
                case 1:
                    System.out.print("Subject: ");
                    String subject = sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    
                    Application app = new Application(user.getUserId(), subject, desc);
                    if (appDAO.submitApplication(app)) {
                        System.out.println("✅ Application Submitted!");
                    } else {
                        System.out.println("❌ Failed to submit.");
                    }
                    break;
                    
                case 2:
                    appDAO.viewMyApplications(user.getUserId());
                    break;

                case 3: // 🔥 Contact Admin Logic
                    System.out.println("\n-- CONTACT ADMIN --");
                    System.out.print("Your Name: ");
                    String cName = sc.nextLine();
                    System.out.print("Phone: ");
                    String cPhone = sc.nextLine();
                    System.out.print("Subject: ");
                    String cSub = sc.nextLine();
                    System.out.print("Message: ");
                    String cMsg = sc.nextLine();

                    Contact c = new Contact(user.getUserId(), cName, cPhone, cSub, cMsg);
                    if (contactDAO.submitMessage(c)) {
                        System.out.println("✅ Message Sent to Admin!");
                    } else {
                        System.out.println("❌ Failed to send.");
                    }
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