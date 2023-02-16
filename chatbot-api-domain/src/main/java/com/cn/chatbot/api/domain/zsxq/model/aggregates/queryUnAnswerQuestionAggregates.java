package com.cn.chatbot.api.domain.zsxq.model.aggregates;

import com.cn.chatbot.api.domain.zsxq.model.res.RespData;

//  未回答问题的聚合信息

public class queryUnAnswerQuestionAggregates {

    private  boolean succeeded;

    private RespData resp_Data;

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public RespData getResp_Data() {
        return resp_Data;
    }

    public void setResp_Data(RespData resp_Data) {
        this.resp_Data = resp_Data;
    }
}
