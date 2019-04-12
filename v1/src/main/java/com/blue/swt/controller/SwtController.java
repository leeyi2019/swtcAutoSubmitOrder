package com.blue.swt.controller;

import spark.Request;
import spark.Response;

/**
 * ClassName:SwtController
 * package:com.blue.swt.controller
 * Descrption:
 *
 * @Date:2019/4/8 0008 9:58
 * @Author:leexinxu@163.com
 */
public interface SwtController {
    String setParam(Request request);

    Object code(Request request, Response response);

    Object login(Request request);

    Object account(Request request);

    Object historyOrder(Request request);

    Object submitOrder(Request request);
}
