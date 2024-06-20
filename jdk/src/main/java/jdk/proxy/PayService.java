package jdk.proxy;

public interface PayService {

    String callback(String outTradeNo);


    int save(int userId, int productId);


    Object cancel(int userId);

    int test();
}
