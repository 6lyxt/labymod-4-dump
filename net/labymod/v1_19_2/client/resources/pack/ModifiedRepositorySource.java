// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.resources.pack;

import java.util.Iterator;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements aia
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void a(final Consumer<ahw> packConsumer, final ahw.a packConstructor) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final ahw pack = ahw.a(registeredPack.getName(), true, () -> new ModifiedPackResources(registeredPack), packConstructor, ahw.b.a, ahz.b);
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
