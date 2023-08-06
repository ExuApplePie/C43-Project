package org.Controller;


import org.Model.User;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        JdbcSqlServerConnection.connectToServer();
        JdbcSqlServerConnection.initDatabase();
        System.out.println("(1) Sign in\n(2) sign up");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        // verify user inputted integer
        while (choice != 1 && choice != 2) {
            System.out.println("Invalid input, please try again");
            choice = scanner.nextInt();
        }
        UserController.addUser(new User(1, "student", "1234", "1999-01-01", 123456789));
        System.out.println(UserController.getUser(1));
        JdbcSqlServerConnection.closeConnection();
    }
}