package com.vincentfazio.ui.vendor.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.SecuritySurveyModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class SecuritySurveyQuestionSaveActivity extends GwtActivity {

    private String vendorId;
    private QuestionBean question;
    private Globals globals;
    
    
    public SecuritySurveyQuestionSaveActivity(String vendorId, QuestionBean question, Globals globals) {
        this.vendorId = vendorId;
        this.question = question;
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        SecuritySurveyModel model = (SecuritySurveyModel) globals.getModel(SecuritySurveyModel.class);
        model.answerQuestion(vendorId, question, new AsyncCallback<String>() {
            
            @Override
            public void onSuccess(String message) {
                StatusDisplay statusDisplay = globals.getStatusDisplay();
                statusDisplay.handleStatusUpdate(new StatusBean(message));
                new SecuritySurveyQuestionActivity(vendorId, question, globals).start(null, null);
            }
            
            @Override
            public void onFailure(Throwable caught) {
                ErrorDisplay errorDisplay = globals.getErrorDisplay();
                errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
            }
        });
    }

}
