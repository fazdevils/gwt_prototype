package com.vincentfazio.ui.administration.view;

import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.vincentfazio.ui.administration.activity.CreateVendorActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.view.MainAdminNavigationDisplay.MainAdminNavigationEnum;
import com.vincentfazio.ui.bean.SearchBoxComparator;
import com.vincentfazio.ui.view.component.DataProviderSearchBox;
import com.vincentfazio.ui.view.component.DataProviderSearchBoxUpdateCallback;
import com.vincentfazio.ui.view.util.GwtCellTableResources;

public class VendorListView extends Composite implements VendorListDisplay, DataProviderSearchBoxUpdateCallback{

    private static VendorListUiBinder uiBinder = GWT.create(VendorListUiBinder.class);


    interface VendorListUiBinder extends UiBinder<Widget, VendorListView> {
    }

    @UiField
    DeckLayoutPanel vendorDeck;

    @UiField(provided=true)
    CellTable<String> vendors;
    
    @UiField(provided=true)
    SimplePager pager;
    
    @UiField
    VendorDetailsView vendorDetails;
    
    @UiField
    NoVendorSelectedView noVendor;
    
    @UiField
    VendorUserPermissionsView vendorUserPermissions;
    
    @UiField(provided=true)
    DataProviderSearchBox<String> vendorSearchBox;

    @UiField
    DivElement vendorlistDiv;
    
    @UiField
    DivElement emptyVendorlistDiv;

    @UiField
    Button addVendorButton;

    private ListDataProvider<String> vendorDataProvider;
    
    private SingleSelectionModel<String> selectionModel;
    
    private Presenter presenter;

    private boolean vendorListLoaded = false;

    private boolean refreshVendorDetail = true;
    
    public VendorListView() {

    	CellTable.Resources resources = GWT.create(GwtCellTableResources.class);
    	vendors = new CellTable<String>(0, resources); // the page size should be set in the UIBinder definition
        Column<String, String> vendorNameColumn = new Column<String, String>(new TextCell()) {
            @Override
            public String getValue(String name) {
                return name;
            }
        };
        vendors.addColumn(vendorNameColumn, "Supplier Name");
        
        selectionModel = new SingleSelectionModel<String>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                switchVendor(VendorDeckEnum.VendorDetails);                
            }

        });
        
        vendors.setSelectionModel(selectionModel);
        
        pager = new SimplePager();
        pager.setDisplay(vendors);
        
        vendorDataProvider = new ListDataProvider<String>();
        vendorDataProvider.addDataDisplay(vendors);

        vendorSearchBox = new DataProviderSearchBox<String>(vendorDataProvider, new VendorContainsStringComparator());
        vendorSearchBox.setPrompt("Type to search or add supplier...");
        vendorSearchBox.setSearchBoxUpdateCallback(this);
        
        initWidget(uiBinder.createAndBindUi(this));
        
        addVendorButton.addStyleName("hidden");
        addVendorButton.addClickHandler(new ClickHandler() {   
            @Override
            public void onClick(ClickEvent event) {
                new CreateVendorActivity(GwtAdminGlobals.getInstance(), vendorSearchBox.getText()).start(null, null);
            }
        });

        GwtAdminGlobals.getInstance().registerDisplay(VendorListDisplay.class, this);
    }
    
    /* (non-Javadoc)
     * @see com.vincentfazio.ui.administration.view.UserListDisplay#setList(java.util.List)
     */
    @Override
    public void setVendorList(List<String> vendorList) {
        vendorSearchBox.setUnfilteredList(vendorList);
        vendorListLoaded = true;
    }

    @Override
    public void selectVendor(Boolean refreshVendorDetail) {
        String selectedVendorId = vendorDetails.vendorId.getText();
        selectVendor(selectedVendorId, refreshVendorDetail);
    }
     
    @Override
    public void selectVendor(String selectedVendorId, Boolean refreshVendorDetail) { 
        this.refreshVendorDetail = refreshVendorDetail;
        
        // no vendor has been selected - so pick one
        if (selectedVendorId.isEmpty()) {    
            List<String> vendorList = vendorSearchBox.getUnfilteredList();
            if (!vendorList.isEmpty()) {
                this.refreshVendorDetail = true;
                selectedVendorId = vendorList.get(0);
            }
        }
        if (!selectedVendorId.isEmpty()) {
            if (selectionModel.isSelected(selectedVendorId)) {
                switchVendor(getCurrentPage());
            } else {
                selectionModel.setSelected(selectedVendorId, true);
            }
        }        
    }

    private VendorDeckEnum getCurrentPage() {
        VendorDeckEnum currentPage = null;
        
        if (null == vendorDeck.getVisibleWidget()) {
            currentPage = VendorDeckEnum.VendorDetails;
        }else if (vendorDeck.getVisibleWidget().equals(vendorDetails)) {
            currentPage = VendorDeckEnum.VendorDetails;
        } else if (vendorDeck.getVisibleWidget().equals(vendorUserPermissions)) {
            currentPage = VendorDeckEnum.VendorUserPermissions;
        }

        return currentPage;
    }
    
    
    @Override
    public boolean isVendorListLoaded() {
        return vendorListLoaded ;
    }

    @Override
    public void showView(VendorDeckEnum vendorView) {
        MainAdminNavigationDisplay navigationDisplay = (MainAdminNavigationDisplay) GwtAdminGlobals.getInstance().getDisplay(MainAdminNavigationDisplay.class);
        navigationDisplay.showView(MainAdminNavigationEnum.Vendors);
        switch (vendorView) {
            case VendorDetails:
                vendorDeck.showWidget(vendorDetails);
                break;
            case NoVendor:
                vendorDeck.showWidget(noVendor);
                break;
            case VendorUserPermissions:
                vendorDeck.showWidget(vendorUserPermissions);
                break;                
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }


    private void switchVendor(VendorDeckEnum currentPage) {
        if (refreshVendorDetail) {
            String selectedVendorId = getSelectedVendorId();
            presenter.switchVendor(selectedVendorId, currentPage);
        }
        refreshVendorDetail = true;
    }
    
    private String getSelectedVendorId() {
        return selectionModel.getSelectedObject();
    }

    public static class VendorContainsStringComparator implements SearchBoxComparator<String> {
        @Override
        public boolean equals(String bean, String searchText) {
            return bean.toLowerCase().contains(searchText.toLowerCase());
        }
    }

    @Override
    public void handleSearchBoxChange() {
        Style listStyle = vendorlistDiv.getStyle();
        Style emptyStyle = emptyVendorlistDiv.getStyle();
        
        List<String> vendorList = vendorDataProvider.getList();
        if (vendorList.isEmpty()) {
            listStyle.setDisplay(Display.NONE);
            emptyStyle.setDisplay(Display.BLOCK);
        } else {
            listStyle.setDisplay(Display.BLOCK);
            emptyStyle.setDisplay(Display.NONE);
        }
        
        String searchText = vendorSearchBox.getText();
        if (vendorSearchBox.isDisabled() || searchText.isEmpty() || vendorExists(vendorList, searchText)) {
            addVendorButton.addStyleName("hidden");    
        } else {
            addVendorButton.removeStyleName("hidden");
        }
        
    }

    private boolean vendorExists(List<String> vendorList, String searchText) {
        for (String vendor: vendorList) {
            if (vendor.equalsIgnoreCase(searchText)) {
                return true;
            }
        }
        return false;
    }

}