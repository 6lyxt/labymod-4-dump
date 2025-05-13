// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.multiplayer;

import net.labymod.api.util.Debounce;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.notification.Notification;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.api.event.client.gui.screen.theme.ThemeUpdateEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.core.client.gui.screen.theme.fancy.FancyTheme;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.gui.screen.theme.ThemeChangeEvent;
import net.labymod.api.client.entity.Entity;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.api.volt.callback.InsertInfo;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.client.network.server.DefaultAbstractServerController;
import io.netty.buffer.ByteBuf;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.render.font.text.msdf.MSDFTextRenderer;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.font.text.TextRendererProvider;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public abstract class ClientNetworkPacketListener
{
    protected final ServerController serverController;
    protected final OldAnimationRegistry oldAnimationRegistry;
    private final TextRendererProvider textRendererProvider;
    private final LabyAPI api;
    
    public ClientNetworkPacketListener(final ServerController serverController, final OldAnimationRegistry oldAnimationRegistry) {
        this.serverController = serverController;
        this.oldAnimationRegistry = oldAnimationRegistry;
        this.api = Laby.labyAPI();
        this.textRendererProvider = Laby.references().textRendererProvider();
        this.api.eventBus().registerListener(this);
    }
    
    public void onLoginOrServerSwitch() {
        final ServerData currentServerData = this.serverController.createServerData(this.getCurrentServer());
        this.serverController.loginOrServerSwitch(currentServerData);
    }
    
    public void onLoadServerResourcePack() {
        final boolean usesFancyFont = this.textRendererProvider.getRenderer() instanceof MSDFTextRenderer;
        if (usesFancyFont) {
            this.pushFancyFontServerResourcePackNotification(2000L);
        }
        this.textRendererProvider.setUseCustomFont(false);
    }
    
    public void onPayloadReceive(final ResourceLocation location, final ByteBuf buffer) {
        ((DefaultAbstractServerController)this.serverController).payloadReceive(location.getNamespace(), location.getPath(), this.readBytes(buffer));
    }
    
    public void onCustomPayloadReceive(@NotNull final ResourceLocation channelIdentifier, @NotNull final ByteBuf buffer, @NotNull final InsertInfo info) {
        final PayloadChannelIdentifier identifier = PayloadChannelIdentifier.create(channelIdentifier.getNamespace(), channelIdentifier.getPath());
        final boolean canHandleCustomPayload = this.serverController.handleCustomPayload(identifier, this.readBytes(buffer));
        if (canHandleCustomPayload) {
            info.cancel();
        }
    }
    
    public abstract void onEntityRotate(final Entity p0, final float p1, final int p2);
    
    public abstract <T> T getCurrentServer();
    
    private byte[] readBytes(@NotNull final ByteBuf buffer) {
        final byte[] data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        return data;
    }
    
    @Subscribe
    public void onThemeChange(final ThemeChangeEvent event) {
        if (event.phase() == Phase.POST && event.newTheme() instanceof FancyTheme) {
            this.delayedFancyFontNotification(1000L);
        }
    }
    
    @Subscribe
    public void onThemeUpdate(final ThemeUpdateEvent event) {
        if (event.reason() == FancyThemeConfig.FONT_UPDATE_REASON) {
            this.delayedFancyFontNotification(100L);
        }
    }
    
    private void delayedFancyFontNotification(final long delay) {
        final FancyThemeConfig config = this.api.themeService().getThemeConfig(FancyThemeConfig.class);
        if (config != null && config.fancyFont().get() && !this.textRendererProvider.useCustomFont()) {
            this.pushFancyFontServerResourcePackNotification(delay);
        }
    }
    
    private void pushFancyFontServerResourcePackNotification(final long delay) {
        Debounce.of("fancy_font_server_resource_pack_notification", delay, () -> this.api.notificationController().push(Notification.builder().title(Component.translatable("labymod.notification.fancyFontServerResourcePack.title", NamedTextColor.YELLOW)).text(Component.translatable("labymod.notification.fancyFontServerResourcePack.description", new Component[0])).build()));
    }
}
