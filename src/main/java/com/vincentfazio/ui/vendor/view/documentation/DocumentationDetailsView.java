package com.vincentfazio.ui.vendor.view.documentation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.vendor.place.DocumentationPlace;

public class DocumentationDetailsView extends Composite implements DocumentationDetailsDisplay {

    private static DocumentationDetailsUiBinder uiBinder = GWT.create(DocumentationDetailsUiBinder.class);


    interface DocumentationDetailsUiBinder extends UiBinder<Widget, DocumentationDetailsView> {
    }


    public DocumentationDetailsView() {
        initWidget(uiBinder.createAndBindUi(this));
        
        undoButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setHasUnsavedChanges(false);
                GwtAdminGlobals.getInstance().gotoPlace(new DocumentationPlace(getVendorId(), getDocumentationDetails()));                
            }
        });

        
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
//                new DocumentationDetailsSaveActivity(GwtAdminGlobals.getInstance(), getDocumentationDetails()).start(null, null);
            }
        });
        
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (Window.confirm("Are you sure you want to permanently delete this document?")) {
//                    new DeleteVendorActivity(GwtAdminGlobals.getInstance(), getVendorId()).start(null, null);
                }
            }
        });
        
        GwtAdminGlobals.getInstance().registerDisplay(DocumentationDetailsDisplay.class, this);
    }

    @UiField
    Label documentationId;

    @UiField
    DivElement saveDiv;

    @UiField
    DivElement deleteDiv;

    @UiField
    Anchor undoButton;
    
    @UiField
    Button saveButton;

    @UiField
    Anchor deleteButton;
    

    

    private boolean hasChanges = false;
    private String vendorId;
    
     
    private String getDocumentationId() {
        return documentationId.getText();
    }


    private void setDocumentationId(String documentationId) {
        this.documentationId.setText(documentationId);
    }


    public void setVendorId(String vendorId) {
        this.vendorId= vendorId;
    }


    @Override
    public void setDocumentationDetails(DocumentationBean documentation) {        
        resetDisplay();
        
        setDocumentationId(documentation.getDocumentationName());

        setHasUnsavedChanges(false);
    }

    @Override
    public DocumentationBean getDocumentationDetails() {
        DocumentationBean documentation = new DocumentationBean();
        documentation.setDocumentationName(getDocumentationId());
        
        return documentation;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    private void resetDisplay() {
        setVendorId(null);
        setDocumentationId(null);
        
        setHasUnsavedChanges(false);

    }

    @Override
    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        hasChanges = hasUnsavedChanges;
        
        Style divStyle = saveDiv.getStyle();
        Style deleteDivStyle = deleteDiv.getStyle();
        if (hasChanges ) {
            deleteDivStyle.setDisplay(Display.NONE);
            divStyle.setDisplay(Display.BLOCK);            
            saveButton.setEnabled(isValid());
            
        } else {
            deleteDivStyle.setDisplay(Display.BLOCK);
            divStyle.setDisplay(Display.NONE);            
        }

    }

    @Override
    public boolean hasUnsavedChanges() {
        return hasChanges;
    }


	public String getVendorId() {
		return vendorId;
	}

}
