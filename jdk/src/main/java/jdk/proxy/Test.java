package jdk.proxy;

import java.lang.annotation.*;

/**
 * @author xdclass
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

    /**
     * 优先级
     * @return
     */
    int priority() default 0;


    /**
     * 是否要禁用
     * @return
     */
    boolean disabled() default false;


}
