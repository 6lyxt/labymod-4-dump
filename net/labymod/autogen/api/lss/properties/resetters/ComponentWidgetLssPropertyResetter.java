// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ComponentWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final ComponentWidget componentWidget) {
            if (componentWidget.forceVanillaFont() != null) {
                componentWidget.forceVanillaFont().reset();
            }
            if (((ComponentWidget)widget).overflowStrategy() != null) {
                ((ComponentWidget)widget).overflowStrategy().reset();
            }
            if (((ComponentWidget)widget).renderHover() != null) {
                ((ComponentWidget)widget).renderHover().reset();
            }
            if (((ComponentWidget)widget).textColor() != null) {
                ((ComponentWidget)widget).textColor().reset();
            }
            if (((ComponentWidget)widget).iconColor() != null) {
                ((ComponentWidget)widget).iconColor().reset();
            }
            if (((ComponentWidget)widget).allowColors() != null) {
                ((ComponentWidget)widget).allowColors().reset();
            }
            if (((ComponentWidget)widget).shadow() != null) {
                ((ComponentWidget)widget).shadow().reset();
            }
            if (((ComponentWidget)widget).lineSpacing() != null) {
                ((ComponentWidget)widget).lineSpacing().reset();
            }
            if (((ComponentWidget)widget).fontSize() != null) {
                ((ComponentWidget)widget).fontSize().reset();
            }
            if (((ComponentWidget)widget).scaleToFit() != null) {
                ((ComponentWidget)widget).scaleToFit().reset();
            }
            if (((ComponentWidget)widget).cache() != null) {
                ((ComponentWidget)widget).cache().reset();
            }
            if (((ComponentWidget)widget).maxLines() != null) {
                ((ComponentWidget)widget).maxLines().reset();
            }
            if (((ComponentWidget)widget).leadingSpaces() != null) {
                ((ComponentWidget)widget).leadingSpaces().reset();
            }
            if (((ComponentWidget)widget).useChatOptions() != null) {
                ((ComponentWidget)widget).useChatOptions().reset();
            }
            if (((ComponentWidget)widget).clippingTextTooltip() != null) {
                ((ComponentWidget)widget).clippingTextTooltip().reset();
            }
            if (((ComponentWidget)widget).maxLinesClipText() != null) {
                ((ComponentWidget)widget).maxLinesClipText().reset();
            }
            if (((ComponentWidget)widget).visualShift() != null) {
                ((ComponentWidget)widget).visualShift().reset();
            }
            if (((ComponentWidget)widget).textColorTransitionDuration() != null) {
                ((ComponentWidget)widget).textColorTransitionDuration().reset();
            }
        }
        super.reset(widget);
    }
}
