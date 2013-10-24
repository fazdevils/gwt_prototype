package com.vincentfazio.ui.model;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.QuestionBean;

public interface SurveyModel extends Model {
    
    void getQuestions(String companyId, AsyncCallback<List<QuestionBean>> asyncCallback);
    
    void getQuestion(String companyId, QuestionBean question, AsyncCallback<QuestionBean> asyncCallback);
    void answerQuestion(String companyId, QuestionBean response, AsyncCallback<String> asyncCallback);

}
