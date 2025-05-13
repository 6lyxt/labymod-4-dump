// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.RenderTypeAttachmentEventCaller;
import net.labymod.api.event.client.render.RenderTypeAttachmentEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gdx.class })
public class MixinRenderStateShardEvents
{
    @Shadow
    @Final
    protected String b;
    
    @Inject(method = { "setupRenderState" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$fireApplyPreRenderTypeAttachmentEvent(final CallbackInfo ci) {
        if (!(this instanceof gdy)) {
            return;
        }
        final boolean cancelled = RenderTypeAttachmentEventCaller.firePre(this.b, RenderTypeAttachmentEvent.State.APPLY);
        if (cancelled) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "setupRenderState" }, at = { @At("TAIL") })
    private void labyMod$fireApplyPostRenderTypeAttachmentEvent(final CallbackInfo ci) {
        if (!(this instanceof gdy)) {
            return;
        }
        RenderTypeAttachmentEventCaller.firePost(this.b, RenderTypeAttachmentEvent.State.APPLY);
    }
    
    @Inject(method = { "clearRenderState" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$fireClearPreRenderTypeAttachmentEvent(final CallbackInfo ci) {
        if (!(this instanceof gdy)) {
            return;
        }
        final boolean cancelled = RenderTypeAttachmentEventCaller.firePre(this.b, RenderTypeAttachmentEvent.State.CLEAR);
        if (cancelled) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "clearRenderState" }, at = { @At("TAIL") })
    private void labyMod$fireClearPostRenderTypeAttachmentEvent(final CallbackInfo ci) {
        if (!(this instanceof gdy)) {
            return;
        }
        RenderTypeAttachmentEventCaller.firePost(this.b, RenderTypeAttachmentEvent.State.CLEAR);
    }
}
