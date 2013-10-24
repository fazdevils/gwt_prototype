package com.vincentfazio.ui.view.util;

import com.google.gwt.user.cellview.client.CellTable;

public interface GwtCellTableResources extends CellTable.Resources {

    /**
       * The styles applied to the table.
       */
    interface TableStyle extends CellTable.Style {
    }

    @Override
    @Source({ CellTable.Style.DEFAULT_CSS, "GwtCellTableStylesheet.css" })
    TableStyle cellTableStyle();

}
