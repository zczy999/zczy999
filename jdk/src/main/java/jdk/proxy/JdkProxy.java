package jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 小滴课堂,愿景：让技术不再难学
 *
 * @Description
 * @Author 二当家小D
 * @Remark 有问题直接联系我，源码-笔记-技术交流群
 * @Version 1.0
 **/
public class JdkProxy implements InvocationHandler {

    /**
     * 定义目标类
     */
    private Object targetObject;


    /**
     * 获取代理对象
     */
    public Object getProxyInstance(Object targetObject) {
        this.targetObject = targetObject;

        //通过反射创建代理对象
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(),
                this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method targetMethod = targetObject.getClass().getMethod(method.getName(), method.getParameterTypes());

        Object result;
        if (targetMethod.isAnnotationPresent(Test.class)) {
            openTransaction();
            result = method.invoke(targetObject, args);
            commitTransaction();
        }else {
            result = method.invoke(targetObject, args);
        }


        return result;
    }


    public static void openTransaction() {
        System.out.println("开启事务");
    }

    public static void commitTransaction() {
        System.out.println("提交事务");
    }
}

