package com.vincentfazio.ui.company.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.vincentfazio.ui.survey.activity.CompanyDetailsActivity;
import com.vincentfazio.ui.survey.view.CompanyDetailsDisplay;
import com.vincentfazio.ui.company.global.MockCompanyGlobals;
import com.vincentfazio.ui.company.view.MockCompanyDetailsDisplay;
import com.vincentfazio.ui.view.MockErrorView;
import com.vincentfazio.ui.view.MockStatusView;

public class CompanyDetailsActivityTest {

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
        new CompanyDetailsActivity(companyId, globals).start(null, null);
        
        
        // verify status display
        assertNull(statusDisplay.getStatus());
        assertNull(errorDisplay.getError());
        
        
        // verify that we're showing the right information
        assertEquals(companyId, detailsView.getCompanyDetails().getCompanyId());
    }

}
