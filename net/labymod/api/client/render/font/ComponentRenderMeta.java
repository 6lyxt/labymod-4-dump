// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font;

import net.labymod.api.client.gui.screen.VanillaScreen;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.Laby;
import java.util.Optional;
import net.labymod.api.util.bounds.Rectangle;

public class ComponentRenderMeta implements Rectangle
{
    private final RenderableComponent hovered;
    private final float minX;
    private final float maxX;
    private final float minY;
    private final float maxY;
    private final float renderedWidth;
    
    public ComponentRenderMeta(final RenderableComponent hovered, final float minX, final float maxX, final float minY, final float maxY, final float renderedWidth) {
        this.hovered = hovered;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.renderedWidth = renderedWidth;
    }
    
    public Optional<RenderableComponent> getHovered() {
        return Optional.ofNullable(this.hovered);
    }
    
    public float getMinX() {
        return this.minX;
    }
    
    @Override
    public float getMaxX() {
        return this.maxX;
    }
    
    public float getMinY() {
        return this.minY;
    }
    
    @Override
    public float getMaxY() {
        return this.maxY;
    }
    
    @Override
    public float getLeft() {
        return this.minX;
    }
    
    @Override
    public float getTop() {
        return this.minY;
    }
    
    @Override
    public float getRight() {
        return this.maxX;
    }
    
    @Override
    public float getBottom() {
        return this.maxY;
    }
    
    public float getRenderedWidth() {
        return this.renderedWidth;
    }
    
    public boolean interact() {
        final Optional<RenderableComponent> hovered = this.getHovered();
        if (hovered.isEmpty()) {
            return false;
        }
        final Style style = hovered.get().style();
        final String insertion = style.getInsertion();
        if (insertion != null && Laby.labyAPI().minecraft().isKeyPressed(Key.L_SHIFT)) {
            Laby.labyAPI().minecraft().chatExecutor().insertText(insertion);
            return true;
        }
        final ClickEvent event = style.getClickEvent();
        if (event != null) {
            Laby.labyAPI().minecraft().chatExecutor().performAction(event);
            return true;
        }
        return false;
    }
    
    public void renderHover(final Stack stack, final MutableMouse mouse) {
        final Optional<RenderableComponent> hovered = this.getHovered();
        if (hovered.isEmpty()) {
            return;
        }
        final Object screen = Laby.labyAPI().minecraft().minecraftWindow().getCurrentVersionedScreen();
        if (screen instanceof final VanillaScreen vanillaScreen) {
            vanillaScreen.renderComponentHoverEffect(stack, hovered.get().style(), mouse.getX(), mouse.getY());
        }
    }
}
