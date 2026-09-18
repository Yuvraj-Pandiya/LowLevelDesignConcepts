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

class OCPShoppingCart {
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

// Storage interface to allow open extension
interface DataPersistable {
    void save(OCPShoppingCart cart);
}

// SQL storage implementation
class SqlStorage implements DataPersistable {
    @Override
    public void save(OCPShoppingCart cart) {
        System.out.println("\n[SQL Database] Connecting to relational instance...");
        for (Product item : cart.getItems()) {
            System.out.println("[SQL] INSERT INTO cart_items VALUES ('" + item.getName() + "', " + item.getPrice() + ");");
        }
        System.out.println("[SQL Database] Transaction committed successfully.");
    }
}

// MongoDB storage implementation
class MongoDbStorage implements DataPersistable {
    @Override
    public void save(OCPShoppingCart cart) {
        System.out.println("\n[MongoDB Cluster] Connecting to NoSQL collection...");
        for (Product item : cart.getItems()) {
            System.out.println("[MongoDB] db.cart.insertOne({ name: \"" + item.getName() + "\", price: " + item.getPrice() + " });");
        }
        System.out.println("[MongoDB Cluster] Document block acknowledgment received.");
    }
}

// File storage implementation
class FileStorage implements DataPersistable {
    @Override
    public void save(OCPShoppingCart cart) {
        System.out.println("\n[Local System] Opening raw file stream append-mode...");
        for (Product item : cart.getItems()) {
            System.out.println("[File Engine] Appending line: " + item.getName() + "," + item.getPrice());
        }
        System.out.println("[Local System] File buffer flushed and closed.");
    }
}

// Storage manager depending on abstraction, closed for modification
class CartDBStorage {
    private DataPersistable storageMechanism;

    public CartDBStorage(DataPersistable storageMechanism) {
        this.storageMechanism = storageMechanism;
    }

    public void executeSave(OCPShoppingCart cart) {
        storageMechanism.save(cart);
    }
}

public class OCPDemo {
    public static void main(String[] args) {
        Product laptop = new Product("MacBook Pro", 150000);
        Product mouse = new Product("MX Master Mouse", 9500);

        OCPShoppingCart cart = new OCPShoppingCart();
        cart.addProduct(laptop);
        cart.addProduct(mouse);

        System.out.println("=== Running OCP Compliant Architecture ===");

        // SQL storage
        CartDBStorage sqlStorageManager = new CartDBStorage(new SqlStorage());
        sqlStorageManager.executeSave(cart);

        // MongoDB storage
        CartDBStorage mongoStorageManager = new CartDBStorage(new MongoDbStorage());
        mongoStorageManager.executeSave(cart);

        // Local file storage
        CartDBStorage fileStorageManager = new CartDBStorage(new FileStorage());
        fileStorageManager.executeSave(cart);
    }
}
