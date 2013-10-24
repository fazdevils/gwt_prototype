package com.vincentfazio.ui.vendor.view.documentation;

import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.view.Display;


public interface DocumentationDetailsDisplay extends Display {

    boolean isValid();

	void setVendorId(String vendorId);
	
    DocumentationBean getDocumentationDetails();    
    void setDocumentationDetails(DocumentationBean documentation);

    boolean hasUnsavedChanges();
    void setHasUnsavedChanges(boolean hasUnsavedChanges);

}