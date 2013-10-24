package com.vincentfazio.ui.administration.view;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.vincentfazio.ui.administration.activity.UserPermissionsSaveActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.place.UserPermissionsPlace;
import com.vincentfazio.ui.administration.view.component.VendorPermissionCellComponentCallback;
import com.vincentfazio.ui.bean.VendorPermissionBean;
import com.vincentfazio.ui.view.component.DataProviderSearchBox;
import com.vincentfazio.ui.view.component.DataProviderSearchBoxUpdateCallback;
import com.vincentfazio.ui.view.util.GwtCellTableResources;

public class UserPermissionsView extends Composite implements UserPermissionsDisplay, DataProviderSearchBoxUpdateCallback, VendorPermissionCellComponentCallback {

    private static UserPermissionsUiBinder uiBinder = GWT.create(UserPermissionsUiBinder.class);


    interface UserPermissionsUiBinder extends UiBinder<Widget, UserPermissionsView> {
    }


    public UserPermissionsView() {
        createUnauthorizedVendorList();
        
        initWidget(uiBinder.createAndBindUi(this));
        
        // TODO this may be a better way to handle all of my click handlers
        // http://code.google.com/webtoolkit/doc/latest/DevGuideUiHandlers.html

        
        undoButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String currentUserId = userId;
                userId = "";
                setHasUnsavedChanges(false);
                GwtAdminGlobals.getInstance().gotoPlace(new UserPermissionsPlace(currentUserId, userRole));                
            }
        });

        
        backButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                History.back();
            }
        });

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                new UserPermissionsSaveActivity(GwtAdminGlobals.getInstance(), userId, userRole.toLowerCase(), vendorSearchBox.getUnfilteredList()).start(null, null);
            }
        });
        
        GwtAdminGlobals.getInstance().registerDisplay(UserPermissionsDisplay.class, this);
    }
    


    @UiField
    Label userRoleTitle;

    @UiField
    Anchor undoButton;
    
    @UiField
    Anchor backButton;
    
    @UiField
    Button saveButton;
    
    @UiField
    DivElement saveDiv;
    
    @UiField
    DivElement backDiv;
    
    @UiField(provided=true)
    DataProviderSearchBox<VendorPermissionBean> vendorSearchBox;

    @UiField(provided=true)
    CellTable<VendorPermissionBean> vendorPermissions;
    
    @UiField(provided=true)
    SimplePager vendorPermissionsPager;

    @UiField
    DivElement vendorlistDiv;
    
    @UiField
    DivElement emptyVendorlistDiv;

     
    private String userId = "";
    private String userRole = "";
    private ListDataProvider<VendorPermissionBean> vendorPermissionsDataProvider;   
    private SingleSelectionModel<VendorPermissionBean> vendorPermissionsSelectionModel;
    private VendorPermissionHeader vendorPermissionHeader;
    private ArrayList<String> vendorList = new ArrayList<String>();

    private boolean hasChanges = false;
    

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
        setUserRoleLabel();
    }

    @Override
    public String getUserRole() {
        return userRole;
    }

    @Override
    public void setUserRole(String userRole) {
        if (userRole.equals("administrator")) {
            this.userRole = "Administrator";            
        } else if (userRole.equals("customer")) {
            this.userRole = "Customer";            
        } else if (userRole.equals("vendor")) {
            this.userRole = "Supplier";            
        }  else {
            this.userRole = userRole;
        }
        setUserRoleLabel();
    }

    @Override
    public List<VendorPermissionBean> getVendorsPermissionList() {
        return vendorSearchBox.getUnfilteredList();
    }
    
    @Override
    public void setVendorsPermissionList(List<VendorPermissionBean> vendorPermissionList) {
        vendorSearchBox.setUnfilteredList(vendorPermissionList);
        setHasUnsavedChanges(false);
    }
    
    @Override
    public void handleSearchBoxChange() {
        areAllSelected();
        
        Style listStyle = vendorlistDiv.getStyle();
        Style emptyStyle = emptyVendorlistDiv.getStyle();
        
        if (vendorPermissionsDataProvider.getList().isEmpty()) {
            listStyle.setDisplay(Display.NONE);
            emptyStyle.setDisplay(Display.BLOCK);
        } else {
            listStyle.setDisplay(Display.BLOCK);
            emptyStyle.setDisplay(Display.NONE);
        }

    }

    @Override
    public boolean hasUnsavedChanges() {
        return hasChanges;
    }
    
    @Override
    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        hasChanges = hasUnsavedChanges;
        
        Style backDivStyle = backDiv.getStyle();
        Style saveDivStyle = saveDiv.getStyle();
        if (hasChanges) {
            backDivStyle.setDisplay(Display.NONE);            
            saveDivStyle.setDisplay(Display.BLOCK);
        } else {
            saveDivStyle.setDisplay(Display.NONE);            
            backDivStyle.setDisplay(Display.BLOCK);            
        }
        
        areAllSelected();
    }

    @Override
    public void handleCellSelection(boolean isSelected) {
        setHasUnsavedChanges(true);
    }

    @Override
    public void handleHeaderSelection(boolean isSelected) {
        selectAllVendors(isSelected);
    }

    private void setUserRoleLabel() {
        userRoleTitle.setText(userId + " - " + userRole + " Role");
    }
    
    
    private void createUnauthorizedVendorList() {

        CellTable.Resources resources = GWT.create(GwtCellTableResources.class);
        vendorPermissions = new CellTable<VendorPermissionBean>(0, resources); // the page size should be set in the UIBinder definition
        
        vendorPermissionsSelectionModel = new SingleSelectionModel<VendorPermissionBean>();
        vendorPermissions.setSelectionModel(vendorPermissionsSelectionModel);
        

        Column<VendorPermissionBean, VendorPermissionBean> vendorPermissionSelectionColumn = new Column<VendorPermissionBean, VendorPermissionBean>(new VendorPermissionCell(this)) {
            @Override
            public VendorPermissionBean getValue(VendorPermissionBean vendorPermission) {
                return vendorPermission;
            }
        };   
        vendorPermissionHeader = new VendorPermissionHeader("Supplier Access", vendorPermissions, this);
        vendorPermissions.addColumn(vendorPermissionSelectionColumn, vendorPermissionHeader);
                
        vendorPermissionsPager = new SimplePager();
        vendorPermissionsPager.setDisplay(vendorPermissions);
        
        vendorPermissionsDataProvider = new ListDataProvider<VendorPermissionBean>();
        vendorPermissionsDataProvider.addDataDisplay(vendorPermissions);
        
        vendorSearchBox = new DataProviderSearchBox<VendorPermissionBean>(vendorPermissionsDataProvider, new VendorPermissionBean.VendorNameFieldContainsStringComparator());
        vendorSearchBox.setPrompt("Type to search for supplier...");
        vendorSearchBox.setSearchBoxUpdateCallback(this);

    }
    
    private void selectAllVendors(boolean hasAccess) {
        List<VendorPermissionBean> vendorPermissions = vendorPermissionsDataProvider.getList();
        for (VendorPermissionBean vendorPermission: vendorPermissions) {
            vendorPermission.setAccess(hasAccess);      
        }
        vendorPermissionsDataProvider.flush();
        vendorPermissionsDataProvider.refresh();
        setHasUnsavedChanges(true);
    }
    
    private void areAllSelected() {
        Boolean allSelected = true;
        
        for (VendorPermissionBean vendorPermission: vendorPermissionsDataProvider.getList()) {
            if (!vendorPermission.hasAccess()) {
                allSelected = false;
                break;
            }
        }
        
        vendorPermissionHeader.setValue(allSelected);
    }

    @Override
    public void setVendorList(ArrayList<String> vendors) {
        vendorList.clear();
        vendorList.addAll(vendors);
        
    }

    @Override
    public ArrayList<String> getVendorList() {
        return vendorList;
    }

    @Override
    public boolean isVendorListLoaded() {
        return (null != vendorList) && !vendorList.isEmpty() ;
    }

}
