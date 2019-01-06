package com.xiaodao.vip.spring.framework.context.support;

import com.xiaodao.vip.spring.framework.beans.GPBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author xiaoqiang
 * @Title: BeanDefinitionReader
 * @ProjectName spring-rest-demo1
 * @Description: TODO
 * @date 2019-01-05 17:05
 */
public class BeanDefinitionReader {

    /**
     * 配置文件
     */
    private Properties properties = new Properties();

    /**
     * 存储beanClassess文件的list
     */
    List<String> registyBeanClasses = new ArrayList<String>();

    /**
     * 读取配置文件的key值
     */
    private static final String scanPackage = "scanPackage";

    public BeanDefinitionReader(String... locations) {
        // 读取web.xml文件，1.定位
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:", ""));
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != resourceAsStream) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("**************定位完成**************");
        // 2.加载指定路径下的资源
        doScanner(properties.getProperty(scanPackage));
        System.out.println("*************加载完成****************");

        // 3.注册 生成bean
        // 4.自动注入
        // 3,4操作在ApplicationContext里处理
    }

    /**
     * 加载指定目录下的class路径到registyBeanClasses中去
     *
     * @param packageName
     */
    private void doScanner(String packageName) {
        // 获取地址的URL，将绝对地址，comn.xiaodao.vip.spring.demo 转出全路径名
        // 获取此 URL 的文件名。返回的文件部分将与 getPath() 相同，再加上 getQuery() 值的规范化形式（如果有）。
        // 如果没有查询部分，此方法和 getPath() 将返回相同的结果。
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        /**
         * 通过将给定路径名字符串转换为抽象路径名来创建一个新 File 实例。
         */
        File classDir = new File(url.getFile());
        /**
         * 返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
         * 如果此抽象路径名不表示一个目录，那么此方法将返回 null。
         * 身及其父目录的名称不包括在结果中。得到的每个抽象路径名都是根据此抽象路径名，使用 File(File, String) 构造方法构造的。
         * 所以，如果此路径名是绝对路径名，那么得到的每个路径名都是绝对路径名；
         * 如果此路径名是相对路径名，那么得到的每个路径名都是相对于同一目录的路径名。
         * 不保证所得数组中的相同字符串将以特定顺序出现，特别是不保证它们按字母顺序出现。
         */
        File[] files = classDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                doScanner(packageName + "." + file.getName());
            } else {
                registyBeanClasses.add(packageName + "." + file.getName().replaceAll(".class", ""));
            }
        }
    }

    /**
     * 在applicationContext注册的时候，我们需要，注册BeanDefinition吗，定义一个方法
     */
    public GPBeanDefinition registerBean(String className) {
        if (this.registyBeanClasses.contains(className)) {
            GPBeanDefinition gpBeanDefinition = new GPBeanDefinition();
            // com.***.**格式
            gpBeanDefinition.setBeanCLassName(className);
            gpBeanDefinition.setFactoryBeanName(lowerFirstCase(className.substring(className.lastIndexOf(".") + 1)));
            return gpBeanDefinition;
        }
        return null;
    }

    /**
     * 第一个字母大写
     *
     * @param str
     * @return
     */
    private String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }


    /**
     * 提供给ApplicationContext使用
     *
     * @return
     */
    public List<String> loadBeanDefinitions() {
        return this.registyBeanClasses;
    }
}
