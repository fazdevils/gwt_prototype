package com.vincentfazio.ui.survey.view;

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
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.place.DocumentationPlace;
import com.vincentfazio.ui.survey.place.ProfileSurveyPlace;
import com.vincentfazio.ui.survey.place.SecuritySurveyPlace;
import com.vincentfazio.ui.survey.place.CompanyDetailsPlace;
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
                    GwtSurveyGlobals.getInstance().gotoPlace(new CompanyDetailsPlace(selectedTask.getCompany()));
                    CompanyDetailsDisplay detailsDisplay = (CompanyDetailsDisplay) GwtSurveyGlobals.getInstance().getDisplay(CompanyDetailsDisplay.class);
                    detailsDisplay.highlightCompanyDetailsPane();  
                } else if  (selectedTask.getTaskType().equals(TaskType.ProfileSurvey)) {
                    GwtSurveyGlobals.getInstance().gotoPlace(new ProfileSurveyPlace(selectedTask.getCompany(), null));
                    // TODO highlight the submit survey button??? 
                } else if  (selectedTask.getTaskType().equals(TaskType.SecuritySurvey)) {
                    GwtSurveyGlobals.getInstance().gotoPlace(new SecuritySurveyPlace(selectedTask.getCompany(), null));
                    // TODO highlight the submit survey button??? 
                } else if  (selectedTask.getTaskType().equals(TaskType.DocumentationRequest)) {
                    GwtSurveyGlobals.getInstance().gotoPlace(new DocumentationPlace(selectedTask.getCompany(), null));
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
                Color bgColor = GwtSurveyGlobals.getInstance().getNotificationColor();
                BackgroundFadeOutCellTableAnimation animation = new BackgroundFadeOutCellTableAnimation(tasks, 0, bgColor);
                notificationAnimations.add(0, animation);

                TaskBean taskNotification = userTasks.first();
                NotificationBean notificationBean = new NotificationBean("REMINDER: " + taskNotification.getTaskType().getDescription());
                
                NotifyDisplay notifyDisplay = GwtSurveyGlobals.getInstance().getNotificationDisplay();
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