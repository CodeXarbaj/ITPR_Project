package com.scl.main;

import java.util.Scanner;
import com.scl.service.AuthService;
import com.scl.util.InputUtil; // 🔥 IMPORT ADD KIYA

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService authService = new AuthService(sc);

        while (true) {
            System.out.println("\n=== SCHOLARSHIP MANAGEMENT SYSTEM ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            
            // 🔥 OLD CODE HATA DIYA: sc.nextInt();
            // ✅ NEW CODE:
            int choice = InputUtil.readInt(sc); 

            switch (choice) {
                case 1:
                    authService.login();
                    break;
                case 2:
                    authService.register();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid Option.");
            }
        }
    }
}