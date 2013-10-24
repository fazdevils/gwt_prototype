package com.vincentfazio.ui.model.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.bean.DocumentationTaskBean;
import com.vincentfazio.ui.bean.MyDetailsBean;
import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.bean.TaskType;
import com.vincentfazio.ui.bean.comparator.TaskBeanComparator;
import com.vincentfazio.ui.model.DocumentationModel;
import com.vincentfazio.ui.model.MyDetailsModel;
import com.vincentfazio.ui.model.TaskModel;

public class MyDetailsMockModel implements MyDetailsModel, TaskModel, DocumentationModel {
    
    private MyDetailsBean myDetailsBean;
    private SortedSet<TaskBean> activeTasks = new TreeSet<TaskBean>(new TaskBeanComparator());
    private SortedSet<TaskBean> completedTasks = new TreeSet<TaskBean>(new TaskBeanComparator());
    
    private HashMap<String, DocumentationTaskBean> documentationTasks = new HashMap<String, DocumentationTaskBean>();


    public MyDetailsMockModel(boolean createTasks) {
        super();
        String userId = "User 1";
        myDetailsBean = new MyDetailsBean();
        myDetailsBean.setEmail("user1@mock.com");
        myDetailsBean.setUserId(userId);
        myDetailsBean.setName("Test User");
        myDetailsBean.setPhone("716-867-5309");
        myDetailsBean.setVendorAccess(new ArrayList<String>());
        
        List<String> vendorAccess = myDetailsBean.getVendorAccess();
        String vendorName = "Vendor 1";
        vendorAccess.add(vendorName);
        if (createTasks) {
        	createTasks(vendorName, 4);
        }
        
        vendorName = "Vendor 2";
        vendorAccess.add(vendorName);
        if (createTasks) {
        	createTasks(vendorName, 2);
        }
        
        vendorName = "Vendor 3";
        vendorAccess.add(vendorName);
        if (createTasks) {
        	createTasks(vendorName, 1);
        }
        
        vendorName = "Vendor 4";
        vendorAccess.add(vendorName);

    }


    private void createTasks(String vendorName, int numberOfTasks) {
        //SortedSet<TaskBean> taskList = new TreeSet<TaskBean>(new TaskBeanComparator());
        if (numberOfTasks > 0) {
            Date date = new Date();
            CalendarUtil.addDaysToDate(date, -21-numberOfTasks);
            TaskBean task = new TaskBean();
            task.setVendor(vendorName);
            task.setRequestedBy("customer");
            task.setRequestedOn(date);
            task.setTaskType(TaskType.DemographicSurvey);
            activeTasks.add(task);
        }
        if (numberOfTasks > 1) {
            Date date = new Date();
            CalendarUtil.addDaysToDate(date, -14-numberOfTasks);
            TaskBean task = new TaskBean();
            task.setVendor(vendorName);
            task.setRequestedBy("customer");
            task.setRequestedOn(date);
            task.setTaskType(TaskType.ProfileSurvey);
            activeTasks.add(task);
        }
        if (numberOfTasks > 2) {
            Date date = new Date();
            CalendarUtil.addDaysToDate(date, -7-numberOfTasks);
            TaskBean task = new TaskBean();
            task.setVendor(vendorName);
            task.setRequestedBy("customer");
            task.setRequestedOn(date);
            task.setTaskType(TaskType.SecuritySurvey);
            activeTasks.add(task);
        }
    }


    @Override
    public void getMyDetails(AsyncCallback<MyDetailsBean> asyncCallback) {
        asyncCallback.onSuccess(copyDetails(myDetailsBean));        
    }


    @Override
    public void saveMyDetails(MyDetailsBean updatedDetails, AsyncCallback<String> asyncCallback) {
        myDetailsBean = copyDetails(updatedDetails);
        asyncCallback.onSuccess("Saved"); 
        
    }
    
    private MyDetailsBean copyDetails(MyDetailsBean bean) {
        MyDetailsBean copy = new MyDetailsBean();
        copy.setEmail(bean.getEmail());
        copy.setName(bean.getName());
        copy.setPhone(bean.getPhone());
        copy.setTitle(bean.getTitle());
        copy.setUserId(bean.getUserId());
        copy.setVendorAccess(bean.getVendorAccess());
        return copy;
    }


    @Override
    public void getMyTasks(AsyncCallback<SortedSet<TaskBean>> asyncCallback) {
        asyncCallback.onSuccess(activeTasks);
    }


    @Override
    public void getCompletedTasks(AsyncCallback<SortedSet<TaskBean>> asyncCallback) {
        asyncCallback.onSuccess(completedTasks);
    }

    @Override
    public void completeTask(TaskBean updatedDetails, AsyncCallback<String> asyncCallback) {
        activeTasks.remove(updatedDetails);
        updatedDetails.setCompletedBy("logged in user");
        updatedDetails.setCompletedOn(new Date());
        completedTasks.add(updatedDetails);

        asyncCallback.onSuccess(updatedDetails.getTaskType().getDescription() + " Completed.  Thank you!");
    }


	@Override
	public void updateDocumentationTask(String vendorName, Collection<DocumentationBean> documentationList) {
		DocumentationTaskBean documentationTaskBean = documentationTasks.get(vendorName);
		if (null == documentationTaskBean) {
			documentationTaskBean = createDocumentationTask(vendorName);
		}
		
		documentationTaskBean.addDocumentation(documentationList);
		
		if (documentationTaskBean.getNumberOfDocumentsRequired() > 0) {
			activeTasks.add(documentationTaskBean);	
			completedTasks.remove(documentationTaskBean);
		} else {
			activeTasks.remove(documentationTaskBean);	
			completedTasks.add(documentationTaskBean);				
		}
		
	}

	private DocumentationTaskBean createDocumentationTask(final String vendorName) {
		DocumentationTaskBean task = new DocumentationTaskBean();
		task.setVendor(vendorName);
		
		documentationTasks.put(vendorName, task);
		
		return task;
	}


	@Override
	public void getDocumentation(String vendorId, DocumentationBean documentation, AsyncCallback<DocumentationBean> asyncCallback) {
		DocumentationTaskBean documentationTask = documentationTasks.get(vendorId);
		if (null != documentationTask) {
			String documentationName = documentation.getDocumentationName();
			for (DocumentationBean documentationItem: documentationTask.getDocumentationList()) {
				String currentDocumentationName = documentationItem.getDocumentationName();
				if (documentationName.equalsIgnoreCase(currentDocumentationName)) {
					asyncCallback.onSuccess(documentationItem);
					return;
				}
			}
			asyncCallback.onFailure(new Exception("No documentation found for " + documentationName));			
		} else {
			asyncCallback.onFailure(new Exception("No documentation items found for " + vendorId));
		}
	}


	@Override
	public void getDocumentationList(String vendorId, AsyncCallback<Collection<DocumentationBean>> asyncCallback) {
		DocumentationTaskBean documentationTask = documentationTasks.get(vendorId);
		if (null != documentationTask) {
			asyncCallback.onSuccess(documentationTask.getDocumentationList());
		} else {
			asyncCallback.onFailure(new Exception("No documentation items found for " + vendorId));
		}
		
	}

}
