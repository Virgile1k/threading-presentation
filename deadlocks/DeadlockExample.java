package deadlocks;

public class DeadlockExample {
    private static final Object RESOURCE_1 = new Object();
    private static final Object RESOURCE_2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (RESOURCE_1) {
                System.out.println("Thread 1: Holding Resource 1...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}

                System.out.println("Thread 1: Waiting for Resource 2...");
                synchronized (RESOURCE_2) {
                    System.out.println("Thread 1: Holding both resources");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (RESOURCE_2) {
                System.out.println("Thread 2: Holding Resource 2...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}

                System.out.println("Thread 2: Waiting for Resource 1...");
                synchronized (RESOURCE_1) {
                    System.out.println("Thread 2: Holding both resources");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}


