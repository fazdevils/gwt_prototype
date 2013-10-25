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
import com.vincentfazio.ui.bean.CompanyPermissionBean;

public interface CompanyPermissionCellComponentCallback {
    
    void handleCellSelection(boolean isSelected);
    void handleHeaderSelection(boolean isSelected);

    // Vender Permission Cell Templates
    interface CheckedCompanyPermissionTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div class=\"has-company-access\"><input type=\"checkbox\" checked=\"checked\"/>{0}</div>")
        SafeHtml cell(SafeHtml companyName);
    }
    static CheckedCompanyPermissionTemplate checkedCompanyPermissionTemplate = GWT.create(CheckedCompanyPermissionTemplate.class);
    
    interface UncheckedCompanyPermissionTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div class=\"no-company-access\"><input type=\"checkbox\"/>{0}</div>")
        SafeHtml cell(SafeHtml companyName);
    }
    static UncheckedCompanyPermissionTemplate uncheckedCompanyPermissionTemplate = GWT.create(UncheckedCompanyPermissionTemplate.class);

    
    
    // Company Permission Cell
    class CompanyPermissionCell extends AbstractCell<CompanyPermissionBean> implements Cell<CompanyPermissionBean> {

        private CompanyPermissionCellComponentCallback callback;

        public CompanyPermissionCell(CompanyPermissionCellComponentCallback callback) {
            super("click");
            this.callback = callback;
        }

        @Override
        public void render(com.google.gwt.cell.client.Cell.Context context, CompanyPermissionBean value, SafeHtmlBuilder sb) {
            if (value == null) {
                return;
            }

            SafeHtml companyName = SafeHtmlUtils.fromString(value.getCompanyName());
            SafeHtml rendered;
            if (value.hasAccess()) {
                rendered = checkedCompanyPermissionTemplate.cell(companyName);   
            } else {
                rendered = uncheckedCompanyPermissionTemplate.cell(companyName);   
            }
            sb.append(rendered);
            
        }

        @Override
        public void onBrowserEvent(Context context, Element parent, CompanyPermissionBean companyAccess, NativeEvent event, ValueUpdater<CompanyPermissionBean> valueUpdater) {
            companyAccess.setAccess(!companyAccess.hasAccess());
            setValue(context, parent, companyAccess);
            callback.handleCellSelection(companyAccess.hasAccess());
        }
        
    }

    
    // Vender Permission Header
    static class CompanyPermissionHeader extends Header<CompanyPermissionBean> {

        private CompanyPermissionBean allAccess;
        private CellTable<CompanyPermissionBean> companyPermissions;
        private CompanyPermissionCellComponentCallback callback;
        
        public CompanyPermissionHeader(String label, CellTable<CompanyPermissionBean> companyPermissionsTable, CompanyPermissionCellComponentCallback callback) {
            super(new CompanyPermissionCell(callback));
            allAccess = new CompanyPermissionBean();
            allAccess.setCompanyName(label);
            allAccess.setAccess(false);
            
            companyPermissions = companyPermissionsTable;
            this.callback = callback;
        }

        @Override
        public CompanyPermissionBean getValue() {
            return allAccess;
        }
        
        public void setValue(Boolean accessToAllCompanies) {
            allAccess.setAccess(accessToAllCompanies);
            companyPermissions.redraw();
        }
        
        @Override
        public void render(Context context, SafeHtmlBuilder sb) {
            SafeHtml companyName = SafeHtmlUtils.fromString(allAccess.getCompanyName());
            SafeHtml rendered;
            if (allAccess.hasAccess()) {
                rendered = checkedCompanyPermissionTemplate.cell(companyName);   
            } else {
                rendered = uncheckedCompanyPermissionTemplate.cell(companyName);   
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
