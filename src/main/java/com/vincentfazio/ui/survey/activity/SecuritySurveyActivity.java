package com.vincentfazio.ui.survey.activity;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.SecuritySurveyModel;
import com.vincentfazio.ui.survey.global.GwtSurveyGlobals;
import com.vincentfazio.ui.survey.view.SecuritySurveyDisplay;
import com.vincentfazio.ui.survey.view.SecuritySurveyQuestionDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class SecuritySurveyActivity extends GwtActivity {

    private String companyId;
    private QuestionBean question;
    private Globals globals;
    
    
    public SecuritySurveyActivity(String companyId, QuestionBean question, Globals globals) {
        this.companyId = companyId;
        this.question = question;
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        SecuritySurveyModel model = (SecuritySurveyModel) globals.getModel(SecuritySurveyModel.class);
        final SecuritySurveyDisplay display = (SecuritySurveyDisplay) globals.getDisplay(SecuritySurveyDisplay.class);
        
        if (display.getCompanyId().equals(companyId)) { // the questions should already be loaded for this company so skip the getQuestions WS call
            if (null != question) {
                new SecuritySurveyQuestionActivity(companyId, question, globals).start(null, null);            
            }
        } else {
            model.getQuestions(companyId, new AsyncCallback<List<QuestionBean>>() {
                
                @Override
                public void onSuccess(List<QuestionBean> result) {
                    // update the display with the set of questions
                    display.setCompanyId(companyId);
                    display.setQuestionList(result);
    
                    if (null == question) {
                        question = result.get(0);
                    }
                    new SecuritySurveyQuestionActivity(companyId, question, globals).start(null, null);
                }
                
                @Override
                public void onFailure(Throwable caught) {
                    ErrorDisplay errorDisplay = globals.getErrorDisplay();
                    errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                }
            });
        }
    }

    @Override
    public String mayStop() {
        SecuritySurveyQuestionDisplay questionDisplay = (SecuritySurveyQuestionDisplay) globals.getDisplay(SecuritySurveyQuestionDisplay.class);
        if (questionDisplay.hasUnsavedChanges() && questionDisplay.isValid()) {
            new SecuritySurveyQuestionSaveActivity(companyId, questionDisplay.getQuestion(), GwtSurveyGlobals.getInstance()).start(null, null);
        } else if (!questionDisplay.isValid()) {
            StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
            statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
        }
        return null;
    }

}
