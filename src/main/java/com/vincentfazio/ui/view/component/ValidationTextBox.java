package com.vincentfazio.ui.view.component;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class ValidationTextBox extends TextBox {
	
	private String regex = "[a-zA-Z]*";
	private String validStyleName = "valid";
	private String invalidStyleName = "invalid";
    private String validErrorLabelStyleName = "valid-error-label";
    private String invalidErrorLabelStyleName = "invalid-error-label";
	protected boolean isValid = true;
	private Label errorLabel;
	
	public ValidationTextBox(){
		super();
		initialize();
	}
	
	public void validate() {
	    validate(getValue());    
	}
	
	public boolean isValid() {
        return isValid;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
        if (errorLabel.getText().isEmpty()) {
            errorLabel.setText(regex);    
        }
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

    private void initialize(){
		
        errorLabel = new InlineLabel("error");

        resetValidation();
		this.setTitle("Only alpha characters are valid.");
		
		this.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				validate(getValue());
				
			}
		});
		
	}

    public void resetValidation() {
        setValidStyle();
        isValid = true;
    }
	
	private void validate(String value){
		/** 
		 * Native java (non-trivial) regex may not work outside of hosted mode
		 * http://stackoverflow.com/questions/2098750/regex-in-gwt-to-match-urls
		 * 
		 * if so, replace this
		 *    if (isValid(value)) {
		 * with a call to a native js function using GWT's JSNI
		 *    if (isValidJSON(value)) { 
		 */
	    if (isValid(value)) {
		    setValidStyle();
		    isValid = true;
		} else {
            setInvalidStyle();
            isValid = false;
            
		}
	}

	
	
	protected boolean isValid(String value) {
	    if (value.isEmpty()) {
	        return true;
	    }
	    return value.matches(regex);
	}
	
	protected native boolean isValidJSON(String value) /*-{
        var pattern = /[a-zA-Z]+/;
        return pattern.test(value);
    }-*/;
	
    private void setInvalidStyle() {
        setStyle(false);
    }

    private void setValidStyle() {
        setStyle(true);
    }

    private void setStyle(boolean isValid) {
        // add the error label to the DOM if we can
        if (null == errorLabel.getParent()) {
            Panel parent = (Panel) getParent();
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
