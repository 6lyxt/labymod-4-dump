// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client;

import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.camera.CameraRotationEvent;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.world.ExtendedMinecraftCamera;

@Mixin({ fes.class })
public abstract class MixinCamera implements ExtendedMinecraftCamera
{
    @Shadow
    private float k;
    @Shadow
    private float l;
    private final FloatVector3 labymod$cameraPosition;
    private final Quaternion labymod$rotation;
    private final DoubleVector3 labyMod$position;
    @Shadow
    private float p;
    @Shadow
    private float o;
    
    public MixinCamera() {
        this.labymod$cameraPosition = new FloatVector3(0.0f, 0.0f, 0.0f);
        this.labymod$rotation = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
        this.labyMod$position = new DoubleVector3();
    }
    
    @Shadow
    public abstract evt b();
    
    @ModifyArgs(method = { "setup" }, at = @At(value = "INVOKE", target = "net/minecraft/client/Camera.setRotation(FF)V", ordinal = 0))
    private void setDirectionYawAndPitch(final Args args) {
        final CameraRotationEvent event = Laby.fireEvent(new CameraRotationEvent((float)args.get(0), (float)args.get(1)));
        args.setAll(new Object[] { event.getYaw(), event.getPitch() });
    }
    
    @NotNull
    @Override
    public FloatVector3 deprecated$position() {
        final evt position = this.b();
        this.labymod$cameraPosition.set((float)position.a(je.a.a), (float)position.a(je.a.b), (float)position.a(je.a.c));
        return this.labymod$cameraPosition;
    }
    
    @NotNull
    @Override
    public DoubleVector3 position() {
        final evt position = this.b();
        this.labyMod$position.set(position.a(je.a.a), position.a(je.a.b), position.a(je.a.c));
        return this.labyMod$position;
    }
    
    @NotNull
    @Override
    public Quaternion rotation() {
        this.labymod$rotation.set(0.0f, 0.0f, 0.0f, 1.0f);
        this.labymod$rotation.multiply(FloatVector3.YP.rotationDegrees(-this.l));
        this.labymod$rotation.multiply(FloatVector3.XP.rotationDegrees(this.k));
        return this.labymod$rotation;
    }
    
    @Override
    public float getEyeHeight(final float partialTicks) {
        return MathHelper.lerp(this.o, this.p, partialTicks);
    }
}
