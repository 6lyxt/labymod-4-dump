// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.initial;

import net.labymod.api.Constants;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.Iterator;
import net.labymod.api.util.io.IOUtil;
import java.util.ArrayList;
import net.labymod.core.addon.AddonLoader;
import java.util.List;
import java.nio.file.Path;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class AdditionalAddonLoader extends AddonLoaderSubService
{
    public static final Path ADDITIONAL_ADDONS_DIRECTORY;
    private final List<String> additionalAddons;
    private final List<String> missingAdditionalAddons;
    
    public AdditionalAddonLoader(final AddonLoader addonLoader) {
        super(addonLoader, SubServiceStage.INITIAL);
        this.missingAdditionalAddons = new ArrayList<String>();
        final String property = System.getProperty("net.labymod.additional-addons");
        if (property != null) {
            final String[] split = property.split(",");
            this.additionalAddons = List.of(split);
        }
        else {
            this.additionalAddons = List.of();
        }
    }
    
    @Override
    public void handle() throws Exception {
        if (this.additionalAddons.isEmpty()) {
            return;
        }
        if (!IOUtil.exists(AdditionalAddonLoader.ADDITIONAL_ADDONS_DIRECTORY)) {
            IOUtil.createDirectories(AdditionalAddonLoader.ADDITIONAL_ADDONS_DIRECTORY);
        }
        this.missingAdditionalAddons.clear();
        for (String additionalAddon : this.additionalAddons) {
            final boolean alreadyInstalled = this.isAddonInList(additionalAddon);
            if (alreadyInstalled) {
                this.logger.info("Additional addon {} is already installed", additionalAddon);
            }
            else {
                final Path path = AdditionalAddonLoader.ADDITIONAL_ADDONS_DIRECTORY.resolve(additionalAddon + ".jar");
                InstalledAddonInfo addonInfo = null;
                if (IOUtil.exists(path)) {
                    addonInfo = this.loadAddonInfo(path, addon -> !addon.getNamespace().equals(additionalAddon));
                }
                if (addonInfo != null) {
                    continue;
                }
                this.missingAdditionalAddons.add(additionalAddon);
            }
        }
    }
    
    public List<String> getMissingAdditionalAddons() {
        return this.missingAdditionalAddons;
    }
    
    static {
        ADDITIONAL_ADDONS_DIRECTORY = Constants.Files.FILE_CACHE.resolve("addons");
    }
}
