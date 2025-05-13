// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font.text;

import java.util.Iterator;
import net.labymod.api.client.render.font.RenderableComponent;
import java.util.List;

record FormattedRenderableComponent(float yOffset, List<RenderableComponent> components) {
    public static FormattedRenderableComponent composite(final float yOffset, final List<RenderableComponent> components) {
        return new FormattedRenderableComponent(yOffset, components);
    }
    
    public float findFirstXOffset() {
        float xOffset = 0.0f;
        for (final RenderableComponent component : this.components) {
            final float componentXOffset = this.findFirstXOffset(component);
            if (componentXOffset > 0.0f) {
                xOffset = componentXOffset;
                break;
            }
        }
        return xOffset;
    }
    
    private float findFirstXOffset(final RenderableComponent component) {
        final float xOffset = component.getXOffset();
        if (xOffset > 0.0f) {
            return xOffset;
        }
        for (final RenderableComponent child : component.getChildren()) {
            final float currentXOffset = this.findFirstXOffset(child);
            if (currentXOffset > 0.0f) {
                return currentXOffset;
            }
        }
        return xOffset;
    }
}
