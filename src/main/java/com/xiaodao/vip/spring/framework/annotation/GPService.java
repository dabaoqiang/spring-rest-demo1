package com.xiaodao.vip.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoqiang
 * @Title: GPService
 * @ProjectName spring-vip-demo2
 * @Description: TODO
 * @date 2019-01-04 21:43
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPService {
    String value() default "";

}
