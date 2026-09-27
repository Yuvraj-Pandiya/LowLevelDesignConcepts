// ============================================================================
// PART 1: SIMPLE FACTORY PATTERN
// ============================================================================
interface Burger {
    void prepare();
}

class BasicBurger implements Burger {
    public void prepare() { System.out.println("Preparing a Basic Burger with standard buns and single patty."); }
}

class StandardBurger implements Burger {
    public void prepare() { System.out.println("Preparing a Standard Burger with cheese and classic sauce."); }
}

class PremiumBurger implements Burger {
    public void prepare() { System.out.println("Preparing a Premium Burger with double patties, bacon, and gourmet cheese."); }
}

// Simple Factory: A single class that decides which concrete class to instantiate based on a type string.
class SimpleBurgerFactory {
    public Burger createBurger(String type) {
        if (type == null) return null;
        if (type.equalsIgnoreCase("BASIC")) return new BasicBurger();
        if (type.equalsIgnoreCase("STANDARD")) return new StandardBurger();
        if (type.equalsIgnoreCase("PREMIUM")) return new PremiumBurger();
        throw new IllegalArgumentException("Unknown burger type: " + type);
    }
}

// ============================================================================
// PART 2: FACTORY METHOD PATTERN
// ============================================================================
// Extending the family of products to support specialized regional menus
class BasicWheatBurger implements Burger {
    public void prepare() { System.out.println("Preparing a Healthy Basic Wheat Burger."); }
}

class StandardWheatBurger implements Burger {
    public void prepare() { System.out.println("Preparing a Healthy Standard Wheat Burger."); }
}

class PremiumWheatBurger implements Burger {
    public void prepare() { System.out.println("Preparing a Healthy Premium Wheat Burger."); }
}

// Factory Method Interface: Defers instantiation to subclasses
interface BurgerFactory {
    Burger createBurger(String type);
}

// Concrete Factory 1: BurgerKing creates standard white bread burgers
class BurgerKingFactory implements BurgerFactory {
    public Burger createBurger(String type) {
        if (type.equalsIgnoreCase("BASIC")) return new BasicBurger();
        if (type.equalsIgnoreCase("STANDARD")) return new StandardBurger();
        if (type.equalsIgnoreCase("PREMIUM")) return new PremiumBurger();
        throw new IllegalArgumentException("BurgerKing does not serve: " + type);
    }
}

// Concrete Factory 2: Tinku's specializes in healthy wheat options
class TinkusFactory implements BurgerFactory {
    public void prepareOrder() { System.out.println("Tinku's is sanitizing the live counter..."); }

    public Burger createBurger(String type) {
        if (type.equalsIgnoreCase("BASIC")) return new BasicWheatBurger();
        if (type.equalsIgnoreCase("STANDARD")) return new StandardWheatBurger();
        if (type.equalsIgnoreCase("PREMIUM")) return new PremiumWheatBurger();
        throw new IllegalArgumentException("Tinku's does not serve: " + type);
    }
}

// ============================================================================
// PART 3: ABSTRACT FACTORY PATTERN (Notification System Example)
// ============================================================================
// Abstract Products
interface AudioNotification {
    void playSound();
}

interface VisualNotification {
    void displayAlert();
}

// Concrete Products for System Platform A (High-Priority/Critical Environment)
class HighPriorityAudio implements AudioNotification {
    public void playSound() { System.out.println("🔊 Playing LOUD SIREN sound!"); }
}
class HighPriorityVisual implements VisualNotification {
    public void displayAlert() { System.out.println("🚨 Displaying FLASHING RED overlay alert!"); }
}

// Concrete Products for System Platform B (Low-Priority/Standard Environment)
class StandardAudio implements AudioNotification {
    public void playSound() { System.out.println("🎵 Playing soft chime melody."); }
}
class StandardVisual implements VisualNotification {
    public void displayAlert() { System.out.println("💬 Displaying standard banner notification."); }
}

// Abstract Factory: Manages families of dependent/related products (Audio + Visual)
interface NotificationFactory {
    AudioNotification createAudioNotification();
    VisualNotification createVisualNotification();
}

// Concrete Families
class CriticalNotificationFactory implements NotificationFactory {
    public AudioNotification createAudioNotification() { return new HighPriorityAudio(); }
    public VisualNotification createVisualNotification() { return new HighPriorityVisual(); }
}

class NormalNotificationFactory implements NotificationFactory {
    public AudioNotification createAudioNotification() { return new StandardAudio(); }
    public VisualNotification createVisualNotification() { return new StandardVisual(); }
}

// ============================================================================
// DEMO EXECUTION CLASS
// ============================================================================
public class FactoryDemo {
    public static void main(String[] args) {
        
        // Why we need object creation classes:
        // Centralizing creation shields the client from the complex details, dependencies, 
        // and string checks of concrete subclasses, keeping application code loosely coupled.

        System.out.println("=== 1. DEMONSTRATING SIMPLE FACTORY ===");
        SimpleBurgerFactory simpleFactory = new SimpleBurgerFactory();
        Burger mySimpleBurger = simpleFactory.createBurger("PREMIUM");
        mySimpleBurger.prepare();

        System.out.println("\n=== 2. DEMONSTRATING FACTORY METHOD ===");
        // The choice of factory can be determined dynamically at runtime
        BurgerFactory bkFactory = new BurgerKingFactory();
        BurgerFactory tinkuFactory = new TinkusFactory();

        System.out.println("Ordering from BurgerKing:");
        Burger bkBurger = bkFactory.createBurger("STANDARD");
        bkBurger.prepare();

        System.out.println("Ordering from Tinku's (Healthy Variant):");
        Burger tinkuBurger = tinkuFactory.createBurger("STANDARD");
        tinkuBurger.prepare();

        System.out.println("\n=== 3. DEMONSTRATING ABSTRACT FACTORY (Notification System) ===");
        // The factory guarantees that audio and visual components match each other perfectly
        NotificationFactory alertFactory;
        
        boolean isEmergencyMode = true; // Simulating dynamic condition
        if (isEmergencyMode) {
            alertFactory = new CriticalNotificationFactory();
        } else {
            alertFactory = new NormalNotificationFactory();
        }

        AudioNotification audio = alertFactory.createAudioNotification();
        VisualNotification visual = alertFactory.createVisualNotification();

        audio.playSound();
        visual.displayAlert();
    }
}
