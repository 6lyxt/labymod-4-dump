// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint.downloader;

import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.core.main.LabyMod;
import net.labymod.api.util.io.web.request.Response;
import java.io.IOException;
import net.labymod.core.addon.AddonLoader;
import net.labymod.api.addon.LoadedAddon;
import java.util.Optional;
import java.util.Iterator;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.core.addon.loader.prepare.AddonPreparer;
import net.labymod.core.addon.loader.AddonValidator;
import net.labymod.api.util.I18n;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.api.Laby;
import java.util.function.Consumer;
import java.util.List;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.flint.FlintController;

public class FlintDownloadTask
{
    private static final FlintController CONTROLLER;
    private static final Logging LOGGER;
    private final FlintModification modification;
    private final List<String> skippedDependencies;
    private Consumer<FlintDownloadTask> finishedCallback;
    private DownloadState state;
    private double percentage;
    
    public FlintDownloadTask(final FlintModification modification, final List<String> skippedDependencies, final Consumer<FlintDownloadTask> finished) {
        this.modification = modification;
        this.skippedDependencies = ((skippedDependencies == null) ? List.of() : skippedDependencies);
        this.finishedCallback = finished;
        this.state = DownloadState.PREPARE;
    }
    
    public static FlintDownloadTask dummy(final FlintModification modification, final DownloadState state) {
        final FlintDownloadTask flintDownloadTask = new FlintDownloadTask(modification, null, null);
        flintDownloadTask.state = state;
        return flintDownloadTask;
    }
    
    public void download() {
        this.state = DownloadState.DOWNLOADING;
        ((AbstractRequest<T, AddonDownloadRequest>)AddonDownloadRequest.create()).async().namespace(this.modification).downloadDependencies(true).downloadOptionalDependencies(true).skipDependencies(this.skippedDependencies).throwNotInIndexException().existsStrategy((namespace, installedAddonInfo) -> namespace.equals(this.modification.getNamespace())).percentageConsumer(percentage -> {
            if (percentage <= 95.0) {
                this.percentage = percentage;
            }
            else {
                this.percentage = 95.0;
            }
        }).execute(result -> {
            if (result.hasException()) {
                this.state = DownloadState.FAILED;
                this.callFinishedCallback();
                FlintDownloadTask.LOGGER.error("Failed to download modification " + this.modification.getNamespace(), (Throwable)result.exception());
            }
            else if (!result.isPresent()) {
                this.state = DownloadState.FAILED;
                this.callFinishedCallback();
                FlintDownloadTask.LOGGER.warn("Tried to download modification " + this.modification.getNamespace() + " but result is empty", new Object[0]);
            }
            else if (((AddonDownloadRequest.AddonDownloadResult)result.get()).getAddonInfos().isEmpty()) {
                this.state = DownloadState.FAILED;
                this.callFinishedCallback();
                FlintDownloadTask.LOGGER.warn("Tried to download modification " + this.modification.getNamespace() + " but the list of addons is empty (is it already installed but was not enabled?)", new Object[0]);
            }
            else {
                this.state = DownloadState.LOADING;
                Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                    this.percentage = 100.0;
                    try {
                        this.enableAddons(((AddonDownloadRequest.AddonDownloadResult)result.get()).getAddonInfos());
                        if (this.state == DownloadState.LOADING) {
                            this.state = DownloadState.FINISHED;
                        }
                    }
                    catch (final Exception e) {
                        FlintDownloadTask.LOGGER.warn("Failed to enable addons. Cause: " + e.getClass().getSimpleName() + ": " + e.getMessage(), new Object[0]);
                        if (this.state == DownloadState.LOADING) {
                            this.state = DownloadState.FAILED;
                        }
                        final Notification addonStore = Notification.builder().title(Component.text("LabyMod")).text(Component.text(e.getMessage())).icon(Textures.SpriteLabyMod.DEFAULT_WOLF_HIGH_RES).duration(20000L).build();
                        Laby.references().notificationController().push(addonStore);
                    }
                    if (this.finishedCallback != null) {
                        this.finishedCallback.accept(this);
                    }
                });
            }
        });
    }
    
    public void setFinishedCallback(final Consumer<FlintDownloadTask> finishedCallback) {
        this.finishedCallback = finishedCallback;
    }
    
    public boolean isFinished() {
        return this.state == DownloadState.FINISHED;
    }
    
    public DownloadState state() {
        return this.state;
    }
    
    public double getPercentage() {
        return this.percentage;
    }
    
    private void callFinishedCallback() {
        if (this.finishedCallback == null) {
            return;
        }
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> this.finishedCallback.accept(this));
    }
    
    private void enableAddons(final List<InstalledAddonInfo> addons) throws IOException {
        final FlintIndex.IndexFilter filter = FlintDownloadTask.CONTROLLER.getFlintIndex().filter();
        for (final InstalledAddonInfo addon : addons) {
            final Optional<FlintModification> optionalModification = filter.namespace(addon.getNamespace());
            if (!optionalModification.isPresent()) {
                throw new IllegalArgumentException("Could not find modification for namespace " + addon.getNamespace());
            }
            final FlintModification modification = optionalModification.get();
            if (modification.hasMeta(AddonMeta.RESTART_REQUIRED)) {
                this.state = DownloadState.REQUIRES_RESTART;
                return;
            }
        }
        final DefaultAddonService addonService = DefaultAddonService.getInstance();
        for (final InstalledAddonInfo addon2 : addons) {
            final String namespace = addon2.getNamespace();
            final Optional<LoadedAddon> optionalAddon = addonService.getAddon(namespace);
            if (optionalAddon.isPresent()) {
                throw new IllegalStateException(I18n.getTranslation("labymod.addons.store.download.alreadyInstalled", addon2.getNamespace()));
            }
        }
        for (final InstalledAddonInfo addon2 : addons) {
            if (AddonValidator.isBuildNumberGreater(addon2)) {
                throw new IllegalStateException(I18n.getTranslation("labymod.addons.store.download.outdated", addon2.getNamespace()));
            }
        }
        final AddonLoader addonLoader = addonService.addonLoader();
        for (final InstalledAddonInfo addon3 : addons) {
            addonLoader.addonPreparer().loadAddon(addon3, AddonPreparer.AddonPrepareContext.RUNTIME);
        }
    }
    
    static {
        CONTROLLER = LabyMod.references().flintController();
        LOGGER = Logging.create(FlintDownloader.class);
    }
}
