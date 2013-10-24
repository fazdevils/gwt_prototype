package com.vincentfazio.ui.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.bean.MyDetailsBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.LogoutModel;

public class HeaderView extends Composite implements HeaderDisplay {

    private static HeaderViewUiBinder uiBinder = GWT.create(HeaderViewUiBinder.class);


    interface HeaderViewUiBinder extends UiBinder<Widget, HeaderView> {}


    @UiConstructor
    public HeaderView(GwtGlobals globals) {
        initWidget(uiBinder.createAndBindUi(this));
        
        headerGlobals = globals;
        
        logoutButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                LogoutModel logoutModel = (LogoutModel) headerGlobals.getModel(LogoutModel.class);
                logoutModel.logout();            
            }
        });
        
        userWelcome.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                headerGlobals.gotoPlace(headerGlobals.createMyUserSettingsPlace());
            }
        });

        home.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                headerGlobals.gotoPlace(headerGlobals.getDefaultPlace());
            }
        });
        
        headerGlobals.registerDisplay(HeaderDisplay.class, this);

    }
    
    @UiField
    Anchor userWelcome;

    @UiField
    Anchor logoutButton;
    
    @UiField
    FocusPanel home;
    
    private final GwtGlobals headerGlobals;

    public void setMyDetails(MyDetailsBean myDetails) {
        userWelcome.setText(myDetails.getName());        
    }


}
