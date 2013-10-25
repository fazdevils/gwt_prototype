package com.vincentfazio.ui.survey.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.ProfileSurveyModel;
import com.vincentfazio.ui.survey.view.ProfileSurveyDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;

public class ProfileSurveyQuestionActivity extends GwtActivity {

    private String companyId;
    private QuestionBean question;
    private Globals globals;
    
    
    public ProfileSurveyQuestionActivity(String companyId, QuestionBean question, Globals globals) {
        this.companyId = companyId;
        this.question = question;
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        ProfileSurveyModel model = (ProfileSurveyModel) globals.getModel(ProfileSurveyModel.class);
        model.getQuestion(companyId, question, new AsyncCallback<QuestionBean>() {
            
            @Override
            public void onSuccess(QuestionBean result) {
                // update the display with the set of questions
                ProfileSurveyDisplay display = (ProfileSurveyDisplay) globals.getDisplay(ProfileSurveyDisplay.class);
                display.setQuestion(result);
            }
            
            @Override
            public void onFailure(Throwable caught) {
                ErrorDisplay errorDisplay = globals.getErrorDisplay();
                errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
            }
        });
    }

}
