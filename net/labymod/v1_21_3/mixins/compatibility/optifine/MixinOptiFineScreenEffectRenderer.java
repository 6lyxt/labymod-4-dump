// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Dynamic;
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
import net.labymod.v1_21_3.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({ glx.class })
public class MixinOptiFineScreenEffectRenderer
{
    @Unique
    private static final Stack labymod$STACK;
    @Unique
    private static FloatVector3 labymod$OFFSET;
    
    @Inject(method = { "getViewBlockingState(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/level/block/state/BlockState;" }, at = { @At("HEAD") })
    @Dynamic
    private static void labyMod$getViewBlockingStateHead(final cpx player, final CallbackInfoReturnable<dxv> cir) {
        MixinOptiFineScreenEffectRenderer.labymod$STACK.push();
        Laby.fireEvent(new CameraSetupEvent(MixinOptiFineScreenEffectRenderer.labymod$STACK, Phase.PRE));
        Laby.fireEvent(new CameraSetupEvent(MixinOptiFineScreenEffectRenderer.labymod$STACK, Phase.POST));
        MixinOptiFineScreenEffectRenderer.labymod$OFFSET = MixinOptiFineScreenEffectRenderer.labymod$STACK.transformVector(0.0f, 0.0f, 0.0f);
        MixinOptiFineScreenEffectRenderer.labymod$STACK.pop();
    }
    
    @Redirect(method = { "getOverlayBlock(Lnet/minecraft/world/entity/player/Player;)Lorg/apache/commons/lang3/tuple/Pair;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;set(DDD)Lnet/minecraft/core/BlockPos$MutableBlockPos;"))
    @Dynamic
    private static jh.a labyMod$getViewBlockingState(final jh.a instance, final double x, final double y, final double z) {
        instance.b(x - MixinOptiFineScreenEffectRenderer.labymod$OFFSET.getX(), y - MixinOptiFineScreenEffectRenderer.labymod$OFFSET.getY(), z - MixinOptiFineScreenEffectRenderer.labymod$OFFSET.getZ());
        return instance;
    }
    
    static {
        labymod$STACK = Stack.getDefaultEmptyStack();
        MixinOptiFineScreenEffectRenderer.labymod$OFFSET = FloatVector3.ZERO;
    }
}
