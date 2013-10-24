package com.vincentfazio.ui.view;

import com.vincentfazio.ui.bean.ErrorBean;

public interface ErrorDisplay extends Display {
    
    void handleError(ErrorBean error);
}
