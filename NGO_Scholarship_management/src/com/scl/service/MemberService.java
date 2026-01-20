package com.scl.service;

import java.util.Scanner;

import com.scl.dao.ApplicationDAO;
import com.scl.dao.ContactDAO;
import com.scl.dao.DonationDAO;
import com.scl.model.Contact;
import com.scl.model.Donation;
import com.scl.model.User;
import com.scl.util.InputUtil;

public class MemberService {

    Scanner sc;
    DonationDAO donationDAO = new DonationDAO();
    ApplicationDAO appDAO = new ApplicationDAO();
    ContactDAO contactDAO = new ContactDAO();

    public MemberService(Scanner sc) {
        this.sc = sc;
    }

    public void showDashboard(User user) {

        boolean loggedIn = true;

        while (loggedIn) {

            System.out.println("\n--- MEMBER DASHBOARD ---");
            System.out.println("1. Add Donation Record");
            System.out.println("2. Review Applications");
            System.out.println("3. View My Donation History");
            System.out.println("4. Contact Admin");
            System.out.println("5. Logout");
            System.out.print("Choice: ");

            int choice = InputUtil.readInt(sc);
            sc.nextLine(); // 🔥 buffer clear

            switch (choice) {

                case 1:
                    System.out.print("Donor Name: ");
                    String name = sc.nextLine();

                    System.out.print("Amount: ");
                    double amount = InputUtil.readDouble(sc);
                    sc.nextLine(); // 🔥 buffer clear

                    System.out.print("Payment Mode: ");
                    String mode = sc.nextLine();

                    Donation d = new Donation(name, amount, mode);

                    if (donationDAO.addDonation(d, user.getUserId()))
                        System.out.println("✅ Donation Added!");
                    else
                        System.out.println("❌ Failed to add donation.");
                    break;

                case 2:
                    boolean hasPending = appDAO.viewPendingApplications();
                    if (!hasPending) break;

                    System.out.print("Take Action? (yes/no): ");
                    String ans = sc.nextLine();

                    if (ans.equalsIgnoreCase("yes")) {
                        System.out.print("Enter Application ID: ");
                        int appId = InputUtil.readInt(sc);
                        sc.nextLine();

                        System.out.print("Status (APPROVED/REJECTED): ");
                        String status = sc.nextLine().toUpperCase();

                        if (appDAO.updateStatus(appId, status))
                            System.out.println("✅ Status Updated!");
                        else
                            System.out.println("❌ Update Failed.");
                    }
                    break;

                case 3:
                    donationDAO.viewMyDonations(user.getUserId());
                    break;

                case 4:
                    System.out.println("\n--- CONTACT ADMIN ---");

                    System.out.print("Your Name: ");
                    String mName = sc.nextLine();

                    System.out.print("Phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Subject: ");
                    String sub = sc.nextLine();

                    System.out.print("Message: ");
                    String msg = sc.nextLine();

                    Contact c = new Contact(user.getUserId(), mName, phone, sub, msg);

                    if (contactDAO.submitMessage(c))
                        System.out.println("✅ Message Sent!");
                    else
                        System.out.println("❌ Failed to send.");
                    break;

                case 5:
                    loggedIn = false;
                    System.out.println("Logged out.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
