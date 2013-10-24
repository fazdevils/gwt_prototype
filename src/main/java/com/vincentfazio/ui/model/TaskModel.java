package com.vincentfazio.ui.model;

import java.util.Collection;
import java.util.SortedSet;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.bean.TaskBean;

public interface TaskModel extends Model {

    void getMyTasks(AsyncCallback<SortedSet<TaskBean>> asyncCallback);

    void completeTask(TaskBean updatedDetails, AsyncCallback<String> asyncCallback);

    void getCompletedTasks(AsyncCallback<SortedSet<TaskBean>> asyncCallback);
  
    void updateDocumentationTask(String vendorName, Collection<DocumentationBean> documents);
}
