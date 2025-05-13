// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.resources.pack;

import java.util.Iterator;
import java.util.Optional;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements atr
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void loadPacks(final Consumer<atm> packConsumer) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final String name = registeredPack.getName();
            final atm pack = atm.a(new asp(name, (wz)wz.b(name), atq.c, (Optional)Optional.empty()), (atm.c)new FixedResourcesSupplier((asq)new ModifiedPackResources(registeredPack)), ass.a, new asr(true, atm.b.a, true));
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
