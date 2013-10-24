package com.vincentfazio.ui.global;

import com.google.gwt.place.shared.Place;
import com.google.web.bindery.event.shared.EventBus;
import com.vincentfazio.ui.configuration.GwtConfiguration;
import com.vincentfazio.ui.model.Model;
import com.vincentfazio.ui.view.Display;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public interface Globals {

    EventBus getEventbus();

    Display getDisplay(Class<? extends Display> display);

    void registerDisplay(Class<? extends Display> display, Display view);

    void gotoPlace(Place newPlace);

    Model getModel(Class<? extends Model> modelClass);

    ErrorDisplay getErrorDisplay();

    StatusDisplay getStatusDisplay();

    GwtConfiguration getConfiguration();

}