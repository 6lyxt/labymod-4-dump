// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.entity;

import java.util.Iterator;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.animation.old.animations.RangeOldAnimation;
import net.labymod.core.main.LabyMod;
import net.labymod.core.event.client.render.entity.EntityEyeHeightEvent;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.entity.EntityPoseMapper;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.asm.mixin.Intrinsic;
import java.util.Set;
import java.util.UUID;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.util.math.position.DynamicPosition;
import net.labymod.core.client.entity.DefaultDataWatcher;
import net.labymod.api.util.math.position.Position;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.Lazy;
import net.labymod.api.client.entity.datawatcher.DataWatcher;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.Entity;

@Mixin({ bvk.class })
@Implements({ @Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE) })
public abstract class MixinEntity implements Entity
{
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    private final DataWatcher labyMod$dataWatcher;
    private final Lazy<ResourceLocation> labyMod$entityId;
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;
    @Shadow
    private float bc;
    @Shadow
    private fby t;
    @Shadow
    public double K;
    @Shadow
    public double L;
    @Shadow
    public double M;
    @Shadow
    public dhi s;
    @Shadow
    private float aA;
    @Shadow
    public float N;
    @Shadow
    public float aB;
    @Shadow
    public float O;
    private final Position labyMod$position;
    private final Position labyMod$previousPosition;
    
    public MixinEntity() {
        this.labyMod$dataWatcher = new DefaultDataWatcher();
        this.labyMod$entityId = Lazy.of(() -> (ResourceLocation)ma.f.b((Object)this.aq()));
        this.labyMod$position = new DynamicPosition(() -> this.t.d, x -> this.t = this.t.b(x, 0.0, 0.0), () -> this.t.e, y -> this.t = this.t.b(0.0, y, 0.0), () -> this.t.f, z -> this.t = this.t.b(0.0, 0.0, z));
        this.labyMod$previousPosition = new DynamicPosition(() -> this.K, x -> this.K = x, () -> this.L, y -> this.L = y, () -> this.M, z -> this.M = z);
    }
    
    @Shadow
    public abstract boolean shadow$ci();
    
    @Shadow
    public abstract boolean shadow$cj();
    
    @Shadow
    public abstract boolean shadow$cp();
    
    @Override
    public boolean isInvisibleFor(final Player player) {
        return this.labyMod$getEntity().d((cpx)player);
    }
    
    @Shadow
    public abstract UUID shadow$cG();
    
    @Shadow
    public abstract float h(final float p0);
    
    @Shadow
    public abstract float i(final float p0);
    
    @Shadow
    public abstract Set<String> as();
    
    @Shadow
    public abstract boolean shadow$bj();
    
    @Shadow
    public abstract bvk shadow$dl();
    
    @Shadow
    public abstract boolean shadow$bx();
    
    @Shadow
    public abstract boolean shadow$aJ();
    
    @Shadow
    public abstract boolean shadow$bo();
    
    @Shadow
    public abstract boolean shadow$bY();
    
    @Shadow
    public abstract fby shadow$du();
    
    @Shadow
    public abstract bvn a(final bws p0);
    
    @Shadow
    public abstract bvr<?> aq();
    
    @Override
    public Position position() {
        return this.labyMod$position;
    }
    
    @Override
    public Position previousPosition() {
        return this.labyMod$previousPosition;
    }
    
    @Intrinsic
    public boolean entity$isCrouching() {
        return this.shadow$ci();
    }
    
    @Intrinsic
    public boolean entity$isSprinting() {
        return this.shadow$cj();
    }
    
    @Intrinsic
    public boolean entity$isInvisible() {
        return this.shadow$cp();
    }
    
    @Override
    public UUID getUniqueId() {
        return this.shadow$cG();
    }
    
    @Override
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return this.labyMod$boundingBox;
    }
    
    @Override
    public FloatVector3 perspectiveVector(final float partialTicks) {
        return FloatVector3.calculateViewVector(this.h(partialTicks), this.i(partialTicks));
    }
    
    @Override
    public EntityPose entityPose() {
        final EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        return mapper.fromMinecraft(this.labyMod$getEntity().aw());
    }
    
    @Override
    public boolean canEnterEntityPose(final EntityPose pose) {
        final EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        final Object minecraftPose = mapper.toMinecraft(pose);
        return minecraftPose != null && this.s.a((bvk)this, this.a((bws)minecraftPose).a(this.shadow$du()).h(1.0E-7));
    }
    
    @Intrinsic
    public float entity$getEyeHeight() {
        return this.labyMod$getEntity().cS();
    }
    
    @Override
    public float getRotationYaw() {
        return this.aA;
    }
    
    @Override
    public void setRotationYaw(final float rotationYaw) {
        this.aA = rotationYaw;
    }
    
    @Override
    public float getPreviousRotationYaw() {
        return this.N;
    }
    
    @Override
    public void setPreviousRotationYaw(final float previousRotationYaw) {
        this.N = previousRotationYaw;
    }
    
    @Override
    public float getRotationPitch() {
        return this.aB;
    }
    
    @Override
    public void setRotationPitch(final float rotationPitch) {
        this.aB = rotationPitch;
    }
    
    @Override
    public float getPreviousRotationPitch() {
        return this.O;
    }
    
    @Override
    public void setPreviousRotationPitch(final float previousRotationPitch) {
        this.O = previousRotationPitch;
    }
    
    @Insert(method = { "setBoundingBox(Lnet/minecraft/world/phys/AABB;)V" }, at = @At("TAIL"))
    private void labyMod$setBoundingBox(final fbt aabb, final InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float)aabb.a, (float)aabb.b, (float)aabb.c, (float)aabb.d, (float)aabb.e, (float)aabb.f);
    }
    
    @Insert(method = { "getEyeY()D" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(final InsertInfoReturnable<Double> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.bc));
        info.setReturnValue(this.t.e + event.getEyeHeight());
    }
    
    @Insert(method = { "getEyeHeight()F" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeHeight(final InsertInfoReturnable<Float> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.bc));
        info.setReturnValue(event.getEyeHeight());
    }
    
    @Insert(method = { "getPickRadius()F" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$oldRange(final InsertInfoReturnable<Float> info) {
        final OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        final RangeOldAnimation animation = oldAnimationRegistry.get("range");
        if (animation == null) {
            return;
        }
        if (animation.isEnabled()) {
            info.setReturnValue(animation.getOldPickRadius());
        }
    }
    
    private bvk labyMod$getEntity() {
        return (bvk)this;
    }
    
    @Override
    public DataWatcher dataWatcher() {
        return this.labyMod$dataWatcher;
    }
    
    @Override
    public void setRendered(final boolean rendered) {
        this.labyMod$rendered = rendered;
    }
    
    @Override
    public boolean isRendered() {
        return this.labyMod$rendered;
    }
    
    @Override
    public TagType nameTagType() {
        final String string = this.getNameTagMatch();
        if (string == null) {
            return TagType.CUSTOM;
        }
        return TagType.fromString(string.substring("labymod-nametag:".length()));
    }
    
    @Override
    public void setNameTagType(final TagType type) {
        final String string = this.getNameTagMatch();
        if (string != null) {
            this.as().remove(string);
        }
        if (type != null) {
            this.as().add("labymod-nametag:" + String.valueOf(type));
        }
    }
    
    @Intrinsic
    @Override
    public Entity getVehicle() {
        return (Entity)this.shadow$dl();
    }
    
    @Intrinsic
    public boolean entity$isInWater() {
        return this.shadow$bj();
    }
    
    @Intrinsic
    public boolean entity$isInLava() {
        return this.shadow$bx();
    }
    
    @Intrinsic
    public boolean entity$isOnGround() {
        return this.shadow$aJ();
    }
    
    private String getNameTagMatch() {
        for (final String tag : this.as()) {
            if (tag.startsWith("labymod-nametag:")) {
                return tag;
            }
        }
        return null;
    }
    
    @Intrinsic
    public boolean entity$isUnderWater() {
        return this.shadow$bo();
    }
    
    @Intrinsic
    public boolean entity$isOnFire() {
        return this.shadow$bY();
    }
    
    @Override
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
