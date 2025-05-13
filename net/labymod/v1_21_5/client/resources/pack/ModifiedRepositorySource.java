// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.resources.pack;

import java.util.Iterator;
import java.util.Optional;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements avb
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void loadPacks(final Consumer<auv> packConsumer) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final String name = registeredPack.getName();
            final auv pack = auv.a(new atz(name, (xg)xg.b(name), ava.c, (Optional)Optional.empty()), (auv.c)new FixedResourcesSupplier((aua)new ModifiedPackResources(registeredPack)), auc.a, new aub(true, auv.b.a, true));
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
