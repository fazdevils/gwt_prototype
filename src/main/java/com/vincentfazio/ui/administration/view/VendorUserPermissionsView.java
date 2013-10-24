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
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.vincentfazio.ui.administration.activity.VendorUserPermissionsSaveActivity;
import com.vincentfazio.ui.administration.global.GwtAdminGlobals;
import com.vincentfazio.ui.administration.place.VendorUserPermissionsPlace;
import com.vincentfazio.ui.administration.view.component.UserPermissionCellComponentCallback;
import com.vincentfazio.ui.bean.VendorUserPermissionBean;
import com.vincentfazio.ui.view.component.DataProviderSearchBox;
import com.vincentfazio.ui.view.component.DataProviderSearchBoxUpdateCallback;
import com.vincentfazio.ui.view.util.GwtCellTableResources;

public class VendorUserPermissionsView extends Composite implements VendorUserPermissionsDisplay, DataProviderSearchBoxUpdateCallback, UserPermissionCellComponentCallback {

    private static VendorPermissionsUiBinder uiBinder = GWT.create(VendorPermissionsUiBinder.class);


    interface VendorPermissionsUiBinder extends UiBinder<Widget, VendorUserPermissionsView> {
    }


    public VendorUserPermissionsView() {
        createUnauthorizedUserList();
        
        initWidget(uiBinder.createAndBindUi(this));
        
        undoButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String currentVendorId = vendor;
                vendor = "";
                setHasUnsavedChanges(false);
                GwtAdminGlobals.getInstance().gotoPlace(new VendorUserPermissionsPlace(currentVendorId, userRole));                
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
                new VendorUserPermissionsSaveActivity(GwtAdminGlobals.getInstance(), vendor, userRole.toLowerCase(), userSearchBox.getUnfilteredList()).start(null, null);
            }
        });
        
        GwtAdminGlobals.getInstance().registerDisplay(VendorUserPermissionsDisplay.class, this);
    }
    


    @UiField
    Label vendorRoleTitle;

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
    DataProviderSearchBox<VendorUserPermissionBean> userSearchBox;

    @UiField(provided=true)
    CellTable<VendorUserPermissionBean> userPermissions;
    
    @UiField(provided=true)
    SimplePager userPermissionsPager;

    @UiField
    DivElement vendorlistDiv;
    
    @UiField
    DivElement emptyVendorlistDiv;

     
    private String vendor = "";
    private String userRole = "";
    private ListDataProvider<VendorUserPermissionBean> userPermissionsDataProvider;   
    private SelectionModel<VendorUserPermissionBean> userPermissionsSelectionModel;
    private UserPermissionHeader userPermissionHeader;
    private ArrayList<String> userList = new ArrayList<String>();

    private boolean hasChanges = false;
    

    @Override
    public String getVendorId() {
        return vendor;
    }

    @Override
    public void setVendorId(String vendorId) {
        this.vendor = vendorId;
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
    public List<VendorUserPermissionBean> getVendorUserPermissionList() {
        return userSearchBox.getUnfilteredList();
    }
    
    @Override
    public void setVendorUserPermissionList(List<VendorUserPermissionBean> vendorPermissionList) {
        userSearchBox.setUnfilteredList(vendorPermissionList);
        setHasUnsavedChanges(false);
    }
    
    @Override
    public void handleSearchBoxChange() {
        areAllSelected();
        
        Style listStyle = vendorlistDiv.getStyle();
        Style emptyStyle = emptyVendorlistDiv.getStyle();
        
        if (userPermissionsDataProvider.getList().isEmpty()) {
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
        selectAllUsers(isSelected);
    }

    private void setUserRoleLabel() {
        vendorRoleTitle.setText(vendor + " - " + userRole + " Users");
    }
    
    
    private void createUnauthorizedUserList() {

        CellTable.Resources resources = GWT.create(GwtCellTableResources.class);
        userPermissions = new CellTable<VendorUserPermissionBean>(0, resources); // the page size should be set in the UIBinder definition
        
        userPermissionsSelectionModel = new SingleSelectionModel<VendorUserPermissionBean>();
        userPermissions.setSelectionModel(userPermissionsSelectionModel);
        

        Column<VendorUserPermissionBean, VendorUserPermissionBean> vendorPermissionSelectionColumn = new Column<VendorUserPermissionBean, VendorUserPermissionBean>(new UserPermissionCell(this)) {
            @Override
            public VendorUserPermissionBean getValue(VendorUserPermissionBean userPermission) {
                return userPermission;
            }
        };   
        userPermissionHeader = new UserPermissionHeader("User Access", userPermissions, this);
        userPermissions.addColumn(vendorPermissionSelectionColumn, userPermissionHeader);
                
        userPermissionsPager = new SimplePager();
        userPermissionsPager.setDisplay(userPermissions);
        
        userPermissionsDataProvider = new ListDataProvider<VendorUserPermissionBean>();
        userPermissionsDataProvider.addDataDisplay(userPermissions);
        
        userSearchBox = new DataProviderSearchBox<VendorUserPermissionBean>(userPermissionsDataProvider, new VendorUserPermissionBean.UserNameFieldContainsStringComparator());
        userSearchBox.setPrompt("Type to search for user...");
        userSearchBox.setSearchBoxUpdateCallback(this);

    }
    
    private void selectAllUsers(boolean hasAccess) {
        List<VendorUserPermissionBean> userPermissions = userPermissionsDataProvider.getList();
        for (VendorUserPermissionBean userPermission: userPermissions) {
            userPermission.setAccess(hasAccess);      
        }
        userPermissionsDataProvider.flush();
        userPermissionsDataProvider.refresh();
        setHasUnsavedChanges(true);
    }
    
    private void areAllSelected() {
        Boolean allSelected = true;
        
        for (VendorUserPermissionBean userPermission: userPermissionsDataProvider.getList()) {
            if (!userPermission.hasAccess()) {
                allSelected = false;
                break;
            }
        }
        
        userPermissionHeader.setValue(allSelected);
    }

    @Override
    public void setUserList(ArrayList<String> vendors) {
        userList.clear();
        userList.addAll(vendors);
        
    }

    @Override
    public ArrayList<String> getUserList() {
        return userList;
    }

}
