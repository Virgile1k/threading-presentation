package basicthreadprogramexample; /**
 * BasicThreadingDemo demonstrates three different approaches to thread safety:
 * 1. Unsafe counter (with race condition)
 * 2. Thread-safe counter using synchronized
 * 3. Thread-safe counter using ReentrantLock
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BasicThreadingDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Basic Thread Creation ---");
        demoBasicThreads();

        System.out.println("\n--- Unsafe Counter Demo (Race Condition) ---");
        demoUnsafeCounter();

        System.out.println("\n--- Synchronized Counter Demo ---");
        demoSynchronizedCounter();

        System.out.println("\n--- Lock-based Counter Demo ---");
        demoLockCounter();
    }

    /**
     * Demo of basic thread creation using both Thread subclass and Runnable interface
     */
    private static void demoBasicThreads() throws InterruptedException {
        // Method 1: Creating thread by extending Thread class
        Thread t1 = new MyThread("Thread-Subclass");

        // Method 2: Creating thread using Runnable interface
        Thread t2 = new Thread(new MyRunnable("Runnable-Thread"), "Runnable-Thread");

        // Start both threads
        t1.start();
        t2.start();

        // Wait for both threads to finish
        t1.join();
        t2.join();
    }

    /**
     * Demo showing race condition with unsafe counter
     */
    private static void demoUnsafeCounter() throws InterruptedException {
        UnsafeCounter unsafeCounter = new UnsafeCounter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                unsafeCounter.increment();
            }
        }, "Unsafe-Thread-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                unsafeCounter.increment();
            }
        }, "Unsafe-Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Unsafe Counter final value: " + unsafeCounter.getCount());
        System.out.println("Expected value: 20000 (but likely less due to race condition)");
    }

    /**
     * Demo using synchronized method for thread safety
     */
    private static void demoSynchronizedCounter() throws InterruptedException {
        SynchronizedCounter syncCounter = new SynchronizedCounter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                syncCounter.increment();
            }
        }, "Sync-Thread-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                syncCounter.increment();
            }
        }, "Sync-Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Synchronized Counter final value: " + syncCounter.getCount());
        System.out.println("Expected value: 20000 (always correct due to synchronization)");
    }

    /**
     * Demo using ReentrantLock for thread safety
     */
    private static void demoLockCounter() throws InterruptedException {
        LockCounter lockCounter = new LockCounter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                lockCounter.increment();
            }
        }, "Lock-Thread-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                lockCounter.increment();
            }
        }, "Lock-Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Lock-based Counter final value: " + lockCounter.getCount());
        System.out.println("Expected value: 20000 (always correct due to lock protection)");
    }
}

/**
 * Custom Thread class that extends Thread
 */
class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(getName() + " is running");
        for (int i = 1; i <= 3; i++) {
            System.out.println(getName() + ": Count - " + i);
            try {
                Thread.sleep(200);  // Pause for 200 milliseconds
            } catch (InterruptedException e) {
                System.out.println(getName() + " interrupted.");
            }
        }
        System.out.println(getName() + " finished.");
    }
}

/**
 * Custom Runnable implementation
 */
class MyRunnable implements Runnable {
    private String threadName;

    public MyRunnable(String name) {
        this.threadName = name;
    }

    @Override
    public void run() {
        System.out.println(threadName + " is running");
        for (int i = 1; i <= 3; i++) {
            System.out.println(threadName + ": Count - " + i);
            try {
                Thread.sleep(200);  // Pause for 200 milliseconds
            } catch (InterruptedException e) {
                System.out.println(threadName + " interrupted.");
            }
        }
        System.out.println(threadName + " finished.");
    }
}

/**
 * Unsafe counter that doesn't handle concurrent access correctly
 * Will exhibit race conditions when accessed by multiple threads
 */
class UnsafeCounter {
    private int count = 0;

    // No synchronization - will cause race conditions
    public void increment() {
        count++;  // This is not atomic!
    }

    public int getCount() {
        return count;
    }
}

/**
 * Thread-safe counter using synchronized method
 */
class SynchronizedCounter {
    private int count = 0;

    // Synchronized method prevents race conditions
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

/**
 * Thread-safe counter using ReentrantLock
 */
class LockCounter {
    private int count = 0;
    private Lock lock = new ReentrantLock();  // Explicit Lock

    public void increment() {
        lock.lock();  // Acquire the lock before accessing shared resource
        try {
            count++;
        } finally {
            lock.unlock();  // Release the lock in finally block (ALWAYS important)
        }
    }

    public int getCount() {
        return count;
    }
}