// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader;

import java.util.function.Predicate;
import java.nio.file.Path;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.SemanticVersion;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.flint.index.FlintIndex;
import java.util.Collection;
import net.labymod.api.util.CollectionHelper;
import net.labymod.core.main.LabyMod;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.core.addon.AddonLoader;

public abstract class AddonLoaderSubService
{
    protected final AddonLoader addonLoader;
    protected final DefaultLabyModLoader labyModLoader;
    protected final Logging logger;
    private final SubServiceStage stage;
    private List<InstalledAddonInfo> stageList;
    
    protected AddonLoaderSubService(final AddonLoader addonLoader, final SubServiceStage stage) {
        this.addonLoader = addonLoader;
        this.stage = stage;
        this.logger = Logging.create(this.getClass());
        this.labyModLoader = (DefaultLabyModLoader)LabyMod.getInstance().labyModLoader();
    }
    
    public abstract void handle() throws Exception;
    
    public void completed() throws Exception {
    }
    
    public final SubServiceStage stage() {
        return this.stage;
    }
    
    protected final List<InstalledAddonInfo> getAddons() {
        return this.stageList;
    }
    
    public final void setAddons(final List<InstalledAddonInfo> list) {
        this.stageList = list;
    }
    
    protected final InstalledAddonInfo getAddon(final String namespace) {
        final InstalledAddonInfo classpathAddon = this.addonLoader.classpathAddonLoader().getAddonInfo();
        if (classpathAddon != null && classpathAddon.getNamespace().equals(namespace)) {
            return classpathAddon;
        }
        return CollectionHelper.get(this.stageList, addonInfo -> addonInfo.getNamespace().equals(namespace));
    }
    
    protected final void addAddon(final InstalledAddonInfo newAddonInfo) {
        this.addAddon(newAddonInfo, false);
    }
    
    protected final void addAddon(final InstalledAddonInfo newAddonInfo, final boolean forceNewer) {
        final InstalledAddonInfo installedAddonInfo = this.getAddon(newAddonInfo.getNamespace());
        if (installedAddonInfo != null) {
            boolean useNew;
            if (forceNewer) {
                useNew = true;
            }
            else if (!newAddonInfo.isFlintAddon() && !installedAddonInfo.isFlintAddon()) {
                useNew = this.isNewVersionHigher(installedAddonInfo, newAddonInfo);
            }
            else if (newAddonInfo.isFlintAddon() && installedAddonInfo.isFlintAddon()) {
                try {
                    final FlintIndex flintIndex = LabyMod.references().flintController().getFlintIndex();
                    final FlintModification modification = flintIndex.getModification(newAddonInfo.getNamespace());
                    final String fileHash = (modification == null) ? null : modification.getFileHash();
                    useNew = ((fileHash == null || !fileHash.equals(installedAddonInfo.getFileHash())) && ((fileHash != null && fileHash.equals(newAddonInfo.getFileHash())) || this.isNewVersionHigher(installedAddonInfo, newAddonInfo)));
                }
                catch (final Exception e) {
                    useNew = this.isNewVersionHigher(installedAddonInfo, newAddonInfo);
                    this.logger.warn("Addon could not compare hashes of identical flint addon {}: installed: {} ({}), new: {} ({})", installedAddonInfo.getNamespace(), installedAddonInfo.getVersion(), installedAddonInfo.getFileHash(), newAddonInfo.getVersion(), newAddonInfo.getFileHash());
                }
            }
            else {
                useNew = !newAddonInfo.isFlintAddon();
            }
            if (!useNew) {
                return;
            }
            this.logger.info("Replacing addon {} v{} ({}) with v{} ({})", installedAddonInfo.getNamespace(), installedAddonInfo.getVersion(), installedAddonInfo.getFileHash(), newAddonInfo.getVersion(), newAddonInfo.getFileHash());
            this.stageList.remove(installedAddonInfo);
        }
        this.stageList.add(newAddonInfo);
    }
    
    private boolean isNewVersionHigher(final InstalledAddonInfo installedAddon, final InstalledAddonInfo newAddon) {
        try {
            final SemanticVersion installedVersion = new SemanticVersion(installedAddon.getVersion());
            final SemanticVersion newVersion = new SemanticVersion(newAddon.getVersion());
            return !installedVersion.isGreaterThan((Version)newVersion);
        }
        catch (final Exception e) {
            this.logger.warn("Addon could not compare versions of identical addon {}: installed: {}, new: {}", installedAddon.getNamespace(), installedAddon.getVersion(), newAddon.getVersion());
            return true;
        }
    }
    
    protected final InstalledAddonInfo loadAddonInfo(final Path path) {
        return this.loadAddonInfo(path, null);
    }
    
    protected final InstalledAddonInfo loadAddonInfo(final Path path, final Predicate<InstalledAddonInfo> predicate) {
        InstalledAddonInfo addonInfo = null;
        try {
            addonInfo = this.addonLoader.loadAddonInfo(path);
            if (predicate != null && predicate.test(addonInfo)) {
                return null;
            }
            this.addAddon(addonInfo);
        }
        catch (final Exception exception) {
            String identifier = path.getFileName().toString();
            if (addonInfo != null) {
                identifier = identifier + " (" + addonInfo.getNamespace();
            }
            this.logger.error("Unable to load addon {}. {}: {}", identifier, exception.getClass().getSimpleName(), exception.getMessage());
            addonInfo = null;
        }
        return addonInfo;
    }
    
    protected final boolean isAddonInList(final String namespace) {
        final InstalledAddonInfo classpathAddon = this.addonLoader.classpathAddonLoader().getAddonInfo();
        return (classpathAddon != null && classpathAddon.getNamespace().equals(namespace)) || this.addonLoader.isAddonInList(this.stageList, namespace);
    }
    
    protected final void logError(final String message, final Throwable throwable) {
        this.logger.error(message + ". {}: {}", throwable.getClass().getSimpleName(), throwable.getMessage());
    }
    
    public enum SubServiceStage
    {
        INITIAL, 
        UPDATE, 
        DOWNLOAD, 
        VERIFY, 
        PREPARE;
    }
}
