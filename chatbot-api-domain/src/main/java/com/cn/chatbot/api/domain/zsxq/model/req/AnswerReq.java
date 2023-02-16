package com.cn.chatbot.api.domain.zsxq.model.req;

//请求回答接口信息
public class AnswerReq {

    private   ReqData req_Data;

    public AnswerReq(ReqData req_Data) {
        this.req_Data = req_Data;
    }

    public ReqData getReq_Data() {
        return req_Data;
    }

    public void setReq_Data(ReqData req_Data) {
        this.req_Data = req_Data;
    }
}
