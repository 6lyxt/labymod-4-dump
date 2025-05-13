// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.renderer;

import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.DynamicStackProvider;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ erc.class })
public class MixinScreenEffectRenderer
{
    @Unique
    private static final Stack labymod$STACK;
    @Unique
    private static FloatVector3 labymod$OFFSET;
    
    @Inject(method = { "getViewBlockingState(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/level/block/state/BlockState;" }, at = { @At("HEAD") })
    private static void labyMod$getViewBlockingStateHead(final boj player, final CallbackInfoReturnable<cov> cir) {
        MixinScreenEffectRenderer.labymod$STACK.push();
        Laby.fireEvent(new CameraSetupEvent(MixinScreenEffectRenderer.labymod$STACK, Phase.PRE));
        Laby.fireEvent(new CameraSetupEvent(MixinScreenEffectRenderer.labymod$STACK, Phase.POST));
        MixinScreenEffectRenderer.labymod$OFFSET = MixinScreenEffectRenderer.labymod$STACK.transformVector(0.0f, 0.0f, 0.0f);
        MixinScreenEffectRenderer.labymod$STACK.pop();
    }
    
    @Redirect(method = { "getViewBlockingState(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/level/block/state/BlockState;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;set(DDD)Lnet/minecraft/core/BlockPos$MutableBlockPos;"))
    private static gj.a labyMod$getViewBlockingState(final gj.a instance, final double x, final double y, final double z) {
        instance.c(x - MixinScreenEffectRenderer.labymod$OFFSET.getX(), y - MixinScreenEffectRenderer.labymod$OFFSET.getY(), z - MixinScreenEffectRenderer.labymod$OFFSET.getZ());
        return instance;
    }
    
    static {
        labymod$STACK = Stack.create(new DynamicStackProvider());
        MixinScreenEffectRenderer.labymod$OFFSET = FloatVector3.ZERO;
    }
}
