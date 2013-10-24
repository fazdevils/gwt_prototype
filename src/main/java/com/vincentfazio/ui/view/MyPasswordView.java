package com.vincentfazio.ui.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.activity.MyPasswordSaveActivity;
import com.vincentfazio.ui.bean.PasswordBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.GwtGlobals;

public class MyPasswordView extends Composite implements MyPasswordDisplay {

    private static MyPasswordViewUiBinder uiBinder = GWT.create(MyPasswordViewUiBinder.class);
    
    interface MyPasswordViewUiBinder extends UiBinder<Widget, MyPasswordView> {}


    @UiConstructor
    public MyPasswordView(GwtGlobals globals) {
        initWidget(uiBinder.createAndBindUi(this));
        userSettingsGlobals = globals;
        
        currentPassword.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
                
            }
        });   
        newPassword.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
                
            }
        });
        verifyPassword.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
                
            }
        });
        undoButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                StatusDisplay statusDisplay = (StatusDisplay) userSettingsGlobals.getStatusDisplay();
                statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
                resetDisplay();
            }
        });

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
               new MyPasswordSaveActivity(userSettingsGlobals).start(null, null);
            }
        });

        resetDisplay();
        
        userSettingsGlobals.registerDisplay(MyPasswordDisplay.class, this);
    }

    GwtGlobals userSettingsGlobals;
    
    @UiField
    TextBox currentPassword;
    
    @UiField
    TextBox newPassword;

    @UiField
    TextBox verifyPassword;
    
    @UiField
    DivElement saveDiv;

    @UiField
    Anchor undoButton;
    
    @UiField
    Button saveButton;

    private boolean hasChanges = false;
    
    @Override
    public PasswordBean getPasswordUpdateBean() {
        PasswordBean password = new PasswordBean();
        
        password.setCurrentPassword(getCurrentPassword());
        password.setNewPassword(getNewPassword());
        
        return password;
    }

    private String getCurrentPassword() {
        return currentPassword.getText();
    }

    private String getNewPassword() {
        return newPassword.getText();
    }

    private String getVerifyPassword() {
        return verifyPassword.getText();
    }

    @Override
    public void resetDisplay() {
        currentPassword.setText(null);
        newPassword.setText(null);
        verifyPassword.setText(null);
        setHasUnsavedChanges(false);
        setStyle(true);
   }

    @Override
    public boolean isValid() {
        boolean validPassword = validateNewPassword();
        setStyle(validPassword);
        return validPassword;
    }

    @Override
    public boolean hasUnsavedChanges() {
        return hasChanges;
    }

    private void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        hasChanges = hasUnsavedChanges;
        
        Style divStyle = saveDiv.getStyle();
        if (hasChanges) {
            divStyle.setDisplay(Display.BLOCK);            
            saveButton.setEnabled(isValid());
        } else {
            divStyle.setDisplay(Display.NONE);            
        }
    }

    private boolean validateNewPassword() {
        boolean isValid = false;
        String newPw = getNewPassword();
        String verifyPw = getVerifyPassword();
        if (isValidPassword(newPw) && isValidPassword(verifyPw) && newPw.equals(verifyPw)) {
            isValid = true;
        } 
        return isValid;
    }
    
    private boolean isValidPassword(String password) {
        return (null != password) && !password.isEmpty();
    }

    private String validStyleName = "valid";
    private String invalidStyleName = "invalid";
    private String validErrorLabelStyleName = "valid-error-label";
    private String invalidErrorLabelStyleName = "invalid-error-label";
    private Label errorLabel = new Label("New password must match confirmation");

    private void setStyle(boolean isValid) {
        // add the error label to the DOM if we can
        if (null == errorLabel.getParent()) {
            HTMLPanel parent = (HTMLPanel) verifyPassword.getParent();
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
        
        
        verifyPassword.addStyleName(onStyle);
        errorLabel.addStyleName(onErrorStyle);
        verifyPassword.removeStyleName(offStyle);
        errorLabel.removeStyleName(offErrorStyle);
    }

}
