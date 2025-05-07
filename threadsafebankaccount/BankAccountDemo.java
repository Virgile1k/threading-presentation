package threadsafebankaccount;

public class BankAccountDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadSafeBankAccount account = new ThreadSafeBankAccount(1000);

        Thread depositThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(100);
            }
        }, "DepositThread");

        Thread withdrawThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(150);
            }
        }, "WithdrawThread");

        depositThread.start();
        withdrawThread.start();

        depositThread.join();
        withdrawThread.join();

        System.out.println("Final account balance: $" + account.getBalance());
    }
}