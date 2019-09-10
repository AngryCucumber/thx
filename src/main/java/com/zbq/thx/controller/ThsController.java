package com.zbq.thx.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: huanglh
 * @date: 2019/9/10 16:01
 * Description: 同花顺阳线反包策略
 */
@RestController
@RequestMapping("ths")
public class ThsController {

    private static final String THS_URL = "http://square.10jqka.com.cn/strategy/detail/hold/5c472caddad04600ae45aca2?page=1&pagesize=10";
    private static final String THS_HOST = "square.10jqka.com.cn";
    private static final String THS_PATH = "/";

    @RequestMapping("strategy")
    public String strategy(){
        String strategy = null;
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie clientCookie = new BasicClientCookie("userid","354820681");
        clientCookie.setDomain(THS_HOST);
        clientCookie.setPath(THS_PATH);
        clientCookie.setSecure(false);
        cookieStore.addCookie(clientCookie);
        HttpGet httpget = new HttpGet(THS_URL);
        HttpClient http= HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpEntity entity = null;
        try {
            HttpResponse response=http.execute(httpget);//发送请求获得返回值

            entity=response.getEntity();//获取返回的响应信息

            strategy = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strategy;
    }
}
