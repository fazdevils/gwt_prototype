package com.vincentfazio.ui.survey.view;

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
import com.vincentfazio.ui.survey.activity.ActiveTasksActivity;
import com.vincentfazio.ui.survey.activity.CompleteTaskActivity;
import com.vincentfazio.ui.survey.activity.CompanyAccessActivity.CompanyAccessDisplayType;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.place.SecuritySurveyPlace;
import com.vincentfazio.ui.view.component.cell.SurveyQuestionCell;
import com.vincentfazio.ui.view.util.GwtCellTableResources;

public class SecuritySurveyView extends Composite implements SecuritySurveyDisplay {

    private static SecuritySurveyViewUiBinder uiBinder = GWT.create(SecuritySurveyViewUiBinder.class);


    interface SecuritySurveyViewUiBinder extends UiBinder<Widget, SecuritySurveyView> {}

    @UiField(provided=true)
    CompanySelectorView companySelector;
       
    @UiField(provided=true)
    CellTable<QuestionBean> questions;

    @UiField
    Label surveyStatus;

    @UiField
    Button surveyCompleteButton;

    @UiField
    SecuritySurveyQuestionView question;
    
    
    private TaskBean openSurveyTask = null;
    private Collection<TaskBean> openTasks;
    private final SingleSelectionModel<QuestionBean> selectionModel;
    

    public SecuritySurveyView() {
        CellTable.Resources resources = GWT.create(GwtCellTableResources.class);  // if we don't want the whole row highlighted when selected, we need to create a new stylesheet and override cellTableSelectedRow & cellTableSelectedRowCell 
        
        questions = new CellTable<QuestionBean>(0, resources); // the page size should be set in the UIBinder definition
        
        selectionModel = new SingleSelectionModel<QuestionBean>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                QuestionBean selectedQuestion = selectionModel.getSelectedObject(); 
                GwtSurveyGlobals.getInstance().gotoPlace(new SecuritySurveyPlace(getCompanyId(), selectedQuestion));
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

        companySelector = new CompanySelectorView(new CompanySelectorView.CompanySelectionHandler() {           
            @Override
            public void handleCompanyChange(String companyId) {               
                GwtSurveyGlobals.getInstance().gotoPlace(new SecuritySurveyPlace(companyId, null));
            }
        }, CompanyAccessDisplayType.SecuritySurvey);

        initWidget(uiBinder.createAndBindUi(this));
        
        surveyCompleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new CompleteTaskActivity(openSurveyTask, GwtSurveyGlobals.getInstance()).start(null, null);                
            }
        });

        GwtSurveyGlobals.getInstance().registerDisplay(SecuritySurveyDisplay.class, this);        
        
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
    public void setCompanySearchOptions(List<String> companyList) {
        companySelector.setCompanySearchOptions(companyList);
    }

    @Override
    public void setCompanyId(String companyId) {
        companySelector.setCompanyId(companyId);
        setSurveyTask();
    }

    @Override
    public void setActiveTasks(SortedSet<TaskBean> activeTasks) {
        this.openTasks = activeTasks;
        setSurveyTask();
    }

    private void setSurveyTask() {
        if (null == openTasks) {
            new ActiveTasksActivity(GwtSurveyGlobals.getInstance()).start(null, null);
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
        return task.getTaskType().equals(TaskType.SecuritySurvey) && task.getCompany().equals(getCompanyId());
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
        
        this.question.setQuestion(getCompanyId(), question);
        questions.redraw();
        updateSurveyStatus();
    }

    @Override
    public String getCompanyId() {
        return companySelector.getCompanyId();
    }

}
