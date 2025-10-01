package com.calc;

import java.util.Scanner;
import java.math.BigInteger;

public class App {
    // Factorial implementation (BigInteger for large numbers)
    public static BigInteger factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative numbers not allowed");
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Scientific Calculator ===");
            System.out.println("1. Square root (√x)");
            System.out.println("2. Factorial (!x)");
            System.out.println("3. Natural log (ln x)");
            System.out.println("4. Power (x^b)");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter number: ");
                        double sqrtInput = sc.nextDouble();
                        System.out.println("Result: " + Math.sqrt(sqrtInput));
                        break;
                    case 2:
                        System.out.print("Enter number: ");
                        int factInput = sc.nextInt();
                        System.out.println("Result: " + factorial(factInput));
                        break;
                    case 3:
                        System.out.print("Enter number: ");
                        double lnInput = sc.nextDouble();
                        System.out.println("Result: " + Math.log(lnInput));
                        break;
                    case 4:
                        System.out.print("Enter base: ");
                        double base = sc.nextDouble();
                        System.out.print("Enter exponent: ");
                        double exp = sc.nextDouble();
                        System.out.println("Result: " + Math.pow(base, exp));
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
