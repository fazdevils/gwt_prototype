package com.vincentfazio.ui.model.web_service;

import com.google.gwt.user.client.Window;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.LogoutModel;

public class LogoutService extends GwtService implements LogoutModel {

    private final String logout;
    
    public LogoutService(GwtGlobals globals) {
        super();
        logout = globals.getUserServicesBaseUrl() + "/secure/logout";
    }


    @Override
    public void logout() {
        String url = encodeUrl(logout);
        Window.Location.assign(url);
    }


}
