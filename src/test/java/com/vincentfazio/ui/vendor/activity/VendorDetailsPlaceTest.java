package com.vincentfazio.ui.vendor.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.vincentfazio.ui.vendor.global.MockVendorGlobals;
import com.vincentfazio.ui.vendor.place.VendorDetailsPlace;
import com.vincentfazio.ui.vendor.view.MockVendorDetailsDisplay;
import com.vincentfazio.ui.vendor.view.VendorDetailsDisplay;
import com.vincentfazio.ui.view.MockErrorView;
import com.vincentfazio.ui.view.MockStatusView;

public class VendorDetailsPlaceTest {

    @Test
    public void testCreateUserActivity() {
        
        // setup
        MockVendorGlobals globals = (MockVendorGlobals) MockVendorGlobals.getInstance();
        MockStatusView statusDisplay = (MockStatusView) globals.getStatusDisplay();
        MockErrorView errorDisplay = (MockErrorView) globals.getErrorDisplay();
        MockVendorDetailsDisplay detailsView = (MockVendorDetailsDisplay) globals.getDisplay(VendorDetailsDisplay.class);
        globals.gotoPlace(null);
        statusDisplay.handleStatusUpdate(null);
        errorDisplay.handleError(null);
        
        assertNull(statusDisplay.getStatus());
        assertNull(errorDisplay.getError());
        assertNull(detailsView.getVendorDetails());
        assertNull(globals.getCurrentPlace());
        
        
        // *** get details for vendor 1
        String vendorId = "Vendor 1";
        globals.gotoPlace(new VendorDetailsPlace(vendorId));
        
        
        // verify status display
        assertNull(statusDisplay.getStatus());
        assertNull(errorDisplay.getError());
        
        
        // verify that we're in the right place
        VendorDetailsPlace currentPlace = (VendorDetailsPlace) globals.getCurrentPlace();
        assertNotNull(currentPlace);
        assertEquals(vendorId, currentPlace.getVendorId());
        
        
    }

}