// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.resources.pack;

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
public class VersionedResourcePackRepository extends AbstractResourcePackRepository<anb>
{
    @Inject
    public VersionedResourcePackRepository() {
    }
    
    @Override
    public void registerSilentPack(final ResourcePack pack) {
        super.registerSilentPack(pack);
        final ant resourceManager = eqv.O().Z();
        if (resourceManager instanceof final anq reloadableResourceManager) {
            ((SilentReloadableResourceManager)reloadableResourceManager).loadSilent(pack);
            this.loadSilentTranslation(pack);
            return;
        }
        this.thrownSilentError(pack);
    }
    
    @Override
    public void onRebuildSelected(final List<anb> selected) {
        this.selectedPacks.clear();
        this.setHasServerPackSelected(false);
        for (final anb pack : selected) {
            this.selectedPacks.add(pack.f());
            this.checkServerPack(pack);
        }
    }
    
    private void checkServerPack(final anb pack) {
        try (final amh resources = pack.e()) {
            final amh unwrappedPackResources = PackUtil.unwrap(resources);
            if ((unwrappedPackResources instanceof amf || unwrappedPackResources instanceof amd) && unwrappedPackResources.a().startsWith("server")) {
                this.setHasServerPackSelected(true);
            }
        }
    }
    
    @Override
    protected void onReloadPackDetails() {
        this.availablePackDetails.clear();
        final Collection<anb> packs = eqv.O().aa().c();
        for (final anb pack : packs) {
            final ResourcePackDetail detail = ResourcePackDetail.create(pack.f(), pack.a(), pack.b());
            try (final amh resources = pack.e()) {
                final anl<InputStream> resource = (anl<InputStream>)resources.a(new String[] { "pack.png" });
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
                final amh unrwappedPackResources = PackUtil.unwrap(resources);
                detail.setHidden(unrwappedPackResources instanceof ModifiedPackResources);
            }
            this.availablePackDetails.add(detail);
        }
    }
}
