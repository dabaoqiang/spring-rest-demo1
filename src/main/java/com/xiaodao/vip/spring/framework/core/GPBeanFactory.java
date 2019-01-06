package com.xiaodao.vip.spring.framework.core;

/**
 * @author xiaoqiang
 * @Title: GPBeanFactory
 * @ProjectName spring-rest-demo1
 * @Description: TODO
 * @date 2019-01-05 17:01
 */
public interface GPBeanFactory {
    /**
     * 根据bean获取名称
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
