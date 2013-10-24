package com.vincentfazio.ui.administration.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.administration.activity.UserListActivity;
import com.vincentfazio.ui.administration.activity.CompanyListActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;

public class MainAdminNavigationView extends Composite implements MainAdminNavigationDisplay {

    private static MainAdminNavigationUiBinder uiBinder = GWT.create(MainAdminNavigationUiBinder.class);


    interface MainAdminNavigationUiBinder extends UiBinder<Widget, MainAdminNavigationView> {
    }


    @UiField
    StackLayoutPanel adminMenu;
    
    @UiField
    UserListView userList;
    
    @UiField
    CompanyListView companyList;
    
    
    public MainAdminNavigationView() {
        initWidget(uiBinder.createAndBindUi(this));
        
        adminMenu.addSelectionHandler(new SelectionHandler<Integer>() {

            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                Widget selectedPanel = adminMenu.getWidget(event.getSelectedItem());
                
                if (selectedPanel instanceof CompanyListDisplay) {
                    new CompanyListActivity(GwtAdminGlobals.getInstance(), true).start(null, null);
                } else if (selectedPanel instanceof UserListDisplay) {
                    new UserListActivity(GwtAdminGlobals.getInstance(), true).start(null, null);
                }
            }
        }); 
        
        GwtAdminGlobals.getInstance().registerDisplay(MainAdminNavigationDisplay.class, this);

    }

    
    @Override
    public void showView(MainAdminNavigationEnum navigationView) {
        switch (navigationView) {
            case Users:
                adminMenu.showWidget(userList, false);
                break;
            case Companies:
                adminMenu.showWidget(companyList, false);
                break;       
        }
    }
    
}
