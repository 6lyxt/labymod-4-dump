// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.gui.background;

import javax.inject.Inject;
import net.labymod.api.LabyAPI;
import net.labymod.core.client.gui.background.panorama.PanoramaRenderer;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.gui.background.panorama.AbstractPanoramaRenderer;

@Singleton
@Implements(PanoramaRenderer.class)
public class VersionedPanoramaRenderer extends AbstractPanoramaRenderer
{
    @Inject
    public VersionedPanoramaRenderer(final LabyAPI labyAPI) {
        super(labyAPI);
    }
}
