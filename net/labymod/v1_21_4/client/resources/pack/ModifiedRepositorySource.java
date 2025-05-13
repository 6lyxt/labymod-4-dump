// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.resources.pack;

import java.util.Iterator;
import java.util.Optional;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements auc
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void loadPacks(final Consumer<atx> packConsumer) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final String name = registeredPack.getName();
            final atx pack = atx.a(new atb(name, (wp)wp.b(name), aub.c, (Optional)Optional.empty()), (atx.c)new FixedResourcesSupplier((atc)new ModifiedPackResources(registeredPack)), ate.a, new atd(true, atx.b.a, true));
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
