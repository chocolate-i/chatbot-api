package com.cn.chatbot.api.domain.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.chatbot.api.domain.zsxq.IZsxqApi;
import com.cn.chatbot.api.domain.zsxq.model.aggregates.queryUnAnswerQuestionAggregates;
import com.cn.chatbot.api.domain.zsxq.model.req.AnswerReq;
import com.cn.chatbot.api.domain.zsxq.model.req.ReqData;
import com.cn.chatbot.api.domain.zsxq.model.res.AnswerRes;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ZsxqApi implements IZsxqApi {

private Logger logger = (Logger) LoggerFactory.getLogger(ZsxqApi.class);

    @Override
    public queryUnAnswerQuestionAggregates queryUnAnswerQuestionTopicId(String groupId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get =new HttpGet(""+groupId +"/topics?scope=unanswered_question&count=20");
        get.addHeader("cookie","");
        get.addHeader("content-Type","");

        CloseableHttpResponse response =httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());//工具把返回的东西转换为实体信息
            logger.info("回答星球的结果.group:{} jsonStr:{},group",groupId,jsonStr);//拉取问题数据

           return JSON.parseObject(jsonStr, queryUnAnswerQuestionAggregates.class);

        }else {
            throw  new  RuntimeException(  "QuestionTpId   err code" +response.getStatusLine().getStatusCode());
        }

    }

    @Override
    public boolean answer(String group, String cookie, String topicId, String text, boolean silenced) throws IOException {
        CloseableHttpClient httpClient =HttpClientBuilder.create().build();
        HttpPost post =new HttpPost("" +topicId +"/answer");
        post.addHeader("cookie",cookie);
        post.addHeader("content-Type","");
        post.addHeader("user-agent","");

//        String paramJson= "";

        AnswerReq answerReq = new AnswerReq(new ReqData(text,silenced));

        String paramJson = JSONObject.toJSONString(answerReq);


        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("test/json"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jasonStr = EntityUtils.toString(response.getEntity());//工具把返回的东西转换为实体信息
            logger.info("回答星球的结果.group:{} jasonStr:{},group",group,topicId,jasonStr);
            AnswerRes answerRes = JSON.parseObject(jasonStr, AnswerRes.class);
            return answerRes.isSucceeded();

        }else {
            throw  new  RuntimeException(  "answer    err code" +response.getStatusLine().getStatusCode());

        }

    }
}
