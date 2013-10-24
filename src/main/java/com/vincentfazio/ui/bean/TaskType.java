package com.vincentfazio.ui.bean;

public enum TaskType {
    DemographicSurvey("Verify Company Information"),
    ProfileSurvey("Complete Profile Survey"),
    SecuritySurvey("Complete Security Survey"),
    DocumentationRequest("Upload Documentation");
    
    
    private TaskType(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
