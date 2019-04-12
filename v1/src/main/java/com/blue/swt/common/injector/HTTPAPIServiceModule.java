package com.blue.swt.common.injector;

import com.blue.swt.http.APIService;
import com.blue.swt.http.DefaultSparkRoutesBuilder;
import com.blue.swt.http.DefaultSparkServer;
import com.blue.swt.http.SparkRoutesBuilder;
import com.google.inject.AbstractModule;

/**
 * ClassName:HTTPAPIServiceModule
 * package:com.proxzone.card.api.http
 * Descrption:依赖绑定
 *
 * @Date:2018/11/5 0005 14:52
 */
public class HTTPAPIServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(APIService.class).to(DefaultSparkServer.class);
        bind(SparkRoutesBuilder.class).to(DefaultSparkRoutesBuilder.class);
    }
}
