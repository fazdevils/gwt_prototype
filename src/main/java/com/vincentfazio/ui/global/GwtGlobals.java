package com.vincentfazio.ui.global;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.vincentfazio.ui.activity.MyDetailsActivity;
import com.vincentfazio.ui.animation.Color;
import com.vincentfazio.ui.configuration.GwtConfiguration;
import com.vincentfazio.ui.model.Model;
import com.vincentfazio.ui.view.Display;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.ErrorView;
import com.vincentfazio.ui.view.NotifyDisplay;
import com.vincentfazio.ui.view.NotifyView;
import com.vincentfazio.ui.view.StatusDisplay;
import com.vincentfazio.ui.view.StatusView;

public abstract class GwtGlobals implements Globals {

    protected final EventBus eventBus;
    protected final HashMap<Class<? extends Display>, Display> displayMap;
    protected final HashMap<Class<? extends Model>, Model> modelMap;
    protected final PlaceController placeController;
    protected final ErrorDisplay errorDisplay;
    protected final NotifyDisplay notificationDisplay;
    protected final StatusDisplay statusDisplay;
    protected final Color notificationColor;
    protected final GwtConfiguration configuration;
    protected final String serviceBaseUrl;
    protected final String userServicesBaseUrl;

    protected GwtGlobals() {
        super();

        // configuration
        if (isDevelopmentMode()) {
            configuration = null;
            serviceBaseUrl = null;
            userServicesBaseUrl = null;
        } else {
            configuration = (GwtConfiguration)GWT.create(GwtConfiguration.class);
            serviceBaseUrl = determineServicesBaseUrl();
            userServicesBaseUrl = determineUserServicesBaseUrl();
        } 

        // event bus
        eventBus = new SimpleEventBus();
        
        // displays
        displayMap = new HashMap<Class<? extends Display>, Display>();
        errorDisplay = createErrorDisplay();
        statusDisplay = createStatusDisplay();
        notificationDisplay = createNotificationDisplay();
        
        // models
        modelMap = new HashMap<Class<? extends Model>, Model>();
        populateModelMap();
        
        // places
        placeController = getPlaceController();
        
        notificationColor = new Color(255, 165, 0);
        
    }

    protected NotifyDisplay createNotificationDisplay() {
        return new NotifyView();
    }

    protected StatusDisplay createStatusDisplay() {
        return new StatusView();
    }

    protected ErrorDisplay createErrorDisplay() {
        return new ErrorView();
    }

    protected PlaceController getPlaceController() {
        return new PlaceController(eventBus);
    }

    private void populateModelMap() {
        if (isDevelopmentMode()) {
            setMockModels(this);                
        } else {            
            if (getConfiguration().useServiceMocks()) {
                setMockModels(this);                
            } else {
                setSerivceModels(this);            
            }
        }
    }

    protected boolean isDevelopmentMode() {
        return GWT.getPermutationStrongName().equals(GWT.HOSTED_MODE_PERMUTATION_STRONG_NAME);
    }

    protected abstract void setMockModels(GwtGlobals globals);
    protected abstract void setSerivceModels(GwtGlobals globals);
    
    public abstract String getRole();

    private String determineServicesBaseUrl() {
        String urlFragment = "_ui/";
        StringBuffer hostUrl = new StringBuffer(GWT.getHostPageBaseURL());
        
        int endIndex = hostUrl.lastIndexOf(urlFragment);
        String servicesBaseUrl = hostUrl.subSequence(0, endIndex) + "_services";
        
        return servicesBaseUrl;
    }

    private String determineUserServicesBaseUrl() {
        StringBuffer hostUrl = new StringBuffer(GWT.getHostPageBaseURL());
        String servicesBaseUrl = (String) hostUrl.subSequence(0, hostUrl.length()-1);
        int endIndex = servicesBaseUrl.lastIndexOf("/");
        String baseUrl = servicesBaseUrl.subSequence(0, endIndex+1) + "user_services";
        
        return baseUrl;
    }

    @Override
    public EventBus getEventbus() {
        return eventBus;
    }

    @Override
    public Display getDisplay(Class<? extends Display> display) {
        return displayMap.get(display);
    }

    @Override
    public void registerDisplay(Class<? extends Display> display, Display view) {
        displayMap.put(display, view);
    }

    @Override
    public void gotoPlace(Place newPlace) {
        placeController.goTo(newPlace);
    }

    @Override
    public Model getModel(Class<? extends Model> modelClass) {
        return modelMap.get(modelClass);
    }

    @Override
    public ErrorDisplay getErrorDisplay() {
        return errorDisplay;
    }

    @Override
    public StatusDisplay getStatusDisplay() {
        return statusDisplay;
    }

    public NotifyDisplay getNotificationDisplay() {
        return notificationDisplay;
    }

    public void registerDefaultPlace(PlaceHistoryHandler historyHandler, Place defaultPlace) {
        historyHandler.register(placeController, eventBus, defaultPlace);
    }

    public Color getNotificationColor() {
        return notificationColor;
    }

    @Override
    public GwtConfiguration getConfiguration() {
        return configuration;
    }

    public String getServiceBaseUrl() {
        return serviceBaseUrl;
    }

    public String getUserServicesBaseUrl() {
        return userServicesBaseUrl;
    }

    public abstract Place createMyUserSettingsPlace();
    public abstract Place createMyUserPasswordPlace();

    public void startMyUserSettingsActivity() {
        new MyDetailsActivity(this).start(null, null);
    }

    public abstract Place getDefaultPlace();


}