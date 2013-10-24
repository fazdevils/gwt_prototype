package com.vincentfazio.ui.view.component;

public class PhoneValidationTextBox extends ValidationTextBox {

    private PhoneValidationTextBox() {
        super();
        setRegex("^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$");           
    }
	

}
