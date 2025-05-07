package threadsafebankaccount;

class ThreadSafeBankAccount {
    private double balance;

    public ThreadSafeBankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        System.out.println(Thread.currentThread().getName() + " depositing $" + amount);
        double newBalance = balance + amount;

        // Simulate some processing time
        try { Thread.sleep(50); } catch (InterruptedException e) { }

        balance = newBalance;
        System.out.println(Thread.currentThread().getName() +
                " completed deposit. New balance: $" + balance);
    }

    public synchronized void withdraw(double amount) {
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