package com.vincentfazio.ui.administration.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.administration.activity.DeleteUserActivity;
import com.vincentfazio.ui.administration.activity.UserDetailsSaveActivity;
import com.vincentfazio.ui.administration.activity.UserPasswordResetActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.place.UserDetailsPlace;
import com.vincentfazio.ui.administration.place.UserPermissionsPlace;
import com.vincentfazio.ui.bean.UserDetailsBean;
import com.vincentfazio.ui.view.component.ValidationTextBox;

public class UserDetailsView extends Composite implements UserDetailsDisplay {

    private static UserDetailsUiBinder uiBinder = GWT.create(UserDetailsUiBinder.class);


    interface UserDetailsUiBinder extends UiBinder<Widget, UserDetailsView> {
    }


    public UserDetailsView() {
        initWidget(uiBinder.createAndBindUi(this));
                
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

        adminCheckbox.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setAdminStyle();
                setHasUnsavedChanges(true);
            }
        });
        
        adminLink.addClickHandler(new ClickHandler() {     
            @Override
            public void onClick(ClickEvent event) {
                if (!hasChanges) {
                    GwtAdminGlobals.getInstance().gotoPlace(new UserPermissionsPlace(userId.getText(), "Administrator"));
                }
            }
        });
        
        vendorCheckbox.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setVendorStyle();
                setHasUnsavedChanges(true);
            }
        });
        
        vendorLink.addClickHandler(new ClickHandler() {     
            @Override
            public void onClick(ClickEvent event) {
                if (!hasChanges) {
                    GwtAdminGlobals.getInstance().gotoPlace(new UserPermissionsPlace(userId.getText(), "Vendor"));
                }
            }
        });
        
        customerCheckbox.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setCustomerStyle();
                setHasUnsavedChanges(true);
            }
        });
        
        customerLink.addClickHandler(new ClickHandler() {     
            @Override
            public void onClick(ClickEvent event) {
                if (!hasChanges) {
                    GwtAdminGlobals.getInstance().gotoPlace(new UserPermissionsPlace(userId.getText(), "Customer"));
                }
            }
        });
        
        undoButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String currentUserId = getUserId();
                setUserId("");
                setHasUnsavedChanges(false);
                GwtAdminGlobals.getInstance().gotoPlace(new UserDetailsPlace(currentUserId));                
            }
        });

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new UserDetailsSaveActivity(GwtAdminGlobals.getInstance(), getUserDetails()).start(null, null);
            }
        });

        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (Window.confirm("Are you sure you want to permanently delete this user?")) {
                    new DeleteUserActivity(GwtAdminGlobals.getInstance(), getUserId()).start(null, null);
                }
            }
        });

        resetPasswordButton.addClickHandler(new ClickHandler() {     
            @Override
            public void onClick(ClickEvent event) {
                if (!hasChanges) {
                    new UserPasswordResetActivity(GwtAdminGlobals.getInstance(), getUserId()).start(null, null);
                }
            }
        });
        

        resetDisplay();
        
        GwtAdminGlobals.getInstance().registerDisplay(UserDetailsDisplay.class, this);
    }

    @UiField
    Label userId;

    @UiField
    TextBox name;
    
    @UiField
    ValidationTextBox email;

    @UiField
    TextBox title;
    
    @UiField
    ValidationTextBox phone;
    
    @UiField
    CheckBox adminCheckbox;
    
    @UiField
    Anchor adminLink;
    
    @UiField
    CheckBox customerCheckbox;
    
    @UiField
    Anchor customerLink;
    
    @UiField
    CheckBox vendorCheckbox;
    
    @UiField
    Anchor vendorLink;
    
    @UiField
    DivElement saveDiv;

    @UiField
    DivElement deleteDiv;

    @UiField
    Anchor undoButton;
    
    @UiField
    Button saveButton;

    @UiField
    Anchor deleteButton;

    @UiField
    Anchor resetPasswordButton;

    private boolean hasChanges = false;
    
    @Override
    public void setUserDetails(UserDetailsBean user) {        
        resetDisplay();
        
        setEmail(user.getEmail());
        setName(user.getName());
        setPhone(user.getPhone());
        setUserId(user.getUserId());
        setUserIsAdministrator(user.isAdministrator());
        setUserIsCustomer(user.isCustomer());
        setUserIsVendor(user.isVendor());
        setUserTitle(user.getTitle());            
    }

    @Override
    public UserDetailsBean getUserDetails() {
        UserDetailsBean user = new UserDetailsBean();
        
        user.setAdministrator(getUserIsAdministrator());
        user.setCustomer(getUserIsCustomer());
        user.setEmail(getEmail());
        user.setName(getName());
        user.setPhone(getPhone());
        user.setTitle(getUserTitle());
        user.setUserId(getUserId());
        user.setVendor(getUserIsVendor());
        
        return user;
    }

    private String getUserId() {
        return userId.getText();
    }

    private void setUserId(String userId) {
        this.userId.setText(userId);
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
    
    private boolean getUserIsAdministrator() {
        return this.adminCheckbox.getValue();
    }
    
    private boolean getUserIsCustomer() {
        return this.customerCheckbox.getValue();
    }
    
    private boolean getUserIsVendor() {
        return this.vendorCheckbox.getValue();
    }

    private void setUserIsAdministrator(boolean hasAccess) {
        this.adminCheckbox.setValue(hasAccess);
        setAdminStyle();
    }
    
    private void setUserIsCustomer(boolean hasAccess) {
        this.customerCheckbox.setValue(hasAccess);
        setCustomerStyle();
    }
    
    private void setUserIsVendor(boolean hasAccess) {
        this.vendorCheckbox.setValue(hasAccess);
        setVendorStyle();
    }

    private void resetDisplay() {
        setEmail(null);
        setName(null);
        setPhone(null);
        setUserId(null);
        setUserIsAdministrator(false);
        setUserIsCustomer(false);
        setUserIsVendor(false);
        setUserTitle(null);

        setHasUnsavedChanges(false);
    }

    private void setCustomerStyle() {
        customerLink.setVisible(getUserIsCustomer());
        customerCheckbox.setStyleName("disabled", !getUserIsCustomer());
    }

    private void setVendorStyle() {
        vendorLink.setVisible(getUserIsVendor());
        vendorCheckbox.setStyleName("disabled", !getUserIsVendor());
    }

    private void setAdminStyle() {
        adminLink.setVisible(false);
        adminCheckbox.setStyleName("disabled", !getUserIsAdministrator());
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
        Style deleteDivStyle = deleteDiv.getStyle();
        if (hasChanges) {
            deleteDivStyle.setDisplay(Display.NONE);
            divStyle.setDisplay(Display.BLOCK);            

            adminLink.setTitle("Save changes to enable this link");
            customerLink.setTitle("Save changes to enable this link");
            vendorLink.setTitle("Save changes to enable this link");
            resetPasswordButton.setTitle("Save changes to enable this link");
            
            adminLink.addStyleName("disabled");
            customerLink.addStyleName("disabled");
            vendorLink.addStyleName("disabled");
            resetPasswordButton.addStyleName("disabled");
            
            saveButton.setEnabled(isValid());
        } else {
            deleteDivStyle.setDisplay(Display.BLOCK);
            divStyle.setDisplay(Display.NONE);            

            adminLink.setTitle(null);
            customerLink.setTitle(null);
            vendorLink.setTitle(null);
            resetPasswordButton.setTitle(null);
            
            adminLink.removeStyleName("disabled");
            customerLink.removeStyleName("disabled");
            vendorLink.removeStyleName("disabled");
            resetPasswordButton.removeStyleName("disabled");
            
            validateEmail();
        }
    }

    private void validateEmail() {
        email.validate();
        if (email.isValid() && !email.getText().isEmpty()) {
            resetPasswordButton.setTitle(null);            
            resetPasswordButton.removeStyleName("disabled");
        } else {
            resetPasswordButton.setTitle("Password reset requires a valid email address");
            resetPasswordButton.addStyleName("disabled");            
        }
        
    }

}
