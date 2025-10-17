package com.example;

import java.util.Scanner;

public class Main {
    public static String[][] userList = new String[50][2];
    public static String[][] supplierList = new String[50][2];
    public static String[][] categoryList = new String[50][2];
    public static String[][] itemList = new String[50][2];
    public static String[] loggedUser;

    public static void main(String[] args) {
        userList = new String[][]{{"user", "1234"}};

    }

    public static void exitSystem() {
        System.exit(0);
    }

    public static String[] loginPage() {
        getTopBarView("LOGIN");
        return checkUserName();
    }

    public static String[] checkUserName() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter User Name: ");
        String userName = sc.nextLine();
        String[] userIndex = new String[2];

        for (String[] index : userList) {
            if (index[0] != null && index[0].equalsIgnoreCase(userName)) {
                userIndex = index;
                break;
            }
        }

        if (userIndex[0] != null) {
            passwordCheck(userIndex);
        } else {
            System.out.println("Oops, Your username is incorrect, please try again");
            checkUserName();
        }

        return userIndex;
    }

    public static void getTopBarView(String topic) {
        String str = "";
        for (int i = 0; i < topic.length(); i++) {
            str = str.concat("-");
        }
        System.out.println();
        System.out.println("+--------------------------------------" + str + "+");
        System.out.println("|                   " + topic + "                   |");
        System.out.println("+--------------------------------------" + str + "+");
    }

    public static void passwordCheck(String[] userIndex) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Your Password: ");
        String password = sc.nextLine();

        if (userIndex[1].equals(password)) {
            System.out.println("Successfully logged in");
        } else {
            System.out.println("Oops, Your password is incorrect, please try again");
            passwordCheck(userIndex);
        }
    }

    public static void resetPassword(String[] userIndex) {
        getTopBarView("CREDENTIAL MANGE");

        System.out.println("Please re enter your username and password to reset your password");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String userName = sc.nextLine();

        while (!userIndex[0].equalsIgnoreCase(userName)) {
            System.out.println("Invalid username, please try again");
            System.out.print("Enter your username: ");
            userName = sc.nextLine();
        }


        System.out.print("Enter your current password: ");
        String password = sc.nextLine();

        while (!userIndex[1].equalsIgnoreCase(password)) {
            System.out.println("Invalid password, please try again");
            System.out.print("Enter your current password: ");
            password = sc.nextLine();
        }

        System.out.print("Please enter your new password: ");
        String newPassword = sc.nextLine();
        userIndex[1] = newPassword;

        for (int i = 0; i < userList.length; i++) {
            if (userList[i][0] != null && userList[i][0].equalsIgnoreCase(userName)) {
                userList[i][1] = newPassword;
            }
        }

        System.out.print("Do you want to go home page? (Y/N): ");
        String homePage = sc.nextLine();
        if (homePage.equalsIgnoreCase("Y")) {

        } else {
            System.exit(0);
        }
    }
}