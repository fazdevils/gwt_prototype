package com.vincentfazio.ui.administration.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.CompanyDnbRefreshModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class ComapnyDnbRefreshActivity extends GwtActivity {

    private final Globals globals;
    private final String companyId;

    public ComapnyDnbRefreshActivity(Globals globals, String companyId) {
        super();
        this.globals = globals;
        this.companyId = companyId;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        CompanyDnbRefreshModel model = (CompanyDnbRefreshModel) globals.getModel(CompanyDnbRefreshModel.class);
        showBusyCursor();
        model.refresh(companyId,
                new AsyncCallback<String>() {
                    public void onSuccess(String result) {
                        StatusDisplay statusDisplay = (StatusDisplay) globals.getStatusDisplay();
                        statusDisplay.handleStatusUpdate(new StatusBean("D&B Data Requested"));
                        showDefaultCursor();
                    }
                    public void onFailure(Throwable caught) {
                        ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                        errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
                        showDefaultCursor();
                    }
                }
             );
    }
    
}
