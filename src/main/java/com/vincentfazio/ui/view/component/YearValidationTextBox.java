package com.vincentfazio.ui.view.component;


public class YearValidationTextBox extends ValidationTextBox {
    
    public YearValidationTextBox() {
        super();     
        setRegex("^[0-9][0-9][0-9][0-9]$");
        setTitle("Enter year");
        setErrorText("Year is not valid");        
    }
	
}
