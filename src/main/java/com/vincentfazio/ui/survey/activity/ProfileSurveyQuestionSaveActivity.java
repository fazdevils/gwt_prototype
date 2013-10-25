package com.vincentfazio.ui.survey.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.ProfileSurveyModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class ProfileSurveyQuestionSaveActivity extends GwtActivity {

    private String companyId;
    private QuestionBean question;
    private Globals globals;
    
    
    public ProfileSurveyQuestionSaveActivity(String companyId, QuestionBean question, Globals globals) {
        this.companyId = companyId;
        this.question = question;
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        ProfileSurveyModel model = (ProfileSurveyModel) globals.getModel(ProfileSurveyModel.class);
        model.answerQuestion(companyId, question, new AsyncCallback<String>() {
            
            @Override
            public void onSuccess(String message) {
                StatusDisplay statusDisplay = globals.getStatusDisplay();
                statusDisplay.handleStatusUpdate(new StatusBean(message));
                new ProfileSurveyQuestionActivity(companyId, question, globals).start(null, null);
            }
            
            @Override
            public void onFailure(Throwable caught) {
                ErrorDisplay errorDisplay = globals.getErrorDisplay();
                errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
            }
        });
    }

}
