package threadunsafebankaccount;

/**
 * UnsafeBankAccount demonstration - shows thread safety issues
 * for learning purposes
 */
public class UnsafeBankAccount {
    private double balance;

    public UnsafeBankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        System.out.println(Thread.currentThread().getName() + " depositing $" + amount);
        double newBalance = balance + amount;

        // Simulate some processing time
        try { Thread.sleep(50); } catch (InterruptedException e) { }

        balance = newBalance;
        System.out.println(Thread.currentThread().getName() +
                " completed deposit. New balance: $" + balance);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            System.out.println(Thread.currentThread().getName() + " withdrawing $" + amount);
            double newBalance = balance - amount;

            // Simulate some processing time
            try { Thread.sleep(50); } catch (InterruptedException e) { }

            balance = newBalance;
            System.out.println(Thread.currentThread().getName() +
                    " completed withdrawal. New balance: $" + balance);
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " cannot withdraw $" + amount + ". Insufficient funds.");
        }
    }

    public double getBalance() {
        return balance;
    }
}