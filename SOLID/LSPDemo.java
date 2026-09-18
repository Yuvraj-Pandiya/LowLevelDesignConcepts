import java.util.ArrayList;
import java.util.List;

public class LSPDemo {

    // Problem: Fixed Deposit violates contract by throwing exception on withdraw()
    interface BrokenAccount {
        void deposit(double amount);
        void withdraw(double amount);
    }

    static class BrokenSavingsAccount implements BrokenAccount {
        private double balance;
        public void deposit(double amount) { balance += amount; }
        public void withdraw(double amount) { balance -= amount; }
    }

    static class BrokenFixedDepositAccount implements BrokenAccount {
        private double balance;
        public void deposit(double amount) { balance += amount; }
        
        // Violates LSP: Subclass cannot fulfill parent's withdraw contract
        public void withdraw(double amount) {
            throw new UnsupportedOperationException("Withdrawals are not allowed for Fixed Deposit accounts!");
        }
    }

    static class BrokenClient {
        // Needs instance checking to avoid crashes
        public static void processAccounts(List<BrokenAccount> accounts) {
            System.out.println("--- Processing Broken Accounts ---");
            for (BrokenAccount acc : accounts) {
                acc.deposit(100);
                
                // Workaround checking subtype directly
                if (acc instanceof BrokenFixedDepositAccount) {
                    System.out.println("Skipping withdrawal for FD to avoid crash.");
                } else {
                    acc.withdraw(50);
                    System.out.println("Withdrawal successful.");
                }
            }
        }
    }

    // Solution: Segregate deposit and withdraw capabilities into proper hierarchy

    interface DepositAccount {
        void deposit(double amount);
        double getBalance();
    }

    interface WithdrawableAccount extends DepositAccount {
        void withdraw(double amount);
    }

    static class SavingsAccount implements WithdrawableAccount {
        private double balance;
        public void deposit(double amount) { balance += amount; }
        public void withdraw(double amount) { balance -= amount; }
        public double getBalance() { return balance; }
    }

    static class CurrentAccount implements WithdrawableAccount {
        private double balance;
        public void deposit(double amount) { balance += amount; }
        public void withdraw(double amount) { balance -= amount; }
        public double getBalance() { return balance; }
    }

    // Fixed deposit only implements DepositAccount
    static class FixedDepositAccount implements DepositAccount {
        private double balance;
        public void deposit(double amount) { balance += amount; }
        public double getBalance() { return balance; }
    }

    static class GoodClient {
        public static void depositToAll(List<DepositAccount> accounts, double amount) {
            System.out.println("\n--- Depositing to All Accounts (LSP Compliant) ---");
            for (DepositAccount acc : accounts) {
                acc.deposit(amount);
                System.out.println("Deposited " + amount + ". New Balance: " + acc.getBalance());
            }
        }

        public static void withdrawFromValidAccounts(List<WithdrawableAccount> accounts, double amount) {
            System.out.println("\n--- Withdrawing from Withdrawable Accounts Only ---");
            for (WithdrawableAccount acc : accounts) {
                acc.withdraw(amount);
                System.out.println("Withdrew " + amount + ". New Balance: " + acc.getBalance());
            }
        }
    }

    public static void main(String[] args) {
        
        // 1. Broken implementation
        List<BrokenAccount> brokenList = new ArrayList<>();
        brokenList.add(new BrokenSavingsAccount());
        brokenList.add(new BrokenFixedDepositAccount());
        BrokenClient.processAccounts(brokenList);

        // 2. LSP compliant implementation
        List<DepositAccount> allAccounts = new ArrayList<>();
        SavingsAccount savings = new SavingsAccount();
        CurrentAccount current = new CurrentAccount();
        FixedDepositAccount fd = new FixedDepositAccount();

        allAccounts.add(savings);
        allAccounts.add(current);
        allAccounts.add(fd);

        GoodClient.depositToAll(allAccounts, 500);

        List<WithdrawableAccount> withdrawableList = new ArrayList<>();
        withdrawableList.add(savings);
        withdrawableList.add(current);
        // withdrawableList.add(fd); // Compile time error if added

        GoodClient.withdrawFromValidAccounts(withdrawableList, 200);
    }
}
