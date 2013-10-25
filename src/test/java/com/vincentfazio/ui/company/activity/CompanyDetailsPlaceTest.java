package com.vincentfazio.ui.company.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.vincentfazio.ui.survey.place.CompanyDetailsPlace;
import com.vincentfazio.ui.survey.view.CompanyDetailsDisplay;
import com.vincentfazio.ui.company.global.MockCompanyGlobals;
import com.vincentfazio.ui.company.view.MockCompanyDetailsDisplay;
import com.vincentfazio.ui.view.MockErrorView;
import com.vincentfazio.ui.view.MockStatusView;

public class CompanyDetailsPlaceTest {

    @Test
    public void testCreateUserActivity() {
        
        // setup
        MockCompanyGlobals globals = (MockCompanyGlobals) MockCompanyGlobals.getInstance();
        MockStatusView statusDisplay = (MockStatusView) globals.getStatusDisplay();
        MockErrorView errorDisplay = (MockErrorView) globals.getErrorDisplay();
        MockCompanyDetailsDisplay detailsView = (MockCompanyDetailsDisplay) globals.getDisplay(CompanyDetailsDisplay.class);
        globals.gotoPlace(null);
        statusDisplay.handleStatusUpdate(null);
        errorDisplay.handleError(null);
        
        assertNull(statusDisplay.getStatus());
        assertNull(errorDisplay.getError());
        assertNull(detailsView.getCompanyDetails());
        assertNull(globals.getCurrentPlace());
        
        
        // *** get details for company 1
        String companyId = "Company 1";
        globals.gotoPlace(new CompanyDetailsPlace(companyId));
        
        
        // verify status display
        assertNull(statusDisplay.getStatus());
        assertNull(errorDisplay.getError());
        
        
        // verify that we're in the right place
        CompanyDetailsPlace currentPlace = (CompanyDetailsPlace) globals.getCurrentPlace();
        assertNotNull(currentPlace);
        assertEquals(companyId, currentPlace.getCompanyId());
        
        
    }

}
