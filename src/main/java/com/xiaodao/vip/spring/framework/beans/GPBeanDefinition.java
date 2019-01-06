package com.xiaodao.vip.spring.framework.beans;

import java.lang.reflect.Method;

/**
 * @author xiaoqiang
 * @Title: GPBeanDefinition
 * @ProjectName spring-rest-demo1
 * @Description: TODO
 * @date 2019-01-05 16:56
 */
public class GPBeanDefinition {
    /*
     * 类目录 com.xiaodao.
     */
    private String beanCLassName;
    /**
     * 延迟加载
     */
    private Boolean lazyInit = false;
    /**
     * bean名称
     */
    private String factoryBeanName;

    /**
     * setter/getter
     */
    public String getBeanCLassName() {
        return beanCLassName;
    }

    public void setBeanCLassName(String beanCLassName) {
        this.beanCLassName = beanCLassName;
    }

    public Boolean getLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(Boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }
}
