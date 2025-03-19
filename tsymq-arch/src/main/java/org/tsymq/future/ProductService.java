package org.tsymq.future;

import java.util.HashMap;
import java.util.Map;

/**
 * 小滴课堂,愿景：让技术不再难学
 *
 * @Description
 * @Author 二当家小D
 * @Remark 有问题直接联系我，源码-笔记-技术交流群
 * @Version 1.0
 **/
public class ProductService {


    private static final Map<Integer, String> map = new HashMap<>();

    static {
        map.put(1, "架构大课");
        map.put(2, "海量数据项目大课");
        map.put(3, "高并发架构大课");
        map.put(4, "在线教育平台");
        map.put(5, "小滴课堂永久会员");
        map.put(6, "java入门到放弃");
        map.put(7, "颈椎病康复指南");
    }

    public String getById(int id) {
        try {
            Thread.sleep(2000);
            System.out.println("ProductService#getById方法运行线程：" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return map.get(id);
    }

}
