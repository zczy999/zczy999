package jdk.concurrent.producer_consumer;

/**
 * https://jyywiki.cn/OS/2025/lect15.md
 * 生产 = 打印左括号 (push into buffer)
 * 消费 = 打印右括号 (pop from buffer)
 * 在 printf 前后增加代码，使得打印的括号序列满足
 * 不能输出错误的括号序列
 * 括号嵌套的深度不超过 n
 * <p>
 * n=3, ((())())((( ✅
 * n=3, (((()))), (())) ❌
 */
public class Producer_Consumer_1 implements Producer_Consumer{


    /**
     * synchronized wait实现
     * @param args
     */
    public static void main(String[] args) {
        int n = 3;
        int threadNum = 5;
        Producer_Consumer_1 producer_consumer1 = new Producer_Consumer_1(n);
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Producer(producer_consumer1)).start();
        }
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Consumer(producer_consumer1)).start();
        }


    }

    private Object lock = new Object();
    private int MAX_SIZE;
    private int count;

    public Producer_Consumer_1(int MAX_SIZE) {
        this.MAX_SIZE = MAX_SIZE;
        this.count = 0;
    }

    @Override
    public void producer() throws InterruptedException {
        synchronized (lock) {
            while (count >= MAX_SIZE) {
                lock.wait();
            }
            System.out.print("(");
            count++;
            lock.notifyAll();
        }
    }

    @Override
    public void consumer() throws InterruptedException {
        synchronized (lock) {
            while (count <= 0) {
                lock.wait();
            }
            System.out.print(")");
            count--;
            lock.notifyAll();
        }
    }


}
