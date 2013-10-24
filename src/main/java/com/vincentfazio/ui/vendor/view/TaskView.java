package com.vincentfazio.ui.vendor.view;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.vincentfazio.ui.animation.BackgroundFadeOutCellTableAnimation;
import com.vincentfazio.ui.animation.Color;
import com.vincentfazio.ui.bean.NotificationBean;
import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.bean.TaskType;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.place.DocumentationPlace;
import com.vincentfazio.ui.vendor.place.ProfileSurveyPlace;
import com.vincentfazio.ui.vendor.place.SecuritySurveyPlace;
import com.vincentfazio.ui.vendor.place.VendorDetailsPlace;
import com.vincentfazio.ui.view.NotifyDisplay;
import com.vincentfazio.ui.view.component.NotificationAcknowledgementCallback;
import com.vincentfazio.ui.view.component.cell.TaskCell;
import com.vincentfazio.ui.view.util.GwtCellTableResources;

public class TaskView extends Composite {

    private static TaskViewUiBinder uiBinder = GWT.create(TaskViewUiBinder.class);


    interface TaskViewUiBinder extends UiBinder<Widget, TaskView> {
    }

    @UiField(provided=true)
    CellTable<TaskBean> tasks;
    
    @UiField(provided=true)
    SimplePager taskPager;

    @UiField
    DivElement taskDiv;
    
    @UiField
    DivElement taskPagerDiv;
    
    @UiField
    DivElement emptyTaskDiv;
    
    private ListDataProvider<TaskBean> taskDataProvider;
    
    public TaskView() {
        CellTable.Resources resources = GWT.create(GwtCellTableResources.class);
        tasks = new CellTable<TaskBean>(0, resources); // the page size should be set in the UIBinder definition
        Column<TaskBean, TaskBean> taskNameColumn = new Column<TaskBean, TaskBean>(new TaskCell()) {
			@Override
			public TaskBean getValue(TaskBean task) {
				return task;
			}
        };
        tasks.addColumn(taskNameColumn, "Task");
        
        final NoSelectionModel<TaskBean> selectionModel = new NoSelectionModel<TaskBean>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                TaskBean selectedTask = selectionModel.getLastSelectedObject(); 
                if (selectedTask.getTaskType().equals(TaskType.DemographicSurvey)) {
                    GwtVendorGlobals.getInstance().gotoPlace(new VendorDetailsPlace(selectedTask.getVendor()));
                    VendorDetailsDisplay detailsDisplay = (VendorDetailsDisplay) GwtVendorGlobals.getInstance().getDisplay(VendorDetailsDisplay.class);
                    detailsDisplay.highlightVendorDetailsPane();  
                } else if  (selectedTask.getTaskType().equals(TaskType.ProfileSurvey)) {
                    GwtVendorGlobals.getInstance().gotoPlace(new ProfileSurveyPlace(selectedTask.getVendor(), null));
                    // TODO highlight the submit survey button??? 
                } else if  (selectedTask.getTaskType().equals(TaskType.SecuritySurvey)) {
                    GwtVendorGlobals.getInstance().gotoPlace(new SecuritySurveyPlace(selectedTask.getVendor(), null));
                    // TODO highlight the submit survey button??? 
                } else if  (selectedTask.getTaskType().equals(TaskType.DocumentationRequest)) {
                    GwtVendorGlobals.getInstance().gotoPlace(new DocumentationPlace(selectedTask.getVendor(), null));
                }
            }

        });
        
        tasks.setSelectionModel(selectionModel);
        
        taskPager = new SimplePager();
        taskPager.setDisplay(tasks);
        
        taskDataProvider = new ListDataProvider<TaskBean>();
        taskDataProvider.addDataDisplay(tasks);
      
        initWidget(uiBinder.createAndBindUi(this));
    }

    public List<TaskBean> getTasks()  {
        return taskDataProvider.getList();
    }

    public void setTasks(
            SortedSet<TaskBean> userTasks, 
            ArrayList<Animation> notificationAnimations, 
            boolean showNotification,
            NotificationAcknowledgementCallback acknowledgementCallback) {
        List<TaskBean> taskList = taskDataProvider.getList();
        taskList.clear();
        taskList.addAll(userTasks);
        
        handleTaskListChange();
        
        taskDataProvider.refresh();
        if (userTasks.size() > 0) {
            if (showNotification) {
                Color bgColor = GwtVendorGlobals.getInstance().getNotificationColor();
                BackgroundFadeOutCellTableAnimation animation = new BackgroundFadeOutCellTableAnimation(tasks, 0, bgColor);
                notificationAnimations.add(0, animation);

                TaskBean taskNotification = userTasks.first();
                NotificationBean notificationBean = new NotificationBean("REMINDER: " + taskNotification.getTaskType().getDescription());
                
                NotifyDisplay notifyDisplay = GwtVendorGlobals.getInstance().getNotificationDisplay();
                notifyDisplay.handleNotification(notificationBean, notificationAnimations, acknowledgementCallback);
            }

        }
    }


    private void handleTaskListChange() {
        List<TaskBean> taskList = taskDataProvider.getList();
        Style listStyle = taskDiv.getStyle();
        Style emptyStyle = emptyTaskDiv.getStyle();

        if (taskList.size() > 0) {
            listStyle.setDisplay(Display.BLOCK);
            emptyStyle.setDisplay(Display.NONE);
            
            Style pagerStyle = taskPagerDiv.getStyle();
            if (taskList.size() > tasks.getPageSize()) {
                pagerStyle.setDisplay(Display.BLOCK);
            } else {
                pagerStyle.setDisplay(Display.NONE);                
            }
        } else {
            listStyle.setDisplay(Display.NONE);
            emptyStyle.setDisplay(Display.BLOCK);            
        }
        
    }
}