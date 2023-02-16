package com.cn.chatbot.api.domain.ai.service;

import com.alibaba.fastjson.JSON;
import com.cn.chatbot.api.domain.ai.IOpenAI;
import com.cn.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import com.cn.chatbot.api.domain.ai.vo.Choices;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;

@Slf4j
public class OpenAI implements IOpenAI {

    @Value("${chatbot-api.openAiKey}")
    private   String openAiKey;

    @Override
    public String doChatGPT(String openAiKey,String question) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.openai.com/v1/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", " "+openAiKey);

        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"" + question + "\", \"temperature\": 0, \"max_tokens\": 1024}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("test/json"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());//工具把返回的东西转换为实体信息
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getText());
            }
            return answers.toString();
        } else {
            throw new RuntimeException("api.openai.com Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
