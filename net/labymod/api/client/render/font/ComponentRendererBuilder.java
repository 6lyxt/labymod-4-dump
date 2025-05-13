// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.component.Component;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.Disposable;

@Referenceable
public interface ComponentRendererBuilder extends Disposable
{
    ComponentRendererBuilder text(final RenderableComponent p0);
    
    default ComponentRendererBuilder text(final String text) {
        this.text(Component.text(text));
        return this;
    }
    
    default ComponentRendererBuilder text(final Component component) {
        this.text(RenderableComponent.of(component));
        return this;
    }
    
    ComponentRendererBuilder pos(final float p0, final float p1);
    
    ComponentRendererBuilder mousePos(final Mouse p0);
    
    ComponentRendererBuilder drawMode(final TextDrawMode p0);
    
    ComponentRendererBuilder iconRenderer(final FontIconRenderListener p0);
    
    default ComponentRendererBuilder color(final int color) {
        return this.textColor(color).iconColor(color);
    }
    
    default ComponentRendererBuilder color(final int color, final boolean adjustColor) {
        return this.textColor(color, adjustColor).iconColor(color);
    }
    
    default ComponentRendererBuilder textColor(final int textColor) {
        return this.textColor(textColor, true);
    }
    
    ComponentRendererBuilder textColor(final int p0, final boolean p1);
    
    ComponentRendererBuilder iconColor(final int p0);
    
    ComponentRendererBuilder allowColors(final boolean p0);
    
    ComponentRendererBuilder shadow(final boolean p0);
    
    ComponentRendererBuilder discrete(final boolean p0);
    
    ComponentRendererBuilder capitalize(final boolean p0);
    
    ComponentRendererBuilder useFloatingPointPosition(final boolean p0);
    
    ComponentRendererBuilder fontWeight(final float p0);
    
    ComponentRendererBuilder centered(final boolean p0);
    
    ComponentRendererBuilder backgroundColor(final int p0);
    
    ComponentRendererBuilder shouldBatch(final boolean p0);
    
    ComponentRendererBuilder scale(final float p0);
    
    ComponentRenderMeta render(final Stack p0);
}
