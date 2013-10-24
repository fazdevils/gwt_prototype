package com.vincentfazio.ui.model.mock;

import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.LogoutModel;
import com.vincentfazio.ui.view.StatusDisplay;

public class LogoutMockModel implements LogoutModel {

    private StatusDisplay statusDisplay;
    
    public LogoutMockModel(GwtGlobals globals) {
        super();
        this.statusDisplay = globals.getStatusDisplay();
    }

    @Override
    public void logout() {
        statusDisplay.handleStatusUpdate(new StatusBean("Simulated Log Out For Development"));
    }
    

}
