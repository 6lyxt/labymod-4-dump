// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw;

import net.labymod.api.client.render.draw.builder.PlayerHeadBuilder;
import java.util.Objects;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.mojang.texture.MojangTextureType;
import java.util.UUID;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.draw.PlayerHeadRenderer;
import net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder;

@Singleton
@Implements(PlayerHeadRenderer.class)
public class DefaultPlayerHeadRenderer extends DefaultRectangleBuilder<PlayerHeadRenderer> implements PlayerHeadRenderer
{
    private ResourceLocation resourceLocation;
    private boolean wearingHat;
    
    @Inject
    public DefaultPlayerHeadRenderer() {
        this.resetBuilder();
    }
    
    @Override
    public void render(final Stack stack) {
        this.validateBuilder();
        final BatchResourceRenderer batchResourceRenderer = Laby.references().resourceRenderer().beginBatch(stack, this.resourceLocation);
        batchResourceRenderer.pos(this.x, this.y).size(this.width, this.height).color(this.color).sprite(8.0f, 8.0f, 8.0f, 8.0f).resolution(64.0f, 64.0f).build();
        if (this.wearingHat) {
            batchResourceRenderer.pos(this.x, this.y).size(this.width, this.height).color(this.color).sprite(40.0f, 8.0f, 8.0f, 8.0f).resolution(64.0f, 64.0f).build();
        }
        batchResourceRenderer.upload();
        this.resetBuilder();
    }
    
    @Override
    public PlayerHeadRenderer player(final UUID uniqueId) {
        this.resourceLocation = Laby.references().mojangTextureService().getTexture(uniqueId, MojangTextureType.SKIN).getCompleted();
        return this;
    }
    
    @Override
    public PlayerHeadRenderer player(final GameProfile profile) {
        return this.player(profile.getUniqueId());
    }
    
    @Override
    public PlayerHeadRenderer player(final ResourceLocation skinTexture) {
        this.resourceLocation = skinTexture;
        return this;
    }
    
    @Override
    public PlayerHeadRenderer wearingHat(final boolean wearingHat) {
        this.wearingHat = wearingHat;
        return this;
    }
    
    @Override
    public void validateBuilder() {
        super.validateBuilder();
        Objects.requireNonNull(this.resourceLocation, "Missing skin texture (call player())");
    }
    
    @Override
    public void resetBuilder() {
        super.resetBuilder();
        this.resourceLocation = null;
        this.wearingHat = true;
    }
}
