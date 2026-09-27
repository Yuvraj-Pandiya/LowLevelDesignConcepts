// === 1. STRATEGY INTERFACES ===
interface Talkable {
    void talk();
}

interface Walkable {
    void walk();
}

interface Flyable {
    void fly();
}

// === 2. CONCRETE STRATEGY IMPLEMENTATIONS ===
class NormalTalk implements Talkable {
    public void talk() { System.out.println("Talking normally like a human."); }
}

class NoTalk implements Talkable {
    public void talk() { System.out.println("... (Cannot talk)"); }
}

class NormalWalk implements Walkable {
    public void walk() { System.out.println("Walking on two legs."); }
}

class NoWalk implements Walkable {
    public void walk() { System.out.println("... (Cannot walk/Immobile)"); }
}

class NormalFly implements Flyable {
    public void fly() { System.out.println("Flying high with jetpacks."); }
}

class NoFly implements Flyable {
    public void fly() { System.out.println("... (Cannot fly/Grounded)"); }
}

// === 3. CLIENT CLASS (Uses Composition & Delegation) ===
abstract class Robot {
    // Has-A relationships (Composition over Inheritance)
    protected Talkable talkBehavior;
    protected Walkable walkBehavior;
    protected Flyable flyBehavior;

    public Robot(Talkable talkBehavior, Walkable walkBehavior, Flyable flyBehavior) {
        this.talkBehavior = talkBehavior;
        this.walkBehavior = walkBehavior;
        this.flyBehavior = flyBehavior;
    }

    // Dynamic Delegation to Strategy Objects
    public void performTalk() { talkBehavior.talk(); }
    public void performWalk() { walkBehavior.walk(); }
    public void performFly() { flyBehavior.fly(); }

    // Mutators to change algorithms at RUN TIME
    public void setTalkBehavior(Talkable tb) { this.talkBehavior = tb; }
    public void setWalkBehavior(Walkable wb) { this.walkBehavior = wb; }
    public void setFlyBehavior(Flyable fb) { this.flyBehavior = fb; }

    // Constant behavior across all robots
    public void projection() {
        System.out.println("Projecting a holographic display... (Same for all robots)");
    }
}

// === 4. CONCRETE CLIENT EXTENSION ===
class CompanionRobot extends Robot {
    public CompanionRobot(Talkable talkBehavior, Walkable walkBehavior, Flyable flyBehavior) {
        super(talkBehavior, walkBehavior, flyBehavior);
    }
}

// === 5. DEMO EXECUTION CLASS ===
public class StrategyDemo {
    public static void main(String[] args) {
        System.out.println("--- Creating a Companion Robot with Ground & Speech Capabilities ---");
        // Injecting initial behaviors via constructor
        Robot wallE = new CompanionRobot(new NormalTalk(), new NormalWalk(), new NoFly());
        
        wallE.projection(); // Standard method
        wallE.performTalk(); // Delegated behavior
        wallE.performWalk(); // Delegated behavior
        wallE.performFly();  // Delegated behavior

        System.out.println("\n--- Upgrading Robot at RUN TIME (Adding Flying, Disabling Talk) ---");
        // Dynamically changing algorithms at runtime using setters
        wallE.setFlyBehavior(new NormalFly());
        wallE.setTalkBehavior(new NoTalk());

        wallE.performTalk();
        wallE.performFly();
    }
}
 