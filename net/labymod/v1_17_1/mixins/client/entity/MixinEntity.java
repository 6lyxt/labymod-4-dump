// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.entity;

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

@Mixin({ atg.class })
@Implements({ @Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE) })
public abstract class MixinEntity implements Entity
{
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    private final DataWatcher labyMod$dataWatcher;
    private final Lazy<ResourceLocation> labyMod$entityId;
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;
    @Shadow
    private float aX;
    @Shadow
    private dna av;
    @Shadow
    public double u;
    @Shadow
    public double v;
    @Shadow
    public double w;
    @Shadow
    public bwq t;
    @Shadow
    private float ay;
    @Shadow
    public float x;
    @Shadow
    public float az;
    @Shadow
    public float y;
    private final Position labyMod$position;
    private final Position labyMod$previousPosition;
    
    public MixinEntity() {
        this.labyMod$dataWatcher = new DefaultDataWatcher();
        this.labyMod$entityId = Lazy.of(() -> (ResourceLocation)gw.Y.b((Object)this.Y()));
        this.labyMod$position = new DynamicPosition(() -> this.av.b, x -> this.av = this.av.b(x, 0.0, 0.0), () -> this.av.c, y -> this.av = this.av.b(0.0, y, 0.0), () -> this.av.d, z -> this.av = this.av.b(0.0, 0.0, z));
        this.labyMod$previousPosition = new DynamicPosition(() -> this.u, x -> this.u = x, () -> this.v, y -> this.v = y, () -> this.w, z -> this.w = z);
    }
    
    @Shadow
    public abstract boolean shadow$bI();
    
    @Shadow
    public abstract boolean shadow$bJ();
    
    @Shadow
    public abstract boolean shadow$bP();
    
    @Shadow
    public abstract UUID shadow$ch();
    
    @Shadow
    protected abstract boolean c(final aug p0);
    
    @Shadow
    public abstract float f(final float p0);
    
    @Shadow
    public abstract float g(final float p0);
    
    @Shadow
    public abstract Set<String> aa();
    
    @Shadow
    public abstract boolean shadow$aL();
    
    @Shadow
    public abstract atg shadow$cI();
    
    @Shadow
    public abstract boolean shadow$aX();
    
    @Shadow
    public abstract boolean shadow$ar();
    
    @Shadow
    public abstract boolean shadow$aP();
    
    @Shadow
    public abstract boolean shadow$bz();
    
    @Shadow
    public abstract atk<?> Y();
    
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
        return this.shadow$bI();
    }
    
    @Intrinsic
    public boolean entity$isSprinting() {
        return this.shadow$bJ();
    }
    
    @Intrinsic
    public boolean entity$isInvisible() {
        return this.shadow$bP();
    }
    
    @Override
    public boolean isInvisibleFor(final Player player) {
        return this.labyMod$getEntity().c((bke)player);
    }
    
    @Override
    public UUID getUniqueId() {
        return this.shadow$ch();
    }
    
    @Override
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return this.labyMod$boundingBox;
    }
    
    @Override
    public FloatVector3 perspectiveVector(final float partialTicks) {
        return FloatVector3.calculateViewVector(this.f(partialTicks), this.g(partialTicks));
    }
    
    @Override
    public EntityPose entityPose() {
        final EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        return mapper.fromMinecraft(this.labyMod$getEntity().af());
    }
    
    @Override
    public boolean canEnterEntityPose(final EntityPose pose) {
        final EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        final Object minecraftPose = mapper.toMinecraft(pose);
        return minecraftPose != null && this.c((aug)minecraftPose);
    }
    
    @Intrinsic
    public float entity$getEyeHeight() {
        return this.labyMod$getEntity().ct();
    }
    
    @Override
    public float getRotationYaw() {
        return this.ay;
    }
    
    @Override
    public void setRotationYaw(final float rotationYaw) {
        this.ay = rotationYaw;
    }
    
    @Override
    public float getPreviousRotationYaw() {
        return this.x;
    }
    
    @Override
    public void setPreviousRotationYaw(final float previousRotationYaw) {
        this.x = previousRotationYaw;
    }
    
    @Override
    public float getRotationPitch() {
        return this.az;
    }
    
    @Override
    public void setRotationPitch(final float rotationPitch) {
        this.az = rotationPitch;
    }
    
    @Override
    public float getPreviousRotationPitch() {
        return this.y;
    }
    
    @Override
    public void setPreviousRotationPitch(final float previousRotationPitch) {
        this.y = previousRotationPitch;
    }
    
    @Insert(method = { "setBoundingBox(Lnet/minecraft/world/phys/AABB;)V" }, at = @At("TAIL"))
    private void labyMod$setBoundingBox(final dmv aabb, final InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float)aabb.a, (float)aabb.b, (float)aabb.c, (float)aabb.d, (float)aabb.e, (float)aabb.f);
    }
    
    @Insert(method = { "getEyeY()D" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(final InsertInfoReturnable<Double> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.aX));
        info.setReturnValue(this.av.c + event.getEyeHeight());
    }
    
    @Insert(method = { "getEyeHeight()F" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeHeight(final InsertInfoReturnable<Float> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.aX));
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
    
    private atg labyMod$getEntity() {
        return (atg)this;
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
            this.aa().remove(string);
        }
        if (type != null) {
            this.aa().add("labymod-nametag:" + String.valueOf(type));
        }
    }
    
    @Intrinsic
    @Override
    public Entity getVehicle() {
        return (Entity)this.shadow$cI();
    }
    
    @Intrinsic
    public boolean entity$isInWater() {
        return this.shadow$aL();
    }
    
    @Intrinsic
    public boolean entity$isInLava() {
        return this.shadow$aX();
    }
    
    @Intrinsic
    public boolean entity$isOnGround() {
        return this.shadow$ar();
    }
    
    private String getNameTagMatch() {
        for (final String tag : this.aa()) {
            if (tag.startsWith("labymod-nametag:")) {
                return tag;
            }
        }
        return null;
    }
    
    @Intrinsic
    public boolean entity$isUnderWater() {
        return this.shadow$aP();
    }
    
    @Intrinsic
    public boolean entity$isOnFire() {
        return this.shadow$bz();
    }
    
    @Override
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
