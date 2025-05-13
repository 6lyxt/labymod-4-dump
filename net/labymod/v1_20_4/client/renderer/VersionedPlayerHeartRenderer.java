// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.renderer;

import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import javax.inject.Inject;
import net.labymod.api.event.EventBus;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.client.render.PlayerHeartRenderer;

@Implements(PlayerHeartRenderer.class)
public class VersionedPlayerHeartRenderer implements PlayerHeartRenderer
{
    private final ResourceLocation guiIconsLocation;
    private final ResourceRenderer resourceRender;
    private long visibilityId;
    private int tickCount;
    private final LabyAPI labyAPI;
    
    @Inject
    public VersionedPlayerHeartRenderer(final LabyAPI labyAPI, final EventBus eventBus, final ResourceRenderer resourceRenderer) {
        this.labyAPI = labyAPI;
        eventBus.registerListener(this);
        this.resourceRender = resourceRenderer;
        this.guiIconsLocation = ResourceLocation.create("minecraft", "textures/gui/icons.png");
    }
    
    @Subscribe
    public void tick(final GameTickEvent event) {
        ++this.tickCount;
    }
    
    @Override
    public void renderHearts(final Stack stack, final float rawX, final float rawY, final int heartSize, final NetworkPlayerInfo info) {
    }
}
