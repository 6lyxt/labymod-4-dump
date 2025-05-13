// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.resources.pack;

import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements apv
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void loadPacks(final Consumer<apq> packConsumer) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final String name = registeredPack.getName();
            final apq pack = apq.a(name, (vf)vf.b(name), true, (apq.c)new FixedResourcesSupplier((aow)new ModifiedPackResources(registeredPack)), new apq.a((vf)vf.i(), apr.c, chs.a(), (List)List.of()), apq.b.a, true, apu.c);
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
