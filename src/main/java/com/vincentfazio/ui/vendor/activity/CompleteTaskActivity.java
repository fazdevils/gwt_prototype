package com.vincentfazio.ui.vendor.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.vincentfazio.ui.activity.GwtActivity;
import com.vincentfazio.ui.bean.ErrorBean;
import com.vincentfazio.ui.bean.StatusBean;
import com.vincentfazio.ui.bean.TaskBean;
import com.vincentfazio.ui.global.Globals;
import com.vincentfazio.ui.global.GwtGlobals;
import com.vincentfazio.ui.model.TaskModel;
import com.vincentfazio.ui.view.ErrorDisplay;
import com.vincentfazio.ui.view.StatusDisplay;

public class CompleteTaskActivity extends GwtActivity {

    private Globals globals;
    private TaskBean completedTask;

    public CompleteTaskActivity(TaskBean completedTask, GwtGlobals gwtGlobals) {
        this.completedTask = completedTask;
        this.globals = gwtGlobals;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        TaskModel taskModel = (TaskModel) globals.getModel(TaskModel.class);
        taskModel.completeTask(completedTask, new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) {
                ErrorDisplay errorDisplay = (ErrorDisplay) globals.getErrorDisplay();
                errorDisplay.handleError(new ErrorBean(caught.getMessage(), caught));
            }

            @Override
            public void onSuccess(String message) {
                StatusDisplay statusDisplay = globals.getStatusDisplay();
                statusDisplay.handleStatusUpdate(new StatusBean(message));

                new ActiveTasksActivity(globals).start(null, null); 
                new CompletedTasksActivity(globals).start(null, null);
            }
            
        });
            
    }

}
