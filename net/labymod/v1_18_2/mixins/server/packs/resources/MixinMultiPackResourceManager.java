// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.server.packs.resources;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import net.labymod.v1_18_2.client.resources.pack.ModifiedPackResources;
import net.labymod.api.client.resources.pack.ResourcePack;
import org.spongepowered.asm.mixin.Mutable;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_18_2.client.resources.pack.SilentReloadableResourceManager;

@Mixin({ afu.class })
public class MixinMultiPackResourceManager implements SilentReloadableResourceManager
{
    @Shadow
    @Final
    private Map<String, aft> a;
    @Shadow
    @Final
    @Mutable
    private List<afa> b;
    
    @Override
    public void loadSilent(final ResourcePack resourcePack) {
        final ModifiedPackResources pack = new ModifiedPackResources(resourcePack);
        (this.b = new ArrayList<afa>(this.b)).add((afa)pack);
        final afb packType = afb.a;
        for (final String namespace : pack.a(packType)) {
            this.a.computeIfAbsent(namespace, s -> new aft(packType, s)).a((afa)pack);
        }
    }
}
