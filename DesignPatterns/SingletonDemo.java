// 1. BASIC APPROACH (Lazy but NOT Thread-Safe)
class BasicSingleton {
    private static BasicSingleton instance;

    private BasicSingleton() {}

    public static BasicSingleton getInstance() {
        if (instance == null) {
            instance = new BasicSingleton();
        }
        return instance;
    }
}

// 2. EAGER INITIALIZATION APPROACH (Thread-Safe, Loaded Immediately)
class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return instance;
    }
}

// 3. MULTITHREADED APPROACH (Thread-Safe, Double-Checked Locking)
class MultithreadedSingleton {
    private static volatile MultithreadedSingleton instance;

    private MultithreadedSingleton() {}

    public static MultithreadedSingleton getInstance() {
        if (instance == null) {
            synchronized (MultithreadedSingleton.class) {
                if (instance == null) {
                    instance = new MultithreadedSingleton();
                }
            }
        }
        return instance;
    }
}

// 4. BILL PUGH LAZY INITIALIZATION APPROACH (Thread-Safe, Recommended)
class BillPughSingleton {
    private BillPughSingleton() {}

    private static class SingletonHelper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}

// Demo Class to verify they all work
public class SingletonDemo {
    public static void main(String[] args) {
        System.out.println("Basic: " + (BasicSingleton.getInstance() == BasicSingleton.getInstance()));
        System.out.println("Eager: " + (EagerSingleton.getInstance() == EagerSingleton.getInstance()));
        System.out.println("Multithreaded: " + (MultithreadedSingleton.getInstance() == MultithreadedSingleton.getInstance()));
        System.out.println("Bill Pugh: " + (BillPughSingleton.getInstance() == BillPughSingleton.getInstance()));
    }
}
