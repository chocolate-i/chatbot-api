package com.cn.chatbot.api;


import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;


public class ApiTest {
@Test
//获取信息
    public  void  query_unanswered_questions() throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get =new HttpGet("");
        get.addHeader("cookie","");
        get.addHeader("content-Type","");

        CloseableHttpResponse response =httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());//工具把返回的东西转换为实体信息
            System.out.printf(res);

        }else {
            System.out.printf(String.valueOf(response.getStatusLine().getStatusCode()));
        }

    }

    @Test
    public void  answer() throws IOException {
        CloseableHttpClient httpClient =HttpClientBuilder.create().build();
        HttpPost post =new HttpPost("");
        post.addHeader("cookie","");
        post.addHeader("content-Type","");

            String paramJson= "";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("test/json"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());//工具把返回的东西转换为实体信息
            System.out.printf(res);

        }else {
            System.out.printf(String.valueOf(response.getStatusLine().getStatusCode()));
        }
    }

    @Test
    public  void  test_chatGPT() throws IOException {
    CloseableHttpClient httpClient =HttpClientBuilder.create().build();


        HttpPost post = new HttpPost("https://api.openai.com/v1/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", " ");

        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"帮我写一个java冒泡排序\", \"temperature\": 0, \"max_tokens\": 1024}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("test/json"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());//工具把返回的东西转换为实体信息
            System.out.printf(res);

        }else {
            System.out.printf(String.valueOf(response.getStatusLine().getStatusCode()));
        }


    }
}
