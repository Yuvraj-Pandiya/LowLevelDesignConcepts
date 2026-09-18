import java.util.ArrayList;
import java.util.List;

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
}

// Violation of SRP: ShoppingCart handles business logic, DB persistence, and invoice generation
class ShoppingCart {
    private List<Product> items = new ArrayList<>();

    public void addProduct(Product product) {
        items.add(product);
    }

    public List<Product> getItems() {
        return items;
    }

    // Cart calculation
    public double calculateTotal() {
        double total = 0;
        for (Product item : items) {
            total += item.getPrice();
        }
        return total;
    }

    // Persistence logic
    public void saveToDB() {
        System.out.println("Saving cart items to the database...");
        for (Product item : items) {
            System.out.println("Stored: " + item.getName() + " - Rs. " + item.getPrice());
        }
    }

    // Printing logic
    public void generateInvoice() {
        System.out.println("--- INVOICE ---");
        for (Product item : items) {
            System.out.println(item.getName() + " : Rs. " + item.getPrice());
        }
        System.out.println("Total Amount: Rs. " + calculateTotal());
        System.out.println("----------------");
    }
}

// SRP solution: Separating concerns into dedicated classes

// Only handles cart items and business logic
class SRPShoppingCart {
    private List<Product> items = new ArrayList<>();

    public void addProduct(Product product) {
        items.add(product);
    }

    public List<Product> getItems() {
        return items;
    }

    public double calculateTotal() {
        double total = 0;
        for (Product item : items) {
            total += item.getPrice();
        }
        return total;
    }
}

// Handles DB operations
class CartDBStorage {
    public void saveToDB(SRPShoppingCart cart) {
        System.out.println("[Database] Saving cart data permanently...");
        for (Product item : cart.getItems()) {
            System.out.println("[Database] Saved: " + item.getName());
        }
    }
}

// Handles invoice generation
class CartInvoicePrinter {
    public void generateInvoice(SRPShoppingCart cart) {
        System.out.println("\n--- SRP COMPLIANT INVOICE ---");
        for (Product item : cart.getItems()) {
            System.out.println(item.getName() + " : Rs. " + item.getPrice());
        }
        System.out.println("Total Due: Rs. " + cart.calculateTotal());
        System.out.println("-----------------------------");
    }
}

public class SRPDemo {
    public static void main(String[] args) {
        Product laptop = new Product("Laptop", 75000);
        Product mouse = new Product("Wireless Mouse", 1500);

        System.out.println("=== Running SRP Violation Code ===");
        ShoppingCart badCart = new ShoppingCart();
        badCart.addProduct(laptop);
        badCart.addProduct(mouse);
        
        badCart.saveToDB();
        badCart.generateInvoice();

        System.out.println("\n=== Running SRP Compliant Code ===");
        SRPShoppingCart goodCart = new SRPShoppingCart();
        goodCart.addProduct(laptop);
        goodCart.addProduct(mouse);

        CartDBStorage dbStorage = new CartDBStorage();
        dbStorage.saveToDB(goodCart);

        CartInvoicePrinter invoicePrinter = new CartInvoicePrinter();
        invoicePrinter.generateInvoice(goodCart);
    }
}
