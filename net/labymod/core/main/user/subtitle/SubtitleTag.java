// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.subtitle;

import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.entity.Entity;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.entity.player.tag.tags.NameTag;

public class SubtitleTag extends NameTag
{
    private final SubtitleService subtitleService;
    @Nullable
    private SubtitleComponent component;
    
    public SubtitleTag(final SubtitleService subtitleService) {
        this.subtitleService = subtitleService;
    }
    
    @Override
    public void begin(final Entity entity) {
        this.component = this.subtitleService.getSubtitleOf(entity.getUniqueId());
        super.begin(entity);
    }
    
    @Override
    public boolean isVisible() {
        return this.component != null && !this.entity.isCrouching();
    }
    
    @Nullable
    @Override
    protected RenderableComponent getRenderableComponent() {
        final SubtitleComponent subtitleComponent = this.component;
        return (subtitleComponent == null) ? null : subtitleComponent.getRenderableComponent();
    }
    
    @Override
    public float getScale() {
        final SubtitleComponent subtitleComponent = this.component;
        return (subtitleComponent == null) ? super.getScale() : ((float)subtitleComponent.getSubtitle().getSize());
    }
}
