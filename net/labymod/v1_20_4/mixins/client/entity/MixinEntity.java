// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.entity;

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
import net.labymod.api.client.entity.player.Player;
import org.spongepowered.asm.mixin.Intrinsic;
import java.util.Set;
import java.util.UUID;
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

@Mixin({ blv.class })
@Implements({ @Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE) })
public abstract class MixinEntity implements Entity
{
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    private final DataWatcher labyMod$dataWatcher;
    private final Lazy<ResourceLocation> labyMod$entityId;
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;
    @Shadow
    private float bi;
    @Shadow
    private elt u;
    @Shadow
    public double K;
    @Shadow
    public double L;
    @Shadow
    public double M;
    @Shadow
    public ctp t;
    @Shadow
    private float aG;
    @Shadow
    public float N;
    @Shadow
    public float aH;
    @Shadow
    public float O;
    private final Position labyMod$position;
    private final Position labyMod$previousPosition;
    
    public MixinEntity() {
        this.labyMod$dataWatcher = new DefaultDataWatcher();
        this.labyMod$entityId = Lazy.of(() -> (ResourceLocation)kd.g.b((Object)this.ai()));
        this.labyMod$position = new DynamicPosition(() -> this.u.c, x -> this.u = this.u.b(x, 0.0, 0.0), () -> this.u.d, y -> this.u = this.u.b(0.0, y, 0.0), () -> this.u.e, z -> this.u = this.u.b(0.0, 0.0, z));
        this.labyMod$previousPosition = new DynamicPosition(() -> this.K, x -> this.K = x, () -> this.L, y -> this.L = y, () -> this.M, z -> this.M = z);
    }
    
    @Shadow
    public abstract boolean shadow$bX();
    
    @Shadow
    public abstract boolean shadow$bY();
    
    @Shadow
    public abstract boolean shadow$ce();
    
    @Shadow
    public abstract UUID shadow$cw();
    
    @Shadow
    public abstract float g(final float p0);
    
    @Shadow
    public abstract float h(final float p0);
    
    @Shadow
    public abstract Set<String> ak();
    
    @Shadow
    public abstract boolean shadow$aZ();
    
    @Shadow
    public abstract blv shadow$cZ();
    
    @Shadow
    public abstract boolean shadow$bn();
    
    @Shadow
    public abstract boolean shadow$aC();
    
    @Shadow
    public abstract boolean shadow$be();
    
    @Shadow
    public abstract boolean shadow$bN();
    
    @Shadow
    public abstract blw a(final bmx p0);
    
    @Shadow
    public abstract elt shadow$dk();
    
    @Shadow
    public abstract blz<?> ai();
    
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
        return this.shadow$bX();
    }
    
    @Intrinsic
    public boolean entity$isSprinting() {
        return this.shadow$bY();
    }
    
    @Intrinsic
    public boolean entity$isInvisible() {
        return this.shadow$ce();
    }
    
    @Override
    public boolean isInvisibleFor(final Player player) {
        return this.labyMod$getEntity().d((cfi)player);
    }
    
    @Override
    public UUID getUniqueId() {
        return this.shadow$cw();
    }
    
    @Override
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return this.labyMod$boundingBox;
    }
    
    @Override
    public FloatVector3 perspectiveVector(final float partialTicks) {
        return FloatVector3.calculateViewVector(this.g(partialTicks), this.h(partialTicks));
    }
    
    @Override
    public EntityPose entityPose() {
        final EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        return mapper.fromMinecraft(this.labyMod$getEntity().ap());
    }
    
    @Override
    public boolean canEnterEntityPose(final EntityPose pose) {
        final EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        final Object minecraftPose = mapper.toMinecraft(pose);
        return minecraftPose != null && this.t.a((blv)this, this.a((bmx)minecraftPose).a(this.shadow$dk()).h(1.0E-7));
    }
    
    @Intrinsic
    public float entity$getEyeHeight() {
        return this.labyMod$getEntity().cI();
    }
    
    @Override
    public float getRotationYaw() {
        return this.aG;
    }
    
    @Override
    public void setRotationYaw(final float rotationYaw) {
        this.aG = rotationYaw;
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
        return this.aH;
    }
    
    @Override
    public void setRotationPitch(final float rotationPitch) {
        this.aH = rotationPitch;
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
    private void labyMod$setBoundingBox(final elo aabb, final InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float)aabb.a, (float)aabb.b, (float)aabb.c, (float)aabb.d, (float)aabb.e, (float)aabb.f);
    }
    
    @Insert(method = { "getEyeY()D" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(final InsertInfoReturnable<Double> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.bi));
        info.setReturnValue(this.u.d + event.getEyeHeight());
    }
    
    @Insert(method = { "getEyeHeight()F" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeHeight(final InsertInfoReturnable<Float> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.bi));
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
    
    private blv labyMod$getEntity() {
        return (blv)this;
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
            this.ak().remove(string);
        }
        if (type != null) {
            this.ak().add("labymod-nametag:" + String.valueOf(type));
        }
    }
    
    @Intrinsic
    @Override
    public Entity getVehicle() {
        return (Entity)this.shadow$cZ();
    }
    
    @Intrinsic
    public boolean entity$isInWater() {
        return this.shadow$aZ();
    }
    
    @Intrinsic
    public boolean entity$isInLava() {
        return this.shadow$bn();
    }
    
    @Intrinsic
    public boolean entity$isOnGround() {
        return this.shadow$aC();
    }
    
    private String getNameTagMatch() {
        for (final String tag : this.ak()) {
            if (tag.startsWith("labymod-nametag:")) {
                return tag;
            }
        }
        return null;
    }
    
    @Intrinsic
    public boolean entity$isUnderWater() {
        return this.shadow$be();
    }
    
    @Intrinsic
    public boolean entity$isOnFire() {
        return this.shadow$bN();
    }
    
    @Override
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
