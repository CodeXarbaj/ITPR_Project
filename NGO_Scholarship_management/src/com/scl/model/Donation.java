package com.scl.model;

public class Donation {
    private String donorName;
    private double amount;
    private String paymentMode;

    public Donation(String donorName, double amount, String paymentMode) {
        this.donorName = donorName;
        this.amount = amount;
        this.paymentMode = paymentMode;
    }

    public String getDonorName() { return donorName; }
    public double getAmount() { return amount; }
    public String getPaymentMode() { return paymentMode; }
}