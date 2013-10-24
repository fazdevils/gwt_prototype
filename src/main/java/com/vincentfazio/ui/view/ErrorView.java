package com.vincentfazio.ui.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.vincentfazio.ui.animation.FadeInAnimation;
import com.vincentfazio.ui.animation.FadeOutAnimation;

public class ErrorView implements ErrorDisplay {

    private static final int ANIMATION_DURATION = 900;

    @Override
    public void handleError(com.vincentfazio.ui.bean.ErrorBean error) {
        final RootPanel errorContainer = RootPanel.get("error");
        
        HTML errorLabel = new HTML();
        errorLabel.setHTML(error.getErrorDescription() + "<div class=\"error-dismiss\">X</div>");
        errorLabel.setTitle("click to dismiss");
        
        errorContainer.clear();
        errorContainer.add(errorLabel);
        
        new FadeInAnimation(errorContainer).run(ANIMATION_DURATION);
        
        errorLabel.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                new FadeOutAnimation(errorContainer).run(ANIMATION_DURATION);
                
            }
        });
        
    }

}
