// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.resources.pack;

import java.util.Iterator;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements afp
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void a(final Consumer<afl> packConsumer, final afl.a packConstructor) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final afl pack = afl.a(registeredPack.getName(), true, () -> new ModifiedPackResources(registeredPack), packConstructor, afl.b.a, afo.b);
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
