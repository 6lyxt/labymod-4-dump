// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.resources.pack;

import java.io.InputStream;
import java.util.Collection;
import java.io.IOException;
import net.labymod.api.util.io.IOUtil;
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
public class VersionedResourcePackRepository extends AbstractResourcePackRepository<adg>
{
    @Inject
    public VersionedResourcePackRepository() {
    }
    
    @Override
    public void registerSilentPack(final ResourcePack pack) {
        super.registerSilentPack(pack);
        final adt resourceManager = dvp.C().N();
        if (resourceManager instanceof final adz simpleResourceManager) {
            ((SilentReloadableResourceManager)simpleResourceManager).loadSilent(pack);
            this.loadSilentTranslation(pack);
            return;
        }
        this.thrownSilentError(pack);
    }
    
    @Override
    public void onRebuildSelected(final List<adg> selected) {
        this.selectedPacks.clear();
        this.setHasServerPackSelected(false);
        for (final adg pack : selected) {
            this.selectedPacks.add(pack.e());
            this.checkServerPack(pack);
        }
    }
    
    private void checkServerPack(final adg pack) {
        try (final acv resources = pack.d()) {
            if (!(resources instanceof act)) {
                if (resources != null) {
                    resources.close();
                }
                return;
            }
            if ("server".equals(pack.e())) {
                this.setHasServerPackSelected(true);
            }
        }
    }
    
    @Override
    protected void onReloadPackDetails() {
        this.availablePackDetails.clear();
        final Collection<adg> packs = dvp.C().O().c();
        for (final adg pack : packs) {
            final ResourcePackDetail detail = ResourcePackDetail.create(pack.e(), pack.a(), pack.b());
            try (final acv resources = pack.d()) {
                final InputStream resource = resources.b("pack.png");
                if (resource != null) {
                    detail.setPackIconStream(IOUtil.copyStream(resource));
                }
                detail.setHidden(resources instanceof ModifiedPackResources);
            }
            catch (final IOException exception) {
                VersionedResourcePackRepository.LOGGER.error("Could not read pack details", exception);
                continue;
            }
            this.availablePackDetails.add(detail);
        }
    }
}
