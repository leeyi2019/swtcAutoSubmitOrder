package com.blue.swt.common.filter;

import com.blue.swt.common.util.DateUtil;
import spark.Filter;
import spark.Request;
import spark.Response;

/**
 * ClassName:GlobalFilter
 * package:com.proxzone.viid.api.http
 * Descrption:全局过滤器，登录后才能进行操作
 *
 * @Date:2018/11/8 0008 10:02
 * @Author:leexinxu@163.com
 */
public class GlobalFilter implements Filter {

    @Override
    public void handle(Request request, Response response){
        System.out.println("--> requestMsg: time="+ DateUtil.getStrTime()+", ip="+request.ip()+", uri="+request.uri());
    }
}
