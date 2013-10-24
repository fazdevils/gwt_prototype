package com.vincentfazio.ui.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.UIObject;

public class FadeInAnimation extends Animation {
    private final UIObject uiObject;

    public FadeInAnimation(final UIObject uiObject) {
        this.uiObject = uiObject;
    }

    @Override
    protected void onUpdate(final double progress) {
        com.google.gwt.dom.client.Style style = uiObject.getElement().getStyle();
        if (progress == 0) {
            style.setDisplay(Display.BLOCK);
        }
        style.setOpacity(progress);
    }
}