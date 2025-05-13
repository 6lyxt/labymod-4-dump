// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.pack;

import net.labymod.api.Laby;
import net.labymod.api.event.client.resources.pack.IncompatibleResourcePacksEvent;
import java.util.Collections;
import net.labymod.api.client.resources.pack.IncompatibleResourcePack;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Arrays;
import net.labymod.api.util.GsonUtil;
import java.util.Collection;
import java.io.Reader;
import java.util.Iterator;
import java.util.HashSet;
import net.labymod.api.client.resources.pack.ResourceFile;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.resources.pack.ResourcePackScanner;

public abstract class AbstractResourcePackScanner implements ResourcePackScanner
{
    private static final String SEPARATOR;
    protected static final Logging LOGGER;
    protected static final String IGNORED_FILES = "labymod/ignored_files.json";
    private final Map<String, Set<String>> blacklistedFiles;
    
    public AbstractResourcePackScanner() {
        this.blacklistedFiles = new HashMap<String, Set<String>>();
    }
    
    @Override
    public void addBlacklist(final ResourceFile file) {
        this.blacklistedFiles.computeIfAbsent(file.baseDirectory(), s -> new HashSet()).add(file.fileName());
    }
    
    @Override
    public void removeBlacklist(final ResourceFile file) {
        final Set<String> blacklistedFiles = this.blacklistedFiles.get(file.baseDirectory());
        if (blacklistedFiles == null || blacklistedFiles.isEmpty()) {
            return;
        }
        blacklistedFiles.remove(file.fileName());
    }
    
    @Override
    public void removeBlacklist(final String fileName) {
        for (final Set<String> blacklistedFiles : this.blacklistedFiles.values()) {
            blacklistedFiles.removeIf(name -> name.equals(fileName));
        }
    }
    
    public Map<String, Set<String>> getBlacklistedFiles() {
        return this.blacklistedFiles;
    }
    
    protected void collectIgnoredFiles(final Reader reader, final Collection<String> ignoredFiles) {
        final String[] files = (String[])GsonUtil.DEFAULT_GSON.fromJson(reader, (Class)String[].class);
        if (files == null || files.length == 0) {
            return;
        }
        ignoredFiles.addAll(Arrays.asList(files));
    }
    
    protected void analyzeResourcePackFile(final Collection<String> blacklistedFiles, final Collection<String> ignoredFiles, final Collection<String> unsupportedFiles, final ResourceLocation location) {
        final String path = location.getPath();
        final String name = path.substring(path.lastIndexOf(47) + 1);
        if (ignoredFiles.contains(name)) {
            return;
        }
        if (!blacklistedFiles.contains(name)) {
            return;
        }
        unsupportedFiles.add(path);
    }
    
    protected final void onScanned(Collection<IncompatibleResourcePack> incompatibleResourcePacks) {
        if (incompatibleResourcePacks.isEmpty()) {
            return;
        }
        incompatibleResourcePacks = Collections.unmodifiableCollection((Collection<? extends IncompatibleResourcePack>)incompatibleResourcePacks);
        this.printDebugMessage(incompatibleResourcePacks);
        Laby.fireEvent(new IncompatibleResourcePacksEvent(incompatibleResourcePacks));
    }
    
    private void printDebugMessage(final Collection<IncompatibleResourcePack> incompatibleResourcePacks) {
        AbstractResourcePackScanner.LOGGER.debug(AbstractResourcePackScanner.SEPARATOR, new Object[0]);
        AbstractResourcePackScanner.LOGGER.debug("Incompatible resource packs were found", new Object[0]);
        for (final IncompatibleResourcePack incompatibleResourcePack : incompatibleResourcePacks) {
            AbstractResourcePackScanner.LOGGER.debug("Name: {}", incompatibleResourcePack.name());
            final Collection<String> ignoredFiles = incompatibleResourcePack.ignoredFiles();
            if (!ignoredFiles.isEmpty()) {
                AbstractResourcePackScanner.LOGGER.debug("Some files have been marked as ignored, which are on the blacklist:", new Object[0]);
                for (final String ignoredFile : ignoredFiles) {
                    AbstractResourcePackScanner.LOGGER.debug("\t - {}", ignoredFile);
                }
            }
            AbstractResourcePackScanner.LOGGER.debug("Following files which are on the blacklist have been modified:", new Object[0]);
            for (final String unsupportedFile : incompatibleResourcePack.unsupportedFiles()) {
                AbstractResourcePackScanner.LOGGER.debug("\t - {}", unsupportedFile);
            }
            AbstractResourcePackScanner.LOGGER.debug("", new Object[0]);
        }
        AbstractResourcePackScanner.LOGGER.debug(AbstractResourcePackScanner.SEPARATOR, new Object[0]);
        AbstractResourcePackScanner.LOGGER.debug("Some features may not work", new Object[0]);
    }
    
    static {
        SEPARATOR = "=".repeat(75);
        LOGGER = Logging.getLogger();
    }
}
