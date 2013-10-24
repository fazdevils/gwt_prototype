package com.vincentfazio.ui.view.component;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;

public class DollarValidationTextBox extends ValidationTextBox {
    
    private NumberFormat dollarFormat = NumberFormat.getFormat("$###,###,###,###,##0.00"); 

    public DollarValidationTextBox() {
        super();     
        setRegex("^-?\\$?([1-9]{1}[0-9]{0,2}(\\,[0-9]{3})*(\\.[0-9]{0,2})?|[1-9]{1}[0-9]{0,}(\\.[0-9]{0,2})?|0(\\.[0-9]{0,2})?|(\\.[0-9]{1,2})?)$");
        setTitle("Enter the dollar amount");
        setErrorText("Dollar amount is not valid");
        
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
        String formattedDollarValue = super.getValue();
        return stripFormattingCharacters(formattedDollarValue);
    }

    private String stripFormattingCharacters(String formattedDollarValue) {
        String dollarValue1 = formattedDollarValue.replaceAll("\\$", "");
        String dollarValue2 = dollarValue1.replaceAll("\\,", "");
        return dollarValue2;
    }

    @Override
    public void setText(String unformatedDollarValue) {
        final String formattedString;
        if ((null == unformatedDollarValue) || unformatedDollarValue.isEmpty()) {
            formattedString = "";
        } else {
            Float dollarAmount = Float.valueOf(unformatedDollarValue);
            formattedString = dollarFormat.format(dollarAmount);
        }
        super.setText(formattedString);
        validate();
    }
}
