package com.vincentfazio.ui.vendor.activity;

import java.util.SortedSet;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.model.TaskModel;
import com.vincentfazio.ui.vendor.view.ProfileSurveyDisplay;
import com.vincentfazio.ui.vendor.view.SecuritySurveyDisplay;
import com.vincentfazio.ui.vendor.view.VendorDetailsDisplay;
import com.vincentfazio.ui.view.ErrorDisplay;

public class ActiveTasksActivity extends GwtActivity {

    private Globals globals;

    public ActiveTasksActivity(Globals globals) {
        this.globals = globals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        TaskModel taskModel = (TaskModel) globals.getModel(TaskModel.class);
        taskModel.getMyTasks(new AsyncCallback<SortedSet<TaskBean>>() {

            @Override
            public void onFailure(Throwable caught) {
                ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
            }

            @Override
            public void onSuccess(SortedSet<TaskBean> activeTasks) {
                VendorDetailsDisplay display = (VendorDetailsDisplay) globals.getDisplay(VendorDetailsDisplay.class);
                display.setActiveTasks(activeTasks);

                ProfileSurveyDisplay profileDisplay = (ProfileSurveyDisplay) globals.getDisplay(ProfileSurveyDisplay.class);
                profileDisplay.setActiveTasks(activeTasks);

                SecuritySurveyDisplay securityDisplay = (SecuritySurveyDisplay) globals.getDisplay(SecuritySurveyDisplay.class);
                securityDisplay.setActiveTasks(activeTasks);
            }
            
        });
    }

}
