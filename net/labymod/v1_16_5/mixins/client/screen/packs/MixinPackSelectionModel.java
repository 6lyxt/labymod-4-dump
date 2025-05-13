// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.screen.packs;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.labymod.v1_16_5.client.resources.pack.ModifiedPackResources;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Collection;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ drh.class })
public class MixinPackSelectionModel
{
    @Shadow
    @Final
    private abw a;
    
    @Redirect(method = { "findNewPacks" }, at = @At(value = "INVOKE", target = "Ljava/util/List;retainAll(Ljava/util/Collection;)Z", ordinal = 0))
    private boolean filterModifiedPackResourcesSelected(final List list, final Collection<?> c) {
        return list.retainAll(this.getAvailablePacks());
    }
    
    @Redirect(method = { "findNewPacks" }, at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z", ordinal = 0))
    private boolean filterModifiedPackResourcesUnselected(final List list, final Collection<?> c) {
        return list.addAll(this.getAvailablePacks());
    }
    
    private List<abu> getAvailablePacks() {
        return this.a.c().stream().filter(pack -> !(pack.d() instanceof ModifiedPackResources)).collect((Collector<? super Object, ?, List<abu>>)Collectors.toList());
    }
}
