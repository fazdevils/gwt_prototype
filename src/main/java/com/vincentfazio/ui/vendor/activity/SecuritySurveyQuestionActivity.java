package com.vincentfazio.ui.vendor.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.SecuritySurveyModel;
import com.vincentfazio.ui.vendor.view.SecuritySurveyDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;

public class SecuritySurveyQuestionActivity extends GwtActivity {

    private String vendorId;
    private QuestionBean question;
    private Globals globals;
    
    
    public SecuritySurveyQuestionActivity(String vendorId, QuestionBean question, Globals globals) {
        this.vendorId = vendorId;
        this.question = question;
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        SecuritySurveyModel model = (SecuritySurveyModel) globals.getModel(SecuritySurveyModel.class);
        model.getQuestion(vendorId, question, new AsyncCallback<QuestionBean>() {
            
            @Override
            public void onSuccess(QuestionBean result) {
                // update the display with the set of questions
                SecuritySurveyDisplay display = (SecuritySurveyDisplay) globals.getDisplay(SecuritySurveyDisplay.class);
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
