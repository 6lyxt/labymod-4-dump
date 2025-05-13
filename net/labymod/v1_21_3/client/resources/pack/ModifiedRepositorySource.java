// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.resources.pack;

import java.util.Iterator;
import java.util.Optional;
import net.labymod.api.client.resources.pack.ResourcePack;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;

public class ModifiedRepositorySource implements avi
{
    private final LabyAPI labyAPI;
    
    public ModifiedRepositorySource(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public void loadPacks(final Consumer<avd> packConsumer) {
        for (final ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            final String name = registeredPack.getName();
            final avd pack = avd.a(new auf(name, (xv)xv.b(name), avh.c, (Optional)Optional.empty()), (avd.c)new FixedResourcesSupplier((aug)new ModifiedPackResources(registeredPack)), aui.a, new auh(true, avd.b.a, true));
            if (pack == null) {
                continue;
            }
            packConsumer.accept(pack);
        }
    }
}
