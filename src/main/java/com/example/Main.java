package com.example;

import java.util.Scanner;

public class Main {
    public static String[][] userList = new String[50][2];
    public static String[][] supplierList = new String[50][2];
    public static String[][] categoryList = new String[50][2];
    public static String[][] itemList = new String[50][2];
    public static String[] loggedUser;

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        userList = new String[][]{{"sachi", "1234"}};

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
                stockMenu();
                break;
            case "4":
                loggedUser = loginPage();
                welComePage();
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
                welComePage();
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
        System.out.print("enter your choice: ");
        String selectNumber = sc.nextLine();

        switch (selectNumber) {
            case "1":
                categoryMenu();
                break;
            case "2":
                addItemPage();
                break;
            case "3":
                searchSupplerWisePage();
                break;
            case "4":
                groupItemCategoryWisePage();
                break;
            case "5":
                rankByUnitPricePage();
                break;
            case "6":
                welComePage();
                break;
            default:
        }
    }

    public static void categoryMenu() {

        getTopBarView("ADD CATEGORY");

        System.out.println("1. Add Category   \t \t \t \t 2. Update Category");
        System.out.println("3. Delete Category\t \t \t \t 4. Stock Manage");

        categorySelectOptionAction(supplierList);
    }

    public static void categorySelectOptionAction(String[][] supplierList) {
        System.out.print("enter your choice: ");
        String selectNumber = sc.nextLine();

        switch (selectNumber) {
            case "1":
                addNewCategoryPage();
                break;
            case "2":
                updateCategoryPage();
                break;
            case "3":
                deleteCategoryPage();
                break;
            case "4":
                stockMenu();
                break;
            default:
                System.out.println("Invalid choice, please try again");
                supplierSelectOptionMenu(supplierList);
        }
    }

    public static void addNewCategoryPage(){
        getTopBarView("ADD CATEGORY");
        addNewCategory();
    }

    private static void addNewCategory(){
        System.out.print("Enter the new category name: ");
        String categoryName = sc.nextLine();

        int lastIndex = categoryList.length - 1;
        int lastId  = Integer.parseInt(categoryList[lastIndex][0])+1;

        String [] newCategoryData = {String.valueOf(lastId),categoryName};
        categoryList = addElement(categoryList, newCategoryData);
        System.out.println("Category successfully added!");

        System.out.println("Category ID : " + lastId);
        System.out.println("Category Name : " + categoryName);

        System.out.println("Do you want to add another Category? (Y/N)");
        if (sc.nextLine().equalsIgnoreCase("Y")) {
            addNewCategory();
        }else {
            categoryMenu();
        }
    }

    public static void updateCategoryPage(){
        getTopBarView("UPDATE CATEGORY");
        updateCategory();
    }

    public static void updateCategory(){
        System.out.print("Enter existing Category ID: ");
        String existingCategoryId = sc.nextLine();

        String[] existsCategory = checkIdAlreadyExists(existingCategoryId, categoryList);

        while (existsCategory == null) {
            System.out.println("Existing Category Not Found!");
            System.out.print("Enter existing Category ID: ");
            String reEnterSupplierId = sc.nextLine();
            existsCategory = checkIdAlreadyExists(reEnterSupplierId, categoryList);
        }

        System.out.println("Current Category name is: " + existsCategory[1]);

        System.out.print("Do you want to update category Name? (Y/N) ");
        String updateName = sc.nextLine();

        if (updateName.equalsIgnoreCase("Y")) {
            System.out.print("Enter new Category Name: ");
            String newSupplierName = sc.nextLine();
            updateSupplierName(newSupplierName, existsCategory[0]);
            System.out.println("Category Name updated successfully!");
            System.out.print("Do you want to update another supplier? (Y/N) ");
            String updateAnotherSupplier = sc.nextLine();
            if (updateAnotherSupplier.equalsIgnoreCase("Y")) {
                updateCategory();
            } else {
                categoryMenu();
            }
        } else {
            updateSupplierPage();
        }
    }

    public static void deleteCategoryPage() {
        getTopBarView("DELETE CATEGORY");
        deleteCategory();
    }

    public static void deleteCategory() {
        System.out.print("Enter existing Category ID: ");

        String exSupplierId = sc.nextLine();
        String[] existsSupplier = checkIdAlreadyExists(exSupplierId, categoryList);

        while (existsSupplier == null) {
            System.out.println("Existing Supplier Not Found!");
            System.out.print("Enter existing Supplier ID: ");
            String reEnterSupplierId = sc.nextLine();
            existsSupplier = checkIdAlreadyExists(reEnterSupplierId, categoryList);
        }

        System.out.println("Current Category ID : " + existsSupplier[0]);
        System.out.println("Current Category Name : " + existsSupplier[1]);

        System.out.print("Do you want to delete this category? (Y/N) ");
        String deleteSupplier = sc.nextLine();

        if (deleteSupplier.equalsIgnoreCase("Y")) {
            for (int i = 0; i < categoryList.length; i++) {
                if (categoryList[i][0] != null && categoryList[i][0].equalsIgnoreCase(exSupplierId)) {
                    categoryList = deleteIndex(categoryList, i);
                    break;
                }
            }
            System.out.println("Category deleted successfully!");
            System.out.print("Do you want to delete another category? (Y/N) ");
            String updateAnotherSupplier = sc.nextLine();
            if (updateAnotherSupplier.equalsIgnoreCase("Y")) {
                deleteCategory();
            } else {
                categoryMenu();
            }
        } else {
            deleteCategory();
        }
    }

    public static void addItemPage(){
        getTopBarView("ADD ITEM");
        addItem();
    }

    public static void addItem(){

        if (categoryList.length == 0) {
            System.out.println("Oops!, It seems that you don't have any category in the system");
            String addCategory = sc.nextLine();
            System.out.print("Do you want to add new Category? (Y/N) ");
            if (addCategory.equalsIgnoreCase("Y")) {
                addNewCategory();
            }else {
                stockMenu();
            }
        }

        if (supplierList.length == 0) {
            System.out.println("Oops!, It seems that you don't have any supplier in the system");
            String addSupplier = sc.nextLine();
            System.out.print("Do you want to add new Supplier? (Y/N) ");
            if (addSupplier.equalsIgnoreCase("Y")) {
                addNewSupplier();
            }else {
                stockMenu();
            }
        }

        System.out.print("Item Code : ");
        String itemCode = sc.nextLine();
        String[] exists = checkIdAlreadyExists(itemCode, itemList);

        while (exists != null) {
            System.out.println("Item Code already exists!");
            System.out.print("Item Code : ");
            itemCode = sc.nextLine();
            exists = checkIdAlreadyExists(itemCode, itemList);
        }

        System.out.println("Supplier List");
        String [] headers ={"SUPPLIER ID", "SUPPLIER NAME"};
        printTable(headers, supplierList);
        System.out.print("Enter Supplier No: ");
        int supplierId = sc.nextInt();

        if (supplierList.length<supplierId){
            System.out.println("Supplier Not Found!, Enter Valid Supplier No");
            System.out.print("Enter Supplier No: ");
            supplierId = sc.nextInt();
        }
        String sid = supplierList[supplierId-1][0];

        System.out.println("Category List");
        String [] categoryHeaders ={"CATEGORY ID", "CATEGORY NAME"};
        printTable(categoryHeaders, categoryList);
        System.out.print("Enter Category No: ");
        int categoryId = sc.nextInt();

        while (categoryList.length<categoryId){
            System.out.println("Category Not Found!, Enter Valid Category No");
            System.out.print("Enter Category No: ");
            categoryId = sc.nextInt();
        }

        String cName = categoryList[categoryId-1][1];

        System.out.print("Description : ");
        String description = sc.nextLine();

        System.out.print("Unit Price : ");
        double price = sc.nextDouble();

        System.out.print("Qty of Units : ");
        int qty = sc.nextInt();

        String [] item ={itemCode,sid, cName, description, String.valueOf(price), String.valueOf(qty)};
        itemList = addElement(itemList, item);

        System.out.println("Item added successfully!");
        System.out.println("Do you want to add another item? (Y/N) ");
        String addAnotherItem = sc.nextLine();
        if (addAnotherItem.equalsIgnoreCase("Y")) {
            addItem();
        }else{
            stockMenu();
        }
    }

    public static void searchSupplerWisePage(){
        getTopBarView("SEARCH SUPPLIER WISE");
        searchSupplerWise();
    }

    public static void searchSupplerWise(){
        System.out.print("Enter suppler ID: ");
        String searchSupplerId = sc.nextLine();
        String[] existsSupplier = checkIdAlreadyExists(searchSupplerId, supplierList);

        while (existsSupplier == null) {
            System.out.println("Existing Supplier Not Found!");
            System.out.print("Enter existing Supplier ID: ");
            String reEnterSupplierId = sc.nextLine();
            existsSupplier = checkIdAlreadyExists(reEnterSupplierId, supplierList);
        }
        System.out.println("SUPPLER NAME : " + existsSupplier[1]);

        String[][] filteredItemList = filterItem(itemList, searchSupplerId,1);
        String [] headers ={"ITEM CODE", "SUPPLIER ID", "CATEGORY", "DESCRIPTION", "PRICE", "QTY"};
        printTable(headers, filteredItemList);
    }

    public static void groupItemCategoryWisePage(){
        getTopBarView("GROUP CATEGORY WISE");
        groupItemCategoryWise();
    }

    public static void groupItemCategoryWise(){
        for(String [] category : categoryList){
            System.out.println(category[1]);

            String[][] filteredItemList = filterItem(itemList, category[1], 2);
            String [] headers ={"ITEM CODE", "SUPPLIER ID", "CATEGORY", "DESCRIPTION", "PRICE", "QTY"};
            printTable(headers, filteredItemList);
        }
    }

    public static void rankByUnitPricePage(){
        getTopBarView("RANK BY UNIT PRICE");
        rankByUnitPrice();
    }

    public static void rankByUnitPrice(){

        for (int i = 0; i < itemList.length - 1; i++) {
            for (int j = i + 1; j < itemList.length; j++) {
                float price1 = Float.parseFloat(itemList[i][4]);
                float price2 = Float.parseFloat(itemList[j][4]);
                if (price1 > price2) {

                    String[] temp = itemList[i];
                    itemList[i] = itemList[j];
                    itemList[j] = temp;
                }
            }
        }

        String [] headers ={"ITEM CODE", "SUPPLIER ID", "CATEGORY", "DESCRIPTION", "PRICE", "QTY"};
        printTable(headers, itemList);
    }

    public static String[][] filterItem(String[][] list, String id,int number) {
        int count = 0;
        for (String[] supplier : list) {
            if (supplier[number]!=null && supplier[number].equalsIgnoreCase(id)) {
                count++;
            }
        }

        String[][] filteredList = new String[count][2];
        int index = 0;
        for (String[] supplier : list) {
            if (supplier[number]!=null && supplier[number].equalsIgnoreCase(id)) {
                filteredList[index++] = supplier;
            }
        }

        return filteredList;
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
}