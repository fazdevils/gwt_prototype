package com.vincentfazio.ui.survey.view.component;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ToggleButton;
import com.vincentfazio.ui.bean.QuestionBean;

public class ChooseManyButtonComponent extends Composite implements HasClickHandlers {
    
    private final QuestionBean thisAnswerChoice;
    private final ToggleButton yesToggle;
    private final ToggleButton noToggle;
    
    
    public ChooseManyButtonComponent(QuestionBean answerChoice) {
        super();
        thisAnswerChoice = answerChoice;

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
                thisAnswerChoice.setAnswerValue(Boolean.toString(yes));
                noToggle.setValue(!yes);
                DomEvent.fireNativeEvent(event.getNativeEvent(), ChooseManyButtonComponent.this);
            }
        });
        togglePanel.add(yesToggle);
        
        noToggle = new ToggleButton("No");
        noToggle.addStyleName("gwt-toggle-no");
        noToggle.addClickHandler(new ClickHandler() { 
            @Override
            public void onClick(ClickEvent event) {
                boolean no = noToggle.getValue();
                thisAnswerChoice.setAnswerValue(Boolean.toString(!no));
                yesToggle.setValue(!no);
                DomEvent.fireNativeEvent(event.getNativeEvent(), ChooseManyButtonComponent.this);
            }
        });
        togglePanel.add(noToggle);
        
        Label label = new Label(answerChoice.getQuestionText());
        togglePanel.add(label);
        
        initWidget(outerPanel);

        if (answerChoice.isAnswered()) {
            boolean isSelected = answerChoice.getAnswerValue().equals("true");         
            yesToggle.setValue(isSelected);
            noToggle.setValue(!isSelected);
        }
    }

    public QuestionBean getAnswerChoice() {
        return thisAnswerChoice;
    }


    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
      return addHandler(handler, ClickEvent.getType());
    }
    
}
