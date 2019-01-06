package com.xiaodao.vip.spring.framework.context;

import com.xiaodao.vip.spring.framework.beans.GPBeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaoqiang
 * @Title: GPDefaultListableBeanFactory
 * @ProjectName spring-rest-demo1
 * @Description: TODO
 * @date 2019-01-05 18:01
 */
public class GPDefaultListableBeanFactory extends  GPAbstractApplicationContext {

    private  final String LOCATION = "contextConfigLocation";

    /**
     * 这里声明一个
     */
    //beanDefinitionMap用来保存配置信息
    protected Map<String, GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, GPBeanDefinition>();

    /**
     * 交给子类去做
     */
    @Override
    protected void onRefresh() {
    }

    @Override
    protected void refreshBeanFactory() {
    }
}
