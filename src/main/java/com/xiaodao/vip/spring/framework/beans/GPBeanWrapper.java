package com.xiaodao.vip.spring.framework.beans;

/**
 * @author xiaoqiang
 * @Title: GPBeanWrapper
 * @ProjectName spring-rest-demo1
 * @Description: TODO
 * @date 2019-01-05 16:59
 */
public class GPBeanWrapper {
    // 用户AOP处理的，先不处理先返回原对象
    private Object wrapperInstance;
    //原始的通过反射new出来，要把包装起来，存下来
    private Object originalInstance;

    public GPBeanWrapper(Object instance) {
        this.wrapperInstance = instance;
        this.originalInstance = instance;
    }

    public Object getWrapperInstance() {
        return wrapperInstance;
    }

    public void setWrapperInstance(Object wrapperInstance) {
        this.wrapperInstance = wrapperInstance;
    }

    public Object getOriginalInstance() {
        return originalInstance;
    }

    public void setOriginalInstance(Object originalInstance) {
        this.originalInstance = originalInstance;
    }
}
