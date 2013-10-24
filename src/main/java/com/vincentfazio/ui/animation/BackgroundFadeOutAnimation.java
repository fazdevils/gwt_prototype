package com.vincentfazio.ui.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;

public class BackgroundFadeOutAnimation extends Animation {
    private final Element uiElement;
    private final Color bgColor;
    
    public BackgroundFadeOutAnimation(final Element uiElement, final Color bgColor) {
        this.uiElement = uiElement;
        this.bgColor = bgColor;
    }

    @Override
    protected void onUpdate(final double progress) {
        com.google.gwt.dom.client.Style style = uiElement.getStyle();

        Double opacity = 1.0 - progress;

        String bgString = "rgba(%r,%g,%b,%o)";
        bgString = bgString.replaceFirst("%r", bgColor.getRed().toString());
        bgString = bgString.replaceFirst("%g", bgColor.getGreen().toString());
        bgString = bgString.replaceFirst("%b", bgColor.getBlue().toString());
        bgString = bgString.replaceFirst("%o", opacity.toString());
        
        style.setBackgroundColor(bgString);
    }
}