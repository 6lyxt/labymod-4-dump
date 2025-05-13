// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client;

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

@Mixin({ eut.class })
public abstract class MixinCamera implements ExtendedMinecraftCamera
{
    @Shadow
    private float j;
    @Shadow
    private float k;
    private final FloatVector3 labymod$cameraPosition;
    private final Quaternion labymod$rotation;
    private final DoubleVector3 labyMod$position;
    @Shadow
    private float o;
    @Shadow
    private float n;
    
    public MixinCamera() {
        this.labymod$cameraPosition = new FloatVector3(0.0f, 0.0f, 0.0f);
        this.labymod$rotation = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
        this.labyMod$position = new DoubleVector3();
    }
    
    @Shadow
    public abstract elt b();
    
    @ModifyArgs(method = { "setup" }, at = @At(value = "INVOKE", target = "net/minecraft/client/Camera.setRotation(FF)V", ordinal = 0))
    private void setDirectionYawAndPitch(final Args args) {
        final CameraRotationEvent event = Laby.fireEvent(new CameraRotationEvent((float)args.get(0), (float)args.get(1)));
        args.setAll(new Object[] { event.getYaw(), event.getPitch() });
    }
    
    @NotNull
    @Override
    public FloatVector3 deprecated$position() {
        final elt position = this.b();
        this.labymod$cameraPosition.set((float)position.a(ic.a.a), (float)position.a(ic.a.b), (float)position.a(ic.a.c));
        return this.labymod$cameraPosition;
    }
    
    @NotNull
    @Override
    public DoubleVector3 position() {
        final elt position = this.b();
        this.labyMod$position.set(position.a(ic.a.a), position.a(ic.a.b), position.a(ic.a.c));
        return this.labyMod$position;
    }
    
    @NotNull
    @Override
    public Quaternion rotation() {
        this.labymod$rotation.set(0.0f, 0.0f, 0.0f, 1.0f);
        this.labymod$rotation.multiply(FloatVector3.YP.rotationDegrees(-this.k));
        this.labymod$rotation.multiply(FloatVector3.XP.rotationDegrees(this.j));
        return this.labymod$rotation;
    }
    
    @Override
    public float getEyeHeight(final float partialTicks) {
        return MathHelper.lerp(this.n, this.o, partialTicks);
    }
}
