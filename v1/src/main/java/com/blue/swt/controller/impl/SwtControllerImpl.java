package com.blue.swt.controller.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blue.swt.common.Param;
import com.blue.swt.common.util.SetHttpHeaderUtil;
import com.blue.swt.controller.SwtController;
import com.google.inject.Inject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import spark.Request;
import spark.Response;
import spark.Session;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:SwtControllerImpl
 * package:com.blue.swt.controller.impl
 * Descrption:
 *
 * @Date:2019/4/8 0008 9:59
 * @Author:leexinxu@163.com
 */
public class SwtControllerImpl implements SwtController {

    //处理参数
    @Override
    public String setParam(Request request) {

        Param param = new Param();

        String userName = request.queryParams("userName");
        if(userName != null && !"".equals(userName)){
            param.setUserName(userName);
        }

        String pwd = request.queryParams("pwd");
        if(pwd != null && !"".equals(pwd)){
            param.setPwd(pwd);
        }

        String ppwd = request.queryParams("ppwd");
        if(ppwd != null && !"".equals(ppwd)){
            param.setPpwd(ppwd);
        }

        Session session = request.session();
        session.maxInactiveInterval(60*60*24*3);
        session.attribute("param", param);

        System.out.println("--> paramOK: "+param.getUserName());

        return "ok";
    }

    @Override
    public Object code(Request request, Response response) {
        try {
            //创建httpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //获取验证码
            String url = "https://app.swtc.pro/util/getCaptcha";
            HttpGet httpGet = new HttpGet(url);
            SetHttpHeaderUtil.setHeaders(httpGet);
            CloseableHttpResponse responseCode = httpClient.execute(httpGet);
            HttpEntity entity = responseCode.getEntity();
            byte[] codeData = EntityUtils.toByteArray(entity);
            //放入session
            Session session = request.session();
            session.attribute("httpClient", httpClient);
            //读取原始响应对象
            HttpServletResponse rawResponse = response.raw();
            //响应图片
            ServletOutputStream out = rawResponse.getOutputStream();
            out.write(codeData);
            out.close();

            //关闭
            EntityUtils.consume(entity);
            responseCode.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("--> codeOK");
        return null;
    }

    @Override
    public Object login(Request request) {

        String msg = "登陆失败";
        try {
            //取出httpClient对象和参数对象
            Session session = request.session();
            CloseableHttpClient httpClient = session.attribute("httpClient");
            Param param = session.attribute("param");

            //取出验证码
            String code = request.queryParams("code");
            //登陆
            String urlLogin = "https://app.swtc.pro/app/user/login";
            String paramJson = "{\"userName\":\""+param.getUserName()+"\",\"pwd\":\""+param.getPwd()+"\",\"code\":\""+code+"\"}";
            HttpPost httpPostLogin = new HttpPost(urlLogin);
            SetHttpHeaderUtil.setHeaders(httpPostLogin);
            StringEntity se = new StringEntity(paramJson);
            se.setContentType("text/json");
            httpPostLogin.setEntity(se);
            CloseableHttpResponse responseLogin = httpClient.execute(httpPostLogin);
            HttpEntity entity = responseLogin.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            //关闭
            EntityUtils.consume(entity);
            responseLogin.close();
            JSONObject jsonObject = JSONObject.parseObject(result);
            msg = jsonObject.getString("msg");
            System.out.println("--> loginOk: "+msg);
        }catch (Exception e){
            e.printStackTrace();
        }
        return msg;
    }

    //挂单
    @Override
    public Object submitOrder(Request request) {

        String amount = request.queryParams("amount");
        String sum = request.queryParams("sum");
        String type = request.queryParams("type");

        System.out.println("--> submitOrder: amount="+amount+", sum="+sum+", type="+type);

        String msg = "";

        try {
            //取出httpClient对象和参数对象
            Session session = request.session();
            CloseableHttpClient httpClient = session.attribute("httpClient");
            Param param = session.attribute("param");

            //挂单
            String url = "https://app.swtc.pro/app/order/submit";
            String paramJson = "{\"amount\":\""+amount+"\",\"base\":{\"currency\": \"SWT\", \"issuer\" : \"\"},\"counter\":{\"currency\": \"CNY\", \"issuer\" : \"\"},\"ppwd\":\""+param.getPpwd()+"\",\"sum\":\""+sum+"\",\"type\":\""+type+"\"}";
            HttpPost httpPost = new HttpPost(url);
            SetHttpHeaderUtil.setHeaders(httpPost);
            StringEntity se = new StringEntity(paramJson);
            se.setContentType("text/json");
            httpPost.setEntity(se);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            //关闭
            EntityUtils.consume(entity);
            response.close();

            JSONObject jsonObject = JSON.parseObject(result);
            msg = jsonObject.getString("msg");

            System.out.println("--> submitOrderOk: " + result);

        }catch (Exception e){
            e.printStackTrace();
        }
        return msg;
    }


    @Override
    public Object account(Request request) {

        String json = "{\"swt\":{\"sum\":\"0\",\"freezed\":\"0\"},\"cny\":{\"sum\":\"0\",\"freezed\":\"0\"}}";

        try {
            //取出httpClient对象
            Session session = request.session();
            CloseableHttpClient httpClient = session.attribute("httpClient");
            //获取账户
            String url = "https://app.swtc.pro/app/user/balances/get";
            HttpGet httpGet = new HttpGet(url);
            SetHttpHeaderUtil.setHeaders(httpGet);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            //关闭
            EntityUtils.consume(entity);
            response.close();
            //处理信息
            JSONObject jsonObject = JSON.parseObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            JSONObject jsonObjectSWT = jsonArray.getJSONObject(0);
            String swtSum = jsonObjectSWT.getString("value");
            String swtFreezed = jsonObjectSWT.getString("freezed");
            JSONObject jsonObjectCNY = jsonArray.getJSONObject(1);
            String cnySum = jsonObjectCNY.getString("value");
            String cnyFreezed = jsonObjectCNY.getString("freezed");

            json = "{\"swt\":{\"sum\":\""+swtSum+"\",\"freezed\":\""+swtFreezed+"\"},\"cny\":{\"sum\":\""+cnySum+"\",\"freezed\":\""+cnyFreezed+"\"}}";

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("--> getAccountOk");
        return json;
    }

    @Override
    public Object historyOrder(Request request) {

        JSONObject jsonObject = null;

        try {
            //取出httpClient对象
            Session session = request.session();
            CloseableHttpClient httpClient = session.attribute("httpClient");
            //获取历史订单
            String url = "https://app.swtc.pro/app/user/tx/0";
            HttpGet httpGet = new HttpGet(url);
            SetHttpHeaderUtil.setHeaders(httpGet);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            //关闭
            EntityUtils.consume(entity);
            response.close();
            //处理信息
            jsonObject = JSON.parseObject(result);

        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonObject;
    }
}
