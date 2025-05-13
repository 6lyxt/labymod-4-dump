// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.server.packs.resources;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import net.labymod.v1_19_3.client.resources.pack.ModifiedPackResources;
import net.labymod.api.client.resources.pack.ResourcePack;
import org.spongepowered.asm.mixin.Mutable;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_3.client.resources.pack.SilentReloadableResourceManager;

@Mixin({ ajx.class })
public class MixinMultiPackResourceManager implements SilentReloadableResourceManager
{
    @Shadow
    @Final
    private Map<String, ajv> b;
    @Shadow
    @Final
    @Mutable
    private List<ais> c;
    
    @Override
    public void loadSilent(final ResourcePack resourcePack) {
        final ModifiedPackResources pack = new ModifiedPackResources(resourcePack);
        (this.c = new ArrayList<ais>(this.c)).add((ais)pack);
        final ait packType = ait.a;
        for (final String namespace : pack.a(packType)) {
            this.b.computeIfAbsent(namespace, s -> new ajv(packType, s)).a((ais)pack);
        }
    }
}
