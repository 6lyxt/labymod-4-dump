// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background;

import javax.inject.Inject;
import net.labymod.core.client.gui.background.bootlogo.MojangStudiosBootLogoRenderer;
import net.labymod.core.client.gui.background.bootlogo.LegacyMojangBootLogoRenderer;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class BootLogoController
{
    private final AbstractBootLogoRenderer renderer;
    
    @Inject
    public BootLogoController() {
        if (MinecraftVersions.V1_16.orOlder()) {
            this.renderer = new LegacyMojangBootLogoRenderer();
        }
        else {
            this.renderer = new MojangStudiosBootLogoRenderer();
        }
    }
    
    public AbstractBootLogoRenderer renderer() {
        return this.renderer;
    }
}
