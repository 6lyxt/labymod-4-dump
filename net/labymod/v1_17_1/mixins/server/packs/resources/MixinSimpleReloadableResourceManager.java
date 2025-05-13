// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.server.packs.resources;

import net.labymod.v1_17_1.client.resources.pack.ModifiedPackResources;
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
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_17_1.client.resources.pack.SilentReloadableResourceManager;

@Mixin({ adz.class })
public abstract class MixinSimpleReloadableResourceManager implements SilentReloadableResourceManager
{
    @Shadow
    @Final
    private List<ado> c;
    private boolean labyMod$isFirstReload;
    
    public MixinSimpleReloadableResourceManager() {
        this.labyMod$isFirstReload = true;
    }
    
    @Shadow
    public abstract void a(final acv p0);
    
    @Inject(method = { "createReload(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Ljava/util/List;)Lnet/minecraft/server/packs/resources/ReloadInstance;" }, at = { @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;isDebugEnabled()Z", shift = At.Shift.BEFORE) })
    private void labyMod$fireResourceReloadEvent(final Executor foregroundExecutor, final Executor backgroundExecutor, final CompletableFuture<ahn> futureUnit, final List<acv> packs, final CallbackInfoReturnable<adq> cir) {
        final ResourceReloadEvent.Type type = this.labyMod$isFirstReload ? ResourceReloadEvent.Type.INITIALIZATION_RESOURCE_PACKS : ResourceReloadEvent.Type.RELOAD;
        Laby.fireEvent(new ResourceReloadEvent(type, Phase.PRE));
        this.c.add((preparationBarrier, resourceManager, profilerFiller1, profilerFiller2, ex1, ex2) -> preparationBarrier.a((Object)ahn.a).thenRunAsync(() -> Laby.fireEvent(new ResourceReloadEvent(type, Phase.POST)), ex2));
        this.labyMod$isFirstReload = false;
    }
    
    @Override
    public void loadSilent(final ResourcePack pack) {
        this.a((acv)new ModifiedPackResources(pack));
    }
}
