package com.vincentfazio.ui.vendor.view.component;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.vincentfazio.ui.bean.QuestionBean;

public class TrueFalseButtonComponent extends Composite implements HasClickHandlers {
    
    private final QuestionBean thisQuestion;
    private final ToggleButton yesToggle;
    private final ToggleButton noToggle;
    
    
    public TrueFalseButtonComponent(QuestionBean tfQuestion) {
        super();
        thisQuestion = tfQuestion;

        FlowPanel outerPanel = new FlowPanel();
        
        FlowPanel togglePanel = new FlowPanel();
        togglePanel.addStyleName("gwt-toggle-button");
        outerPanel.add(togglePanel);
        
        yesToggle = new ToggleButton("Yes");
        yesToggle.addStyleName("gwt-toggle-yes");
        yesToggle.addClickHandler(new ClickHandler() { 
            @Override
            public void onClick(ClickEvent event) {
                boolean yes = yesToggle.getValue();
                for (QuestionBean choice: thisQuestion.getChoices()) {
                    if (choice.getQuestionText().equals("yes")) {
                        choice.setAnsweredBy("me");
                        choice.setAnsweredTime(new Date());
                    } else {
                        choice.setAnsweredBy(null);
                        choice.setAnsweredTime(null);                        
                    }
                }
                noToggle.setValue(!yes);
                DomEvent.fireNativeEvent(event.getNativeEvent(), TrueFalseButtonComponent.this);
            }
        });
        togglePanel.add(yesToggle);
        
        noToggle = new ToggleButton("No");
        noToggle.addStyleName("gwt-toggle-no");
        noToggle.addClickHandler(new ClickHandler() { 
            @Override
            public void onClick(ClickEvent event) {
                boolean no = noToggle.getValue();
                for (QuestionBean choice: thisQuestion.getChoices()) {
                    if (choice.getQuestionText().equals("no")) {
                        choice.setAnsweredBy("me");
                        choice.setAnsweredTime(new Date());
                    } else {
                        choice.setAnsweredBy(null);
                        choice.setAnsweredTime(null);                        
                    }
                }
                yesToggle.setValue(!no);
                DomEvent.fireNativeEvent(event.getNativeEvent(), TrueFalseButtonComponent.this);
            }
        });
        togglePanel.add(noToggle);
        
        initWidget(outerPanel);

        QuestionBean answer = tfQuestion.getSelectedChoice();
        if (null != answer) {
            boolean isSelected = answer.getAnswerValue().equals("true");         
            yesToggle.setValue(isSelected);
            noToggle.setValue(!isSelected);
        }

    }


    public QuestionBean getAnswerChoice() {
        return thisQuestion;
    }


    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
      return addHandler(handler, ClickEvent.getType());
    }
        
}
