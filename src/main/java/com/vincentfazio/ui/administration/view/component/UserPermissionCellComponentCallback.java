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
import com.vincentfazio.ui.bean.CompanyUserPermissionBean;

public interface UserPermissionCellComponentCallback {
    
    void handleCellSelection(boolean isSelected);
    void handleHeaderSelection(boolean isSelected);

    // Vender Permission Cell Templates
    interface CheckedUserPermissionTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div class=\"has-company-access\"><input type=\"checkbox\" checked=\"checked\"/>{0}</div>")
        SafeHtml cell(SafeHtml userName);
    }
    static CheckedUserPermissionTemplate checkedUserPermissionTemplate = GWT.create(CheckedUserPermissionTemplate.class);
    
    interface UncheckedUserPermissionTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div class=\"no-company-access\"><input type=\"checkbox\"/>{0}</div>")
        SafeHtml cell(SafeHtml companyName);
    }
    static UncheckedUserPermissionTemplate uncheckedUserPermissionTemplate = GWT.create(UncheckedUserPermissionTemplate.class);

    
    
    // User Permission Cell
    class UserPermissionCell extends AbstractCell<CompanyUserPermissionBean> implements Cell<CompanyUserPermissionBean> {

        private UserPermissionCellComponentCallback callback;

        public UserPermissionCell(UserPermissionCellComponentCallback callback) {
            super("click");
            this.callback = callback;
        }

        @Override
        public void render(com.google.gwt.cell.client.Cell.Context context, CompanyUserPermissionBean value, SafeHtmlBuilder sb) {
            if (value == null) {
                return;
            }

            SafeHtml companyName = SafeHtmlUtils.fromString(value.getUserName());
            SafeHtml rendered;
            if (value.hasAccess()) {
                rendered = checkedUserPermissionTemplate.cell(companyName);   
            } else {
                rendered = uncheckedUserPermissionTemplate.cell(companyName);   
            }
            sb.append(rendered);
            
        }

        @Override
        public void onBrowserEvent(Context context, Element parent, CompanyUserPermissionBean companyAccess, NativeEvent event, ValueUpdater<CompanyUserPermissionBean> valueUpdater) {
            companyAccess.setAccess(!companyAccess.hasAccess());
            setValue(context, parent, companyAccess);
            callback.handleCellSelection(companyAccess.hasAccess());
        }
        
    }

    
    // Vender Permission Header
    static class UserPermissionHeader extends Header<CompanyUserPermissionBean> {

        private CompanyUserPermissionBean allAccess;
        private CellTable<CompanyUserPermissionBean> companyPermissions;
        private UserPermissionCellComponentCallback callback;
        
        public UserPermissionHeader(String label, CellTable<CompanyUserPermissionBean> companyPermissionsTable, UserPermissionCellComponentCallback callback) {
            super(new UserPermissionCell(callback));
            allAccess = new CompanyUserPermissionBean();
            allAccess.setUserName(label);
            allAccess.setAccess(false);
            
            companyPermissions = companyPermissionsTable;
            this.callback = callback;
        }

        @Override
        public CompanyUserPermissionBean getValue() {
            return allAccess;
        }
        
        public void setValue(Boolean accessToAllUsers) {
            allAccess.setAccess(accessToAllUsers);
            companyPermissions.redraw();
        }
        
        @Override
        public void render(Context context, SafeHtmlBuilder sb) {
            SafeHtml companyName = SafeHtmlUtils.fromString(allAccess.getUserName());
            SafeHtml rendered;
            if (allAccess.hasAccess()) {
                rendered = checkedUserPermissionTemplate.cell(companyName);   
            } else {
                rendered = uncheckedUserPermissionTemplate.cell(companyName);   
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
