// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.resources.pack;

import java.util.Collection;
import java.io.IOException;
import net.labymod.api.util.io.IOUtil;
import java.io.InputStream;
import net.labymod.api.client.resources.pack.ResourcePackDetail;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.resources.pack.ResourcePack;
import javax.inject.Inject;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.resources.pack.AbstractResourcePackRepository;

@Singleton
@Implements(ResourcePackRepository.class)
public class VersionedResourcePackRepository extends AbstractResourcePackRepository<avd>
{
    @Inject
    public VersionedResourcePackRepository() {
    }
    
    @Override
    public void registerSilentPack(final ResourcePack pack) {
        super.registerSilentPack(pack);
        final avv resourceManager = fmg.Q().ac();
        if (resourceManager instanceof final avs reloadableResourceManager) {
            ((SilentReloadableResourceManager)reloadableResourceManager).loadSilent(pack);
            this.loadSilentTranslation(pack);
            return;
        }
        this.thrownSilentError(pack);
    }
    
    @Override
    public void onRebuildSelected(final List<avd> selected) {
        this.selectedPacks.clear();
        this.setHasServerPackSelected(false);
        for (final avd pack : selected) {
            this.selectedPacks.add(pack.g());
            this.checkServerPack(pack);
        }
    }
    
    private void checkServerPack(final avd pack) {
        try (final aug resources = pack.f()) {
            final aug unwrappedPackResources = PackUtil.unwrap(resources);
            if ((unwrappedPackResources instanceof aud || unwrappedPackResources instanceof atz) && unwrappedPackResources.b().startsWith("server")) {
                this.setHasServerPackSelected(true);
            }
        }
    }
    
    @Override
    protected void onReloadPackDetails() {
        this.availablePackDetails.clear();
        final Collection<avd> packs = fmg.Q().ad().d();
        for (final avd pack : packs) {
            final ResourcePackDetail detail = ResourcePackDetail.create(pack.g(), pack.b(), pack.c());
            try (final aug resources = pack.f()) {
                final avn<InputStream> resource = (avn<InputStream>)resources.a(new String[] { "pack.png" });
                if (resource != null) {
                    try (final InputStream stream = (InputStream)resource.get()) {
                        if (stream != null) {
                            detail.setPackIconStream(IOUtil.copyStream(stream));
                        }
                    }
                    catch (final IOException exception) {
                        VersionedResourcePackRepository.LOGGER.error("Could not read pack icon", exception);
                    }
                }
                final aug unrwappedPackResources = PackUtil.unwrap(resources);
                detail.setHidden(unrwappedPackResources instanceof ModifiedPackResources);
            }
            this.availablePackDetails.add(detail);
        }
    }
}
