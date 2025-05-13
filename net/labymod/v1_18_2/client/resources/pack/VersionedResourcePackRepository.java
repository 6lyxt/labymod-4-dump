// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.resources.pack;

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
public class VersionedResourcePackRepository extends AbstractResourcePackRepository<afl>
{
    @Inject
    public VersionedResourcePackRepository() {
    }
    
    @Override
    public void registerSilentPack(final ResourcePack pack) {
        super.registerSilentPack(pack);
        final aga resourceManager = dyr.D().N();
        if (resourceManager instanceof final afy reloadableResourceManager) {
            ((SilentReloadableResourceManager)reloadableResourceManager).loadSilent(pack);
            this.loadSilentTranslation(pack);
            return;
        }
        this.thrownSilentError(pack);
    }
    
    @Override
    public void onRebuildSelected(final List<afl> selected) {
        this.selectedPacks.clear();
        this.setHasServerPackSelected(false);
        for (final afl pack : selected) {
            this.selectedPacks.add(pack.e());
            this.checkServerPack(pack);
        }
    }
    
    private void checkServerPack(final afl pack) {
        try (final afa resources = pack.d()) {
            if (!(resources instanceof aey)) {
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
        final Collection<afl> packs = dyr.D().O().c();
        for (final afl pack : packs) {
            final ResourcePackDetail detail = ResourcePackDetail.create(pack.e(), pack.a(), pack.b());
            try (final afa resources = pack.d()) {
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
