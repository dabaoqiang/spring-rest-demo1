package com.xiaodao.vip.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoqiang
 * @Title: GPController
 * @ProjectName spring-vip-demo2
 * @Description: TODO
 * @date 2019-01-04 21:38
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPController {
    String value() default "";
}
