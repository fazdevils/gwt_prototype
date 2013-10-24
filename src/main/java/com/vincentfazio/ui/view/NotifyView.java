package com.vincentfazio.ui.view;

import java.util.ArrayList;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.vincentfazio.ui.animation.FadeInAnimation;
import com.vincentfazio.ui.animation.FadeOutAnimation;
import com.vincentfazio.ui.bean.NotificationBean;
import com.vincentfazio.ui.view.component.NotificationAcknowledgementCallback;

public class NotifyView implements NotifyDisplay {

    private static final int ANIMATION_DURATION = 900;

    @Override
    public void handleNotification(final NotificationBean notification, final ArrayList<Animation> notificationAnimations, final NotificationAcknowledgementCallback callback) {
        final RootPanel notificationContainer = RootPanel.get("notify");
        
        HTML notificationLabel = new HTML();
        notificationLabel.setHTML(notification.getNotificationDescription() + "<div class=\"notify-dismiss\">X</div>");
        notificationLabel.setTitle("click to dismiss");
        
        notificationContainer.clear();
        notificationContainer.add(notificationLabel);
        
        new FadeInAnimation(notificationContainer).run(ANIMATION_DURATION);
        
        notificationLabel.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                if (null != callback) {
                    callback.acknowledgeNotification();
                }
        
                new FadeOutAnimation(notificationContainer).run(ANIMATION_DURATION);
                if (null != notificationAnimations) {
                    final int duration = 1500;
                    int count = 0;
                    for (final Animation animation: notificationAnimations) {
                        Timer t = new Timer() {
                            public void run() {
                                animation.run(duration);
                            }
                        };

                        t.schedule(((1000 * count) + 1));
                        ++count;
                    }
                    
                }
            }
        });
        
    }

}
