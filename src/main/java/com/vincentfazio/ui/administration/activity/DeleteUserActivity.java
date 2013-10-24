package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.UserDetailsDisplay;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.UserDetailsBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.UserDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class DeleteUserActivity extends GwtActivity {

    private final Globals globals;
    private final String userId;

    public DeleteUserActivity(Globals globals, String userId) {
        super();
        this.globals = globals;
        this.userId = userId;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        UserDetailsModel model = (UserDetailsModel) globals.getModel(UserDetailsModel.class);
        model.deleteUser(userId,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("User Deleted"));
                        UserDetailsDisplay detailsDisplay = (UserDetailsDisplay) globals.getDisplay(UserDetailsDisplay.class);
                        detailsDisplay.setUserDetails(new UserDetailsBean());
                        new UserListActivity(GwtAdminGlobals.getInstance(), true).start(null, null);
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }
    
}
