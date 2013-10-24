package com.vincentfazio.ui.configuration;

import com.google.gwt.i18n.client.Constants;


public interface GwtConfiguration extends Constants  {
    
    @Key("use-service-mocks")
    boolean useServiceMocks();

}
