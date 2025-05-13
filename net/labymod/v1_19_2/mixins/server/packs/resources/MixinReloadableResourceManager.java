// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.server.packs.resources;

import net.labymod.api.client.resources.pack.ResourcePack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.spongepowered.asm.mixin.Final;
import java.util.List;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_2.client.resources.pack.SilentReloadableResourceManager;

@Mixin({ aij.class })
public class MixinReloadableResourceManager implements SilentReloadableResourceManager
{
    @Shadow
    private aid b;
    @Shadow
    @Final
    private List<aig> c;
    private boolean labyMod$isFirstReload;
    
    public MixinReloadableResourceManager() {
        this.labyMod$isFirstReload = true;
    }
    
    @Inject(method = { "createReload" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/SimpleReloadInstance;create(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Z)Lnet/minecraft/server/packs/resources/ReloadInstance;", shift = At.Shift.BEFORE) })
    private void registerModifiedResourcePacks(final Executor $$0, final Executor $$1, final CompletableFuture<anf> $$2, final List<ahl> $$3, final CallbackInfoReturnable<aii> cir) {
        final ResourceReloadEvent.Type type = this.labyMod$isFirstReload ? ResourceReloadEvent.Type.INITIALIZATION_RESOURCE_PACKS : ResourceReloadEvent.Type.RELOAD;
        Laby.fireEvent(new ResourceReloadEvent(type, Phase.PRE));
        this.c.add((preparationBarrier, resourceManager, profilerFiller1, profilerFiller2, ex1, ex2) -> preparationBarrier.a((Object)anf.a).thenRunAsync(() -> Laby.fireEvent(new ResourceReloadEvent(type, Phase.POST)), ex2));
        this.labyMod$isFirstReload = false;
    }
    
    @Override
    public void loadSilent(final ResourcePack pack) {
        final aid b = this.b;
        if (b instanceof final aif multiResources) {
            ((SilentReloadableResourceManager)multiResources).loadSilent(pack);
            return;
        }
        throw new IllegalStateException("Resource pack could not be loaded silently (" + pack.getName() + ") because it was not loaded by MultiPackResourceManager");
    }
}
