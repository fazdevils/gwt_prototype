package com.vincentfazio.ui.survey.view.documentation;

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
    
    void showView(DocumentationDeckEnum companyView);

    public interface Presenter {
        void switchDocumentation(String documentationId, DocumentationDeckEnum currentPage);
    }

	String getCompanyId();
	void setCompanyId(String companyId);
    
}