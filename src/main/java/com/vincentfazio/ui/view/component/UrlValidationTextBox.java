package com.vincentfazio.ui.view.component;

public class UrlValidationTextBox extends ValidationTextBox {

    private UrlValidationTextBox() {
        super();
        setRegex("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");           
    }
	

}
