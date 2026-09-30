import java.util.*;

/*
 * Task 1 - Java
 * Manage retail products in memory: accept product ID, name, price, quantity.
 * Calculate purchase amount, show Out of Stock / Low Stock, reject over-purchase.
 */
public class RetailStockManager {

    // Simple in-memory "database" using a HashMap
    static Map<String, Product> products = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== RETAIL STOCK MANAGEMENT =====");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Purchase Product");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewProducts();
                case 3 -> purchaseProduct();
                case 4 -> System.out.println("Exiting... Thank you!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 4);
    }

    static void addProduct() {
        System.out.print("Enter Product ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("Enter Quantity: ");
        int qty = Integer.parseInt(sc.nextLine());

        Product p = new Product(id, name, price, qty);
        products.put(id, p);
        System.out.println("Product added successfully.");
    }

    static void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.printf("%-10s %-15s %-10s %-10s %-15s%n", "ID", "Name", "Price", "Qty", "Status");
        for (Product p : products.values()) {
            System.out.printf("%-10s %-15s %-10.2f %-10d %-15s%n",
                    p.id, p.name, p.price, p.quantity, p.getStatus());
        }
    }

    static void purchaseProduct() {
        System.out.print("Enter Product ID to purchase: ");
        String id = sc.nextLine();
        Product p = products.get(id);

        if (p == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter quantity to purchase: ");
        int qty = Integer.parseInt(sc.nextLine());

        if (qty > p.quantity) {
            System.out.println("Purchase rejected: requested quantity exceeds available stock ("
                    + p.quantity + " available).");
            return;
        }

        double amount = qty * p.price;
        p.quantity -= qty;

        System.out.println("Purchase successful!");
        System.out.printf("Amount to pay: %.2f%n", amount);
        System.out.println("Remaining stock: " + p.quantity + " (" + p.getStatus() + ")");
    }
}

class Product {
    String id;
    String name;
    double price;
    int quantity;

    Product(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Business rule from the task: Out of Stock if 0, Low Stock if < 10
    String getStatus() {
        if (quantity == 0) return "Out of Stock";
        else if (quantity < 10) return "Low Stock";
        else return "In Stock";
    }
}
