// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.resources.pack;

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
public class VersionedResourcePackRepository extends AbstractResourcePackRepository<auv>
{
    @Inject
    public VersionedResourcePackRepository() {
    }
    
    @Override
    public void registerSilentPack(final ResourcePack pack) {
        super.registerSilentPack(pack);
        final avo resourceManager = fqq.Q().ac();
        if (resourceManager instanceof final avl reloadableResourceManager) {
            ((SilentReloadableResourceManager)reloadableResourceManager).loadSilent(pack);
            this.loadSilentTranslation(pack);
            return;
        }
        this.thrownSilentError(pack);
    }
    
    @Override
    public void onRebuildSelected(final List<auv> selected) {
        this.selectedPacks.clear();
        this.setHasServerPackSelected(false);
        for (final auv pack : selected) {
            this.selectedPacks.add(pack.g());
            this.checkServerPack(pack);
        }
    }
    
    private void checkServerPack(final auv pack) {
        try (final aua resources = pack.f()) {
            final aua unwrappedPackResources = PackUtil.unwrap(resources);
            if ((unwrappedPackResources instanceof atx || unwrappedPackResources instanceof att) && unwrappedPackResources.b().startsWith("server")) {
                this.setHasServerPackSelected(true);
            }
        }
    }
    
    @Override
    protected void onReloadPackDetails() {
        this.availablePackDetails.clear();
        final Collection<auv> packs = fqq.Q().ad().d();
        for (final auv pack : packs) {
            final ResourcePackDetail detail = ResourcePackDetail.create(pack.g(), pack.b(), pack.c());
            try (final aua resources = pack.f()) {
                final avg<InputStream> resource = (avg<InputStream>)resources.a(new String[] { "pack.png" });
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
                final aua unrwappedPackResources = PackUtil.unwrap(resources);
                detail.setHidden(unrwappedPackResources instanceof ModifiedPackResources);
            }
            this.availablePackDetails.add(detail);
        }
    }
}
