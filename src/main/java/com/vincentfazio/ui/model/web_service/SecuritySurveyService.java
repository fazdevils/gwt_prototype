package com.vincentfazio.ui.model.web_service;

import java.util.List;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.SecuritySurveyModel;
import com.vincentfazio.ui.model.parser.xml.RawXmlNonParser;
import com.vincentfazio.ui.model.parser.xml.SecuritySurveyXmlParser;

public class SecuritySurveyService extends GwtService implements SecuritySurveyModel {

    private final String baseUrl;
    private final SecuritySurveyXmlParser xmlParser;
    private final RawXmlNonParser rawXmlNonParser; 
    private final GwtGlobals globals;
   
    public SecuritySurveyService(GwtGlobals globals) {
        super();
        this.xmlParser = new SecuritySurveyXmlParser();
        this.rawXmlNonParser = new RawXmlNonParser();
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
        
        AsyncCallback<String> questionResponseCallback = new AsyncCallback<String>() {            
            @Override
            public void onSuccess(String responseXml) {
                if (null != responseXml) {
                    Document messageDom = XMLParser.parse(responseXml);                    
                    Element answerElement = (Element) messageDom.getElementsByTagName("answer").item(0);

                    xmlParser.setAnswerResponses(question, answerElement);
                }
                asyncCallback.onSuccess(question);                
            }
            
            @Override
            public void onFailure(Throwable t) {
                asyncCallback.onFailure(t);
            }
        };
        
        GwtRequestCallback<String> requestCallback = new MissingIsOkRequestCallback<String>(url, questionResponseCallback, rawXmlNonParser, errorModel);  
        
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
        builder.setRequestData(xmlParser.createAnswerXml(response)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }


	private String getEncodedSurveyUrl(String companyId) {
        return encodeUrl(baseUrl + "/" + companyId + "/survey");
    }

    private String getEncodedSurveyQuestionUrl(String companyId, String questionId) {
        return encodeUrl(baseUrl + "/" + companyId + "/survey/" + questionId);
    }
}
