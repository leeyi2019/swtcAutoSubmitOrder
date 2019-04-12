package com.blue.swt.common.injector;

import com.blue.swt.common.Param;
import com.blue.swt.controller.SwtController;
import com.blue.swt.controller.impl.SwtControllerImpl;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * ClassName:CoreModule
 * package:com.proxzone.viid.api.common.injector
 * Descrption:依赖绑定
 *
 * @Date:2018/11/6 0006 10:40
 */
public class CoreModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SwtController.class).to(SwtControllerImpl.class);

        /*Param param = new Param();
        bind(Param.class).toInstance(param);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        bind(CloseableHttpClient.class).toInstance(httpClient);*/

        /*CloseableHttpClient getSwtPriceHttpClient = HttpClients.createDefault();
        bind(CloseableHttpClient.class).annotatedWith(Names.named("getSwtPriceHttpClient")).toInstance(getSwtPriceHttpClient);

        HttpGet getSwtPriceHttpGet = new HttpGet("https://ib149d5a1e5.jccdex.cn/info/alltickers");
        bind(HttpGet.class).annotatedWith(Names.named("getSwtPriceHttpGet")).toInstance(getSwtPriceHttpGet);*/

    }
}
