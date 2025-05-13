// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.resources.pack;

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
public class VersionedResourcePackRepository extends AbstractResourcePackRepository<ajn>
{
    @Inject
    public VersionedResourcePackRepository() {
    }
    
    @Override
    public void registerSilentPack(final ResourcePack pack) {
        super.registerSilentPack(pack);
        final ake resourceManager = ejf.N().Y();
        if (resourceManager instanceof final akb reloadableResourceManager) {
            ((SilentReloadableResourceManager)reloadableResourceManager).loadSilent(pack);
            this.loadSilentTranslation(pack);
            return;
        }
        this.thrownSilentError(pack);
    }
    
    @Override
    public void onRebuildSelected(final List<ajn> selected) {
        this.selectedPacks.clear();
        this.setHasServerPackSelected(false);
        for (final ajn pack : selected) {
            this.selectedPacks.add(pack.f());
            this.checkServerPack(pack);
        }
    }
    
    private void checkServerPack(final ajn pack) {
        try (final ais resources = pack.e()) {
            final ais unwrappedPackResources = PackUtil.unwrap(resources);
            if (!(unwrappedPackResources instanceof air)) {
                if (resources != null) {
                    resources.close();
                }
                return;
            }
            if ("server".equals(unwrappedPackResources.a())) {
                this.setHasServerPackSelected(true);
            }
        }
    }
    
    @Override
    protected void onReloadPackDetails() {
        this.availablePackDetails.clear();
        final Collection<ajn> packs = ejf.N().Z().c();
        for (final ajn pack : packs) {
            final ResourcePackDetail detail = ResourcePackDetail.create(pack.f(), pack.a(), pack.b());
            try (final ais resources = pack.e()) {
                final ajw<InputStream> resource = (ajw<InputStream>)resources.a(new String[] { "pack.png" });
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
                final ais unrwappedPackResources = PackUtil.unwrap(resources);
                detail.setHidden(unrwappedPackResources instanceof ModifiedPackResources);
            }
            this.availablePackDetails.add(detail);
        }
    }
}
