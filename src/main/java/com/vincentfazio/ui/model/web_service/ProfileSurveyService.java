package com.vincentfazio.ui.model.web_service;

import java.util.List;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.ProfileSurveyModel;
import com.vincentfazio.ui.model.parser.xml.ProfileSurveyQuestionResponseXmlParser;
import com.vincentfazio.ui.model.parser.xml.ProfileSurveyXmlParser;

public class ProfileSurveyService extends GwtService implements ProfileSurveyModel {

    private final String baseUrl;
    private final ProfileSurveyXmlParser xmlParser;
    private final ProfileSurveyQuestionResponseXmlParser questionResponseXmlParser;
    private final GwtGlobals globals;
   
    public ProfileSurveyService(GwtGlobals globals) {
        super();
        this.xmlParser = new ProfileSurveyXmlParser();
        this.questionResponseXmlParser = new ProfileSurveyQuestionResponseXmlParser();
        this.baseUrl = globals.getServiceBaseUrl() + "/" + globals.getRole() + "/companies";
        this.globals = globals;
    }


    @Override
    public void getQuestions(String companyId, final AsyncCallback<List<QuestionBean>> asyncCallback) {
        String url = getEncodedSurveyUrl(companyId);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        GwtRequestCallback<List<QuestionBean>> requestCallback = new GwtRequestCallback<List<QuestionBean>>(url, asyncCallback, xmlParser, errorModel);  
        RequestBuilder builder = createRequestBuilder(RequestBuilder.GET, url);

        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }


    @Override
    public void getQuestion(String companyId, final QuestionBean question, final AsyncCallback<QuestionBean> asyncCallback) {
        String url = getEncodedSurveyQuestionUrl(companyId, question.getQuestionId());
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        
        GwtRequestCallback<QuestionBean> requestCallback = new GwtRequestCallback<QuestionBean>(url, asyncCallback, questionResponseXmlParser, errorModel);  
        RequestBuilder builder = createRequestBuilder(RequestBuilder.GET, url);
        
        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }


    @Override
    public void answerQuestion(String companyId, QuestionBean response, AsyncCallback<String> asyncCallback) {
        String url = getEncodedSurveyQuestionUrl(companyId, response.getQuestionId());
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Saved", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.PUT, url);

        builder.setHeader("Content-Type", "application/xml");
        builder.setRequestData(questionResponseXmlParser.createXml(response)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }
    
 
    private String getEncodedSurveyUrl(String companyId) {
        return encodeUrl(baseUrl + "/" + companyId + "/profile_survey");
    }

    private String getEncodedSurveyQuestionUrl(String companyId, String questionId) {
        return encodeUrl(baseUrl + "/" + companyId + "/profile_survey/question2/" + questionId);
    }
}
