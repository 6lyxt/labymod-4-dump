// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.resources;

import net.labymod.core.client.resources.pack.util.EventResourcePackRepositoryCaller;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.Iterator;
import net.labymod.v1_12_2.client.resources.pack.ModifiedPackResources;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Set;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.resources.pack.SilentReloadableResourceManager;

@Mixin({ cev.class })
public abstract class MixinSimpleReloadableResourceManager implements SilentReloadableResourceManager
{
    @Shadow
    @Final
    private Set<String> e;
    @Shadow
    @Final
    private Map<String, cei> c;
    @Shadow
    @Final
    private cfg f;
    private boolean labyMod$isFirstReload;
    
    @Inject(method = { "reloadResources" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/SimpleReloadableResourceManager;clearResources()V", shift = At.Shift.AFTER) })
    private void labyMod$registerModifiedResourcesPacks(final List<cer> packs, final CallbackInfo ci) {
        for (final ResourcePack pack : Laby.labyAPI().renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            packs.add((cer)new ModifiedPackResources(pack));
        }
    }
    
    @Inject(method = { "reloadResources" }, at = { @At("HEAD") })
    private void labyMod$firePreResourceReloadEvent(final List<cer> packs, final CallbackInfo ci) {
        final ResourceReloadEvent.Type type = this.labyMod$isFirstReload ? ResourceReloadEvent.Type.INITIALIZATION_RESOURCE_PACKS : ResourceReloadEvent.Type.RELOAD;
        Laby.fireEvent(new ResourceReloadEvent(type, Phase.PRE));
    }
    
    @Inject(method = { "reloadResources" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/SimpleReloadableResourceManager;notifyReloadListeners()V", shift = At.Shift.BEFORE) })
    private void labyMod$rebuildSelected(final List<cer> selected, final CallbackInfo ci) {
        EventResourcePackRepositoryCaller.onRebuildSelected(selected);
    }
    
    @Inject(method = { "reloadResources" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/SimpleReloadableResourceManager;notifyReloadListeners()V", shift = At.Shift.AFTER) })
    private void labyMod$firePostResourceReloadEvent(final List<cer> packs, final CallbackInfo ci) {
        final ResourceReloadEvent.Type type = this.labyMod$isFirstReload ? ResourceReloadEvent.Type.INITIALIZATION_RESOURCE_PACKS : ResourceReloadEvent.Type.RELOAD;
        Laby.fireEvent(new ResourceReloadEvent(type, Phase.POST));
        this.labyMod$isFirstReload = false;
    }
    
    @Override
    public void loadSilent(final cer pack) {
        for (final String resourceDomain : pack.c()) {
            this.e.add(resourceDomain);
            this.c.computeIfAbsent(resourceDomain, s -> new cei(this.f)).a(pack);
        }
    }
}
