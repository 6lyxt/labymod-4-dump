// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.entity;

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

@Mixin({ bfh.class })
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
    private ede t;
    @Shadow
    public double I;
    @Shadow
    public double J;
    @Shadow
    public double K;
    @Shadow
    public cmi H;
    @Shadow
    private float aE;
    @Shadow
    public float L;
    @Shadow
    private float aF;
    @Shadow
    public float M;
    private final Position labyMod$position;
    private final Position labyMod$previousPosition;
    
    public MixinEntity() {
        this.labyMod$dataWatcher = new DefaultDataWatcher();
        this.labyMod$entityId = Lazy.of(() -> (ResourceLocation)ja.h.b((Object)this.ae()));
        this.labyMod$position = new DynamicPosition(() -> this.t.c, x -> this.t = this.t.b(x, 0.0, 0.0), () -> this.t.d, y -> this.t = this.t.b(0.0, y, 0.0), () -> this.t.e, z -> this.t = this.t.b(0.0, 0.0, z));
        this.labyMod$previousPosition = new DynamicPosition(() -> this.I, x -> this.I = x, () -> this.J, y -> this.J = y, () -> this.K, z -> this.K = z);
    }
    
    @Shadow
    public abstract boolean shadow$bT();
    
    @Shadow
    public abstract boolean shadow$bU();
    
    @Shadow
    public abstract boolean shadow$ca();
    
    @Shadow
    public abstract UUID shadow$cs();
    
    @Shadow
    protected abstract boolean d(final bgj p0);
    
    @Shadow
    public abstract float k(final float p0);
    
    @Shadow
    public abstract float l(final float p0);
    
    @Shadow
    public abstract Set<String> ag();
    
    @Shadow
    public abstract boolean shadow$aT();
    
    @Shadow
    public abstract bfh shadow$cV();
    
    @Shadow
    public abstract boolean shadow$bg();
    
    @Shadow
    public abstract boolean shadow$ax();
    
    @Shadow
    public abstract boolean shadow$aX();
    
    @Shadow
    public abstract boolean shadow$bK();
    
    @Shadow
    public abstract bfl<?> ae();
    
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
        return this.shadow$bT();
    }
    
    @Intrinsic
    public boolean entity$isSprinting() {
        return this.shadow$bU();
    }
    
    @Intrinsic
    public boolean entity$isInvisible() {
        return this.shadow$ca();
    }
    
    @Override
    public boolean isInvisibleFor(final Player player) {
        return this.labyMod$getEntity().d((bym)player);
    }
    
    @Override
    public UUID getUniqueId() {
        return this.shadow$cs();
    }
    
    @Override
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return this.labyMod$boundingBox;
    }
    
    @Override
    public FloatVector3 perspectiveVector(final float partialTicks) {
        return FloatVector3.calculateViewVector(this.k(partialTicks), this.l(partialTicks));
    }
    
    @Override
    public EntityPose entityPose() {
        final EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        return mapper.fromMinecraft(this.labyMod$getEntity().al());
    }
    
    @Override
    public boolean canEnterEntityPose(final EntityPose pose) {
        final EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        final Object minecraftPose = mapper.toMinecraft(pose);
        return minecraftPose != null && this.d((bgj)minecraftPose);
    }
    
    @Intrinsic
    public float entity$getEyeHeight() {
        return this.labyMod$getEntity().cE();
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
        return this.L;
    }
    
    @Override
    public void setPreviousRotationYaw(final float previousRotationYaw) {
        this.L = previousRotationYaw;
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
        return this.M;
    }
    
    @Override
    public void setPreviousRotationPitch(final float previousRotationPitch) {
        this.M = previousRotationPitch;
    }
    
    @Insert(method = { "setBoundingBox(Lnet/minecraft/world/phys/AABB;)V" }, at = @At("TAIL"))
    private void labyMod$setBoundingBox(final ecz aabb, final InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float)aabb.a, (float)aabb.b, (float)aabb.c, (float)aabb.d, (float)aabb.e, (float)aabb.f);
    }
    
    @Insert(method = { "getEyeY()D" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(final InsertInfoReturnable<Double> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.bf));
        info.setReturnValue(this.t.d + event.getEyeHeight());
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
    
    private bfh labyMod$getEntity() {
        return (bfh)this;
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
            this.ag().remove(string);
        }
        if (type != null) {
            this.ag().add("labymod-nametag:" + String.valueOf(type));
        }
    }
    
    @Intrinsic
    @Override
    public Entity getVehicle() {
        return (Entity)this.shadow$cV();
    }
    
    @Intrinsic
    public boolean entity$isInWater() {
        return this.shadow$aT();
    }
    
    @Intrinsic
    public boolean entity$isInLava() {
        return this.shadow$bg();
    }
    
    @Intrinsic
    public boolean entity$isOnGround() {
        return this.shadow$ax();
    }
    
    private String getNameTagMatch() {
        for (final String tag : this.ag()) {
            if (tag.startsWith("labymod-nametag:")) {
                return tag;
            }
        }
        return null;
    }
    
    @Intrinsic
    public boolean entity$isUnderWater() {
        return this.shadow$aX();
    }
    
    @Intrinsic
    public boolean entity$isOnFire() {
        return this.shadow$bK();
    }
    
    @Override
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
