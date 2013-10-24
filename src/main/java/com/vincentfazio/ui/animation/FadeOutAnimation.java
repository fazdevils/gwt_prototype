package com.vincentfazio.ui.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.UIObject;

public class FadeOutAnimation extends Animation {
    private final UIObject uiObject;

    public FadeOutAnimation(final UIObject uiObject) {
        this.uiObject = uiObject;
    }

    @Override
    protected void onUpdate(final double progress) {
        com.google.gwt.dom.client.Style style = uiObject.getElement().getStyle();
        if (style.getDisplay().equalsIgnoreCase(Display.BLOCK.getCssName())) {
            style.setOpacity(1.0 - progress);
            if (progress == 1) {
                style.setDisplay(Display.NONE);
            }
        }
    }
}