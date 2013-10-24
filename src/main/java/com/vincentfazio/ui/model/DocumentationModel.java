package com.vincentfazio.ui.model;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.DocumentationBean;

public interface DocumentationModel extends Model {

	void getDocumentation(String companyId, DocumentationBean documentation, AsyncCallback<DocumentationBean> asyncCallback);

	void getDocumentationList(String companyId, AsyncCallback<Collection<DocumentationBean>> asyncCallback);

}
