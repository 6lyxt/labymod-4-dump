// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.resources.pack;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import net.labymod.api.client.resources.pack.ResourcePackDetail;
import net.labymod.api.client.component.Component;
import java.util.Iterator;
import javax.inject.Inject;
import java.util.ArrayList;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.List;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.resources.pack.AbstractResourcePackRepository;

@Singleton
@Implements(ResourcePackRepository.class)
public class VersionedResourcePackRepository extends AbstractResourcePackRepository<bnk>
{
    private final List<ResourcePack> registeredPacks;
    
    @Inject
    public VersionedResourcePackRepository() {
        this.registeredPacks = new ArrayList<ResourcePack>();
    }
    
    @Override
    public void registerPack(final ResourcePack pack) {
        this.registeredPacks.add(pack);
    }
    
    @Override
    public void registerSilentPack(final ResourcePack pack) {
        super.registerSilentPack(pack);
        final bni resourceManager = ave.A().Q();
        if (resourceManager instanceof final bnn simpleResourceManager) {
            ((SilentReloadableResourceManager)simpleResourceManager).loadSilent((bnk)new ModifiedPackResources(pack));
            this.loadSilentTranslation(pack);
            return;
        }
        this.thrownSilentError(pack);
    }
    
    @Override
    public void onRebuildSelected(final List<bnk> selectedPacks) {
        this.selectedPacks.clear();
        this.setHasServerPackSelected(false);
        for (final bnk selectedResourcePack : selectedPacks) {
            this.selectedPacks.add(selectedResourcePack.b());
            this.checkServerPack(selectedResourcePack);
        }
        this.selectedPacks.add("vanilla");
    }
    
    private void checkServerPack(final bnk selectedResourcePack) {
        if (selectedResourcePack instanceof final bnc fileResourcePack) {
            if ("server".equals(fileResourcePack.b())) {
                this.setHasServerPackSelected(true);
            }
        }
    }
    
    @Override
    public List<ResourcePack> getRegisteredPacks() {
        return this.registeredPacks;
    }
    
    @Override
    protected void onReloadPackDetails() {
        this.availablePackDetails.clear();
        final Collection<bnm.a> packs = ave.A().R().b();
        for (final bnm.a pack : packs) {
            final ResourcePackDetail detail = ResourcePackDetail.create(pack.d(), Component.text(pack.d()), Component.text(pack.e()));
            final bnk resourcePack = pack.c();
            BufferedImage packImage = null;
            try {
                packImage = resourcePack.a();
            }
            catch (final IOException ex) {}
            if (packImage != null) {
                try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                    ImageIO.write(packImage, "png", outputStream);
                    detail.setPackIconStream(new ByteArrayInputStream(outputStream.toByteArray()));
                }
                catch (final IOException ex2) {}
            }
            detail.setHidden(resourcePack instanceof ModifiedPackResources);
            this.availablePackDetails.add(detail);
        }
    }
}
