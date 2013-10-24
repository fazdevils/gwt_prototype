package com.vincentfazio.ui.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.cellview.client.CellTable;

public class BackgroundFadeOutCellTableAnimation extends Animation {
    
    private final CellTable<?> cellTable;
    private final int indexToHighlight;
    private final Color bgColor;
    
    public BackgroundFadeOutCellTableAnimation(final CellTable<?> cellTable, int indexToHighlight, final Color bgColor) {
        this.cellTable = cellTable;
        this.indexToHighlight = indexToHighlight;
        this.bgColor = bgColor;
    }

    @Override
    public void run(int duration) {
        new BackgroundFadeOutAnimation(cellTable.getRowElement(indexToHighlight), bgColor).run(duration);        
        cancel();
    }
    
    @Override
    protected void onUpdate(final double progress) {
        cancel();
    }
}