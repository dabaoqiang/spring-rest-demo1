package com.xiaodao.vip.spring.framework.context;

/**
 * @author xiaoqiang
 * @Title: GPAbstractApplicationContext
 * @ProjectName spring-rest-demo1
 * @Description: TODO
 * @date 2019-01-05 17:48
 */
public abstract class GPAbstractApplicationContext {
    //提供给子类重写
    protected void onRefresh() {

    }

    protected abstract void refreshBeanFactory();

}
