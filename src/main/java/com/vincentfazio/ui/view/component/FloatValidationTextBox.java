package com.vincentfazio.ui.view.component;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;

public class FloatValidationTextBox extends ValidationTextBox {
    
    private NumberFormat floatFormat = NumberFormat.getFormat("###,###,###,###,##0.00"); 

    public FloatValidationTextBox() {
        super();     
        setRegex("^-?([1-9]{1}[0-9]{0,2}(\\,[0-9]{3})*(\\.[0-9]{0,2})?|[1-9]{1}[0-9]{0,}(\\.[0-9]{0,2})?|0(\\.[0-9]{0,2})?|(\\.[0-9]{1,2})?)$");
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
        String formattedFloatValue = super.getValue();
        return stripFormattingCharacters(formattedFloatValue);
    }

    private String stripFormattingCharacters(String formattedFloatValue) {
        String floatValue1 = formattedFloatValue.replaceAll("\\,", "");
        return floatValue1;
    }

    @Override
    public void setText(String unformatedFloatValue) {
        final String formattedString;
        if ((null == unformatedFloatValue) || unformatedFloatValue.isEmpty()) {
            formattedString = "";
        } else {
            Float floatAmount = Float.valueOf(unformatedFloatValue);
            formattedString = floatFormat.format(floatAmount);
        }
        super.setText(formattedString);
        validate();
    }
}
