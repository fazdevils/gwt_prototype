package com.vincentfazio.ui.view.component;

public class EmailValidationTextBox extends ValidationTextBox {

    private EmailValidationTextBox() {
        super();     
        setRegex("([_A-Za-z0-9-]+)(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})");           
    }
	

}
