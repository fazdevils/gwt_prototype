package com.vincentfazio.ui.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.MyPasswordModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.MyPasswordDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class MyPasswordSaveActivity extends GwtActivity {

    private final Globals globals;
    
    
    public MyPasswordSaveActivity(Globals globals) {
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        MyPasswordModel model = (MyPasswordModel) globals.getModel(MyPasswordModel.class);
        MyPasswordDisplay passwordDisplay = (MyPasswordDisplay) globals.getDisplay(MyPasswordDisplay.class);
        model.changePassword(passwordDisplay.getPasswordUpdateBean(),
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("New Password Saved"));
                        MyPasswordDisplay passwordDisplay = (MyPasswordDisplay) globals.getDisplay(MyPasswordDisplay.class);
                        passwordDisplay.resetDisplay();
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }
    
}
