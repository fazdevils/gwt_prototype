package com.vincentfazio.ui.model.mock;

import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.ErrorModel;
import com.vincentfazio.ui.view.StatusDisplay;

public class ErrorMockModel implements ErrorModel {

    private StatusDisplay statusDisplay;
    
    public ErrorMockModel(GwtGlobals globals) {
        super();
        this.statusDisplay = globals.getStatusDisplay();
    }

    @Override
    public void logError(String url, String errorDescription) {
        statusDisplay.handleStatusUpdate(new StatusBean("Simulated Error For Development"));
    }

}
