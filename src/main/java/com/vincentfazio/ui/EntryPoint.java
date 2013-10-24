package com.vincentfazio.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.administration.AdminActivityMapper;
import com.vincentfazio.ui.administration.AdminPlaceHistoryMapper;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.AdminHomeView;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.vendor.VendorActivityMapper;
import com.vincentfazio.ui.vendor.VendorPlaceHistoryMapper;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.VendorHomeView;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.FooterView;
import com.vincentfazio.ui.view.HeaderView;
import com.vincentfazio.ui.view.MyDetailsDisplay;
import com.vincentfazio.ui.view.MyDetailsView;
import com.vincentfazio.ui.view.MyPasswordView;

public class EntryPoint implements com.google.gwt.core.client.EntryPoint {

    @Override
    public void onModuleLoad() {
        DockLayoutPanel basePanel = new DockLayoutPanel(Unit.PX);
        RootLayoutPanel.get().add(basePanel);

        ApplicationInstance applicationType = getApplicationType();
        final GwtGlobals globals = getGlobals(applicationType);
 
        new MyDetailsView(globals);
        new MyPasswordView(globals);
        basePanel.addNorth(createHeader(globals), 60);
        basePanel.addSouth(createFooter(globals), 20);
        basePanel.add(createMainPanel(applicationType));
        
        
        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {  
            @Override
            public void onUncaughtException(Throwable t) {
                ErrorDisplay errorDisplay = globals.getErrorDisplay();
                MyDetailsDisplay detailsDisplay = (MyDetailsDisplay) globals.getDisplay(MyDetailsDisplay.class);
                String errorMessage = "Uncaught Exception: " + t.getMessage() + " [" + detailsDisplay.getMyDetails().getUserId() + "]";

                errorDisplay.handleError(new ErrorBean(t.getMessage(), t));
                Logger.getLogger("").log(Level.SEVERE, errorMessage, t);
            }
        }); 
        
        /* TODO UNCOMMENT THIS WHEN READY TO INCORPORATE JQUERY TOOLTIPS
        styleToolTips();
        */
        
        globals.startMyUserSettingsActivity();
    }

    private Widget createMainPanel(ApplicationInstance applicationType) {
        Widget mainPanel = null;
        switch (applicationType) {
            case Admin:
                mainPanel = createAdminInterface();
                break;
            case Vendor:
                mainPanel = createVendorInterface();
                break;
        }
        return mainPanel;
    }

    private GwtGlobals getGlobals(ApplicationInstance applicationType) {
        GwtGlobals globals = null;
        switch (applicationType) {
            case Admin:
                globals = GwtAdminGlobals.getInstance();
                break;
            case Vendor:
                globals = GwtVendorGlobals.getInstance();
                break;
        }
        return globals;
    }

    private ApplicationInstance getApplicationType() {
        if (null != RootPanel.get("admin")) {
            return ApplicationInstance.Admin;
        }
        if (null != RootPanel.get("vendor")) {
            return ApplicationInstance.Vendor;
        }
        return null;
    }

    private Panel createAdminInterface() {
        SimpleLayoutPanel basePanel = new SimpleLayoutPanel();
        basePanel.add(new AdminHomeView());
               
        // Start ActivityManager for the main widget with our ActivityMapper
        GwtGlobals globals = GwtAdminGlobals.getInstance();
        ActivityMapper activityMapper = new AdminActivityMapper(globals);
        ActivityManager activityManager = new ActivityManager(activityMapper, globals.getEventbus());
        activityManager.setDisplay(basePanel);
    
        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        AdminPlaceHistoryMapper historyMapper= GWT.create(AdminPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        Place defaultPlace = globals.getDefaultPlace();
        globals.registerDefaultPlace(historyHandler, defaultPlace);
    
        // Goes to the place represented on URL else default place
        historyHandler.handleCurrentHistory();

        return basePanel;
        
    }

    private Panel createVendorInterface() {
        SimpleLayoutPanel basePanel = new SimpleLayoutPanel();
        basePanel.add(new VendorHomeView());
               
        // Start ActivityManager for the main widget with our ActivityMapper
        GwtGlobals globals = GwtVendorGlobals.getInstance();
        ActivityMapper activityMapper = new VendorActivityMapper(globals);
        ActivityManager activityManager = new ActivityManager(activityMapper, globals.getEventbus());
        activityManager.setDisplay(basePanel);
    
        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        VendorPlaceHistoryMapper historyMapper= GWT.create(VendorPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        Place defaultPlace = globals.getDefaultPlace();
        globals.registerDefaultPlace(historyHandler, defaultPlace);
        
        // Goes to the place represented on URL else default place
        historyHandler.handleCurrentHistory();

        return basePanel;
    }
    
    private enum ApplicationInstance {
        Admin,
        Vendor;
    }
    
    private Panel createHeader(GwtGlobals globals) {
        SimpleLayoutPanel basePanel = new SimpleLayoutPanel();
        basePanel.add(new HeaderView(globals));
        return basePanel;
    }

    private Panel createFooter(GwtGlobals globals) {
        SimpleLayoutPanel basePanel = new SimpleLayoutPanel();
        basePanel.add(new FooterView());
        return basePanel;
    }

    private static native void styleToolTips() /*-{
        // tipsy $wnd.$('a[title]').tipsy({gravity: $wnd.$.fn.tipsy.autoNS, fade: true});
        $wnd.$('a').tooltip({track: true, delay: 0, showURL: false, showBody: " - ", fade: 250, top: 5, left: 5});        
    }-*/;
}
