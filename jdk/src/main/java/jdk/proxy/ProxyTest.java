package jdk.proxy;

/**
 * 小滴课堂,愿景：让技术不再难学
 *
 * @Description
 * @Author 二当家小D
 * @Remark 有问题直接联系我，源码-笔记-技术交流群
 * @Version 1.0
 **/
public class ProxyTest {

    public static void main(String[] args) {

        PayService payService = new PayServiceImpl();

//        openTransaction();
//        payService.save(12,323);
//        commitTransaction();
//
//        openTransaction();
//        payService.callback("oosdfdsfds");
//        commitTransaction();


//        StaticProxyPayServiceImpl proxyPayService = new StaticProxyPayServiceImpl(payService);
//        proxyPayService.save(12,442);
//        proxyPayService.callback("3r23r23");


        //创建jdk动态代理
        JdkProxy jdkProxy = new JdkProxy();
        PayService proxyInstance = (PayService)jdkProxy.getProxyInstance(payService);

//        proxyInstance.save(123,323);
        proxyInstance.cancel(2323);
//        proxyInstance.callback("23423423");
        proxyInstance.test();



//        System.out.println(JdkProxy.class.getClassLoader().getName());
//
//        System.out.println(JdkProxy.class.getClassLoader().getParent().getName());
//
//        System.out.println(JdkProxy.class.getClassLoader().getParent().getParent());




    }
}
