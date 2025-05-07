package reetrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockBankAccount {
    private double balance;
    private final Lock lock = new ReentrantLock();

    public LockBankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        lock.lock();  // Acquire lock
        try {
            System.out.println(Thread.currentThread().getName() + " depositing $" + amount);
            double newBalance = balance + amount;

            // Simulate some processing time
            try { Thread.sleep(50); } catch (InterruptedException e) { }

            balance = newBalance;
            System.out.println(Thread.currentThread().getName() +
                    " completed deposit. New balance: $" + balance);
        } finally {
            lock.unlock();  // ALWAYS release in finally block
        }
    }

    public void withdraw(double amount) {
        lock.lock();  // Acquire lock
        try {
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
        } finally {
            lock.unlock();  // ALWAYS release in finally block
        }
    }

    public double getBalance() {
        return balance;
    }
}