// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.resources.pack;

import java.util.Iterator;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements ajr
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void a(final Consumer<ajn> packConsumer) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final String name = registeredPack.getName();
            final ajn pack = ajn.a(name, (ss)ss.b(name), true, f -> new ModifiedPackResources(registeredPack), new ajn.a((ss)ss.h(), 11, byx.a()), ait.a, ajn.b.a, true, ajq.c);
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
