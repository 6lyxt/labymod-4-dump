// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class IconWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final IconWidget iconWidget) {
            if (iconWidget.icon() != null) {
                iconWidget.icon().reset();
            }
            if (((IconWidget)widget).color() != null) {
                ((IconWidget)widget).color().reset();
            }
            if (((IconWidget)widget).colorTransitionDuration() != null) {
                ((IconWidget)widget).colorTransitionDuration().reset();
            }
            if (((IconWidget)widget).clickable() != null) {
                ((IconWidget)widget).clickable().reset();
            }
            if (((IconWidget)widget).objectFit() != null) {
                ((IconWidget)widget).objectFit().reset();
            }
            if (((IconWidget)widget).cleanupOnDispose() != null) {
                ((IconWidget)widget).cleanupOnDispose().reset();
            }
            if (((IconWidget)widget).zoom() != null) {
                ((IconWidget)widget).zoom().reset();
            }
            if (((IconWidget)widget).blurry() != null) {
                ((IconWidget)widget).blurry().reset();
            }
        }
        super.reset(widget);
    }
}
