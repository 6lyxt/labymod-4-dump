// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.entity;

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

@Mixin({ bsw.class })
@Implements({ @Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE) })
public abstract class MixinEntity implements Entity
{
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    private final DataWatcher labyMod$dataWatcher;
    private final Lazy<ResourceLocation> labyMod$entityId;
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;
    @Shadow
    private float bg;
    @Shadow
    private evt s;
    @Shadow
    public double L;
    @Shadow
    public double M;
    @Shadow
    public double N;
    @Shadow
    public dca r;
    @Shadow
    private float aF;
    @Shadow
    public float O;
    @Shadow
    public float aG;
    @Shadow
    public float P;
    private final Position labyMod$position;
    private final Position labyMod$previousPosition;
    
    public MixinEntity() {
        this.labyMod$dataWatcher = new DefaultDataWatcher();
        this.labyMod$entityId = Lazy.of(() -> (ResourceLocation)lp.g.b((Object)this.ak()));
        this.labyMod$position = new DynamicPosition(() -> this.s.c, x -> this.s = this.s.b(x, 0.0, 0.0), () -> this.s.d, y -> this.s = this.s.b(0.0, y, 0.0), () -> this.s.e, z -> this.s = this.s.b(0.0, 0.0, z));
        this.labyMod$previousPosition = new DynamicPosition(() -> this.L, x -> this.L = x, () -> this.M, y -> this.M = y, () -> this.N, z -> this.N = z);
    }
    
    @Shadow
    public abstract boolean shadow$ca();
    
    @Shadow
    public abstract boolean shadow$cb();
    
    @Shadow
    public abstract boolean shadow$ch();
    
    @Override
    public boolean isInvisibleFor(final Player player) {
        return this.labyMod$getEntity().d((cmz)player);
    }
    
    @Shadow
    public abstract UUID shadow$cz();
    
    @Shadow
    public abstract float g(final float p0);
    
    @Shadow
    public abstract float h(final float p0);
    
    @Shadow
    public abstract Set<String> am();
    
    @Shadow
    public abstract boolean shadow$be();
    
    @Shadow
    public abstract bsw shadow$dc();
    
    @Shadow
    public abstract boolean shadow$bs();
    
    @Shadow
    public abstract boolean shadow$aE();
    
    @Shadow
    public abstract boolean shadow$bj();
    
    @Shadow
    public abstract boolean shadow$bQ();
    
    @Shadow
    public abstract evt shadow$dn();
    
    @Shadow
    public abstract bsz a(final bud p0);
    
    @Shadow
    public abstract btc<?> ak();
    
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
        return this.shadow$ca();
    }
    
    @Intrinsic
    public boolean entity$isSprinting() {
        return this.shadow$cb();
    }
    
    @Intrinsic
    public boolean entity$isInvisible() {
        return this.shadow$ch();
    }
    
    @Override
    public UUID getUniqueId() {
        return this.shadow$cz();
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
        return mapper.fromMinecraft(this.labyMod$getEntity().ar());
    }
    
    @Override
    public boolean canEnterEntityPose(final EntityPose pose) {
        final EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        final Object minecraftPose = mapper.toMinecraft(pose);
        return minecraftPose != null && this.r.a((bsw)this, this.a((bud)minecraftPose).a(this.shadow$dn()).h(1.0E-7));
    }
    
    @Intrinsic
    public float entity$getEyeHeight() {
        return this.labyMod$getEntity().cL();
    }
    
    @Override
    public float getRotationYaw() {
        return this.aF;
    }
    
    @Override
    public void setRotationYaw(final float rotationYaw) {
        this.aF = rotationYaw;
    }
    
    @Override
    public float getPreviousRotationYaw() {
        return this.O;
    }
    
    @Override
    public void setPreviousRotationYaw(final float previousRotationYaw) {
        this.O = previousRotationYaw;
    }
    
    @Override
    public float getRotationPitch() {
        return this.aG;
    }
    
    @Override
    public void setRotationPitch(final float rotationPitch) {
        this.aG = rotationPitch;
    }
    
    @Override
    public float getPreviousRotationPitch() {
        return this.P;
    }
    
    @Override
    public void setPreviousRotationPitch(final float previousRotationPitch) {
        this.P = previousRotationPitch;
    }
    
    @Insert(method = { "setBoundingBox(Lnet/minecraft/world/phys/AABB;)V" }, at = @At("TAIL"))
    private void labyMod$setBoundingBox(final evo aabb, final InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float)aabb.a, (float)aabb.b, (float)aabb.c, (float)aabb.d, (float)aabb.e, (float)aabb.f);
    }
    
    @Insert(method = { "getEyeY()D" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(final InsertInfoReturnable<Double> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.bg));
        info.setReturnValue(this.s.d + event.getEyeHeight());
    }
    
    @Insert(method = { "getEyeHeight()F" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeHeight(final InsertInfoReturnable<Float> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.bg));
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
    
    private bsw labyMod$getEntity() {
        return (bsw)this;
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
            this.am().remove(string);
        }
        if (type != null) {
            this.am().add("labymod-nametag:" + String.valueOf(type));
        }
    }
    
    @Intrinsic
    @Override
    public Entity getVehicle() {
        return (Entity)this.shadow$dc();
    }
    
    @Intrinsic
    public boolean entity$isInWater() {
        return this.shadow$be();
    }
    
    @Intrinsic
    public boolean entity$isInLava() {
        return this.shadow$bs();
    }
    
    @Intrinsic
    public boolean entity$isOnGround() {
        return this.shadow$aE();
    }
    
    private String getNameTagMatch() {
        for (final String tag : this.am()) {
            if (tag.startsWith("labymod-nametag:")) {
                return tag;
            }
        }
        return null;
    }
    
    @Intrinsic
    public boolean entity$isUnderWater() {
        return this.shadow$bj();
    }
    
    @Intrinsic
    public boolean entity$isOnFire() {
        return this.shadow$bQ();
    }
    
    @Override
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
