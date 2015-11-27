package org.myapp.common.httpservice;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpGetPost {
    private static Logger logger = Logger.getLogger(CrawPage.class);

    // 发送get请求
    public String getUrlReturn(String url, String parameters) {
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        // 请求地址
        HttpGet httpGet = new HttpGet(url + "?" + parameters);

        UrlEncodedFormEntity entity;
        try {

            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            // getEntity()
            HttpEntity httpEntity = response.getEntity();

            // 获取连接状态
            int statusCode = response.getStatusLine().getStatusCode();
            // 如果连接正常 处理返回信息
            if (statusCode == 200) {
                if (httpEntity != null) {
                    // 处理响应内容
                    String content = EntityUtils.toString(httpEntity, "UTF-8");
                    return content;
                }
            }

        } catch (Exception e) {
            logger.error("发送get请求有误：" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // 发送post请求
    public String postUrlReturn(String url, String parameters) {
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        // 请求地址
        HttpPost httpPost = new HttpPost(url + "?" + parameters);

        UrlEncodedFormEntity entity;
        try {

            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
            // getEntity()
            HttpEntity httpEntity = response.getEntity();

            // 获取连接状态
            int statusCode = response.getStatusLine().getStatusCode();
            // 如果连接正常 处理返回信息
            if (statusCode == 200) {
                if (httpEntity != null) {
                    // 处理响应内容
                    String content = EntityUtils.toString(httpEntity, "UTF-8");
                    return content;
                }
            }

        } catch (Exception e) {
            logger.error("发送post请求有误：" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
