// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.renderer.nametag;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.entity.player.tag.event.NameTagBackgroundRenderEvent;
import net.labymod.api.client.component.Component;
import java.util.Objects;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.TagType;
import java.util.function.Consumer;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.font.ComponentMapper;

public class NameTagRenderer
{
    private final ComponentMapper mapper;
    private final RenderEnvironmentContext environmentContext;
    private final TagRegistry tagRegistry;
    
    public NameTagRenderer() {
        this.mapper = Laby.references().componentMapper();
        this.environmentContext = Laby.references().renderEnvironmentContext();
        this.tagRegistry = Laby.labyAPI().tagRegistry();
    }
    
    public boolean transformNameTagContent(final Stack stack, final Entity entity, final Object displayName, final Consumer<Object> displayNameConsumer) {
        final TagType type = entity.nameTagType();
        if (type != TagType.MAIN_TAG || !(entity instanceof Player)) {
            return false;
        }
        final Player player = (Player)entity;
        final Component eventComponent = this.mapper.fromMinecraftComponent(displayName);
        final PlayerNameTagRenderEvent event = new PlayerNameTagRenderEvent(PlayerNameTagRenderEvent.Context.PLAYER_RENDER, player.getNetworkPlayerInfo(), eventComponent, type);
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            this.resetTag(entity);
            stack.pop();
            return true;
        }
        Component modifiedNameTag = event.nameTag();
        if (!Objects.equals(modifiedNameTag, eventComponent)) {
            modifiedNameTag = modifiedNameTag.append(PlayerNameTagRenderEvent.EDITED_COMPONENT);
            displayNameConsumer.accept(this.mapper.toMinecraftComponent(modifiedNameTag));
        }
        return false;
    }
    
    public void renderNameTags(final Stack stack, final Entity entity, final int nameWidth, final int packedLight) {
        this.environmentContext.setPackedLight(packedLight);
        this.tagRegistry.render(stack, entity, (float)nameWidth, entity.nameTagType());
    }
    
    public int getNameTagBackgroundColor(final float currentOpacity) {
        final NameTagBackgroundRenderEvent event = Laby.fireEvent(NameTagBackgroundRenderEvent.singleton());
        if (event.isCancelled()) {
            return 0;
        }
        final ColorFormat format = ColorFormat.ARGB32;
        return format.pack(event.getColor(), format.normalize(currentOpacity));
    }
    
    public void resetTag(final Entity entity) {
        entity.setNameTagType(null);
    }
}
