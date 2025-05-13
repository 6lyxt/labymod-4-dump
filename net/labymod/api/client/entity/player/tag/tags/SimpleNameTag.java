// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag.tags;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.RenderableComponent;

public class SimpleNameTag extends NameTag
{
    private final RenderableComponent renderableComponent;
    private final boolean hideWhileCrouching;
    
    public SimpleNameTag(final String text, final boolean hideWhileCrouching) {
        this(Component.text(text), hideWhileCrouching);
    }
    
    public SimpleNameTag(final Component component, final boolean hideWhileCrouching) {
        this.renderableComponent = RenderableComponent.of(component);
        this.hideWhileCrouching = hideWhileCrouching;
    }
    
    @Override
    public boolean isVisible() {
        return !this.hideWhileCrouching || !this.entity.isCrouching();
    }
    
    @Override
    protected RenderableComponent getRenderableComponent() {
        return this.renderableComponent;
    }
}
