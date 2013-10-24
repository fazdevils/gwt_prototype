package com.vincentfazio.ui.vendor.view.documentation;

import java.util.Collection;

import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.view.Display;

public interface DocumentationListDisplay extends Display {

    public static enum DocumentationDeckEnum {
        DocumentationDetails,
        NoDocumentation;
    }

    boolean isDocumentationListLoaded();
    
    void selectDocumentation(Boolean refreshDocumentationDetail);

    void selectDocumentation(String selectedDocumentationId, Boolean refreshDocumentationDetail);

    void setDocumentationList(Collection<DocumentationBean> result);
    
    void setPresenter(Presenter presenter);
    
    void showView(DocumentationDeckEnum vendorView);

    public interface Presenter {
        void switchDocumentation(String documentationId, DocumentationDeckEnum currentPage);
    }

	String getVendorId();
	void setVendorId(String vendorId);
    
}