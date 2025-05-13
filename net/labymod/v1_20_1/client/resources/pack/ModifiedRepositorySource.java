// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.resources.pack;

import java.util.Iterator;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements akk
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void a(final Consumer<akg> packConsumer) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final String name = registeredPack.getName();
            final akg pack = akg.a(name, (sw)sw.b(name), true, f -> new ModifiedPackResources(registeredPack), new akg.a((sw)sw.h(), 11, caw.a()), ajm.a, akg.b.a, true, akj.c);
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
