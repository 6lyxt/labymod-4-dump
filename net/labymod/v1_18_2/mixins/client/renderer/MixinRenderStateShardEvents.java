// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.RenderTypeAttachmentEventCaller;
import net.labymod.api.event.client.render.RenderTypeAttachmentEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eqz.class })
public class MixinRenderStateShardEvents
{
    @Shadow
    @Final
    protected String a;
    
    @Inject(method = { "setupRenderState" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$fireApplyPreRenderTypeAttachmentEvent(final CallbackInfo ci) {
        if (!(this instanceof era)) {
            return;
        }
        final boolean cancelled = RenderTypeAttachmentEventCaller.firePre(this.a, RenderTypeAttachmentEvent.State.APPLY);
        if (cancelled) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "setupRenderState" }, at = { @At("TAIL") })
    private void labyMod$fireApplyPostRenderTypeAttachmentEvent(final CallbackInfo ci) {
        if (!(this instanceof era)) {
            return;
        }
        RenderTypeAttachmentEventCaller.firePost(this.a, RenderTypeAttachmentEvent.State.APPLY);
    }
    
    @Inject(method = { "clearRenderState" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$fireClearPreRenderTypeAttachmentEvent(final CallbackInfo ci) {
        if (!(this instanceof era)) {
            return;
        }
        final boolean cancelled = RenderTypeAttachmentEventCaller.firePre(this.a, RenderTypeAttachmentEvent.State.CLEAR);
        if (cancelled) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "clearRenderState" }, at = { @At("TAIL") })
    private void labyMod$fireClearPostRenderTypeAttachmentEvent(final CallbackInfo ci) {
        if (!(this instanceof era)) {
            return;
        }
        RenderTypeAttachmentEventCaller.firePost(this.a, RenderTypeAttachmentEvent.State.CLEAR);
    }
}
