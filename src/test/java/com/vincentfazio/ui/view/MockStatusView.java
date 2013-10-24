package com.vincentfazio.ui.view;

import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.view.StatusDisplay;

public class MockStatusView implements StatusDisplay {

    private StatusBean status;
    
    @Override
    public void handleStatusUpdate(StatusBean status) {
        this.status = status;
    }

    public StatusBean getStatus() {
        return status;
    }

}
