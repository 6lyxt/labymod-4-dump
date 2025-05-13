// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.subtitle;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.serverapi.core.model.display.Subtitle;

public class SubtitleComponent
{
    private final Subtitle subtitle;
    private final Component component;
    private final RenderableComponent renderableComponent;
    
    public SubtitleComponent(final Subtitle subtitle) {
        this.subtitle = subtitle;
        this.component = Laby.references().labyModProtocolService().mapComponent(subtitle.getText());
        this.renderableComponent = RenderableComponent.of(this.component);
    }
    
    public Subtitle getSubtitle() {
        return this.subtitle;
    }
    
    @Nullable
    public Component getComponent() {
        return this.component;
    }
    
    @Nullable
    public RenderableComponent getRenderableComponent() {
        return this.renderableComponent;
    }
}
