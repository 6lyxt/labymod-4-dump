// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.screen.components.events;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fkh.class })
public interface MixinContainerEventHandler
{
    @Shadow
    boolean a(final int p0, final int p1, final int p2);
    
    @Inject(method = { "charTyped" }, at = { @At("HEAD") }, cancellable = true)
    default void labyMod$fixControlsScreenKeys(final char c, final int i, final CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof frm) {
            final Key key = DefaultKeyMapper.lastPressed();
            if (key == null) {
                return;
            }
            if (this.a(key.getId(), i, 0)) {
                cir.setReturnValue((Object)true);
            }
        }
    }
}
