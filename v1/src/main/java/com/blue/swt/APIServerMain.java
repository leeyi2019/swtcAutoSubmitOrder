package com.blue.swt;


import com.blue.swt.common.injector.ApplicationInject;
import com.blue.swt.http.APIService;
import com.google.inject.Injector;

/**
 * main
 *
 */
public class APIServerMain
{
    public static void main( String[] args ) {

        //初始化依赖注入
        Injector injector = new ApplicationInject().init();

        //取得接口服务对象
        APIService apiService = injector.getInstance(APIService.class);

        try {
            //启动接口服务
            apiService.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
