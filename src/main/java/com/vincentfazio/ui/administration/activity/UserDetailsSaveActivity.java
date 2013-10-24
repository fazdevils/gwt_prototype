package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.view.UserDetailsDisplay;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.UserDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.UserDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class UserDetailsSaveActivity extends GwtActivity {

    private final Globals globals;
    private final UserDetailsBean userDetails;

    public UserDetailsSaveActivity(Globals globals, UserDetailsBean userDetails) {
        super();
        this.globals = globals;
        this.userDetails = userDetails;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        UserDetailsModel model = (UserDetailsModel) globals.getModel(UserDetailsModel.class);
        model.saveUser(userDetails,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("User Changes Saved"));
                        UserDetailsDisplay detailsDisplay = (UserDetailsDisplay) globals.getDisplay(UserDetailsDisplay.class);
                        detailsDisplay.setHasUnsavedChanges(false);
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }
    
}
