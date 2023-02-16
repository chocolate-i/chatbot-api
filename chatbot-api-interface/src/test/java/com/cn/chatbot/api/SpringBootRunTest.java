package com.cn.chatbot.api;


import com.alibaba.fastjson.JSON;
import com.cn.chatbot.api.domain.ai.service.OpenAI;
import com.cn.chatbot.api.domain.zsxq.IZsxqApi;
import com.cn.chatbot.api.domain.zsxq.model.aggregates.queryUnAnswerQuestionAggregates;
import com.cn.chatbot.api.domain.zsxq.model.vo.Topics;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

@Value("${chatbot-api.groupId}")
    private  String groupId;


    @Value("${chatbot-api.cookie}")
    private  String cookie;

    @Value("${chatbot-api.group01.openAiKey}")
    private String openAiKey;
    @Resource
    private OpenAI openAI;
    @Resource
    private IZsxqApi zsxqApi;


        @Test
    public  void  test_zxsqApi() throws IOException {


            queryUnAnswerQuestionAggregates queryUnAnswerQuestionAggregates = zsxqApi.queryUnAnswerQuestionTopicId(groupId, cookie);

            log.info("测试结果:{}" , JSON.toJSONString(queryUnAnswerQuestionAggregates));
      List<Topics> topics =queryUnAnswerQuestionAggregates.getResp_Data().getTopics();


            for (Topics topic : topics) {

                String topicId = topic.getTopic_id();
                String text = topic.getQuestion().getText();
                log.info("topicId:{} text" ,  topicId,text);


                //回答问题
                zsxqApi.answer(groupId,cookie,topicId,text,false);


            }


        }

    @Test
    public void test_openAi() throws IOException {
        String response = openAI.doChatGPT(openAiKey, "帮我写一个java冒泡排序");
        log.info("测试结果：{}", response);
    }
}
