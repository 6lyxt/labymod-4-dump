// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.initial;

import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Iterator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.Constants;
import java.util.ArrayList;
import net.labymod.core.addon.AddonLoader;
import java.util.List;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class InstalledAddonLoader extends AddonLoaderSubService
{
    private final List<String> scheduledForRemoval;
    
    public InstalledAddonLoader(final AddonLoader addonLoader) {
        super(addonLoader, SubServiceStage.INITIAL);
        this.scheduledForRemoval = new ArrayList<String>();
    }
    
    @Override
    public void handle() throws Exception {
        if (!IOUtil.exists(Constants.Files.ADDONS)) {
            IOUtil.createDirectories(Constants.Files.ADDONS);
            return;
        }
        this.gatherAddonsScheduledForRemoval();
        for (final Path path : this.getJarsInAddonDirectory()) {
            this.loadAddonInfo(path, addonInfo -> {
                if (!this.scheduledForRemoval.contains(addonInfo.getNamespace())) {
                    return false;
                }
                else {
                    try {
                        Files.delete(path);
                    }
                    catch (final IOException e) {
                        this.logger.error("Unable to remove addon {}", addonInfo.getNamespace(), e);
                    }
                    return true;
                }
            });
        }
    }
    
    @Override
    public void completed() throws Exception {
        try {
            if (this.scheduledForRemoval.isEmpty()) {
                return;
            }
            if (IOUtil.exists(Constants.Files.ADDONS_SCHEDULE_FOR_REMOVAL)) {
                Files.delete(Constants.Files.ADDONS_SCHEDULE_FOR_REMOVAL);
            }
        }
        catch (final Exception e) {
            this.logError("Could not delete addons scheduled for removal file", e);
        }
    }
    
    private List<Path> getJarsInAddonDirectory() throws IOException {
        try (final Stream<Path> paths = Files.list(Constants.Files.ADDONS)) {
            final List<? super Path> list = paths.filter(path -> !IOUtil.isDirectory(path)).filter(path -> path.getFileName().toString().endsWith(".jar")).collect((Collector<? super Path, ?, List<? super Path>>)Collectors.toList());
            return (List<Path>)list;
        }
    }
    
    private void gatherAddonsScheduledForRemoval() {
        if (!IOUtil.exists(Constants.Files.ADDONS_SCHEDULE_FOR_REMOVAL)) {
            return;
        }
        this.scheduledForRemoval.clear();
        try {
            this.scheduledForRemoval.addAll(Files.readAllLines(Constants.Files.ADDONS_SCHEDULE_FOR_REMOVAL));
        }
        catch (final IOException e) {
            this.logError("Could not read addons scheduled for removal file", e);
        }
    }
}
