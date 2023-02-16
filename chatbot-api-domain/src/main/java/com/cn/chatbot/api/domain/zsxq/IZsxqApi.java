package com.cn.chatbot.api.domain.zsxq;

import com.cn.chatbot.api.domain.zsxq.model.aggregates.queryUnAnswerQuestionAggregates;

import java.io.IOException;

public interface IZsxqApi {


    queryUnAnswerQuestionAggregates queryUnAnswerQuestionTopicId(String groupId, String cookie) throws IOException;

    boolean answer(String group,String cookie, String topicId ,String text,boolean silenced )throws  IOException;
}
