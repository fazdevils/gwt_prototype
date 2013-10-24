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
import com.vincentfazio.ui.bean.VendorPermissionBean;

public interface VendorPermissionCellComponentCallback {
    
    void handleCellSelection(boolean isSelected);
    void handleHeaderSelection(boolean isSelected);

    // Vender Permission Cell Templates
    interface CheckedVendorPermissionTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div class=\"has-vendor-access\"><input type=\"checkbox\" checked=\"checked\"/>{0}</div>")
        SafeHtml cell(SafeHtml vendorName);
    }
    static CheckedVendorPermissionTemplate checkedVendorPermissionTemplate = GWT.create(CheckedVendorPermissionTemplate.class);
    
    interface UncheckedVendorPermissionTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div class=\"no-vendor-access\"><input type=\"checkbox\"/>{0}</div>")
        SafeHtml cell(SafeHtml vendorName);
    }
    static UncheckedVendorPermissionTemplate uncheckedVendorPermissionTemplate = GWT.create(UncheckedVendorPermissionTemplate.class);

    
    
    // Vendor Permission Cell
    class VendorPermissionCell extends AbstractCell<VendorPermissionBean> implements Cell<VendorPermissionBean> {

        private VendorPermissionCellComponentCallback callback;

        public VendorPermissionCell(VendorPermissionCellComponentCallback callback) {
            super("click");
            this.callback = callback;
        }

        @Override
        public void render(com.google.gwt.cell.client.Cell.Context context, VendorPermissionBean value, SafeHtmlBuilder sb) {
            if (value == null) {
                return;
            }

            SafeHtml vendorName = SafeHtmlUtils.fromString(value.getVendorName());
            SafeHtml rendered;
            if (value.hasAccess()) {
                rendered = checkedVendorPermissionTemplate.cell(vendorName);   
            } else {
                rendered = uncheckedVendorPermissionTemplate.cell(vendorName);   
            }
            sb.append(rendered);
            
        }

        @Override
        public void onBrowserEvent(Context context, Element parent, VendorPermissionBean vendorAccess, NativeEvent event, ValueUpdater<VendorPermissionBean> valueUpdater) {
            vendorAccess.setAccess(!vendorAccess.hasAccess());
            setValue(context, parent, vendorAccess);
            callback.handleCellSelection(vendorAccess.hasAccess());
        }
        
    }

    
    // Vender Permission Header
    static class VendorPermissionHeader extends Header<VendorPermissionBean> {

        private VendorPermissionBean allAccess;
        private CellTable<VendorPermissionBean> vendorPermissions;
        private VendorPermissionCellComponentCallback callback;
        
        public VendorPermissionHeader(String label, CellTable<VendorPermissionBean> vendorPermissionsTable, VendorPermissionCellComponentCallback callback) {
            super(new VendorPermissionCell(callback));
            allAccess = new VendorPermissionBean();
            allAccess.setVendorName(label);
            allAccess.setAccess(false);
            
            vendorPermissions = vendorPermissionsTable;
            this.callback = callback;
        }

        @Override
        public VendorPermissionBean getValue() {
            return allAccess;
        }
        
        public void setValue(Boolean accessToAllVendors) {
            allAccess.setAccess(accessToAllVendors);
            vendorPermissions.redraw();
        }
        
        @Override
        public void render(Context context, SafeHtmlBuilder sb) {
            SafeHtml vendorName = SafeHtmlUtils.fromString(allAccess.getVendorName());
            SafeHtml rendered;
            if (allAccess.hasAccess()) {
                rendered = checkedVendorPermissionTemplate.cell(vendorName);   
            } else {
                rendered = uncheckedVendorPermissionTemplate.cell(vendorName);   
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
