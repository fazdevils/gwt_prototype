package com.vincentfazio.ui.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.view.MyPasswordDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class MyPasswordActivity extends GwtActivity {

    private Globals globals;

    public MyPasswordActivity(Globals globals) {
        super();
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {        
    }
    
    @Override
    public String mayStop() {
        MyPasswordDisplay userPasswordDisplay = (MyPasswordDisplay) globals.getDisplay(MyPasswordDisplay.class);
        if (userPasswordDisplay.hasUnsavedChanges()) {
            StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
            statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
            userPasswordDisplay.resetDisplay();
        }
        return null;
    }


}
