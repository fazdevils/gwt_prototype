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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.activity.MyDetailsSaveActivity;
import com.vincentfazio.ui.bean.MyDetailsBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.view.component.ValidationTextBox;

public class MyDetailsView extends Composite implements MyDetailsDisplay {

    private static MyDetailsViewUiBinder uiBinder = GWT.create(MyDetailsViewUiBinder.class);
    
    interface MyDetailsViewUiBinder extends UiBinder<Widget, MyDetailsView> {}


    @UiConstructor
    public MyDetailsView(GwtGlobals globals) {
        initWidget(uiBinder.createAndBindUi(this));
        userSettingsGlobals = globals;
        
        name.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
                
            }
        });   
        email.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
                
            }
        });
        title.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
                
            }
        });
        phone.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setHasUnsavedChanges(true);
                
            }
        });

        undoButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (hasChanges) {
                    StatusDisplay statusDisplay = (StatusDisplay) userSettingsGlobals.getStatusDisplay();
                    statusDisplay.handleStatusUpdate(new StatusBean("Changes Discarded"));            
                }
                setHasUnsavedChanges(false);
                userSettingsGlobals.startMyUserSettingsActivity();
            }
        });

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new MyDetailsSaveActivity(userSettingsGlobals).start(null, null);
            }
        });

        changePasswordButton.addClickHandler(new ClickHandler() {     
            @Override
            public void onClick(ClickEvent event) {
                if (!hasChanges) {
                    userSettingsGlobals.gotoPlace(userSettingsGlobals.createMyUserPasswordPlace());
                }
            }
        });
        

        resetDisplay();
        
        userSettingsGlobals.registerDisplay(MyDetailsDisplay.class, this);
    }

    GwtGlobals userSettingsGlobals;
    
    @UiField
    TextBox name;
    
    @UiField
    ValidationTextBox email;

    @UiField
    TextBox title;
    
    @UiField
    ValidationTextBox phone;
    
    @UiField
    DivElement saveDiv;

    @UiField
    Anchor undoButton;
    
    @UiField
    Button saveButton;

    @UiField
    Anchor changePasswordButton;

    private boolean hasChanges = false;
    
    @Override
    public void setMyDetails(MyDetailsBean user) {        
        resetDisplay();
        
        setEmail(user.getEmail());
        setName(user.getName());
        setPhone(user.getPhone());
        setUserTitle(user.getTitle());            
    }

    @Override
    public MyDetailsBean getMyDetails() {
        MyDetailsBean user = new MyDetailsBean();
        
        user.setEmail(getEmail());
        user.setName(getName());
        user.setPhone(getPhone());
        user.setTitle(getUserTitle());
        
        return user;
    }

    private String getName() {
        return name.getText();
    }

    private void setName(String name) {
        this.name.setText(name);
    }

    private String getEmail() {
        return email.getText();
    }

    private void setEmail(String email) {
        this.email.setText(email);
        validateEmail();
    }

    private String getPhone() {
        return phone.getText();
    }

    private void setPhone(String phone) {
        this.phone.setText(phone);
    }

    private String getUserTitle() {
        return title.getText();
    }

    private void setUserTitle(String title) {
        this.title.setText(title);
    }
    
    private void resetDisplay() {
        setEmail(null);
        setName(null);
        setPhone(null);
        setUserTitle(null);

        setHasUnsavedChanges(false);
    }

    @Override
    public boolean isValid() {
        return phone.isValid() && email.isValid();
    }

    @Override
    public boolean hasUnsavedChanges() {
        return hasChanges;
    }

    @Override
    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        hasChanges = hasUnsavedChanges;
        
        Style divStyle = saveDiv.getStyle();
        if (hasChanges) {
            divStyle.setDisplay(Display.BLOCK);            
            changePasswordButton.setTitle("Save changes to enable this link");
            changePasswordButton.addStyleName("disabled");
            saveButton.setEnabled(isValid());
        } else {
            divStyle.setDisplay(Display.NONE);            
            changePasswordButton.setTitle(null);
            changePasswordButton.removeStyleName("disabled");
            validateEmail();
        }
    }

    private void validateEmail() {
        email.validate();        
    }

}
