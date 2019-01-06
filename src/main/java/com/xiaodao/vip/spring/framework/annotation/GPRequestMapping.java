package com.xiaodao.vip.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoqiang
 * @Title: GPRequestMapping
 * @ProjectName spring-vip-demo2
 * @Description: TODO
 * @date 2019-01-04 21:40
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestMapping {
    String value() default "";
}
