// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.icon.ping;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.core.client.gui.icon.ping.providers.IncompatiblePingIcon;
import net.labymod.core.client.gui.icon.ping.providers.PingingPingIcon;
import net.labymod.core.client.gui.icon.ping.providers.ServerListPingIcon;
import net.labymod.core.client.gui.icon.ping.providers.GenericPingIcon;
import net.labymod.api.client.gui.icon.ping.PingType;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.LabyAPI;
import net.labymod.core.util.ArrayIndex;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.icon.ping.PingIconRegistry;

@Singleton
@Implements(PingIconRegistry.class)
public class DefaultPingIconRegistry implements PingIconRegistry
{
    private final ArrayIndex<PingIcon> pingIcons;
    private final LabyAPI labyAPI;
    private final ResourceRenderContext resourceRenderContext;
    
    @Inject
    public DefaultPingIconRegistry(final LabyAPI labyAPI, final ResourceRenderContext resourceRenderContext) {
        this.pingIcons = new ArrayIndex<PingIcon>(PingType.VALUES.length, PingIcon[]::new);
        this.labyAPI = labyAPI;
        this.resourceRenderContext = resourceRenderContext;
        this.registerPingIcon(PingType.PLAYER_PING, new GenericPingIcon());
        this.registerPingIcon(PingType.SERVER_PING, new ServerListPingIcon());
        this.registerPingIcon(PingType.LOADING_PING, new PingingPingIcon());
        this.registerPingIcon(PingType.ERROR, new IncompatiblePingIcon());
    }
    
    private void registerPingIcon(final PingType type, final PingIcon icon) {
        this.pingIcons.set(type.ordinal(), icon);
    }
    
    @Override
    public PingIconRegistry createMultiResourceRenderer(final Stack stack) {
        this.resourceRenderContext.begin(stack, this.labyAPI.minecraft().textures().iconsTexture());
        return this;
    }
    
    @Override
    public PingIconRegistry render(final PingType type, final int value, final float x, final float y) {
        final Icon icon = this.icon(type, value);
        icon.render(this.resourceRenderContext, x, y, 10.0f, 8.0f);
        return this;
    }
    
    @Override
    public void upload() {
        this.resourceRenderContext.uploadToBuffer();
    }
    
    @Override
    public Icon icon(final PingType type, final int value) {
        return this.pingIcons.get(type.ordinal()).get(value);
    }
}
