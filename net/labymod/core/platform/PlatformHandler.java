// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform;

import net.labymod.core.main.LabyMod;
import javax.inject.Inject;
import java.util.Iterator;
import net.labymod.api.service.CustomServiceLoader;
import java.util.ArrayList;
import java.util.Collection;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class PlatformHandler
{
    private final Collection<Platform> platforms;
    
    @Inject
    public PlatformHandler() {
        this.platforms = new ArrayList<Platform>();
        for (final Platform platform : CustomServiceLoader.load(Platform.class, PlatformHandler.class.getClassLoader(), CustomServiceLoader.ServiceType.ADVANCED)) {
            this.platforms.add(platform);
        }
    }
    
    public void onInitialization(final LabyMod labyMod) {
        for (final Platform platform : this.platforms) {
            platform.initialize(labyMod);
        }
    }
    
    public void onPostStartup() {
        for (final Platform platform : this.platforms) {
            platform.onPostStartup();
        }
    }
}
