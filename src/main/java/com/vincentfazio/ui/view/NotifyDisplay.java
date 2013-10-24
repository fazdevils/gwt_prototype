package com.vincentfazio.ui.view;

import java.util.ArrayList;

import com.google.gwt.animation.client.Animation;
import com.vincentfazio.ui.bean.NotificationBean;
import com.vincentfazio.ui.view.component.NotificationAcknowledgementCallback;

public interface NotifyDisplay extends Display {
    
    void handleNotification(
            NotificationBean notification, 
            ArrayList<Animation> notificationAnimations, 
            final NotificationAcknowledgementCallback callback);
}
