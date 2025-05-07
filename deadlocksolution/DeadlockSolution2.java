package deadlocksolution; /**
 * SOLUTION 2: USING LOCK TIMEOUTS
 *
 * Use explicit Lock objects with timeout capabilities to avoid indefinite waiting.
 */
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class DeadlockSolution2 {
    private static final Lock LOCK_1 = new ReentrantLock();
    private static final Lock LOCK_2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;

            try {
                // Try to acquire first lock with timeout
                gotFirstLock = LOCK_1.tryLock(100, TimeUnit.MILLISECONDS);
                if (gotFirstLock) {
                    System.out.println("Thread 1: Holding Lock 1...");

                    // Try to acquire second lock with timeout
                    gotSecondLock = LOCK_2.tryLock(100, TimeUnit.MILLISECONDS);
                    if (gotSecondLock) {
                        System.out.println("Thread 1: Holding both locks");
                        // Do work here
                    } else {
                        System.out.println("Thread 1: Could not acquire Lock 2, releasing Lock 1");
                        // Failed to get second lock, will release first and retry later
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Release locks in reverse order of acquisition
                if (gotSecondLock) {
                    LOCK_2.unlock();
                }
                if (gotFirstLock) {
                    LOCK_1.unlock();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;

            try {
                // Try to acquire locks with timeout
                gotFirstLock = LOCK_2.tryLock(100, TimeUnit.MILLISECONDS);
                if (gotFirstLock) {
                    System.out.println("Thread 2: Holding Lock 2...");

                    gotSecondLock = LOCK_1.tryLock(100, TimeUnit.MILLISECONDS);
                    if (gotSecondLock) {
                        System.out.println("Thread 2: Holding both locks");
                        // Do work here
                    } else {
                        System.out.println("Thread 2: Could not acquire Lock 1, releasing Lock 2");
                        // Failed to get second lock, will release first and retry later
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Release locks in reverse order of acquisition
                if (gotSecondLock) {
                    LOCK_1.unlock();
                }
                if (gotFirstLock) {
                    LOCK_2.unlock();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
