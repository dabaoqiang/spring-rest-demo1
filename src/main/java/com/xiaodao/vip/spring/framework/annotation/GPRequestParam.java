package com.xiaodao.vip.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoqiang
 * @Title: GPRequestParam
 * @ProjectName spring-vip-demo2
 * @Description: TODO
 * @date 2019-01-04 21:42
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestParam {
    String value() default "";

    boolean required() default true;
}
