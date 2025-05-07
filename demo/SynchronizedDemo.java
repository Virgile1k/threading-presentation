package demo;

class SynchronizedCounter {
    private int count = 0;

    public synchronized void increment() { // Thread-safe!
        count++;
    }

    public int getCount() {
        return count;
    }
}

public class SynchronizedDemo {
    public static void main(String[] args) throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.increment();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Expected count: 200000");
        System.out.println("Actual count: " + counter.getCount());
        // Now always 200000!
    }
}