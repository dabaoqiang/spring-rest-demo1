package com.xiaodao.vip.spring.demo.service.impl;

import com.xiaodao.vip.spring.demo.service.IQueryService;
import com.xiaodao.vip.spring.framework.annotation.GPService;

/**
 * @author xiaoqiang
 * @Title: IQueryServiceImpl
 * @ProjectName spring-rest-demo1
 * @Description: TODO
 * @date 2019-01-06 16:34
 */
@GPService(value = "iQueryService")
public class IQueryServiceImpl implements IQueryService {

    @Override
    public String query(String name) {
        return name + "你好，spring ioc";
    }
}
