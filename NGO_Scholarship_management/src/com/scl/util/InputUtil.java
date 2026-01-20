package com.scl.util;

import java.util.Scanner;

public class InputUtil {
    
    // Ye method tab tak loop karega jab tak user sahi integer nahi daalta
    public static int readInt(Scanner sc) {
        while (true) {
            try {
                String input = sc.nextLine(); // Pehle String ki tarah padho
                return Integer.parseInt(input.trim()); // Fir number mein badlo
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid Input! Sirf Number daalo: ");
            }
        }
    }
    
    // Ye method Double values (Amount) ke liye
    public static double readDouble(Scanner sc) {
        while (true) {
            try {
                String input = sc.nextLine();
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid Amount! Sahi number daalo: ");
            }
        }
    }
}