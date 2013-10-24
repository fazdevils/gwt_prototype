package com.vincentfazio.ui.bean.comparator;

import java.util.Comparator;

import com.vincentfazio.ui.bean.DocumentBean;

public class DocumentBeanComparator implements Comparator<DocumentBean> {

	@Override
	public int compare(DocumentBean document1, DocumentBean document2) {
		Integer documentId1 = document1.getId();
		Integer documentId2 = document2.getId();
		return documentId1.compareTo(documentId2);
	}

}
