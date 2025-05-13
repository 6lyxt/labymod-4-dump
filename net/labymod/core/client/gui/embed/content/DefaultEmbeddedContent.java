// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.embed.content;

import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.Iterator;
import net.labymod.api.client.gui.lss.meta.LinkMetaList;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.embed.content.EmbeddedContent;

public abstract class DefaultEmbeddedContent implements EmbeddedContent
{
    @Override
    public final Widget createWidget() {
        final Widget widget = this.createWidgetBase();
        final LinkMetaList metaList = Laby.references().linkMetaLoader().getMeta(this.getClass());
        if (metaList != null) {
            for (final LinkReference link : metaList.getLinks()) {
                final StyleSheet styleSheet = link.loadStyleSheet();
                if (styleSheet != null) {
                    widget.applyStyleSheet(styleSheet);
                }
            }
        }
        return widget;
    }
    
    protected abstract Widget createWidgetBase();
}
