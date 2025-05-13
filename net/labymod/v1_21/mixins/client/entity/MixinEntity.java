// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.entity;

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

@Mixin({ bsr.class })
@Implements({ @Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE) })
public abstract class MixinEntity implements Entity
{
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    private final DataWatcher labyMod$dataWatcher;
    private final Lazy<ResourceLocation> labyMod$entityId;
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;
    @Shadow
    private float be;
    @Shadow
    private exc s;
    @Shadow
    public double L;
    @Shadow
    public double M;
    @Shadow
    public double N;
    @Shadow
    public dcw r;
    @Shadow
    private float aD;
    @Shadow
    public float O;
    @Shadow
    public float aE;
    @Shadow
    public float P;
    private final Position labyMod$position;
    private final Position labyMod$previousPosition;
    
    public MixinEntity() {
        this.labyMod$dataWatcher = new DefaultDataWatcher();
        this.labyMod$entityId = Lazy.of(() -> (ResourceLocation)lt.f.b((Object)this.am()));
        this.labyMod$position = new DynamicPosition(() -> this.s.c, x -> this.s = this.s.b(x, 0.0, 0.0), () -> this.s.d, y -> this.s = this.s.b(0.0, y, 0.0), () -> this.s.e, z -> this.s = this.s.b(0.0, 0.0, z));
        this.labyMod$previousPosition = new DynamicPosition(() -> this.L, x -> this.L = x, () -> this.M, y -> this.M = y, () -> this.N, z -> this.N = z);
    }
    
    @Shadow
    public abstract boolean shadow$cb();
    
    @Shadow
    public abstract boolean shadow$cc();
    
    @Shadow
    public abstract boolean shadow$ci();
    
    @Override
    public boolean isInvisibleFor(final Player player) {
        return this.labyMod$getEntity().d((cmx)player);
    }
    
    @Shadow
    public abstract UUID shadow$cz();
    
    @Shadow
    public abstract float h(final float p0);
    
    @Shadow
    public abstract float i(final float p0);
    
    @Shadow
    public abstract Set<String> ao();
    
    @Shadow
    public abstract boolean shadow$bf();
    
    @Shadow
    public abstract bsr shadow$dc();
    
    @Shadow
    public abstract boolean shadow$bt();
    
    @Shadow
    public abstract boolean shadow$aF();
    
    @Shadow
    public abstract boolean shadow$bk();
    
    @Shadow
    public abstract boolean shadow$bR();
    
    @Shadow
    public abstract exc shadow$dm();
    
    @Shadow
    public abstract bsu a(final bua p0);
    
    @Shadow
    public abstract bsx<?> am();
    
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
        return this.shadow$cb();
    }
    
    @Intrinsic
    public boolean entity$isSprinting() {
        return this.shadow$cc();
    }
    
    @Intrinsic
    public boolean entity$isInvisible() {
        return this.shadow$ci();
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
        return FloatVector3.calculateViewVector(this.h(partialTicks), this.i(partialTicks));
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
        return minecraftPose != null && this.r.a((bsr)this, this.a((bua)minecraftPose).a(this.shadow$dm()).h(1.0E-7));
    }
    
    @Intrinsic
    public float entity$getEyeHeight() {
        return this.labyMod$getEntity().cL();
    }
    
    @Override
    public float getRotationYaw() {
        return this.aD;
    }
    
    @Override
    public void setRotationYaw(final float rotationYaw) {
        this.aD = rotationYaw;
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
        return this.aE;
    }
    
    @Override
    public void setRotationPitch(final float rotationPitch) {
        this.aE = rotationPitch;
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
    private void labyMod$setBoundingBox(final ewx aabb, final InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float)aabb.a, (float)aabb.b, (float)aabb.c, (float)aabb.d, (float)aabb.e, (float)aabb.f);
    }
    
    @Insert(method = { "getEyeY()D" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(final InsertInfoReturnable<Double> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.be));
        info.setReturnValue(this.s.d + event.getEyeHeight());
    }
    
    @Insert(method = { "getEyeHeight()F" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeHeight(final InsertInfoReturnable<Float> info) {
        final EntityEyeHeightEvent event = Laby.fireEvent(new EntityEyeHeightEvent(this, this.be));
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
    
    private bsr labyMod$getEntity() {
        return (bsr)this;
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
            this.ao().remove(string);
        }
        if (type != null) {
            this.ao().add("labymod-nametag:" + String.valueOf(type));
        }
    }
    
    @Intrinsic
    @Override
    public Entity getVehicle() {
        return (Entity)this.shadow$dc();
    }
    
    @Intrinsic
    public boolean entity$isInWater() {
        return this.shadow$bf();
    }
    
    @Intrinsic
    public boolean entity$isInLava() {
        return this.shadow$bt();
    }
    
    @Intrinsic
    public boolean entity$isOnGround() {
        return this.shadow$aF();
    }
    
    private String getNameTagMatch() {
        for (final String tag : this.ao()) {
            if (tag.startsWith("labymod-nametag:")) {
                return tag;
            }
        }
        return null;
    }
    
    @Intrinsic
    public boolean entity$isUnderWater() {
        return this.shadow$bk();
    }
    
    @Intrinsic
    public boolean entity$isOnFire() {
        return this.shadow$bR();
    }
    
    @Override
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
