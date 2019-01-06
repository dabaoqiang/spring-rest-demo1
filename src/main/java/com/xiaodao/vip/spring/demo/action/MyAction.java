package com.xiaodao.vip.spring.demo.action;

import com.xiaodao.vip.spring.demo.service.IQueryService;
import com.xiaodao.vip.spring.framework.annotation.GPAutowired;
import com.xiaodao.vip.spring.framework.annotation.GPController;
import com.xiaodao.vip.spring.framework.annotation.GPRequestMapping;
import com.xiaodao.vip.spring.framework.annotation.GPService;

/**
 * @author xiaoqiang
 * @Title: MyAction
 * @ProjectName spring-rest-demo1
 * @Description: TODO
 * @date 2019-01-06 16:33
 */
@GPController
@GPRequestMapping(value = "/web")
public class MyAction {


    @GPAutowired
    private IQueryService iQueryService;

    public void hello(String name) {
        iQueryService.query(name);

    }

}
