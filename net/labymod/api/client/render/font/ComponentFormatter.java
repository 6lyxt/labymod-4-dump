// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.HorizontalAlignment;

public interface ComponentFormatter
{
    ComponentFormatter lineSpacing(final float p0);
    
    ComponentFormatter overflow(final TextOverflowStrategy p0);
    
    ComponentFormatter maxWidth(final float p0);
    
    ComponentFormatter alignment(final HorizontalAlignment p0);
    
    ComponentFormatter disableCache();
    
    default ComponentFormatter cache(final boolean cache) {
        if (!cache) {
            this.disableCache();
        }
        return this;
    }
    
    default ComponentFormatter keepLeadingSpaces() {
        return this.leadingSpaces(true);
    }
    
    ComponentFormatter leadingSpaces(final boolean p0);
    
    ComponentFormatter maxLines(final int p0);
    
    ComponentFormatter maxLinesClipText(final boolean p0);
    
    default ComponentFormatter useChatOptions() {
        return this.useChatOptions(true);
    }
    
    ComponentFormatter useChatOptions(final boolean p0);
    
    RenderableComponent format(final Component p0);
}
