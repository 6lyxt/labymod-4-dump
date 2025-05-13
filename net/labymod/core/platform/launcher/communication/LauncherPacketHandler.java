// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform.launcher.communication;

import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.prepare.AddonPreparer;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.api.Constants;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherAddonInstalledPacket;
import net.labymod.api.client.Minecraft;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.core.platform.launcher.communication.packets.core.LauncherStopPacket;
import net.labymod.api.util.logging.Logging;

public class LauncherPacketHandler
{
    private final LauncherCommunicationClient client;
    private final Logging logger;
    
    public LauncherPacketHandler(final LauncherCommunicationClient client) {
        this.logger = Logging.create(LauncherPacketHandler.class);
        this.client = client;
    }
    
    public void handleStopPacket(final LauncherStopPacket packet) {
        this.logger.info("Received stop packet, shutting down Minecraft...", new Object[0]);
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft != null) {
            final Minecraft minecraft2 = minecraft;
            final Minecraft obj = minecraft;
            Objects.requireNonNull(obj);
            minecraft2.executeOnRenderThread(obj::shutdownGame);
        }
        else {
            System.exit(0);
        }
    }
    
    public void handleAddonInstalledPacket(final LauncherAddonInstalledPacket packet) {
        final DefaultAddonService addonService = DefaultAddonService.getInstance();
        final AddonLoader addonLoader = addonService.addonLoader();
        addonLoader.executeServiceTask(() -> Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            if (addonService.getAddon(packet.getNamespace()).isPresent()) {
                this.logger.warn("Addon " + packet.getNamespace() + " is already installed!", new Object[0]);
            }
            else {
                try {
                    final InstalledAddonInfo addonInfo = addonLoader.loadAddonInfo(Constants.Files.ADDONS.resolve(packet.getFileName()));
                    if (addonInfo.hasMeta(AddonMeta.RESTART_REQUIRED)) {
                        this.logger.info("Addon " + packet.getNamespace() + " requires a restart!", new Object[0]);
                    }
                    else {
                        this.logger.info("Loading addon " + packet.getNamespace() + " from launcher packet...", new Object[0]);
                        addonLoader.addonPreparer().loadAddon(addonInfo, AddonPreparer.AddonPrepareContext.RUNTIME);
                    }
                }
                catch (final Exception e) {
                    this.logger.error("Failed to load addon " + packet.getNamespace(), (Throwable)e);
                }
            }
        }));
    }
}
