package com.vincentfazio.ui.model;

public interface ErrorModel extends Model {

    void logError(String url, String errorDescription);

}
