// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.resources.pack;

import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements ang
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void a(final Consumer<anb> packConsumer) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final String name = registeredPack.getName();
            final anb pack = anb.a(name, (tl)tl.b(name), true, (anb.c)new FixedResourcesSupplier((amh)new ModifiedPackResources(registeredPack)), new anb.a((tl)tl.h(), anc.c, cec.a(), (List)List.of()), anb.b.a, true, anf.c);
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
