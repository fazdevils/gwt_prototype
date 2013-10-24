package com.vincentfazio.ui.vendor.view.documentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.vincentfazio.ui.bean.DocumentationBean;
import com.vincentfazio.ui.bean.SearchBoxComparator;
import com.vincentfazio.ui.vendor.global.GwtVendorGlobals;
import com.vincentfazio.ui.vendor.view.VendorHomeDisplay;
import com.vincentfazio.ui.vendor.view.VendorHomeDisplay.VendorDeckEnum;
import com.vincentfazio.ui.view.component.DataProviderSearchBox;
import com.vincentfazio.ui.view.component.DataProviderSearchBoxUpdateCallback;
import com.vincentfazio.ui.view.util.GwtCellTableResources;

public class DocumentationListView extends Composite implements DocumentationListDisplay, DataProviderSearchBoxUpdateCallback {

    private static DocumentationListUiBinder uiBinder = GWT.create(DocumentationListUiBinder.class);


    interface DocumentationListUiBinder extends UiBinder<Widget, DocumentationListView> {
    }

    @UiField
    DeckLayoutPanel documentationDeck;

    @UiField(provided=true)
    CellTable<DocumentationBean> documentation;
    
    @UiField(provided=true)
    SimplePager pager;
    
    @UiField
    DocumentationDetailsView documentationDetails;
    
    @UiField
    NoDocumentationSelectedView noDocumentation;
    
    @UiField(provided=true)
    DataProviderSearchBox<DocumentationBean> documentationSearchBox;

    @UiField
    DivElement documentationlistDiv;
    
    @UiField
    DivElement emptyDocumentationlistDiv;

    private ListDataProvider<DocumentationBean> documentationDataProvider;
    
    private SingleSelectionModel<DocumentationBean> selectionModel;
    
    private Presenter presenter;

    private boolean documentationListLoaded = false;

    private boolean refreshDocumentationDetail = true;
    
    public DocumentationListView() {

    	CellTable.Resources resources = GWT.create(GwtCellTableResources.class);
    	documentation = new CellTable<DocumentationBean>(0, resources); // the page size should be set in the UIBinder definition
        Column<DocumentationBean, String> documentationNameColumn = new Column<DocumentationBean, String>(new TextCell()) {
            @Override
            public String getValue(DocumentationBean documentation) {
                return documentation.getDocumentationName();
            }
        };
        documentation.addColumn(documentationNameColumn, "Documentation Name");
        
        selectionModel = new SingleSelectionModel<DocumentationBean>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                switchDocumentation(DocumentationDeckEnum.DocumentationDetails);                
            }

        });
        
        documentation.setSelectionModel(selectionModel);
        
        pager = new SimplePager();
        pager.setDisplay(documentation);
        
        documentationDataProvider = new ListDataProvider<DocumentationBean>();
        documentationDataProvider.addDataDisplay(documentation);

        documentationSearchBox = new DataProviderSearchBox<DocumentationBean>(documentationDataProvider, new DocumentationContainsStringComparator());
        documentationSearchBox.setPrompt("Type to search for documentation...");
        documentationSearchBox.setSearchBoxUpdateCallback(this);
        
        initWidget(uiBinder.createAndBindUi(this));
        
        GwtVendorGlobals.getInstance().registerDisplay(DocumentationListDisplay.class, this);
    }
    
    /* (non-Javadoc)
     * @see com.vincentfazio.ui.administration.view.UserListDisplay#setList(java.util.List)
     */
    @Override
    public void setDocumentationList(Collection<DocumentationBean> documentationList) {
    	ArrayList<DocumentationBean> documentationBeans= new ArrayList<DocumentationBean>(documentationList);
    	documentationSearchBox.setUnfilteredList(documentationBeans);
        documentationListLoaded = true;
    }

    @Override
    public void selectDocumentation(Boolean refreshDocumentationDetail) {
        String selectedDocumentationId = documentationDetails.documentationId.getText();
        selectDocumentation(selectedDocumentationId, refreshDocumentationDetail);
    }
     
    @Override
    public void selectDocumentation(String selectedDocumentationId, Boolean refreshDocumentationDetail) { 
        this.refreshDocumentationDetail = refreshDocumentationDetail;
        
        // no documentation has been selected - so pick one
        if (selectedDocumentationId.isEmpty()) {    
            List<DocumentationBean> documentationList = documentationSearchBox.getUnfilteredList();
            if (!documentationList.isEmpty()) {
                this.refreshDocumentationDetail = true;
                selectedDocumentationId = documentationList.get(0).getDocumentationName();
            }
        }
        if (!selectedDocumentationId.isEmpty()) {
        	DocumentationBean selectedDocumentation = new DocumentationBean();
        	selectedDocumentation.setDocumentationName(selectedDocumentationId);
            if (selectionModel.isSelected(selectedDocumentation)) {
                switchDocumentation(getCurrentPage());
            } else {
                selectionModel.setSelected(selectedDocumentation, true);
            }
        }        
    }

    private DocumentationDeckEnum getCurrentPage() {
        DocumentationDeckEnum currentPage = null;
        
        if (null == documentationDeck.getVisibleWidget()) {
            currentPage = DocumentationDeckEnum.DocumentationDetails;
        }else if (documentationDeck.getVisibleWidget().equals(documentationDetails)) {
            currentPage = DocumentationDeckEnum.DocumentationDetails;
        }

        return currentPage;
    }
    
    
    @Override
    public boolean isDocumentationListLoaded() {
        return documentationListLoaded ;
    }

    @Override
    public void showView(DocumentationDeckEnum documentationView) {
        VendorHomeDisplay navigationDisplay = (VendorHomeDisplay) GwtVendorGlobals.getInstance().getDisplay(VendorHomeDisplay.class);
        navigationDisplay.showView(VendorDeckEnum.Documentation);
        switch (documentationView) {
            case DocumentationDetails:
            	documentationDeck.showWidget(documentationDetails);
                break;
            case NoDocumentation:
            	documentationDeck.showWidget(noDocumentation);
                break;
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }


    private void switchDocumentation(DocumentationDeckEnum currentPage) {
        if (refreshDocumentationDetail) {
        	DocumentationBean selectedDocumentation = getSelectedDocumentation();
            presenter.switchDocumentation(selectedDocumentation.getDocumentationName(), currentPage);
        }
        refreshDocumentationDetail = true;
    }
    
    private DocumentationBean getSelectedDocumentation() {
        return selectionModel.getSelectedObject();
    }

    public static class DocumentationContainsStringComparator implements SearchBoxComparator<DocumentationBean> {
        @Override
        public boolean equals(DocumentationBean documentation, String searchText) {
        	String documentationName = documentation.getDocumentationName();
            return documentationName.toLowerCase().contains(searchText.toLowerCase());
        }
    }

    @Override
    public void handleSearchBoxChange() {
        Style listStyle = documentationlistDiv.getStyle();
        Style emptyStyle = emptyDocumentationlistDiv.getStyle();
        
        List<DocumentationBean> vendorList = documentationDataProvider.getList();
        if (vendorList.isEmpty()) {
            listStyle.setDisplay(Display.NONE);
            emptyStyle.setDisplay(Display.BLOCK);
        } else {
            listStyle.setDisplay(Display.BLOCK);
            emptyStyle.setDisplay(Display.NONE);
        }
        
    }

	@Override
	public String getVendorId() {
		return documentationDetails.getVendorId();
	}

	@Override
	public void setVendorId(String vendorId) {
		documentationDetails.setVendorId(vendorId);
		
	}

}