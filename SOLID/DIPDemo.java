import java.util.ArrayList;
import java.util.List;

public class DIPDemo {

    // Low level module: MySQL
    static class MySqlDatabase {
        public void saveToSql(String data) {
            System.out.println("Saving '" + data + "' securely inside MySQL Database...");
        }
    }

    // Low level module: MongoDB
    static class MongoDatabase {
        public void saveToMongo(String data) {
            System.out.println("Saving '" + data + "' as a document inside MongoDB...");
        }
    }

    // High level module tightly coupled to concrete classes
    static class BrokenApplication {
        private MySqlDatabase sqlDb = new MySqlDatabase();
        private MongoDatabase mongoDb = new MongoDatabase();

        public void saveData(String data, String dbType) {
            System.out.println("--- BrokenApp Processing Data ---");
            if (dbType.equalsIgnoreCase("SQL")) {
                sqlDb.saveToSql(data);
            } else if (dbType.equalsIgnoreCase("MONGO")) {
                mongoDb.saveToMongo(data);
            }
        }
    }

    // Abstraction interface
    interface PersistenceMechanism {
        void save(String data);
    }

    // MySQL persistence
    static class MySqlPersistence implements PersistenceMechanism {
        @Override
        public void save(String data) {
            System.out.println("Saving '" + data + "' securely inside MySQL Database...");
        }
    }

    // MongoDB persistence
    static class MongoPersistence implements PersistenceMechanism {
        @Override
        public void save(String data) {
            System.out.println("Saving '" + data + "' as a document inside MongoDB...");
        }
    }

    // Cassandra persistence
    static class CassandraPersistence implements PersistenceMechanism {
        @Override
        public void save(String data) {
            System.out.println("Saving '" + data + "' dynamically across clusters in Cassandra DB...");
        }
    }

    // High level module depends on abstraction via constructor injection
    static class GoodApplication {
        private final PersistenceMechanism persistence;

        public GoodApplication(PersistenceMechanism persistence) {
            this.persistence = persistence;
        }

        public void saveData(String data) {
            persistence.save(data);
        }
    }

    public static void main(String[] args) {
        
        // Direct coupling
        BrokenApplication brokenApp = new BrokenApplication();
        brokenApp.saveData("User profile info", "SQL");
        brokenApp.saveData("Sensor metrics data", "MONGO");

        System.out.println("\n--- Switching to DIP Compliant System ---");

        // Decoupled with DIP
        PersistenceMechanism mysql = new MySqlPersistence();
        GoodApplication appWithSql = new GoodApplication(mysql);
        appWithSql.saveData("User profile info");

        PersistenceMechanism mongo = new MongoPersistence();
        GoodApplication appWithMongo = new GoodApplication(mongo);
        appWithMongo.saveData("Sensor metrics data");

        PersistenceMechanism cassandra = new CassandraPersistence();
        GoodApplication appWithCassandra = new GoodApplication(cassandra);
        appWithCassandra.saveData("Big data analytics record");
    }
}
