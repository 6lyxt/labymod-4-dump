// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.resources.pack;

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
public class VersionedResourcePackRepository extends AbstractResourcePackRepository<akg>
{
    @Inject
    public VersionedResourcePackRepository() {
    }
    
    @Override
    public void registerSilentPack(final ResourcePack pack) {
        super.registerSilentPack(pack);
        final akx resourceManager = enn.N().Y();
        if (resourceManager instanceof final aku reloadableResourceManager) {
            ((SilentReloadableResourceManager)reloadableResourceManager).loadSilent(pack);
            this.loadSilentTranslation(pack);
            return;
        }
        this.thrownSilentError(pack);
    }
    
    @Override
    public void onRebuildSelected(final List<akg> selected) {
        this.selectedPacks.clear();
        this.setHasServerPackSelected(false);
        for (final akg pack : selected) {
            this.selectedPacks.add(pack.f());
            this.checkServerPack(pack);
        }
    }
    
    private void checkServerPack(final akg pack) {
        try (final ajl resources = pack.e()) {
            final ajl unwrappedPackResources = PackUtil.unwrap(resources);
            if (!(unwrappedPackResources instanceof ajk)) {
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
        final Collection<akg> packs = enn.N().Z().c();
        for (final akg pack : packs) {
            final ResourcePackDetail detail = ResourcePackDetail.create(pack.f(), pack.a(), pack.b());
            try (final ajl resources = pack.e()) {
                final akp<InputStream> resource = (akp<InputStream>)resources.a(new String[] { "pack.png" });
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
                final ajl unrwappedPackResources = PackUtil.unwrap(resources);
                detail.setHidden(unrwappedPackResources instanceof ModifiedPackResources);
            }
            this.availablePackDetails.add(detail);
        }
    }
}
