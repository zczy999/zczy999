package jdk.concurrent.future;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 小滴课堂,愿景：让技术不再难学
 *
 * @Description
 * @Author 二当家小D
 * @Remark 有问题直接联系我，源码-笔记-技术交流群
 * @Version 1.0
 **/
public class FutureTest {

    public static void main(String[] args) throws Exception {


        System.out.println("begin:"+LocalDateTime.now());
        Map<String, Object> homePage = homePageAggApi();
        System.out.println(homePage.toString());
        System.out.println("end:"+LocalDateTime.now());
    }


    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            16,
            32,
            100,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(1000),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    public static Map<String, Object> homePageAggApi() throws Exception {

        Map<String,Object> homePageInfo = new HashMap<>();

        //模拟不同服务调用
        EduService eduService = new EduService();

        CompletableFuture<Void> bannerFuture = CompletableFuture.runAsync(() -> {
            String banner = eduService.getBanner();
            homePageInfo.put("banner", banner);
        }, executor);

        CompletableFuture<Void> categoryFuture = CompletableFuture.runAsync(() -> {
            String category = eduService.getCategory();
            homePageInfo.put("category", category);
        }, executor);


        CompletableFuture<Void> rankFuture = CompletableFuture.runAsync(() -> {
            String rank = eduService.getRank();
            homePageInfo.put("rank", rank);
        }, executor);


        CompletableFuture<Void> videoCardFuture = CompletableFuture.runAsync(() -> {
            String videoCard = eduService.getVideoCard();
            homePageInfo.put("videoCard", videoCard);
        }, executor);

        CompletableFuture.allOf(bannerFuture,categoryFuture,rankFuture,videoCardFuture).join();
        return homePageInfo;
    }


    public static void testAnyOf() throws Exception {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("future1完成");
            return "future1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("future2完成");
            return "future2";
        });


        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("future3完成");
            return "future3";
        });


        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future1, future2, future3);
        System.out.println("begin=" + LocalDateTime.now());

        //阻塞等待任何一个任务完成
        anyOf.join();
        if (anyOf.isDone()) {
            System.out.println("有任务完成:" + anyOf.get());
        }
        System.out.println("end=" + LocalDateTime.now());


    }


    public static void testAllOf() throws Exception {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("future1完成");
            return "future1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("future2完成");
            return "future2";
        });


        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("future3完成");
            return "future3";
        });


        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);
        System.out.println("begin=" + LocalDateTime.now());

        //阻塞等待全部任务完成
        allOf.join();
        if (allOf.isDone()) {
            System.out.println("全部任务完成");
        }

        System.out.println("end=" + LocalDateTime.now());


    }


    public static void testThenCombineFuture() throws Exception {
        ProductService productService = new ProductService();
        ProductDetailService detailService = new ProductDetailService();
        int id = 1;
        System.out.println("begin=" + LocalDateTime.now());
        //第一个任务
        CompletableFuture<Product> future1 = CompletableFuture.supplyAsync(() -> {
            String title = productService.getById(id);
            Product product = new Product();
            product.setTitle(title);
            product.setId(id);
            return product;
        });

        //第二个任务
        CompletableFuture<Product> future2 = CompletableFuture.supplyAsync(() -> {
            String detail = detailService.getById(id);
            Product product = new Product();
            product.setDetail(detail);
            return product;
        });


        //将上面两个任务合并，返回新的CompletableFuture
        CompletableFuture<Product> combineFuture = future1.thenCombine(future2, new BiFunction<Product, Product, Product>() {
            @Override
            public Product apply(Product product, Product product2) {
                product.setDetail(product2.getDetail());
                return product;
            }
        });

        System.out.println(combineFuture.get());
        System.out.println("end=" + LocalDateTime.now());
    }


    public static void testThenComposeFuture() throws Exception {
        ProductService productService = new ProductService();
        ProductDetailService detailService = new ProductDetailService();
        int id = 1;

        CompletableFuture<Product> thenCompose = CompletableFuture.supplyAsync(() -> {
            String title = productService.getById(id);
            Product product = new Product();
            product.setTitle(title);
            product.setId(id);
            return product;
        }).thenCompose(new Function<Product, CompletionStage<Product>>() {
            @Override
            public CompletionStage<Product> apply(Product product) {
                return CompletableFuture.supplyAsync(() -> {
                    //用到上一步的结果参数
                    String detail = detailService.getById(product.getId());
                    product.setDetail(detail);
                    return product;
                });
            }
        });

        System.out.println(thenCompose.get());
    }

    public static void testEmbedFuture() throws Exception {
        ProductService productService = new ProductService();
        ProductDetailService detailService = new ProductDetailService();
        int id = 1;

        CompletableFuture<CompletableFuture<Product>> future = CompletableFuture.supplyAsync(() -> {
            String title = productService.getById(id);
            Product product = new Product();
            product.setTitle(title);
            product.setId(id);
            return product;
        }).thenApply(new Function<Product, CompletableFuture<Product>>() {
            @Override
            public CompletableFuture<Product> apply(Product product) {
                return CompletableFuture.supplyAsync(() -> {
                    //用到上一步的结果参数
                    String detail = detailService.getById(product.getId());
                    product.setDetail(detail);
                    return product;
                });
            }
        });

        System.out.println(future.get().get());


    }


    //任务编排，无返回值
    public static void testFuture4() throws Exception {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "执行任务一");
            return "小滴课堂大课一";
        });

        CompletableFuture<Void> future2 = future.thenAccept((ele) -> {
            System.out.println("入参数：" + ele);
            System.out.println(Thread.currentThread().getName() + "执行任务二");
        });

        System.out.println("future2:" + future2.get());
    }


    //任务编排，有返回值
    public static void testFuture3() throws Exception {


        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "执行任务一");
            return "小滴课堂大课一";
        });

        CompletableFuture<String> future2 = future.thenApply(new Function<String, String>() {
            @Override
            public String apply(String ele) {
                System.out.println("入参数：" + ele);
                System.out.println(Thread.currentThread().getName() + "执行任务二");

                return ele + ",大课二";
            }
        });

        System.out.println("future2:" + future2.get());
    }


    public static void testFuture2() throws Exception {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "执行");
            return "小滴课堂架构大课";
        });

        System.out.println(Thread.currentThread().getName() + ":" + future.getNow("默认值"));

    }

    public static void testFuture1() throws Exception {
        System.out.println(Thread.currentThread().getName() + " 程序开始执行");
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //定义一个异步任务
        Future<String> future = executorService.submit(() -> {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + " 程序在线程池里面执行");
            return "我要去小滴课堂删库跑路";
        });

        System.out.println(Thread.currentThread().getName() + " 我现在可以去做其他操作");

        //轮训获取结果，耗费CPU资源
        while (true) {
            if (future.isDone()) {
                System.out.println(future.get());
                break;
            }
        }
        System.out.println(Thread.currentThread().getName() + " 程序结束执行");
    }
}
