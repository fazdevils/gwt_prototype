package com.vincentfazio.ui.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.MyDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.MyDetailsDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class MyDetailsSaveActivity extends GwtActivity {

    private final Globals globals;
    
    
    public MyDetailsSaveActivity(Globals globals) {
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        MyDetailsModel model = (MyDetailsModel) globals.getModel(MyDetailsModel.class);
        MyDetailsDisplay userSettings = (MyDetailsDisplay) globals.getDisplay(MyDetailsDisplay.class);
        model.saveMyDetails(userSettings.getMyDetails(),
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("User Settings Saved"));
                        new MyDetailsActivity(globals).start(null, null);
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }
    
}
