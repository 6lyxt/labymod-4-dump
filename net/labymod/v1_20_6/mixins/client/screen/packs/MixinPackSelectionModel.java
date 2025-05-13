// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.screen.packs;

import java.util.Iterator;
import net.labymod.v1_20_6.client.resources.pack.PackUtil;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Collection;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fqk.class })
public class MixinPackSelectionModel
{
    @Shadow
    @Final
    private aua a;
    
    @Redirect(method = { "findNewPacks" }, at = @At(value = "INVOKE", target = "Ljava/util/List;retainAll(Ljava/util/Collection;)Z", ordinal = 0))
    private boolean filterModifiedPackResourcesSelected(final List list, final Collection<?> c) {
        return list.retainAll(this.getAvailablePacks());
    }
    
    @Redirect(method = { "findNewPacks" }, at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z", ordinal = 0))
    private boolean filterModifiedPackResourcesUnselected(final List list, final Collection<?> c) {
        return list.addAll(this.getAvailablePacks());
    }
    
    private List<atx> getAvailablePacks() {
        final List<atx> list = new ArrayList<atx>();
        for (final atx pack : this.a.c()) {
            if (PackUtil.isModifiedPackResources(pack.f())) {
                continue;
            }
            list.add(pack);
        }
        return list;
    }
}
