// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client.resources.pack;

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
            final atx pack = atx.a(new ata(name, (xp)xp.b(name), aub.c, (Optional)Optional.empty()), (atx.c)new FixedResourcesSupplier((atb)new ModifiedPackResources(registeredPack)), atd.a, new atc(true, atx.b.a, true));
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
