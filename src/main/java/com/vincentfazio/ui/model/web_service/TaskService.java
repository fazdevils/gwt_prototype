package com.vincentfazio.ui.model.web_service;

import java.util.Collection;
import java.util.SortedSet;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.model.TaskModel;
import com.vincentfazio.ui.model.parser.xml.TaskListXmlParser;

public class TaskService extends GwtService implements TaskModel {

    private final String openTaskUrl;
    private final String completedTaskUrl;
    private final TaskListXmlParser xmlParser;
    private final GwtGlobals globals;
    
    public TaskService(GwtGlobals globals) {
        super();
        this.xmlParser = new TaskListXmlParser();
        openTaskUrl = globals.getServiceBaseUrl() + "/" + globals.getRole() + "/companies/open_tasks";
        completedTaskUrl = globals.getServiceBaseUrl() + "/" + globals.getRole() + "/companies/completed_tasks";
        this.globals = globals;
    }

    
    @Override
    public void getMyTasks(AsyncCallback<SortedSet<TaskBean>> asyncCallback) {
        String url = encodeUrl(openTaskUrl);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        GwtRequestCallback<SortedSet<TaskBean>> requestCallback = new GwtRequestCallback<SortedSet<TaskBean>>(url, asyncCallback, xmlParser, errorModel);  
        RequestBuilder builder = createRequestBuilder(RequestBuilder.GET, url);

        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }
    
    
    @Override
    public void completeTask(TaskBean updatedDetails, AsyncCallback<String> asyncCallback) {
        
        // TODO figure out what task we're completing and call the appropriate ws... or add ws to do it on the server side
        
        String url = encodeUrl(openTaskUrl);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        RequestCallback requestCallback = new GwtStatusOnlyRequestCallback(url, asyncCallback, "Saved", errorModel);
        RequestBuilder builder = createRequestBuilder(RequestBuilder.PUT, url);

        builder.setHeader("Content-Type", "application/xml");
        // TODO builder.setRequestData(xmlParser.createXml(updatedDetails)); 
        builder.setCallback(requestCallback);
        
        try {
          builder.send();
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }

    
    @Override
    public void getCompletedTasks(AsyncCallback<SortedSet<TaskBean>> asyncCallback) {
        String url = encodeUrl(completedTaskUrl);
        ErrorModel errorModel = (ErrorModel) globals.getModel(ErrorModel.class);
        GwtRequestCallback<SortedSet<TaskBean>> requestCallback = new GwtRequestCallback<SortedSet<TaskBean>>(url, asyncCallback, xmlParser, errorModel);  
        RequestBuilder builder = createRequestBuilder(RequestBuilder.GET, url);

        try {
          builder.sendRequest(null, requestCallback);
        } catch (Exception e) {
            asyncCallback.onFailure(e);
        }    
    }


	@Override
	public void updateDocumentationTask(String companyName, Collection<DocumentationBean> documents) {
		// TODO Auto-generated method stub
		
	}
    
}
