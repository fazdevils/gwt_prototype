package com.vincentfazio.ui.view.component;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;

public class IntegerValidationTextBox extends ValidationTextBox {
    
    private NumberFormat integerFormat = NumberFormat.getFormat("###,###,###,###,##0"); 

    public IntegerValidationTextBox() {
        super();     
        setRegex("^-?([1-9]{1}[0-9]{0,2}(\\,[0-9]{3})*(\\.[0-9]{0,2})?|[1-9]{1}[0-9]{0,})$");
        setTitle("Enter a number");
        setErrorText("Number is not valid");
        
        this.addValueChangeHandler(new ValueChangeHandler<String>() {           
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                if (isValid) {
                    setText(getValue());
                }
            }                
        });
        
    }
	
    @Override
    public String getValue() {
        String formattedIntegerValue = super.getValue();
        return stripFormattingCharacters(formattedIntegerValue);
    }

    private String stripFormattingCharacters(String formattedIntegerValue) {
        String integerValue1 = formattedIntegerValue.replaceAll("\\,", "");
        return integerValue1;
    }

    @Override
    public void setText(String unformatedIntegerValue) {
        final String formattedString;
        if ((null == unformatedIntegerValue) || unformatedIntegerValue.isEmpty()) {
            formattedString = "";
        } else {
            Float integerAmount = Float.valueOf(unformatedIntegerValue);
            formattedString = integerFormat.format(integerAmount);
        }
        super.setText(formattedString);
        validate();
    }
}
