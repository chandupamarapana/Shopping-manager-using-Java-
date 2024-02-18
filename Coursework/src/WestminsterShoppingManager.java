import javax.swing.*;
import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private List<Product> productList;

    public void startApplication() { //start application method
        System.out.println("**************************************");
        System.out.println("*  Welcome to Westminster Shopping   *");
        System.out.println("*               Center               *");
        System.out.println("**************************************");
        int userType = askUserType();
        if (userType == 1) {
            System.out.println("The GUI for the Customer is loaded. ");
            // Customer
            SwingUtilities.invokeLater(() -> new GUIWestminsterShopping());
        } else if (userType == 2) {
            // Manager
            managerInterface();
        } else {
            System.out.println("Invalid user type. Exiting the program.");
        }
    }

    private int askUserType() {   //Asking the user to select between customer and manager method
        Scanner scanner = new Scanner(System.in);
        int userType = 0;

        while (true) {
            System.out.println("Enter 1 if you are a customer, or 2 if you are a manager:");

            // Check if the input is an integer
            if (scanner.hasNextInt()) {
                userType = scanner.nextInt();

                // Check if the input is 1 or 2
                if (userType == 1 || userType == 2) {
                    break; // Valid input, exit the loop
                } else {
                    System.out.println("Invalid input. Enter 1 for customer, or 2 for manager:");
                }
            } else {
                System.out.println("Invalid input. Enter 1 for customer, or 2 for manager:");
                scanner.next(); // Consume the invalid input
            }
        }

        return userType;
    }



    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        return productList;
    }

    // This is the menu console that is shown to the manager
    public boolean managerInterface() { 
        boolean exit = false;
        do {
            System.out.println("""
                    ____________________________Westminster Shopping Center - Manager Interface______________________________________________________________________
                    Enter 1 to add a product to the System.
                    Enter 2 to Delete a product from the System.
                    Enter 3 to print product list.
                    Enter 4 to save The products to a text file.  
                    Enter 5 to Load the products. 
                    Enter 0 to exit the program. 
                    """);
            Scanner input = new Scanner(System.in);

            System.out.print("Your Input: ");
            while (!input.hasNextInt()){
                System.out.println("Invalid input. please enter a valid number.");
                input.next();
            }

            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    addProductToSystem();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    //print the product list
                    printProductList();
                    break;
                case 4:
                    //save to a text file
                    saveProductsToFile("coursework.txt");
                    break;
                case 5:
                    //load from the text file
                    loadProductsFromFile();
                    break;
                case 0:
                    System.out.println("you have exited the program. ");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. please choose a valid option.");
            }

        }
        while (!exit);

        return exit;
    }

    public void addProductToSystem(){
        System.out.println("""
                            Press 1 if you want to add an Electronics item.
                            Press 2 if you want to add a clothing item.
                            """);
        System.out.print("Your Input: ");
        Scanner input = new Scanner(System.in);
        String choice1 = input.next();
        input.nextLine();
        while(!choice1.equals("1") && !choice1.equals("2")){
            System.out.print("Enter a valid input. the inputs should be  1 or 2: ");
            choice1 = input.next();
            input.nextLine();
        }
        String productId;
        do {
            System.out.print("Enter the product Id of the product: ");
            productId = input.nextLine();
        }
        while (productIdChecker(productId));   // to check the id if it is unique
        System.out.print("Enter the Name of the Product: ");
        String productName = input.nextLine();

        System.out.print("Enter how many items  you are adding to the system: ");
        while (!input.hasNextInt()){
            System.out.print("Invalid input. please enter a valid number: ");
            input.next();
        }
        int no_of_available_items = input.nextInt();

        System.out.print("Enter the price of the product in LKR: ");
        while (!input.hasNextDouble()) {
            System.out.print("Invalid input. please enter a valid number: ");
            input.next();
        }
        double productPrice = input.nextDouble();



        switch (choice1) {
            case "1":
                // the product added is an electronics item
                System.out.print("Enter the brand of the item: ");
                String brand = input.next();
                System.out.print("Enter the warranty period of the product in years : ");
                while (!input.hasNextInt()){
                    System.out.print("Invalid input. please enter a valid number: ");
                    input.next();
                }
                String warranty = input.next();
                Electronics electronics = new Electronics(productId, productName, no_of_available_items, productPrice, brand, warranty);
                this.addProduct(electronics);
                break;
            case "2":
                //the product added to the system is a clothing item
                System.out.print("Enter the size of the clothing item: ");
                String size = input.next();
                System.out.print("Enter the color of the clothing item: ");
                String color = input.next();
                Clothing clothing = new Clothing(productId, productName, no_of_available_items, productPrice, size, color);
                this.addProduct(clothing);
                break;

        }
    }
    @Override
    public void addProduct(Product product) {
        if (productList.size() < 50) {
            productList.add(product);
            System.out.println("Product " + product.getProductName()+ " added to the System ");
        } else {
            System.out.println("Cannot add more products because the system can have a maximum of 50 products ");
        }
    }

    @Override
    public void deleteProduct() {
        printProductList();
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the product Id to delete: ");
        String productId = input.nextLine();
        int sizeOfProductList = productList.size();
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {   //remove the product according to the respective product id
                String productType = (product instanceof Electronics) ? "Electronics" : "Clothing";
                //checking whether the variable product is an instance of the respective class if it is an instance then product type is assigned the value of the respective class


                product.setNumber_of_available_products(0);


                productList.remove(product);
                System.out.println("The product with the ID: " + product.getProductId() + " and the product name " + product.getProductName() + "  is deleted from the system ");
                System.out.println("The type of the product deleted: " + productType);
                System.out.println("The remaining Number of products :" + productList.size()); //details of the deleted product
                break;
            }

        }
        if(sizeOfProductList == productList.size()) {
            System.out.println("invalid product Id " + productId + " is not found in the system "); //if the method is not exitted then this message will be printed
        }
    }

    public void printProductList() {
        if (productList.isEmpty()) {
            System.out.println("No products in the system.");
            return;
        }
        //sort in the alphabetical order by product Id
        Collections.sort(productList);
        for (Product product : productList) {
            System.out.println("_________ The product list _________________");
            System.out.println("Product ID : " + product.getProductId());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Number of products added to the System: " + product.getNumber_of_available_products());
            System.out.println("Price of the product: " + product.getPrice());

            if (product instanceof Electronics) {  // to get the type of product
                Electronics electronics = (Electronics) product;
                System.out.println("Brand: " + electronics.getBrand());
                System.out.println("Warranty Period in years : " + electronics.getWarranty());
                System.out.println("Type: Electronics");
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                System.out.println("Size: " + clothing.getSize());
                System.out.println("color: " + clothing.getColour());
                System.out.println("Type: Clothing");
            }
            System.out.println();
        }

    }

    public void saveProductsToFile(String fileName) {
        try (BufferedWriter fileWrite = new BufferedWriter(new FileWriter("coursework.txt"))) {
            for (Product product : productList) {
                String productInformation = product.toString();
                fileWrite.write(productInformation);
                fileWrite.newLine();
            }
            System.out.println("products saved to file: " + "coursework.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadProductsFromFile(){
        List<Product> loadedProducts = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("coursework.txt"))){
            String line;
            while ((line = reader.readLine()) != null){
                //Assuming the product has a static method to create a product from a string
                Product product = createProductFromString(line);
                if (product != null){
                    loadedProducts.add(product);
                }
                System.out.println("Product loaded from file: ");
            }
            this.productList = loadedProducts;
        }
        catch (IOException e ){
            e.printStackTrace();
        }
        this.productList = loadedProducts;
    }
    // Method to create a Product object from a string representation
    private Product createProductFromString(String lineByLine) {
        try {
            String[] values = lineByLine.split(", ");   // Split the input string by ", " to extract values

            if (values[0].startsWith("Electronics")) {
                return new Electronics(values[1], values[2], Integer.parseInt(values[3]), Double.parseDouble(values[4]), values[5], values[6]);
            } else {
                return new Clothing(values[1], values[2], Integer.parseInt(values[3]), Double.parseDouble(values[4]), values[5], values[6]);
            }

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error parsing product from string: " + e.getMessage());
            return null;
        }
    }
    public boolean productIdChecker(String productId){ // the method that is used to check if the id is used priviously.
        boolean value = false ;
        for (Product product : productList){
            if (product.getProductId().equals(productId)){
                value = true;
                System.out.println("This product Id is used previously. ");
                break;
            }
            else {
                value = false;
            }
        }
        return value;
    }
    public List<Product> filterProductsByCategory(String category){   // this method is used to filter the table in the GUI
        List<Product> filteredProducts = new ArrayList<>();

        for(Product product: productList){
            if(category.equals("All") || (product instanceof Electronics && category.equals("Electronics")) || (product instanceof Clothing && category.equals("Clothing"))){
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }


    @Override
    public String toString() {
        return "WestminsterShoppingManager{" +
                "productList=" + productList +
                '}';
    }
}


