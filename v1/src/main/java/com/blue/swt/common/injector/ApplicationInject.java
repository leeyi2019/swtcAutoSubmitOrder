package com.blue.swt.common.injector;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * ClassName:ApplicationInject
 * package:com.proxzone.viid.api.common.injector
 * Descrption:依赖注入初始化
 *
 * @Date:2018/11/5 0005 18:00
 */
public class ApplicationInject {

    public Injector init(){

        return Guice.createInjector(new HTTPAPIServiceModule(),
                                    new CoreModule());

    }
}
