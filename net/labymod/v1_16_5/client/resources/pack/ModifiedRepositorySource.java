// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.resources.pack;

import java.util.Iterator;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements aby
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void a(final Consumer<abu> packConsumer, final abu.a packConstructor) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final abu pack = abu.a(registeredPack.getName(), true, () -> new ModifiedPackResources(registeredPack), packConstructor, abu.b.a, abx.b);
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
