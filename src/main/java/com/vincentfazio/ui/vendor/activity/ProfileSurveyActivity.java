package com.vincentfazio.ui.vendor.activity;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.QuestionBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.ProfileSurveyModel;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.ProfileSurveyDisplay;
import com.vincentfazio.ui.vendor.view.ProfileSurveyQuestionDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class ProfileSurveyActivity extends GwtActivity {

    private String vendorId;
    private QuestionBean question;
    private Globals globals;
    
    
    public ProfileSurveyActivity(String vendorId, QuestionBean question, Globals globals) {
        this.vendorId = vendorId;
        this.question = question;
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        ProfileSurveyModel model = (ProfileSurveyModel) globals.getModel(ProfileSurveyModel.class);
        final ProfileSurveyDisplay display = (ProfileSurveyDisplay) globals.getDisplay(ProfileSurveyDisplay.class);
        
        if (display.getVendorId().equals(vendorId)) { // the questions should already be loaded for this vendor so skip the getQuestions WS call
            if (null != question) {
                new ProfileSurveyQuestionActivity(vendorId, question, globals).start(null, null);            
            }
        } else {
            model.getQuestions(vendorId, new AsyncCallback<List<QuestionBean>>() {
                
                @Override
                public void onSuccess(List<QuestionBean> result) {
                    // update the display with the set of questions
                    display.setVendorId(vendorId);
                    display.setQuestionList(result);
    
                    if (null == question) {
                        question = result.get(0);
                    }
                    new ProfileSurveyQuestionActivity(vendorId, question, globals).start(null, null);
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
        ProfileSurveyQuestionDisplay questionDisplay = (ProfileSurveyQuestionDisplay) globals.getDisplay(ProfileSurveyQuestionDisplay.class);
        if (questionDisplay.hasUnsavedChanges() && questionDisplay.isValid()) {
            new ProfileSurveyQuestionSaveActivity(vendorId, questionDisplay.getQuestion(), GwtVendorGlobals.getInstance()).start(null, null);
        } else if (!questionDisplay.isValid()) {
            StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
            statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
        }
        return null;
    }

}
