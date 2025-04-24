package jdk.concurrent.producer_consumer;

public class Consumer implements Runnable {

    private Producer_Consumer producer_consumer;

    public Consumer(Producer_Consumer producer_consumer) {
        this.producer_consumer = producer_consumer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                producer_consumer.consumer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
