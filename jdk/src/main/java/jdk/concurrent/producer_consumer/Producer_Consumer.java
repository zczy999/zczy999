package jdk.concurrent.producer_consumer;

public interface Producer_Consumer {

    void producer() throws InterruptedException;

    void consumer() throws InterruptedException;
}
