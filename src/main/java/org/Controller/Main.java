package org.Controller;


import org.Model.User;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        JdbcSqlServerConnection.connectToServer();
        String str = UserController.getUser(1).toString();
        System.out.println(str);
        User user = new User(3, "student", "1234", "1999-01-01", 123456789);
        UserController.addUser(user);
        System.out.println(UserController.getUser(3).toString());
        JdbcSqlServerConnection.closeConnection();
    }
}