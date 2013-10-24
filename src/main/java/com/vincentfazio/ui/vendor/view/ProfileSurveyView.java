package com.vincentfazio.ui.vendor.view;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.bean.TaskType;
import com.vincentfazio.ui.bean.QuestionBean.QuestionStatus;
import com.vincentfazio.ui.vendor.activity.ActiveTasksActivity;
import com.vincentfazio.ui.vendor.activity.CompleteTaskActivity;
import com.vincentfazio.ui.vendor.activity.VendorAccessActivity.VendorAccessDisplayType;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.place.ProfileSurveyPlace;
import com.vincentfazio.ui.view.component.cell.SurveyQuestionCell;
import com.vincentfazio.ui.view.util.GwtCellTableResources;

public class ProfileSurveyView extends Composite implements ProfileSurveyDisplay {

    private static ProfileSurveyViewUiBinder uiBinder = GWT.create(ProfileSurveyViewUiBinder.class);


    interface ProfileSurveyViewUiBinder extends UiBinder<Widget, ProfileSurveyView> {}

    @UiField(provided=true)
    VendorSelectorView vendorSelector;
       
    @UiField(provided=true)
    CellTable<QuestionBean> questions;

    @UiField
    Label surveyStatus;

    @UiField
    Button surveyCompleteButton;

    @UiField
    ProfileSurveyQuestionView question;
    
    
    private TaskBean openSurveyTask = null;
    private Collection<TaskBean> openTasks;
    private final SingleSelectionModel<QuestionBean> selectionModel;
    

    public ProfileSurveyView() {
        CellTable.Resources resources = GWT.create(GwtCellTableResources.class);
        
        questions = new CellTable<QuestionBean>(0, resources); // the page size should be set in the UIBinder definition

        selectionModel = new SingleSelectionModel<QuestionBean>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                QuestionBean selectedQuestion = selectionModel.getSelectedObject(); 
                GwtVendorGlobals.getInstance().gotoPlace(new ProfileSurveyPlace(getVendorId(), selectedQuestion));
            }
        });
        
        questions.setSelectionModel(selectionModel);

        Column<QuestionBean, QuestionBean> questionColumn = new Column<QuestionBean, QuestionBean>(new SurveyQuestionCell(selectionModel)) {
            @Override
            public QuestionBean getValue(QuestionBean question) {
                return question;
            }
        };
        questions.addColumn(questionColumn, "Question");
        
        vendorSelector = new VendorSelectorView(new VendorSelectorView.VendorSelectionHandler() {           
            @Override
            public void handleVendorChange(String vendorId) {               
                GwtVendorGlobals.getInstance().gotoPlace(new ProfileSurveyPlace(vendorId, null));
            }
        }, VendorAccessDisplayType.ProfileSurvey);

        initWidget(uiBinder.createAndBindUi(this));
        
        surveyCompleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new CompleteTaskActivity(openSurveyTask, GwtVendorGlobals.getInstance()).start(null, null);                
            }
        });

        GwtVendorGlobals.getInstance().registerDisplay(ProfileSurveyDisplay.class, this);        
        
    }
    
    @Override
    public void setQuestionList(List<QuestionBean> questionList) {
        questions.setRowData(questionList);        
        updateSurveyStatus();
    }

    private void updateSurveyStatus() {
        List<QuestionBean> questionList = questions.getVisibleItems();

        QuestionStatus surveyStatus = new QuestionStatus();
        for (QuestionBean question: questionList) {
            QuestionStatus questionStatus = question.getQuestionStatus();
            surveyStatus.numberOfSubquestions += questionStatus.numberOfSubquestions;
            surveyStatus.numberOfAnsweredSubquestions += questionStatus.numberOfAnsweredSubquestions;
        }
   
        this.surveyStatus.setText(surveyStatus.numberOfAnsweredSubquestions + " of " + surveyStatus.numberOfSubquestions + " survey questions complete");
    }

    @Override
    public void setVendorSearchOptions(List<String> vendorList) {
        vendorSelector.setVendorSearchOptions(vendorList);
    }

    @Override
    public void setVendorId(String vendorId) {
        vendorSelector.setVendorId(vendorId);
        setSurveyTask();
    }

    @Override
    public void setActiveTasks(SortedSet<TaskBean> activeTasks) {
        this.openTasks = activeTasks;
        setSurveyTask();
    }

    private void setSurveyTask() {
        if (null == openTasks) {
            new ActiveTasksActivity(GwtVendorGlobals.getInstance()).start(null, null);
            return;
        }
        
        openSurveyTask = null;
        for (TaskBean task: openTasks) {
            if (openSurveyTask(task)) {
                openSurveyTask = task;
                break;
            }
        }
        boolean showSurveyCompleteButton = null != openSurveyTask;
        surveyCompleteButton.setVisible(showSurveyCompleteButton);        
    }

    private boolean openSurveyTask(TaskBean task) {
        return task.getTaskType().equals(TaskType.ProfileSurvey) && task.getVendor().equals(getVendorId());
    }

    @Override
    public void setQuestion(QuestionBean question) {
        List<QuestionBean> questionList = questions.getVisibleItems();
        int questionIndex = questionList.indexOf(question);
        QuestionBean questionToUpdate = questions.getVisibleItem(questionIndex);
        questionToUpdate.setAnsweredBy(question.getAnsweredBy());
        questionToUpdate.setAnsweredTime(question.getAnsweredTime());
        questionToUpdate.setAnswerValue(question.getAnswerValue());
        questionToUpdate.setChoices(question.getChoices());
        questionToUpdate.setQuestionId(question.getQuestionId());
        questionToUpdate.setQuestionText(question.getQuestionText());
        questionToUpdate.setQuestionType(question.getQuestionType());
        questionToUpdate.setSubquestions(question.getSubquestions());
        questionToUpdate.setValidationType(question.getValidationType());
        
        selectionModel.setSelected(questionToUpdate, true);
        
        this.question.setQuestion(getVendorId(), question);
        questions.redraw();
        updateSurveyStatus();
    }

    @Override
    public String getVendorId() {
        return vendorSelector.getVendorId();
    }

}
