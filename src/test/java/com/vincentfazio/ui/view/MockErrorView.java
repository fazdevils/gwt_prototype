package com.vincentfazio.ui.view;

import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.view.ErrorDisplay;

public class MockErrorView implements ErrorDisplay {

    private ErrorBean error;
    
    @Override
    public void handleError(ErrorBean error) {
        this.error = error;
    }

    public ErrorBean getError() {
        return error;
    }

}
