package com.xiaodao.vip.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoqiang
 * @Title: GPAutowired
 * @ProjectName spring-vip-demo2
 * @Description: TODO
 * @date 2018-12-31 16:30
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPAutowired {
    String value() default "";
}
