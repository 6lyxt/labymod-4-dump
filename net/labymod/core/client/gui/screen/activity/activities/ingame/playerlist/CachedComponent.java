// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist;

import net.labymod.api.client.gui.HorizontalAlignment;
import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.RenderableComponent;

class CachedComponent
{
    public static final RenderableComponent EMPTY_COMPONENT;
    protected Component component;
    protected RenderableComponent renderableComponent;
    private float maxWidth;
    private int ticksSinceLastUse;
    
    public CachedComponent() {
    }
    
    public CachedComponent(final Component component) {
        this.update(component);
    }
    
    public void update(final Component component) {
        this.update(component, 0.0f);
    }
    
    public void update(final Component component, final float maxWidth) {
        this.maxWidth = maxWidth;
        if (Objects.equals(this.component, component)) {
            return;
        }
        this.component = component;
        this.renderableComponent = null;
    }
    
    public Component component() {
        return this.component;
    }
    
    public RenderableComponent renderableComponent() {
        this.ticksSinceLastUse = 0;
        if (this.renderableComponent == null) {
            final Component component = this.component();
            if (component == null) {
                return CachedComponent.EMPTY_COMPONENT;
            }
            this.renderableComponent = RenderableComponent.of(component, this.maxWidth, HorizontalAlignment.CENTER);
        }
        final RenderableComponent renderableComponent = (this.renderableComponent == null) ? CachedComponent.EMPTY_COMPONENT : this.renderableComponent;
        return renderableComponent;
    }
    
    public void invalidate() {
        this.component = null;
        this.renderableComponent = null;
    }
    
    public void refresh() {
        this.renderableComponent = null;
    }
    
    public boolean isEmpty() {
        return this.component == null;
    }
    
    public void tick() {
        if (this.renderableComponent == null) {
            this.ticksSinceLastUse = 0;
            return;
        }
        ++this.ticksSinceLastUse;
        if (this.ticksSinceLastUse > 100) {
            System.out.println("invalidated " + this.component.toString());
            this.renderableComponent = null;
        }
    }
    
    static {
        EMPTY_COMPONENT = RenderableComponent.of(Component.empty());
    }
}
