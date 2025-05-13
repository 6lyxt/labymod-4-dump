// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.initial;

import net.labymod.core.addon.loader.prepare.AddonPreparer;
import java.io.Reader;
import java.io.InputStream;
import net.labymod.core.addon.file.AddonResource;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.loader.platform.PlatformChildClassloader;
import com.google.gson.JsonElement;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.addon.exception.AddonLoadException;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import net.labymod.core.addon.file.AddonResourceFinder;
import java.net.URL;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.main.LabyMod;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class ClasspathAddonLoader extends AddonLoaderSubService
{
    private AddonClassLoader classLoader;
    
    public ClasspathAddonLoader(final AddonLoader addonLoader) {
        super(addonLoader, SubServiceStage.INITIAL);
    }
    
    @Override
    public void handle() throws Exception {
        if (!LabyMod.getInstance().labyModLoader().isAddonDevelopmentEnvironment()) {
            return;
        }
        try {
            final List<URL> urls = PlatformEnvironment.getPlatformClassloader().getPotentialClasspathAddons();
            InstalledAddonInfo installedAddonInfo = null;
            for (final URL url : urls) {
                final AddonResource resource = AddonResourceFinder.find(url, "addon.json");
                if (resource == null) {
                    continue;
                }
                try (final InputStream stream = resource.openStream()) {
                    try (final Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                        final JsonObject object = (JsonObject)AddonLoader.GSON.fromJson(reader, (Class)JsonObject.class);
                        if (object == null) {
                            throw new AddonLoadException("Invalid addon.json: not a json object");
                        }
                        final InstalledAddonInfo addonInfo = (InstalledAddonInfo)AddonLoader.GSON.fromJson((JsonElement)object, (Class)InstalledAddonInfo.class);
                        installedAddonInfo = ((installedAddonInfo == null) ? addonInfo : installedAddonInfo.merge(addonInfo));
                    }
                    if (stream == null) {
                        continue;
                    }
                }
            }
            if (installedAddonInfo == null) {
                throw new AddonLoadException("No addon json was found on the classpath");
            }
            this.classLoader = new AddonClassLoader(urls.toArray(new URL[0]), ClasspathAddonLoader.class.getClassLoader(), installedAddonInfo);
            PlatformEnvironment.getPlatformClassloader().registerChildClassloader(installedAddonInfo.getNamespace(), this.classLoader);
        }
        catch (final Exception exception) {
            throw new AddonLoadException("An error occurred while searching for the addon json", exception);
        }
    }
    
    @Override
    public void completed() throws Exception {
        if (this.classLoader == null) {
            return;
        }
        this.addonLoader.addonPreparer().prepareAddon(this.classLoader, AddonPreparer.AddonPrepareContext.CLASSPATH);
    }
    
    public InstalledAddonInfo getAddonInfo() {
        return (this.classLoader == null) ? null : this.classLoader.getAddonInfo();
    }
}
