package jdk.proxy;

/**
 * 小滴课堂,愿景：让技术不再难学
 *
 * @Description
 * @Author 二当家小D
 * @Remark 有问题直接联系我，源码-笔记-技术交流群
 * @Version 1.0
 **/
public class PayServiceImpl  implements PayService{

    @Override
    public String callback(String outTradeNo) {
        System.out.println("回调方法运行，更新订单状态");
        return outTradeNo;
    }


    @Override
    public int save(int userId, int productId) {
        System.out.println("新增订单成功");
        return productId;
    }

    @Override
    @Test
    public Object cancel(int userId) {
        test();
        System.out.println("取消订单成功");
        return null;
    }

    @Override
    @Test
    public int test(){
        System.out.println("test");
        return 0;
    }
}
