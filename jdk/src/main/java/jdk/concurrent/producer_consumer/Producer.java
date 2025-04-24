package jdk.concurrent.producer_consumer;

public class Producer implements Runnable {

    private Producer_Consumer producer_consumer;

    public Producer(Producer_Consumer producer_consumer) {
        this.producer_consumer = producer_consumer;
    }

    @Override
    public void run() {
        while (true){
            try {
                producer_consumer.producer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
