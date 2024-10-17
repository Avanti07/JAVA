// print even no using 1 thread and odd using another

class EvenOddPrinter {
    private int number = 1; // Start from 1
    private final int limit; // Limit to print

    public EvenOddPrinter(int limit) {
        this.limit = limit;
    }

    public synchronized void printEven() {
        while (number <= limit) {
            if (number % 2 == 0) {
                System.out.println("Even: " + number);
                number++;
                notify(); // Notify the other thread
            } else {
                try {
                    wait(); // Wait for the odd thread to notify
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public synchronized void printOdd() {
        while (number <= limit) {
            if (number % 2 != 0) {
                System.out.println("Odd: " + number);
                number++;
                notify(); // Notify the other thread
            } else {
                try {
                    wait(); // Wait for the even thread to notify
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

public class EvenOddThreadExample {
    public static void main(String[] args) {
        EvenOddPrinter printer = new EvenOddPrinter(20); // Set limit to 20

        Thread evenThread = new Thread(printer::printEven);
        Thread oddThread = new Thread(printer::printOdd);

        evenThread.start();
        oddThread.start();
        
        // Wait for threads to finish
        try {
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}





// Same program using java util concurrent pacakage

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class EvenOddPrinter {
    private int number = 1; // Start from 1
    private final int limit; // Limit to print
    private final Lock lock = new ReentrantLock();
    private final Condition evenCondition = lock.newCondition();
    private final Condition oddCondition = lock.newCondition();

    public EvenOddPrinter(int limit) {
        this.limit = limit;
    }

    public void printEven() {
        lock.lock();
        try {
            while (number <= limit) {
                if (number % 2 == 0) {
                    System.out.println("Even: " + number);
                    number++;
                    oddCondition.signal(); // Notify the odd thread
                } else {
                    evenCondition.await(); // Wait for the odd thread
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void printOdd() {
        lock.lock();
        try {
            while (number <= limit) {
                if (number % 2 != 0) {
                    System.out.println("Odd: " + number);
                    number++;
                    evenCondition.signal(); // Notify the even thread
                } else {
                    oddCondition.await(); // Wait for the even thread
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}

public class EvenOddThreadExample {
    public static void main(String[] args) {
        EvenOddPrinter printer = new EvenOddPrinter(20);

        Thread evenThread = new Thread(printer::printEven);
        Thread oddThread = new Thread(printer::printOdd);

        evenThread.start();
        oddThread.start();

        try {
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

