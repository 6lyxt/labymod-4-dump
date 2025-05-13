// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.server.packs.resources;

import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.client.resources.pack.util.EventResourcePackRepositoryCaller;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.List;
import net.labymod.v1_21_5.client.resources.pack.ModifiedRepositorySource;
import net.labymod.api.Laby;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.common.collect.ImmutableSet;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ auz.class })
public class MixinPackRepository
{
    @Redirect(method = { "<init>" }, at = @At(remap = false, value = "INVOKE", target = "Lcom/google/common/collect/ImmutableSet;copyOf([Ljava/lang/Object;)Lcom/google/common/collect/ImmutableSet;"))
    private <E> ImmutableSet<E> registerCustomRepositorySources(final E[] elements) {
        final List<E> repositorySources = new ArrayList<E>((Collection<? extends E>)Arrays.asList(elements));
        repositorySources.add((E)new ModifiedRepositorySource(Laby.labyAPI()));
        final E[] objects = (E[])repositorySources.toArray();
        return (ImmutableSet<E>)ImmutableSet.copyOf((Object[])objects);
    }
    
    @Inject(method = { "rebuildSelected" }, at = { @At("RETURN") })
    private void labyMod$rebuildSelected(final Collection<String> selected, final CallbackInfoReturnable<List<auv>> cir) {
        final List<auv> selectedPacks = (List<auv>)cir.getReturnValue();
        EventResourcePackRepositoryCaller.onRebuildSelected(selectedPacks);
    }
}
