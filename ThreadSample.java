// print 1 - 10 using thread 1 and 11-20 using another 

class NumberPrinter implements Runnable {
    private final int start;
    private final int end;

    public NumberPrinter(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            System.out.println(i);
        }
    }
}

public class ThreadNumberPrinting {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new NumberPrinter(1, 10));
        Thread thread2 = new Thread(new NumberPrinter(11, 20));

        // Start both threads
        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}



/// using java util concurrent lock

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class NumberPrinter {
    private final int limit1;
    private final int limit2;
    private final Lock lock = new ReentrantLock();
    private final Condition condition1 = lock.newCondition();
    private final Condition condition2 = lock.newCondition();
    private boolean isFirstThreadTurn = true; // To control turn

    public NumberPrinter(int limit1, int limit2) {
        this.limit1 = limit1;
        this.limit2 = limit2;
    }

    public void printFirst() {
        lock.lock();
        try {
            for (int i = 1; i <= limit1; i++) {
                while (!isFirstThreadTurn) {
                    condition1.await(); // Wait for the first thread's turn
                }
                System.out.println(i);
                isFirstThreadTurn = false; // Switch to the second thread
                condition2.signal(); // Notify the second thread
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void printSecond() {
        lock.lock();
        try {
            for (int i = limit1 + 1; i <= limit1 + limit2; i++) {
                while (isFirstThreadTurn) {
                    condition2.await(); // Wait for the second thread's turn
                }
                System.out.println(i);
                isFirstThreadTurn = true; // Switch to the first thread
                condition1.signal(); // Notify the first thread
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}

public class ThreadNumberPrinting {
    public static void main(String[] args) {
        NumberPrinter printer = new NumberPrinter(10, 10); // Print 1-10 and 11-20

        Thread thread1 = new Thread(printer::printFirst);
        Thread thread2 = new Thread(printer::printSecond);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
