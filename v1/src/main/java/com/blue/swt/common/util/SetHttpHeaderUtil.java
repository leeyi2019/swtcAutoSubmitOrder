package com.blue.swt.common.util;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

/**
 * ClassName:SetHttpHeaderUtil
 * package:com.blue.other
 * Descrption:
 *
 * @Date:2019/4/7 0007 20:32
 * @Author:leexinxu@163.com
 */
public class SetHttpHeaderUtil {
    public static void setHeaders(HttpGet httpGet){
        httpGet.setHeader("Accept", "text/html, */*; q=0.01");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate,sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36)");
    }
    public static void setHeaders(HttpPost httpPost){
        httpPost.setHeader("Accept", "text/html, */*; q=0.01");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate,sdch");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36)");
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
    }
}
