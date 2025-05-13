// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.entity;

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

@Mixin({ aqa.class })
@Implements({ @Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE) })
public abstract class MixinEntity implements Entity
{
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    private final DataWatcher labyMod$dataWatcher;
    private final Lazy<ResourceLocation> labyMod$entityId;
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;
    @Shadow
    private float aD;
    @Shadow
    private dcn ai;
    @Shadow
    public double m;
    @Shadow
    public double n;
    @Shadow
    public double o;
    @Shadow
    public brx l;
    @Shadow
    private float p;
    @Shadow
    public float r;
    @Shadow
    public float q;
    @Shadow
    public float s;
    private final Position labyMod$position;
    private final Position labyMod$previousPosition;
    
    public MixinEntity() {
        this.labyMod$dataWatcher = new DefaultDataWatcher();
        this.labyMod$entityId = Lazy.of(() -> (ResourceLocation)gm.S.b((Object)this.X()));
        this.labyMod$position = new DynamicPosition(() -> this.ai.b, x -> this.ai = this.ai.b(x, 0.0, 0.0), () -> this.ai.c, y -> this.ai = this.ai.b(0.0, y, 0.0), () -> this.ai.d, z -> this.ai = this.ai.b(0.0, 0.0, z));
        this.labyMod$previousPosition = new DynamicPosition(() -> this.m, x -> this.m = x, () -> this.n, y -> this.n = y, () -> this.o, z -> this.o = z);
    }
    
    @Shadow
    public abstract boolean shadow$bz();
    
    @Shadow
    public abstract boolean shadow$bA();
    
    @Shadow
    public abstract boolean shadow$bF();
    
    @Shadow
    public abstract UUID shadow$bS();
    
    @Shadow
    protected abstract boolean c(final aqx p0);
    
    @Shadow
    public abstract float g(final float p0);
    
    @Shadow
    public abstract float h(final float p0);
    
    @Shadow
    public abstract Set<String> Z();
    
    @Shadow
    public abstract boolean shadow$aE();
    
    @Shadow
    public abstract aqa shadow$ct();
    
    @Shadow
    public abstract boolean shadow$aQ();
    
    @Shadow
    public abstract boolean shadow$ao();
    
    @Shadow
    public abstract boolean shadow$aI();
    
    @Shadow
    public abstract boolean shadow$bq();
    
    @Shadow
    public abstract void ad();
    
    @Shadow
    public abstract aqe<?> X();
    
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
        return this.shadow$bz();
    }
    
    @Intrinsic
    public boolean entity$isSprinting() {
        return this.shadow$bA();
    }
    
    @Intrinsic
    public boolean entity$isInvisible() {
        return this.shadow$bF();
    }
    
    @Override
    public boolean isInvisibleFor(final Player player) {
        return this.labyMod$getEntity().c((bfw)player);
    }
    
    @Override
    public UUID getUniqueId() {
        return this.shadow$bS();
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
        return mapper.fromMinecraft(this.labyMod$getEntity().ae());
    }
    
    @Override
    public boolean canEnterEntityPose(final EntityPose pose) {
        final EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        final Object minecraftPose = mapper.toMinecraft(pose);
        return minecraftPose != null && this.c((aqx)minecraftPose);
    }
    
    @Intrinsic
    public float entity$getEyeHeight() {
        return this.labyMod$getEntity().ce();
    }
    
    @Override
    public float getRotationYaw() {
        return this.p;
    }
    
    @Override
    public void setRotationYaw(final float rotationYaw) {
        this.p = rotationYaw;
    }
    
    @Override
    public float getPreviousRotationYaw() {
        return this.r;
    }
    
    @Override
    public void setPreviousRotationYaw(final float previousRotationYaw) {
        this.r = previousRotationYaw;
    }
    
    @Override
    public float getRotationPitch() {
        return this.q;
    }
    
    @Override
    public void setRotationPitch(final float rotationPitch) {
        this.q = rotationPitch;
    }
    
    @Override
    public float getPreviousRotationPitch() {
        return this.s;
    }
    
    @Override
    public void setPreviousRotationPitch(final float previousRotationPitch) {
        this.s = previousRotationPitch;
    }
    
    @Insert(method = { "setBoundingBox(Lnet/minecraft/world/phys/AABB;)V" }, at = @At("TAIL"))
    private void labyMod$setBoundingBox(final dci aabb, final InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float)aabb.a, (float)aabb.b, (float)aabb.c, (float)aabb.d, (float)aabb.e, (float)aabb.f);
    }
    
    @Insert(method = { "getEyeY()D" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(final InsertInfoReturnable<Double> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.aD));
        info.setReturnValue(this.ai.c + event.getEyeHeight());
    }
    
    @Insert(method = { "getEyeHeight()F" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeHeight(final InsertInfoReturnable<Float> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.aD));
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
    
    private aqa labyMod$getEntity() {
        return (aqa)this;
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
            this.Z().remove(string);
        }
        if (type != null) {
            this.Z().add("labymod-nametag:" + String.valueOf(type));
        }
    }
    
    @Intrinsic
    @Override
    public Entity getVehicle() {
        return (Entity)this.shadow$ct();
    }
    
    @Intrinsic
    public boolean entity$isInWater() {
        return this.shadow$aE();
    }
    
    @Intrinsic
    public boolean entity$isInLava() {
        return this.shadow$aQ();
    }
    
    @Intrinsic
    public boolean entity$isOnGround() {
        return this.shadow$ao();
    }
    
    private String getNameTagMatch() {
        for (final String tag : this.Z()) {
            if (tag.startsWith("labymod-nametag:")) {
                return tag;
            }
        }
        return null;
    }
    
    @Intrinsic
    public boolean entity$isUnderWater() {
        return this.shadow$aI();
    }
    
    @Intrinsic
    public boolean entity$isOnFire() {
        return this.shadow$bq();
    }
    
    @Override
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
