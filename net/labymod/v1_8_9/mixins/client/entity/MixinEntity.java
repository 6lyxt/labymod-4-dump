// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.entity.EntityPose;
import java.util.UUID;
import net.labymod.api.client.entity.player.Player;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.api.util.math.position.DynamicPosition;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.DynamicStackProvider;
import net.labymod.core.util.LegacyEntityTypeRegistry;
import net.labymod.core.client.entity.DefaultDataWatcher;
import net.labymod.api.util.math.position.Position;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.client.render.matrix.Stack;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.Lazy;
import net.labymod.api.client.entity.datawatcher.DataWatcher;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.forge.entity.ForgeEntity;
import net.labymod.api.client.entity.Entity;

@Mixin({ pk.class })
@Implements({ @Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE) })
public abstract class MixinEntity implements Entity, ForgeEntity
{
    private final AxisAlignedBoundingBox labyMod$boundingBox;
    private final DataWatcher labyMod$dataWatcher;
    private final Lazy<ResourceLocation> labyMod$entityId;
    private boolean labyMod$rendered;
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion = "forge support")
    private TagType labyMod$tagType;
    @Unique
    private final Stack labymod$stack;
    @Unique
    private FloatVector3 labymod$offset;
    @Shadow
    public double s;
    @Shadow
    public double t;
    @Shadow
    public double u;
    @Shadow
    public double r;
    @Shadow
    public double q;
    @Shadow
    public double p;
    @Shadow
    private aug f;
    @Shadow
    public float y;
    @Shadow
    public float A;
    @Shadow
    public boolean C;
    @Shadow
    public pk m;
    @Shadow
    public float z;
    @Shadow
    public float B;
    @Shadow
    public adm o;
    private final Position labyMod$position;
    private final Position labyMod$previousPosition;
    
    public MixinEntity() {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox();
        this.labyMod$dataWatcher = new DefaultDataWatcher();
        this.labyMod$entityId = Lazy.of(() -> LegacyEntityTypeRegistry.getId(this.getClass()));
        this.labyMod$tagType = TagType.CUSTOM;
        this.labymod$stack = Stack.create(new DynamicStackProvider());
        this.labymod$offset = FloatVector3.ZERO;
        this.labyMod$position = new DynamicPosition(() -> this.s, x -> this.s = x, () -> this.t, y -> this.t = y, () -> this.u, z -> this.u = z);
        this.labyMod$previousPosition = new DynamicPosition(() -> this.p, x -> this.p = x, () -> this.q, y -> this.q = y, () -> this.r, z -> this.r = z);
    }
    
    @Shadow
    public abstract boolean shadow$aw();
    
    @Shadow
    public abstract boolean shadow$ax();
    
    @Shadow
    public aui d(final float partialTicks) {
        return null;
    }
    
    @Shadow
    public abstract float aS();
    
    @Shadow
    public abstract boolean shadow$V();
    
    @Shadow
    public abstract boolean shadow$ab();
    
    @Shadow
    public abstract boolean shadow$at();
    
    @Shadow
    public abstract boolean a(final arm p0);
    
    @Override
    public Position position() {
        return this.labyMod$position;
    }
    
    @Override
    public Position previousPosition() {
        return this.labyMod$previousPosition;
    }
    
    @Override
    public boolean isCrouching() {
        return this.labyMod$getEntity().av();
    }
    
    @Intrinsic
    public boolean entity$isSprinting() {
        return this.shadow$aw();
    }
    
    @Intrinsic
    public boolean entity$isInvisible() {
        return this.shadow$ax();
    }
    
    @Override
    public boolean isInvisibleFor(final Player player) {
        return this.labyMod$getEntity().f((wn)player);
    }
    
    @Override
    public UUID getUniqueId() {
        return this.labyMod$getEntity().aK();
    }
    
    @Override
    public EntityPose entityPose() {
        final pk player = this.labyMod$getEntity();
        if (player.av()) {
            return EntityPose.CROUCHING;
        }
        if (player.I) {
            return EntityPose.DYING;
        }
        return EntityPose.STANDING;
    }
    
    @Override
    public boolean canEnterEntityPose(final EntityPose pose) {
        return true;
    }
    
    @Override
    public float getRotationYaw() {
        return this.y;
    }
    
    @Override
    public void setRotationYaw(final float rotationYaw) {
        this.y = rotationYaw;
    }
    
    @Override
    public float getPreviousRotationYaw() {
        return this.A;
    }
    
    @Override
    public void setPreviousRotationYaw(final float previousRotationYaw) {
        this.A = previousRotationYaw;
    }
    
    @Override
    public float getRotationPitch() {
        return this.z;
    }
    
    @Override
    public void setRotationPitch(final float rotationPitch) {
        this.z = rotationPitch;
    }
    
    @Override
    public float getPreviousRotationPitch() {
        return this.B;
    }
    
    @Override
    public void setPreviousRotationPitch(final float previousRotationPitch) {
        this.B = previousRotationPitch;
    }
    
    @Override
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return this.labyMod$boundingBox;
    }
    
    @Override
    public FloatVector3 perspectiveVector(final float partialTicks) {
        final aui look = this.d(partialTicks);
        return new FloatVector3((float)look.a, (float)look.b, (float)look.c);
    }
    
    @Intrinsic
    public float entity$getEyeHeight() {
        return this.aS();
    }
    
    @Insert(method = { "setEntityBoundingBox(Lnet/minecraft/util/AxisAlignedBB;)V" }, at = @At("TAIL"))
    private void labyMod$setAxisAlignedBoundingBox(final aug box, final InsertInfo info) {
        this.labyMod$boundingBox.set((float)box.a, (float)box.b, (float)box.c, (float)box.d, (float)box.e, (float)box.f);
    }
    
    private pk labyMod$getEntity() {
        return (pk)this;
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
    
    @Intrinsic
    public boolean entity$isInWater() {
        return this.shadow$V();
    }
    
    @Intrinsic
    public boolean entity$isInLava() {
        return this.shadow$ab();
    }
    
    @Override
    public Entity getVehicle() {
        return (Entity)this.m;
    }
    
    @Override
    public boolean isOnGround() {
        return this.C;
    }
    
    @Override
    public dn getForgeEntityData() {
        return null;
    }
    
    @Override
    public TagType nameTagType() {
        return this.labyMod$tagType;
    }
    
    @Override
    public void setNameTagType(final TagType type) {
        if (type == null) {
            this.labyMod$tagType = TagType.CUSTOM;
        }
        else {
            this.labyMod$tagType = type;
        }
    }
    
    @Override
    public boolean isUnderWater() {
        return this.a(arm.h);
    }
    
    @Override
    public boolean isOnFire() {
        return this.shadow$at();
    }
    
    @Inject(method = { "isEntityInsideOpaqueBlock" }, at = { @At("HEAD") })
    private void labyMod$getViewBlockingStateHeadSetup(final CallbackInfoReturnable<Boolean> cir) {
        if (!this.o.D) {
            return;
        }
        this.labymod$stack.push();
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.PRE));
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.POST));
        this.labymod$offset = this.labymod$stack.transformVector(0.0f, 0.0f, 0.0f, false);
        this.labymod$stack.pop();
    }
    
    @WrapOperation(method = { "isEntityInsideOpaqueBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/MathHelper;floor_double(D)I", ordinal = 1) })
    private int labyMod$getViewBlockingStateX(final double x, final Operation<Integer> original) {
        if (!this.o.D) {
            return (int)original.call(new Object[] { x });
        }
        return (int)original.call(new Object[] { x - this.labymod$offset.getX() });
    }
    
    @WrapOperation(method = { "isEntityInsideOpaqueBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/MathHelper;floor_double(D)I", ordinal = 0) })
    private int labyMod$getViewBlockingStateY(final double y, final Operation<Integer> original) {
        if (!this.o.D) {
            return (int)original.call(new Object[] { y });
        }
        return (int)original.call(new Object[] { y - this.labymod$offset.getY() });
    }
    
    @WrapOperation(method = { "isEntityInsideOpaqueBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/MathHelper;floor_double(D)I", ordinal = 2) })
    private int labyMod$getViewBlockingStateZ(final double z, final Operation<Integer> original) {
        if (!this.o.D) {
            return (int)original.call(new Object[] { z });
        }
        return (int)original.call(new Object[] { z - this.labymod$offset.getZ() });
    }
    
    @Override
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
