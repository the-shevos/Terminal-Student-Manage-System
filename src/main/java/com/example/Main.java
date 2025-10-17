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
}