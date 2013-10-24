package com.vincentfazio.ui.view.component;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;

public class PercentValidationTextBox extends ValidationTextBox {

    private NumberFormat percentFormat = NumberFormat.getFormat("##0.00"); 

    public PercentValidationTextBox() {
        super();     
        setRegex("^(0*100{1,1}\\.?((?<=\\.)0*)?%?$)|(^0*\\d{0,2}\\.?((?<=\\.)\\d*)?%?)$");
        setTitle("Enter the percentage");
        setErrorText("Percentage is not valid");

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
        String formattedPercentValue = super.getValue();
        return stripFormattingCharacters(formattedPercentValue);
    }
    
    private String stripFormattingCharacters(String formattedPercentValue) {
        String percentValue1 = formattedPercentValue.replace("%", "");
        return percentValue1;
    }
    
    @Override
    public void setText(String unformatedPercentValue) {
        final String formattedString;
        if ((null == unformatedPercentValue) || unformatedPercentValue.isEmpty()) {
            formattedString = "";
        } else {
            Float percentAmount = Float.valueOf(unformatedPercentValue);
            formattedString = percentFormat.format(percentAmount) + "%";
        }
        super.setText(formattedString);
        validate();
    }


}
