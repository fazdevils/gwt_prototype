package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.administration.place.UserDetailsPlace;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.UserDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class CreateUserActivity extends GwtActivity {

    private final Globals globals;
    private final String userId;

    public CreateUserActivity(Globals globals, String userId) {
        super();
        this.globals = globals;
        this.userId = userId;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        UserDetailsModel model = (UserDetailsModel) globals.getModel(UserDetailsModel.class);
        model.createUser(userId,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("User Created"));
                        new UserListActivity(globals, false).start(null, null);
                        globals.gotoPlace(new UserDetailsPlace(userId));
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }
    
}
