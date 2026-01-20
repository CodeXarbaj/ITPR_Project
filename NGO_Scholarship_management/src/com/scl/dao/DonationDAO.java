package com.scl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.scl.util.DBConnection;
import com.scl.model.Donation;

public class DonationDAO {

    // 1. Add Donation
    public boolean addDonation(Donation d, int memberId) {

        String query = "INSERT INTO donations " +
                       "(donor_name, amount, payment_mode, collected_by, donation_date) " +
                       "VALUES (?, ?, ?, ?, NOW())";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, d.getDonorName());
            pst.setDouble(2, d.getAmount());
            pst.setString(3, d.getPaymentMode());
            pst.setInt(4, memberId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. View My Donations Only
    public void viewMyDonations(int memberId) {

        String query = "SELECT donation_id, donor_name, amount, payment_mode, donation_date " +
                       "FROM donations WHERE collected_by = ? ORDER BY donation_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, memberId);
            ResultSet rs = pst.executeQuery();

            System.out.println("\n--- MY COLLECTED DONATIONS ---");
            System.out.printf("%-5s %-20s %-10s %-12s %-15s\n",
                    "ID", "Donor Name", "Amount", "Mode", "Date");
            System.out.println("-------------------------------------------------------------");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.printf("%-5d %-20s %-10.2f %-12s %-15s\n",
                        rs.getInt("donation_id"),
                        rs.getString("donor_name"),
                        rs.getDouble("amount"),
                        rs.getString("payment_mode"),
                        rs.getDate("donation_date"));
            }

            if (!found) {
                System.out.println("No donations found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
