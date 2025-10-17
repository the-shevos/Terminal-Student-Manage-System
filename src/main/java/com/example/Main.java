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

        welComePage();
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
            welComePage();
        } else {
            System.exit(0);
        }
    }

    public static void welComePage() {

        if (loggedUser == null) {
            loggedUser = loginPage();
        }

        supplierList = new String[][]{{"1", "Kasun"}, {"2", "Nayana"}};
        getTopBarView("WELCOME TO  STOCK MANAGEMENT SYSTEM");
        System.out.println("1. Change the Credentials \t \t \t \t 2. Supplier Management");
        System.out.println("3. Stock Management       \t \t \t \t 4. Log out");
        System.out.println("5. Exit the system");

        Scanner sc = new Scanner(System.in);
        System.out.print("enter your choice: ");
        String selectNumber = sc.nextLine();

        switch (selectNumber) {
            case "1":
                resetPassword(loggedUser);
                break;
            case "2":
                supplierMenu();
                break;
            case "3":

                break;
            case "4":
                loginPage();
                break;
            case "5":
                exitSystem();
                break;
            default:
        }
    }

    public static void supplierMenu() {

        getTopBarView("SUPPLIER MANAGE");

        System.out.println("1. Add Supplier   \t \t \t \t 2. Update Supplier");
        System.out.println("3. Delete Supplier\t \t \t \t 4. View Supplier");
        System.out.println("5. Search Supplier\t \t \t \t 6. Home Page");

        supplierSelectOptionMenu(supplierList);
    }

    public static void supplierSelectOptionMenu(String[][] supplierList) {
        Scanner sc = new Scanner(System.in);
        System.out.print("enter your choice: ");
        String selectNumber = sc.nextLine();

        switch (selectNumber) {
            case "1":
                addNewSupplierPage();
                break;
            case "2":
                updateSupplierPage();
                break;
            case "3":
                deleteSupplierPage();
                break;
            case "4":
                viewSupplierPage();
                break;
            case "5":
                searchSupplierPage();
                break;
            case "6":
                exitSystem();
                break;
            default:
                System.out.println("Invalid choice, please try again");
                supplierSelectOptionMenu(supplierList);
        }
    }

    public static void printTable(String[] headers, String[][] rows) {

        int columnCount = headers.length + 1;

        int[] columnWidths = new int[columnCount];
        columnWidths[0] = "#".length();

        for (int i = 1; i < columnCount; i++) {
            columnWidths[i] = headers[i - 1].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < rows.length; i++) {

                columnWidths[0] = Math.max(columnWidths[0], String.valueOf(i + 1).length());
            }
            for (int i = 0; i < headers.length; i++) {
                if (row[i] != null) {
                    columnWidths[i + 1] = Math.max(columnWidths[i + 1], row[i].length());
                }
            }
        }

        for (int i = 0; i < columnCount; i++) {
            columnWidths[i] += 4;
        }

        StringBuilder formatBuilder = new StringBuilder();
        formatBuilder.append("|");
        for (int width : columnWidths) {
            formatBuilder.append(" %-").append(width).append("s |");
        }
        formatBuilder.append("%n");
        String format = formatBuilder.toString();

        printBorder(columnWidths);

        System.out.printf(format, mergeArrays(new String[]{"#"}, headers));

        printBorder(columnWidths);

        for (int i = 0; i < rows.length; i++) {
            String[] row = rows[i];
            Object[] rowObjects = new Object[columnCount];
            rowObjects[0] = String.valueOf(i + 1); // Row number
            for (int j = 0; j < headers.length; j++) {
                rowObjects[j + 1] = row[j] != null ? row[j] : "";
            }
            System.out.printf(format, rowObjects);
        }

        printBorder(columnWidths);
    }

    public static void printBorder(int[] columnWidths) {
        System.out.print("+");
        for (int width : columnWidths) {
            for (int i = 0; i < width + 2; i++) {
                System.out.print("-");
            }
            System.out.print("+");
        }
        System.out.println();
    }

    public static String[] mergeArrays(String[] first, String[] second) {
        String[] result = new String[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static void searchSupplierPage() {
        getTopBarView("SEARCH SUPPLIER");
        searchSupplier();
    }

    public static void searchSupplier() {
        System.out.print("Enter existing Supplier ID: ");
        Scanner sc = new Scanner(System.in);

        String exSupplierId = sc.nextLine();
        String[] existsSupplier = checkIdAlreadyExists(exSupplierId, supplierList);

        while (existsSupplier == null) {
            System.out.println("Supplier Not Found!");
            System.out.print("Enter existing Supplier ID: ");
            String reEnterSupplierId = sc.nextLine();
            existsSupplier = checkIdAlreadyExists(reEnterSupplierId, supplierList);
        }

        System.out.println("Current Supplier ID : " + existsSupplier[0]);
        System.out.println("Current Supplier Name : " + existsSupplier[1]);

        System.out.print("Do you want to search another supplier? (Y/N) ");
        String searchSupplier = sc.nextLine();

        if (searchSupplier.equalsIgnoreCase("Y")) {
            searchSupplier();
        } else {
            supplierMenu();
        }
    }

    public static void viewSupplierPage() {
        getTopBarView("VIEW SUPPLIER");
        String [] headers ={"SUPPLIER ID", "SUPPLIER NAME"};
        printTable(headers, supplierList);
        supplierMenu();
    }

    public static void addNewSupplierPage() {
        getTopBarView("ADD SUPPLIER");
        addNewSupplier();
    }

    public static void updateSupplierPage() {
        getTopBarView("UPDATE SUPPLIER");
        updateNewSupplier();
    }

    public static void deleteSupplierPage() {
        getTopBarView("DELETE SUPPLIER");
        deleteSupplier();
    }

    public static void deleteSupplier() {
        System.out.print("Enter existing Supplier ID: ");
        Scanner sc = new Scanner(System.in);

        String exSupplierId = sc.nextLine();
        String[] existsSupplier = checkIdAlreadyExists(exSupplierId, supplierList);

        while (existsSupplier == null) {
            System.out.println("Existing Supplier Not Found!");
            System.out.print("Enter existing Supplier ID: ");
            String reEnterSupplierId = sc.nextLine();
            existsSupplier = checkIdAlreadyExists(reEnterSupplierId, supplierList);
        }

        System.out.println("Current Supplier ID : " + existsSupplier[0]);
        System.out.println("Current Supplier Name : " + existsSupplier[1]);

        System.out.print("Do you want to delete this supplier? (Y/N) ");
        String deleteSupplier = sc.nextLine();

        if (deleteSupplier.equalsIgnoreCase("Y")) {
            for (int i = 0; i < supplierList.length; i++) {
                if (supplierList[i][0] != null && supplierList[i][0].equalsIgnoreCase(exSupplierId)) {
                    supplierList = deleteIndex(supplierList, i);
                    break;
                }
            }
            System.out.println("Supplier deleted successfully!");
            System.out.print("Do you want to delete another supplier? (Y/N) ");
            String updateAnotherSupplier = sc.nextLine();
            if (updateAnotherSupplier.equalsIgnoreCase("Y")) {
                deleteSupplier();
            } else {
                supplierMenu();
            }
        } else {
            deleteSupplier();
        }
    }

    public static void updateNewSupplier() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter existing Supplier ID: ");
        String exSupplierId = sc.nextLine();

        String[] existsSupplier = checkIdAlreadyExists(exSupplierId, supplierList);

        while (existsSupplier == null) {
            System.out.println("Existing Supplier Not Found!");
            System.out.print("Enter existing Supplier ID: ");
            String reEnterSupplierId = sc.nextLine();
            existsSupplier = checkIdAlreadyExists(reEnterSupplierId, supplierList);
        }

        System.out.println("Current Supplier name is: " + existsSupplier[1]);

        System.out.print("Do you want to update supplier Name? (Y/N) ");
        String updateName = sc.nextLine();

        if (updateName.equalsIgnoreCase("Y")) {
            System.out.print("Enter new Supplier Name: ");
            String newSupplierName = sc.nextLine();
            updateSupplierName(newSupplierName, existsSupplier[0]);
            System.out.println("New Supplier Name updated successfully!");
            System.out.print("Do you want to update another supplier? (Y/N) ");
            String updateAnotherSupplier = sc.nextLine();
            if (updateAnotherSupplier.equalsIgnoreCase("Y")) {
                updateNewSupplier();
            } else {
                supplierMenu();
            }
        } else {
            updateSupplierPage();
        }
    }

    public static void addNewSupplier() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Supplier ID: ");
        String supplierId = sc.nextLine();

        while (checkIdAlreadyExists(supplierId, supplierList) != null) {
            System.out.println("Supplier ID already exists, try another supplier Id!");
            System.out.print("Supplier ID: ");
            supplierId = sc.nextLine();
        }

        System.out.print("Supplier Name: ");
        String supplierName = sc.nextLine();
        supplierList = addNewSupplier(supplierId, supplierName);
        System.out.print("Supplier successfully added!, Do you want to add another Supplier? (Y/N)");

        String homePage = sc.nextLine();

        if (homePage.equalsIgnoreCase("Y")) {
            addNewSupplier();
        } else {
            supplierMenu();
        }
    }

    public static String[][] addNewSupplier(String supplierId, String name) {
        String[] newSupplier = {supplierId, name};
        return addElement(supplierList, newSupplier);
    }

    public static void updateSupplierName(String name, String id) {
        for (int i = 0; i < supplierList.length; i++) {
            if (supplierList[i][0] != null && supplierList[i][0].equalsIgnoreCase(id)) {
                supplierList[i][1] = name;
                break;
            }
        }
    }

    public static String[][] addElement(String[][] original, String[] newElement) {
        int originalLength = original.length;
        String[][] newArray = new String[originalLength + 1][2];
        System.arraycopy(original, 0, newArray, 0, originalLength);
        newArray[originalLength] = newElement;
        return newArray;
    }

    public static String[][] deleteIndex(String[][] original, int index) {
        if (index < 0 || index >= original.length) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        String[][] newArray = new String[original.length - 1][2];

        for (int i = 0, j = 0; i < original.length; i++) {
            if (i != index) {
                newArray[j++] = original[i];
            }
        }

        return newArray;
    }

    public static String[] checkIdAlreadyExists(String id, String [] [] list) {
        if (list==null) return null;
        for (String[] singleList : list) {
            if (singleList[0] != null && singleList[0].equalsIgnoreCase(id)) {
                return singleList;
            }
        }
        return null;
    }

    public static void stockMenu() {

        getTopBarView("STOCK MANAGE");

        categoryList = new String[][]{{"1", "Mouse"}, {"2", "keyBord"}};
        itemList = new String[][]{{"001", "1", "Mouse", "item description", "400", "20"}, {"002", "1", "keyBord", "item description", "120", "49"}};

        System.out.println("1. Manage Item Category  \t \t \t \t 2. Add Item");
        System.out.println("3. Get Item Supplier Wise\t \t \t \t 4. View Item");
        System.out.println("5. Rank Items Unit Price \t \t \t \t 6. Home Page");

        stockMenuSelectOptionMenu();
    }

    public static void stockMenuSelectOptionMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("enter your choice: ");
        String selectNumber = sc.nextLine();

        switch (selectNumber) {
            case "1":

                break;
            case "2":

                break;
            case "3":

                break;
            case "4":

                break;
            case "5":

                break;
            case "6":
                welComePage();
                break;
            default:
        }
    }
}