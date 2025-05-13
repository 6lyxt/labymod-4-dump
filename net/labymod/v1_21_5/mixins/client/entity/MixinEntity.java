// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.entity;

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

@Mixin({ bxe.class })
@Implements({ @Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE) })
public abstract class MixinEntity implements Entity
{
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    private final DataWatcher labyMod$dataWatcher;
    private final Lazy<ResourceLocation> labyMod$entityId;
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;
    @Shadow
    private float bf;
    @Shadow
    private fgc aA;
    @Shadow
    public double K;
    @Shadow
    public double L;
    @Shadow
    public double M;
    @Shadow
    public dkj az;
    @Shadow
    private float aE;
    @Shadow
    public float N;
    @Shadow
    public float aF;
    @Shadow
    public float O;
    private final Position labyMod$position;
    private final Position labyMod$previousPosition;
    
    public MixinEntity() {
        this.labyMod$dataWatcher = new DefaultDataWatcher();
        this.labyMod$entityId = Lazy.of(() -> (ResourceLocation)mh.f.b((Object)this.an()));
        this.labyMod$position = new DynamicPosition(() -> this.aA.d, x -> this.aA = this.aA.b(x, 0.0, 0.0), () -> this.aA.e, y -> this.aA = this.aA.b(0.0, y, 0.0), () -> this.aA.f, z -> this.aA = this.aA.b(0.0, 0.0, z));
        this.labyMod$previousPosition = new DynamicPosition(() -> this.K, x -> this.K = x, () -> this.L, y -> this.L = y, () -> this.M, z -> this.M = z);
    }
    
    @Shadow
    public abstract boolean shadow$ch();
    
    @Shadow
    public abstract boolean shadow$ci();
    
    @Shadow
    public abstract boolean shadow$co();
    
    @Override
    public boolean isInvisibleFor(final Player player) {
        return this.labyMod$getEntity().d((csi)player);
    }
    
    @Shadow
    public abstract UUID shadow$cG();
    
    @Shadow
    public abstract float i(final float p0);
    
    @Shadow
    public abstract float j(final float p0);
    
    @Shadow
    public abstract Set<String> ap();
    
    @Shadow
    public abstract boolean shadow$bi();
    
    @Shadow
    public abstract bxe shadow$dk();
    
    @Shadow
    public abstract boolean shadow$bv();
    
    @Shadow
    public abstract boolean shadow$aH();
    
    @Shadow
    public abstract boolean shadow$bm();
    
    @Shadow
    public abstract boolean shadow$bX();
    
    @Shadow
    public abstract fgc shadow$dt();
    
    @Shadow
    public abstract bxh a(final byr p0);
    
    @Shadow
    public abstract bxn<?> an();
    
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
        return this.shadow$ch();
    }
    
    @Intrinsic
    public boolean entity$isSprinting() {
        return this.shadow$ci();
    }
    
    @Intrinsic
    public boolean entity$isInvisible() {
        return this.shadow$co();
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
        return FloatVector3.calculateViewVector(this.i(partialTicks), this.j(partialTicks));
    }
    
    @Override
    public EntityPose entityPose() {
        final EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        return mapper.fromMinecraft(this.labyMod$getEntity().at());
    }
    
    @Override
    public boolean canEnterEntityPose(final EntityPose pose) {
        final EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        final Object minecraftPose = mapper.toMinecraft(pose);
        return minecraftPose != null && this.az.a((bxe)this, this.a((byr)minecraftPose).a(this.shadow$dt()).h(1.0E-7));
    }
    
    @Intrinsic
    public float entity$getEyeHeight() {
        return this.labyMod$getEntity().cS();
    }
    
    @Override
    public float getRotationYaw() {
        return this.aE;
    }
    
    @Override
    public void setRotationYaw(final float rotationYaw) {
        this.aE = rotationYaw;
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
        return this.aF;
    }
    
    @Override
    public void setRotationPitch(final float rotationPitch) {
        this.aF = rotationPitch;
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
    private void labyMod$setBoundingBox(final ffx aabb, final InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float)aabb.a, (float)aabb.b, (float)aabb.c, (float)aabb.d, (float)aabb.e, (float)aabb.f);
    }
    
    @Insert(method = { "getEyeY()D" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(final InsertInfoReturnable<Double> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.bf));
        info.setReturnValue(this.aA.e + event.getEyeHeight());
    }
    
    @Insert(method = { "getEyeHeight()F" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeHeight(final InsertInfoReturnable<Float> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.bf));
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
    
    private bxe labyMod$getEntity() {
        return (bxe)this;
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
            this.ap().remove(string);
        }
        if (type != null) {
            this.ap().add("labymod-nametag:" + String.valueOf(type));
        }
    }
    
    @Intrinsic
    @Override
    public Entity getVehicle() {
        return (Entity)this.shadow$dk();
    }
    
    @Intrinsic
    public boolean entity$isInWater() {
        return this.shadow$bi();
    }
    
    @Intrinsic
    public boolean entity$isInLava() {
        return this.shadow$bv();
    }
    
    @Intrinsic
    public boolean entity$isOnGround() {
        return this.shadow$aH();
    }
    
    private String getNameTagMatch() {
        for (final String tag : this.ap()) {
            if (tag.startsWith("labymod-nametag:")) {
                return tag;
            }
        }
        return null;
    }
    
    @Intrinsic
    public boolean entity$isUnderWater() {
        return this.shadow$bm();
    }
    
    @Intrinsic
    public boolean entity$isOnFire() {
        return this.shadow$bX();
    }
    
    @Override
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
