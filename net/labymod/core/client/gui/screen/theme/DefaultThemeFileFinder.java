// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme;

import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.io.IOUtil;
import java.io.IOException;
import net.labymod.core.util.classpath.ClasspathUtil;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.BasicThemeFile;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.theme.ThemeFileFinder;

@Singleton
@Implements(ThemeFileFinder.class)
public class DefaultThemeFileFinder implements ThemeFileFinder
{
    @Inject
    public DefaultThemeFileFinder() {
    }
    
    @Override
    public boolean exists(final BasicThemeFile file) {
        final ResourceLocation location = file.toResourceLocation();
        final String themeLocation = "assets/" + location.getNamespace() + "/" + location.getPath();
        final String namespace = file.getNamespace();
        final Optional<LoadedAddon> addon = Laby.labyAPI().addonService().getAddon(namespace);
        if (addon.isPresent()) {
            final AddonClassLoader classLoader = (AddonClassLoader)addon.get().getClassLoader();
            final URL resourceObject = classLoader.findResourceObject(themeLocation);
            return resourceObject != null;
        }
        InputStream stream = null;
        try {
            stream = ClasspathUtil.getResourceAsInputStream(namespace, themeLocation, false);
            return true;
        }
        catch (final IOException exception) {
            return false;
        }
        finally {
            IOUtil.closeSilent(stream);
        }
    }
}
