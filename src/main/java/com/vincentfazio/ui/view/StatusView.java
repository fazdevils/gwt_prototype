package com.vincentfazio.ui.view;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.vincentfazio.ui.animation.FadeInAnimation;
import com.vincentfazio.ui.animation.FadeOutAnimation;
import com.vincentfazio.ui.bean.StatusBean;

public class StatusView implements StatusDisplay {

    private static final int ANIMATION_DURATION = 900;

    @Override
    public void handleStatusUpdate(StatusBean status) {
        final RootPanel errorContainer = RootPanel.get("status");
        Label errorLabel = new Label();
        errorLabel.setText(status.getStatusDescription());
        errorContainer.clear();
        errorContainer.add(errorLabel);
        new FadeInAnimation(errorContainer).run(ANIMATION_DURATION);
        Timer t = new Timer() {
            public void run() {
                new FadeOutAnimation(errorContainer).run(ANIMATION_DURATION);
            }
        };

        // Schedule the timer to run once in 1.5 seconds.
        t.schedule(1500);
                
    }

}
