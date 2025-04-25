package jdk.concurrent.producer_consumer;

import java.util.concurrent.Semaphore;

public class Producer_Consumer_Semaphore implements Producer_Consumer {

    public static void main(String[] args) {
        int threadNum = 2;
        Producer_Consumer_Semaphore producer_consumer = new Producer_Consumer_Semaphore();
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Producer(producer_consumer)).start();
        }
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Consumer(producer_consumer)).start();
        }

    }

    private Semaphore emptySlots = new Semaphore(3); // Slots available for '(' (max depth 3)
    private Semaphore fullSlots = new Semaphore(0);

    @Override
    public void producer() throws InterruptedException {
        emptySlots.acquire();        // Wait if depth would exceed 3
        System.out.print("(");       // Print left bracket
        fullSlots.release();         // Signal an unmatched '(' is available
    }

    @Override
    public void consumer() throws InterruptedException {
        fullSlots.acquire();         // Wait if no unmatched '(' exists
        System.out.print(")");       // Print right bracket
        emptySlots.release();        // Signal a slot is available for '('
    }
}
