package threadunsafebankaccount;

/**
 * Demo class to demonstrate the thread safety issues
 * with UnsafeBankAccount
 */
public class UnsafeBankDemo {
    public static void main(String[] args) throws InterruptedException {
        // Create an account with initial balance of $1000
        UnsafeBankAccount account = new UnsafeBankAccount(1000);

        // Create multiple threads that will deposit and withdraw simultaneously
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(100);
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(100);
            }
        }, "Thread-2");

        // Start the threads
        t1.start();
        t2.start();

        // Wait for both threads to complete
        t1.join();
        t2.join();

        // Print final balance
        System.out.println("\nFinal balance: $" + account.getBalance());
        System.out.println("Expected balance should be $1000 (starting with $1000, " +
                "depositing $500, withdrawing $500)");
        System.out.println("If the final balance is not $1000, it demonstrates the thread safety issue!");
    }
}