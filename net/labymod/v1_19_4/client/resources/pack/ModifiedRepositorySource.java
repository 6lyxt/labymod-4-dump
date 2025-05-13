// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.resources.pack;

import java.util.Iterator;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements aku
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void a(final Consumer<akq> packConsumer) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final String name = registeredPack.getName();
            final akq pack = akq.a(name, (tj)tj.b(name), true, f -> new ModifiedPackResources(registeredPack), new akq.a((tj)tj.h(), 11, cau.a()), ajw.a, akq.b.a, true, akt.c);
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
