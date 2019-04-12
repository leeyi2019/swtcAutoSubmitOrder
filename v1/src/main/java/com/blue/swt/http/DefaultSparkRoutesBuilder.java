package com.blue.swt.http;

import com.blue.swt.common.filter.GlobalFilter;
import com.blue.swt.controller.SwtController;
import com.google.inject.Inject;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.get;

import spark.Session;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 * ClassName:DefaultSparkRoutesBuilder
 * package:com.proxzone.viid.api.http
 * Descrption:Spark路由构建实现类
 *
 * @Date:2018/11/6 0006 10:32
 */
public class DefaultSparkRoutesBuilder implements SparkRoutesBuilder {

    private SwtController swtController;

    @Inject
    public DefaultSparkRoutesBuilder(SwtController swtController){
        this.swtController = swtController;
    }

    @Override
    public void build() throws Exception {
        //静态资源
        staticFiles.location("/static");

        //过滤器
        before("/*",new GlobalFilter());

        //主页
        Map<String,String> resMap = new HashMap<>();
        get("/index", (rq, rs) -> new ModelAndView(resMap, "index"), new ThymeleafTemplateEngine());

        //传入参数
        post("/param", (request, response) -> {
            return swtController.setParam(request);
        });

        //获取验证码
        get("/code", (request, response) -> {
            return swtController.code(request, response);
        });

        //登陆
        post("/login", (request, response) -> {
            return swtController.login(request);
        });

        //挂单
        post("/submitOrder", (request, response) -> {
            return swtController.submitOrder(request);
        });

        //获取SWT和CNY余额
        get("/account", (request, response) -> {
            return swtController.account(request);
        });

        //获取现在挂单
        get("/historyOrder", (request, response) -> {
            return swtController.historyOrder(request);
        });

        System.out.println("--> http ok");

    }
}
