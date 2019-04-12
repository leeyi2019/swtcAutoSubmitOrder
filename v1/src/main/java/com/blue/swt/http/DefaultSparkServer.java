package com.blue.swt.http;

import com.google.inject.Inject;

import static spark.Spark.*;

/**
 * ClassName:DefaultSparkServer
 * package:com.proxzone.viid.api.http
 * Descrption:接口服务实现类
 *
 * @Date:2018/11/5 0005 14:46
 */
public class DefaultSparkServer implements APIService {

    private SparkRoutesBuilder sparkRoutesBuilder;

    @Inject
    public DefaultSparkServer(SparkRoutesBuilder sparkRoutesBuilder) {
        this.sparkRoutesBuilder = sparkRoutesBuilder;
    }

    /**
     * 启动API服务器
     *
     * @throws Exception 启动失败异常信息
     */
    @Override
    public void start() throws Exception {
        port(7777);
        threadPool(1000);
        sparkRoutesBuilder.build();
        awaitInitialization();
    }

}
