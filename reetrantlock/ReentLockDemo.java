package reetrantlock;

public class ReentLockDemo {
    public static void main(String[] args) {
        // Create a bank account with initial balance of $1000
        LockBankAccount account = new LockBankAccount(1000.0);

        // Create multiple deposit threads
        Thread depositThread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(100.0);
                try { Thread.sleep(10); } catch (InterruptedException e) { }
            }
        }, "Deposit-Thread-1");

        Thread depositThread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(50.0);
                try { Thread.sleep(10); } catch (InterruptedException e) { }
            }
        }, "Deposit-Thread-2");

        // Create multiple withdrawal threads
        Thread withdrawThread1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                account.withdraw(200.0);
                try { Thread.sleep(10); } catch (InterruptedException e) { }
            }
        }, "Withdraw-Thread-1");

        Thread withdrawThread2 = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                account.withdraw(300.0);
                try { Thread.sleep(10); } catch (InterruptedException e) { }
            }
        }, "Withdraw-Thread-2");

        // Start all threads
        depositThread1.start();
        depositThread2.start();
        withdrawThread1.start();
        withdrawThread2.start();

        // Wait for all threads to complete
        try {
            depositThread1.join();
            depositThread2.join();
            withdrawThread1.join();
            withdrawThread2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }

        // Display final balance
        System.out.println("\nTransactions completed!");
        System.out.println("Final account balance: $" + account.getBalance());
    }
}