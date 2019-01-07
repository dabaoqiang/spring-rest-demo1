package com.xiaodao.vip.spring.framework.context;

import com.xiaodao.vip.spring.framework.annotation.GPAutowired;
import com.xiaodao.vip.spring.framework.annotation.GPController;
import com.xiaodao.vip.spring.framework.annotation.GPService;
import com.xiaodao.vip.spring.framework.beans.GPBeanDefinition;
import com.xiaodao.vip.spring.framework.beans.GPBeanWrapper;
import com.xiaodao.vip.spring.framework.context.support.BeanDefinitionReader;
import com.xiaodao.vip.spring.framework.core.GPBeanFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaoqiang
 * @Title: GPApplicationContext
 * @ProjectName spring-rest-demo1
 * @Description: TODO
 * @date 2019-01-05 17:03
 */
public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {

    /**
     * 配置文件
     */
    private String[] configLocations;

    /**
     * 注入读取bean
     */
    private BeanDefinitionReader beanDefinitionReader;

    /**
     * 用来保存单例的bean实例
     */
    private Map<String, Object> beanCacheMap = new ConcurrentHashMap<String, Object>();

    //用来存储所有的被代理过的对象
    private Map<String, GPBeanWrapper> beanWrapperMap = new ConcurrentHashMap<String, GPBeanWrapper>();

    /**
     * 初始化spring上下文，进行定位，加载，注册，注入
     *
     * @param configLocations
     */
    public GPApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    /**
     * 进行spring IOC操作
     */
    private void refresh() {

        /**
         * 定位，加载，完成
         */
        this.beanDefinitionReader = new BeanDefinitionReader(configLocations);

        /**
         * 开始，注册，自动注入
         */
        // 开始注册，获取要实例的类全定称名
        List<String> beanDefinitions = beanDefinitionReader.loadBeanDefinitions();

        // 进行注册beanDefinition
        doRegister(beanDefinitions);

        // 自动注入开始
        doAutowrited();


    }

    /**
     * 自动注入
     */
    private void doAutowrited() {
        // 第一步，注入，将原始类，以及包装类，进行初始化
        // 循环已经注册的bean，对其进行处理，于是我们有
        for (Map.Entry<String, GPBeanDefinition> beanDefinitionEntry : beanDefinitionMap.entrySet()) {
            // 处理懒加载，第一次加载bean
            if (!beanDefinitionEntry.getValue().getLazyInit()) {
                String beanName = beanDefinitionEntry.getKey();
                Object bean = getBean(beanName);
                System.out.println("被包装的类：" + bean);
            }
        }
        // 第二步，循环包装类，进行自动注入
        for (Map.Entry<String, GPBeanWrapper> beanWrapperEntry : beanWrapperMap.entrySet()) {
            // 进行类的set private Service service 给这个service注入属性
            populateBean(beanWrapperEntry.getKey(), beanWrapperEntry.getValue().getOriginalInstance());
        }

        System.out.println("IOC注入完成");

    }

    /**
     * 填充bean
     *
     * @param beanName
     * @param instance
     */
    private void populateBean(String beanName, Object instance) {
        Class<?> clazz = instance.getClass();
        try {
            //是否继承了controller注解，service 注解
            if (!(clazz.isAnnotationPresent(GPController.class) ||
                    clazz.isAnnotationPresent(GPService.class))) {
                return;
            }

            // 循环该类下所有的字段
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(GPAutowired.class)) {
                    continue;
                }
                GPAutowired autowired = field.getAnnotation(GPAutowired.class);
                // 获取注解的名称
                String autowiredBeanName = autowired.value().trim();
                if ("".equals(autowiredBeanName)) {
                    // com.gupaoed.vip.spring.demo.service.IQueryService 这个名称
                    autowiredBeanName = field.getType().getName();
                }
                field.setAccessible(true);
                try {
                   String interfacename =  lowerFirstCase(autowiredBeanName.substring(autowiredBeanName.lastIndexOf(".") + 1));
                    System.out.println("=======================" + instance + "," + autowiredBeanName + "," + this.beanWrapperMap.get(autowiredBeanName));
                    field.set(instance, this.beanWrapperMap.get(interfacename).getWrapperInstance());
                } catch (Exception e) {
                    e.getStackTrace();
                }

            }

        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    /**
     * 注册bean，到beanDefinition
     *
     * @param beanDefinitions
     */
    private void doRegister(List<String> beanDefinitions) {
        try {

            for (String beanDefinition : beanDefinitions) {
                // 注册bean的时候，有三种情况，一种是自定义名称，一种是默认小写，一种是接口注解
                // 根据类的全路径地址即字节码，获取class对象
                Class<?> beanClass = Class.forName(beanDefinition);
                // 第一判断是否是接口,接口让类去实现注入接口，不对接口进行注册
                // 判定指定的 Class 对象是否表示一个接口类型。
                // 如果此对象表示一个接口，则返回 true；否则返回 false。
                if (beanClass.isInterface()) {
                    continue;
                }
                // 注册bean到beanDefinition
                GPBeanDefinition gpBeanDefinition = beanDefinitionReader.registerBean(beanDefinition);
                if (gpBeanDefinition != null) {
                    beanDefinitionMap.put(gpBeanDefinition.getFactoryBeanName(), gpBeanDefinition);
                }
                // 如果该对象实现了某接口
                Class<?>[] interfaces = beanClass.getInterfaces();
                if (interfaces != null && interfaces.length > 0) {
                    for (Class<?> anInterface : interfaces) {
                        // 可以给接口自定名称
                        String interfaceName = lowerFirstCase(anInterface.getName().substring((anInterface.getName().lastIndexOf(".") + 1)));
                        beanDefinitionMap.put(interfaceName, gpBeanDefinition);
                    }
                }

            }
            System.out.println("*****************注册完成第一步，将beanefinition配置成功*******************");

        } catch (Exception e) {
            e.getStackTrace();
        }


    }

    /**
     * 转换
     *
     * @param substring
     * @return
     */
    private String lowerFirstCase(String substring) {
        char[] chars = substring.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * 依赖注入，从这里开始，通过获取BeanDefinition中的信息
     * 然后，通过反射机制创建一个实例并返回
     * spring的做法是，不会把最原始的对象放出去，会用一个beanWrapper进行一次包装
     * 装饰器模式，1.保留了原来得的oop关系，，然后可以对它进行扩展，比如，增强，为AOP打下了基础，
     * 所以，注入bean的第一步，是要讲原有的bean进行封装，然后才对已经生产的代理对象进行注入，
     *
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) {

        GPBeanDefinition gpBeanDefinition = beanDefinitionMap.get(beanName);
        Object beanCLassName = gpBeanDefinition.getBeanCLassName();

        try {
            // 根据beanDefinitionnew获得bean
            Object object = instantionBean(gpBeanDefinition);
            if (object == null) {
                return null;
            }
            // 这个用来处理bean的封装类，现在不处理AOP先不管
            GPBeanWrapper gpBeanWrapper = new GPBeanWrapper(object);
            // 存起来这个封装bean
            beanWrapperMap.put(beanName, gpBeanWrapper);
            // spring 返回包装bean，不返回原始的类
            return this.beanWrapperMap.get(beanName).getWrapperInstance();

        } catch (Exception e) {
            e.getStackTrace();
        }

        return null;
    }

    /**
     * new出bean实例
     *
     * @param gpBeanDefinition
     */
    private Object instantionBean(GPBeanDefinition gpBeanDefinition) {
        Object instance = null;
        String beanCLassName = gpBeanDefinition.getBeanCLassName();
        try {
            if (this.beanCacheMap.containsKey(beanCLassName)) {
                return this.beanCacheMap.get(beanCLassName);
            } else {
                Class<?> clazz = Class.forName(beanCLassName);
                instance = clazz.newInstance();
                beanCacheMap.put(beanCLassName, instance);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return instance;
    }
}
