package com.vincentfazio.ui.view;

import com.vincentfazio.ui.bean.StatusBean;

public interface StatusDisplay extends Display {
    
    void handleStatusUpdate(StatusBean status);
}
