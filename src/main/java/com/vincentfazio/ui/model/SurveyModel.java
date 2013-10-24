package com.vincentfazio.ui.model;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.QuestionBean;

public interface SurveyModel extends Model {
    
    void getQuestions(String vendorId, AsyncCallback<List<QuestionBean>> asyncCallback);
    
    void getQuestion(String vendorId, QuestionBean question, AsyncCallback<QuestionBean> asyncCallback);
    void answerQuestion(String vendorId, QuestionBean response, AsyncCallback<String> asyncCallback);

}
