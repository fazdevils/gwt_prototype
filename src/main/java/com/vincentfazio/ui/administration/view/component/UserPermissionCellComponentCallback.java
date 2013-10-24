package com.vincentfazio.ui.administration.view.component;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Header;
import com.vincentfazio.ui.bean.VendorUserPermissionBean;

public interface UserPermissionCellComponentCallback {
    
    void handleCellSelection(boolean isSelected);
    void handleHeaderSelection(boolean isSelected);

    // Vender Permission Cell Templates
    interface CheckedUserPermissionTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div class=\"has-vendor-access\"><input type=\"checkbox\" checked=\"checked\"/>{0}</div>")
        SafeHtml cell(SafeHtml userName);
    }
    static CheckedUserPermissionTemplate checkedUserPermissionTemplate = GWT.create(CheckedUserPermissionTemplate.class);
    
    interface UncheckedUserPermissionTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div class=\"no-vendor-access\"><input type=\"checkbox\"/>{0}</div>")
        SafeHtml cell(SafeHtml vendorName);
    }
    static UncheckedUserPermissionTemplate uncheckedUserPermissionTemplate = GWT.create(UncheckedUserPermissionTemplate.class);

    
    
    // User Permission Cell
    class UserPermissionCell extends AbstractCell<VendorUserPermissionBean> implements Cell<VendorUserPermissionBean> {

        private UserPermissionCellComponentCallback callback;

        public UserPermissionCell(UserPermissionCellComponentCallback callback) {
            super("click");
            this.callback = callback;
        }

        @Override
        public void render(com.google.gwt.cell.client.Cell.Context context, VendorUserPermissionBean value, SafeHtmlBuilder sb) {
            if (value == null) {
                return;
            }

            SafeHtml vendorName = SafeHtmlUtils.fromString(value.getUserName());
            SafeHtml rendered;
            if (value.hasAccess()) {
                rendered = checkedUserPermissionTemplate.cell(vendorName);   
            } else {
                rendered = uncheckedUserPermissionTemplate.cell(vendorName);   
            }
            sb.append(rendered);
            
        }

        @Override
        public void onBrowserEvent(Context context, Element parent, VendorUserPermissionBean vendorAccess, NativeEvent event, ValueUpdater<VendorUserPermissionBean> valueUpdater) {
            vendorAccess.setAccess(!vendorAccess.hasAccess());
            setValue(context, parent, vendorAccess);
            callback.handleCellSelection(vendorAccess.hasAccess());
        }
        
    }

    
    // Vender Permission Header
    static class UserPermissionHeader extends Header<VendorUserPermissionBean> {

        private VendorUserPermissionBean allAccess;
        private CellTable<VendorUserPermissionBean> vendorPermissions;
        private UserPermissionCellComponentCallback callback;
        
        public UserPermissionHeader(String label, CellTable<VendorUserPermissionBean> vendorPermissionsTable, UserPermissionCellComponentCallback callback) {
            super(new UserPermissionCell(callback));
            allAccess = new VendorUserPermissionBean();
            allAccess.setUserName(label);
            allAccess.setAccess(false);
            
            vendorPermissions = vendorPermissionsTable;
            this.callback = callback;
        }

        @Override
        public VendorUserPermissionBean getValue() {
            return allAccess;
        }
        
        public void setValue(Boolean accessToAllUsers) {
            allAccess.setAccess(accessToAllUsers);
            vendorPermissions.redraw();
        }
        
        @Override
        public void render(Context context, SafeHtmlBuilder sb) {
            SafeHtml vendorName = SafeHtmlUtils.fromString(allAccess.getUserName());
            SafeHtml rendered;
            if (allAccess.hasAccess()) {
                rendered = checkedUserPermissionTemplate.cell(vendorName);   
            } else {
                rendered = uncheckedUserPermissionTemplate.cell(vendorName);   
            }
            sb.append(rendered);
        }
        
        @Override
        public void onBrowserEvent(Cell.Context context, Element elem, NativeEvent event) {
            setValue(!allAccess.hasAccess());
            callback.handleHeaderSelection(allAccess.hasAccess());
        }

    }

}
