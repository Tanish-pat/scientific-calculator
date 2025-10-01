package com.calc;

import java.util.Scanner;
import java.math.BigInteger;

public class App {
    // Factorial implementation (BigInteger for large numbers)
    public static BigInteger factorial(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Negative numbers not allowed");
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n" + GREEN + "=== Scientific Calculator ===" + RESET);
            System.out.println(CYAN + "1. Square root (√x)" + RESET);
            System.out.println(CYAN + "2. Factorial (!x)" + RESET);
            System.out.println(CYAN + "3. Natural log (ln x)" + RESET);
            System.out.println(CYAN + "4. Power (x^b)" + RESET);
            System.out.println(CYAN + "5. Exit" + RESET);
            System.out.print(YELLOW + "Choose an option: " + RESET);

            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.print(YELLOW + "Enter number: " + RESET);
                        double sqrtInput = sc.nextDouble();
                        System.out.println(GREEN + "Result: " + sqrtInput + " → " + Math.sqrt(sqrtInput) + RESET);
                        break;
                    case 2:
                        System.out.print(YELLOW + "Enter number: " + RESET);
                        int factInput = sc.nextInt();
                        System.out.println(GREEN + "Result: " + factorial(factInput) + RESET);
                        break;
                    case 3:
                        System.out.print(YELLOW + "Enter number: " + RESET);
                        double lnInput = sc.nextDouble();
                        System.out.println(GREEN + "Result: " + Math.log(lnInput) + RESET);
                        break;
                    case 4:
                        System.out.print(YELLOW + "Enter base: " + RESET);
                        double base = sc.nextDouble();
                        System.out.print(YELLOW + "Enter exponent: " + RESET);
                        double exp = sc.nextDouble();
                        System.out.println(GREEN + "Result: " + Math.pow(base, exp) + RESET);
                        break;
                    case 5:
                        System.out.println(RED + "Exiting..." + RESET);
                        sc.close();
                        return;
                    default:
                        System.out.println(RED + "Invalid choice" + RESET);
                }
            } catch (Exception e) {
                System.out.println(RED + "Error: " + e.getMessage() + RESET);
            }
        }
    }
}
