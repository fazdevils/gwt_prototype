package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.UserPasswordResetModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class UserPasswordResetActivity extends GwtActivity {

    private final Globals globals;
    private final String userId;

    public UserPasswordResetActivity(Globals globals, String userId) {
        super();
        this.globals = globals;
        this.userId = userId;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        UserPasswordResetModel model = (UserPasswordResetModel) globals.getModel(UserPasswordResetModel.class);
        model.resetUserPassword(userId,
            new AsyncCallback<String>() {
                public void onSuccess(String result) {
                    StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                    statusDisplay.handleStatusUpdate(new StatusBean("Successful Password Reset For " + userId));
                }
                public void onFailure(Throwable caught) {
                    ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                    errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                }
            }
         );
    }
    
}
