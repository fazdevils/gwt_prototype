package com.vincentfazio.ui.view.component;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class ValidationSuggestBox extends SuggestBox {

    private String validStyleName = "valid";
    private String invalidStyleName = "invalid";
    private String validErrorLabelStyleName = "valid-error-label";
    private String invalidErrorLabelStyleName = "invalid-error-label";
    protected boolean isValid = true;
    private Label errorLabel;
    private Collection<String> suggestedItems = new ArrayList<String>();

    public ValidationSuggestBox(Collection<String> suggestedItems) {
        super(new MultiWordSuggestOracle());
        setSuggestedItems(suggestedItems);
    }

    public boolean noSuggestedItems() {
        return suggestedItems.isEmpty();
    }
    
    public void setSuggestedItems(Collection<String> suggestedItems) {
        MultiWordSuggestOracle oracle = (MultiWordSuggestOracle)super.getSuggestOracle();
        oracle.addAll(suggestedItems);
        
        this.suggestedItems = suggestedItems;
    }

    public ValidationSuggestBox() {
        super(new MultiWordSuggestOracle());
    }

    public boolean isValid() {
        return isValid;
    }

    public void setErrorText(String errorText) {
        errorLabel.setText(errorText);
    }

    public String getValidStyleName() {
        return validStyleName;
    }

    public void setValidStyleName(String validStyleName) {
        this.validStyleName = validStyleName;
    }

    public String getInvalidStyleName() {
        return invalidStyleName;
    }

    public void setInvalidStyleName(String invalidStyleName) {
        this.invalidStyleName = invalidStyleName;
    }

    protected void initialize() {
    	
        errorLabel = new Label("error");
    
        resetValidation();
    	
    	this.addKeyUpHandler(new KeyUpHandler() {
    		
    		@Override
    		public void onKeyUp(KeyUpEvent event) {
    			validate(getValue());
    			
    		}
    	});
    	
    	this.addSelectionHandler(new SelectionHandler<Suggestion>() {
            
            @Override
            public void onSelection(SelectionEvent<Suggestion> event) {
                validate(getValue());
            }
        });
    }

    public void resetValidation() {
        setValidStyle();
        isValid = true;
    }

    public void validate(String value) {
        if (isValid(value)) {
    	    setValidStyle();
    	    isValid = true;
    	} else {
            setInvalidStyle();
            isValid = false;
    	}
    }

    private boolean isValid(String value) {
        return suggestedItems.contains(value) || value.trim().isEmpty();
    }

    private void setInvalidStyle() {
        setStyle(false);
    }

    private void setValidStyle() {
        setStyle(true);
    }

    private void setStyle(boolean isValid) {
        // add the error label to the DOM if we can
        if (null == errorLabel.getParent()) {
            HTMLPanel parent = (HTMLPanel) getParent();
            if (null != parent) {
                parent.add(errorLabel);
            }
        }
        
        // Set the styles
        String onStyle;
        String offStyle;
        String onErrorStyle;
        String offErrorStyle;
        
        if (isValid) {
            onStyle = validStyleName;
            onErrorStyle = validErrorLabelStyleName;
            offStyle = invalidStyleName;
            offErrorStyle = invalidErrorLabelStyleName;
        } else {
            onStyle = invalidStyleName;
            onErrorStyle = invalidErrorLabelStyleName;
            offStyle = validStyleName;
            offErrorStyle = validErrorLabelStyleName;            
        }
        
        
        this.addStyleName(onStyle);
        errorLabel.addStyleName(onErrorStyle);
        this.removeStyleName(offStyle);
        errorLabel.removeStyleName(offErrorStyle);
    }

}