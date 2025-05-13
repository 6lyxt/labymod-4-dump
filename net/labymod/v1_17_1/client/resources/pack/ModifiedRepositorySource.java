// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.resources.pack;

import java.util.Iterator;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements adk
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void a(final Consumer<adg> packConsumer, final adg.a packConstructor) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final adg pack = adg.a(registeredPack.getName(), true, () -> new ModifiedPackResources(registeredPack), packConstructor, adg.b.a, adj.b);
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
