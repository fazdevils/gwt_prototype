package com.vincentfazio.ui.bean.comparator;

import java.util.Comparator;

import com.vincentfazio.ui.bean.DocumentationBean;

public class DocumentationBeanComparator implements Comparator<DocumentationBean> {

	@Override
	public int compare(DocumentationBean doc1, DocumentationBean doc2) {
		String documentationName1 = doc1.getDocumentationName();
		String documentationName2 = doc2.getDocumentationName();
		return documentationName1.compareToIgnoreCase(documentationName2);
	}
	
}