// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.util;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.FutureTask;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ g.class })
public class MixinUtil
{
    @Insert(method = { "runTask" }, at = @At("HEAD"), cancellable = true)
    private static void labyMod$fixNullPointer(final FutureTask task, final Logger logger, final InsertInfoReturnable<?> cir) {
        if (task == null) {
            cir.cancel();
        }
    }
}
