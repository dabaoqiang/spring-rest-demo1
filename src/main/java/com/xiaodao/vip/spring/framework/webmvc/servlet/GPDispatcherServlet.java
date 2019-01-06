package com.xiaodao.vip.spring.framework.webmvc.servlet;

import com.xiaodao.vip.spring.demo.action.MyAction;
import com.xiaodao.vip.spring.demo.service.IQueryService;
import com.xiaodao.vip.spring.framework.context.GPApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiaoqiang
 * @Title: GPDispatcherServlet
 * @ProjectName spring-rest-demo1
 * @Description: TODO
 * @date 2019-01-05 17:54
 */
public class GPDispatcherServlet extends HttpServlet {
    /**
     * 对于web.xml的servlet，init-param需要读取的参数key
     */
    private final String location = "contextConfigLocation";

    /**
     * 初始化
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        // 定位，加载，注册，注入
        // 定位，加载，都是由，BeanDefinitionReader处理，注册，注入在applicationCOntext处
        // IOC容器初始化
        GPApplicationContext gpApplicationContext = new GPApplicationContext(config.getInitParameter(location));
        System.out.println("IOC容初始话完成");
        MyAction myAction = (MyAction) gpApplicationContext.getBean("myAction");
        myAction.hello("注入bean完成");
        // 接口名称还是全路径的名称
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
