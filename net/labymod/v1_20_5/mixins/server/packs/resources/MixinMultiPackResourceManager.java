// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.server.packs.resources;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import net.labymod.v1_20_5.client.resources.pack.ModifiedPackResources;
import net.labymod.api.client.resources.pack.ResourcePack;
import org.spongepowered.asm.mixin.Mutable;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_5.client.resources.pack.SilentReloadableResourceManager;

@Mixin({ aui.class })
public class MixinMultiPackResourceManager implements SilentReloadableResourceManager
{
    @Shadow
    @Final
    private Map<String, aug> c;
    @Shadow
    @Final
    @Mutable
    private List<atb> d;
    
    @Override
    public void loadSilent(final ResourcePack resourcePack) {
        final ModifiedPackResources pack = new ModifiedPackResources(resourcePack);
        (this.d = new ArrayList<atb>(this.d)).add((atb)pack);
        final atd packType = atd.a;
        for (final String namespace : pack.a(packType)) {
            this.c.computeIfAbsent(namespace, s -> new aug(packType, s)).a((atb)pack);
        }
    }
}
