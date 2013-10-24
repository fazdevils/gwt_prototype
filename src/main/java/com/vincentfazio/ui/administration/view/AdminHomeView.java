package com.vincentfazio.ui.administration.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.view.HomeDisplay;
import com.vincentfazio.ui.view.MyDetailsDisplay;
import com.vincentfazio.ui.view.MyDetailsView;
import com.vincentfazio.ui.view.MyPasswordDisplay;
import com.vincentfazio.ui.view.MyPasswordView;
import com.vincentfazio.ui.view.ViewDeckEnum;

public class AdminHomeView extends Composite implements AdminHomeDisplay {

    @UiField
    DeckLayoutPanel adminScreens;
    
    @UiField
    MainAdminNavigationView adminNavivation;
    
    @UiField(provided=true)
    MyDetailsView userSettings;
    
    @UiField(provided=true)
    MyPasswordView userPassword;
    
    private static AdminHomeUiBinder uiBinder = GWT.create(AdminHomeUiBinder.class);


    interface AdminHomeUiBinder extends UiBinder<Widget, AdminHomeView> {
    }

    public AdminHomeView() {
        GwtGlobals globals = GwtAdminGlobals.getInstance();
        userSettings = (MyDetailsView) globals.getDisplay(MyDetailsDisplay.class);
        userPassword = (MyPasswordView) globals.getDisplay(MyPasswordDisplay.class);        
        
        initWidget(uiBinder.createAndBindUi(this));
        adminScreens.showWidget(adminNavivation);
                
        globals.registerDisplay(HomeDisplay.class, this);

    }

    @Override
    public void showView(ViewDeckEnum view) {
        AdminDeckEnum adminView = (AdminDeckEnum)view;
        switch (adminView) {
            case MainNavigation:
                adminScreens.showWidget(adminNavivation);
                
                /**
                 * There a bug in IE where, when going directly to a user or 
                 * company detail page, the stack layout panel does not seem to
                 * draw correctly for some reason.  Calling forceLayout seems 
                 * to fix that. 
                 */
                adminScreens.forceLayout();
                break;
            case UserSettings:
                adminScreens.showWidget(userSettings);
                break;       
            case UserPassword:
                adminScreens.showWidget(userPassword);
                break;       
        }
    }  
    
    
}
