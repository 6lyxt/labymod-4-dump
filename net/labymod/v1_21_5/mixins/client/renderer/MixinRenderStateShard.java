// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.Iterator;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.util.RenderStateShardAttachment;

@Mixin({ grx.class })
public class MixinRenderStateShard implements RenderStateShardAttachment
{
    private final List<Runnable> labyMod$attachments;
    
    public MixinRenderStateShard() {
        this.labyMod$attachments = new ArrayList<Runnable>();
    }
    
    @Inject(method = { "clearRenderState" }, at = { @At("TAIL") })
    private void labyMod$callAttachments(final CallbackInfo ci) {
        for (final Runnable runnable : this.labyMod$attachments) {
            if (runnable == null) {
                continue;
            }
            runnable.run();
        }
        this.labyMod$attachments.clear();
    }
    
    @Override
    public void addAttachment(final Runnable renderer) {
        this.labyMod$attachments.add(renderer);
    }
}
