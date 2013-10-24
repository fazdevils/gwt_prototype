package com.vincentfazio.ui.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.MyDetailsBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.MyDetailsModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.HeaderDisplay;
import com.vincentfazio.ui.view.MyDetailsDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class MyDetailsActivity extends GwtActivity {

    private Globals globals;

    public MyDetailsActivity(Globals globals) {
        super();
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {        
        MyDetailsModel model = (MyDetailsModel) globals.getModel(MyDetailsModel.class);
        model.getMyDetails(
                new AsyncCallback<MyDetailsBean>() {
                    public void onSuccess(MyDetailsBean result) {
                        HeaderDisplay headerDisplay = (HeaderDisplay) globals.getDisplay(HeaderDisplay.class);
                        headerDisplay.setMyDetails(result);
                        MyDetailsDisplay userSettingsDisplay = (MyDetailsDisplay) globals.getDisplay(MyDetailsDisplay.class);
                        userSettingsDisplay.setMyDetails(result);
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                    }
                }
             );
    }
    
    @Override
    public String mayStop() {
        MyDetailsDisplay userSettingsDisplay = (MyDetailsDisplay) globals.getDisplay(MyDetailsDisplay.class);
        if (userSettingsDisplay.hasUnsavedChanges() && userSettingsDisplay.isValid()) {
            new MyDetailsSaveActivity(globals).start(null, null);
        } else if (!userSettingsDisplay.isValid()) {
            StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
            statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
        }
        return null;
    }


}
