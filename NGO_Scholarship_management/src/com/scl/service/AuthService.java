package com.scl.service;

import java.util.Scanner;
import com.scl.dao.UserDAO;
import com.scl.model.User;
import com.scl.util.InputUtil; 

public class AuthService {
    Scanner sc;
    UserDAO userDAO = new UserDAO();
    
    StudentService studentService;
    MemberService memberService;
    AdminService adminService;

    public AuthService(Scanner sc) {
        this.sc = sc;
        this.studentService = new StudentService(sc);
        this.memberService = new MemberService(sc);
        this.adminService = new AdminService(sc);
    }

    public void login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();

        User user = userDAO.loginUser(u, p);

        if (user != null) {
            System.out.println("\n✅ Welcome " + user.getRole());
            if (user.getRole().equalsIgnoreCase("STUDENT")) {
                studentService.showDashboard(user);
            } else if (user.getRole().equalsIgnoreCase("MEMBER")) {
                memberService.showDashboard(user);
            } else if (user.getRole().equalsIgnoreCase("ADMIN")) {
                adminService.showDashboard(user);
            }
        } else {
            System.out.println("Invalid Credentials or Locked Account.");
        }
    }

    public void register() {
        System.out.println("\n--- REGISTER ---");
        // 1. Basic User Info
        System.out.print("Enter Username: ");
        String u = sc.nextLine();
        System.out.print("Enter Password: ");
        String p = sc.nextLine();
        
        System.out.println("Role: 1. STUDENT  2. MEMBER");
        int r = InputUtil.readInt(sc); 

        String role = "STUDENT";
        
        // Variables for extra details
        String fullName = "";
        String phone = "";
        String extra1 = ""; // Course OR Designation
        String extra2 = ""; // Address OR Null

        // 2. Role Specific Logic
        if (r == 1) {
            // --- STUDENT DETAILS ---
            System.out.println("\n-- Student Details --");
            System.out.print("Full Name: ");
            fullName = sc.nextLine();
            
            System.out.print("Phone Number: ");
            phone = sc.nextLine();
            
            System.out.print("Course (e.g. B.Tech): ");
            extra1 = sc.nextLine();
            
            System.out.print("Address: ");
            extra2 = sc.nextLine();
            
        } else if (r == 2) {
            // --- MEMBER DETAILS ---
            role = "MEMBER";
            System.out.print("Enter PIN: ");
            if (!sc.nextLine().equals("MWT2026")) {
                System.out.println("❌ Wrong PIN.");
                return;
            }

            System.out.println("\n-- Member Details --");
            System.out.print("Full Name: ");
            fullName = sc.nextLine();
            
            System.out.print("Phone Number: ");
            phone = sc.nextLine();
            
            System.out.print("Designation (e.g. Volunteer): ");
            extra1 = sc.nextLine();
            // Address (extra2) ki zaroorat nahi members table mein
        }

        // 3. Send EVERYTHING to Database
        if (userDAO.registerUser(new User(u, p, role), fullName, phone, extra1, extra2)) {
            System.out.println("✅ Registered Successfully!");
            if(role.equals("MEMBER")) {
                System.out.println("👉 Account is Locked. Ask Admin for Approval.");
            } else {
                System.out.println("👉 You can Login now.");
            }
        } else {
            System.out.println("❌ Registration Failed! Username might exist.");
        }
    }
}