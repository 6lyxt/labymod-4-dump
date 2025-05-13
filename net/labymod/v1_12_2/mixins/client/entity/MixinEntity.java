// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.entity;

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
import net.labymod.api.client.render.matrix.Stack;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.Lazy;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.client.entity.datawatcher.DataWatcher;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.forge.entity.ForgeEntity;
import net.labymod.api.client.entity.Entity;

@Mixin({ vg.class })
@Implements({ @Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE) })
public abstract class MixinEntity implements Entity, ForgeEntity
{
    private AxisAlignedBoundingBox labyMod$boundingBox;
    @Unique
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
    public double p;
    @Shadow
    public double q;
    @Shadow
    public double r;
    @Shadow
    public double o;
    @Shadow
    public double n;
    @Shadow
    public double m;
    @Shadow
    private bhb av;
    @Shadow
    public float v;
    @Shadow
    public float x;
    @Shadow
    public boolean z;
    @Shadow
    public vg au;
    @Shadow
    public float w;
    @Shadow
    private int h;
    @Shadow
    public float y;
    @Shadow
    public amu l;
    private final Position labyMod$position;
    private final Position labyMod$previousPosition;
    
    public MixinEntity() {
        this.labyMod$dataWatcher = new DefaultDataWatcher();
        this.labyMod$entityId = Lazy.of(() -> LegacyEntityTypeRegistry.getId(this.getClass()));
        this.labyMod$tagType = TagType.CUSTOM;
        this.labymod$stack = Stack.create(new DynamicStackProvider());
        this.labymod$offset = FloatVector3.ZERO;
        this.labyMod$position = new DynamicPosition(() -> this.p, x -> this.p = x, () -> this.q, y -> this.q = y, () -> this.r, z -> this.r = z);
        this.labyMod$previousPosition = new DynamicPosition(() -> this.m, x -> this.m = x, () -> this.n, y -> this.n = y, () -> this.o, z -> this.o = z);
    }
    
    @Shadow
    public abstract boolean shadow$aV();
    
    @Shadow
    public abstract boolean shadow$aX();
    
    @Shadow
    public abstract float by();
    
    @Shadow
    public abstract boolean shadow$ao();
    
    @Shadow
    public abstract boolean shadow$au();
    
    @Shadow
    public abstract boolean shadow$aR();
    
    @Shadow
    public abstract boolean a(final bcz p0);
    
    @Shadow
    public abstract bhe e(final float p0);
    
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
        return this.labyMod$getEntity().aU();
    }
    
    @Intrinsic
    public boolean entity$isSprinting() {
        return this.shadow$aV();
    }
    
    @Intrinsic
    public boolean entity$isInvisible() {
        return this.shadow$aX();
    }
    
    @Override
    public boolean isInvisibleFor(final Player player) {
        return this.labyMod$getEntity().e((aed)player);
    }
    
    @Override
    public UUID getUniqueId() {
        return this.labyMod$getEntity().bm();
    }
    
    @Override
    public EntityPose entityPose() {
        final vg player = this.labyMod$getEntity();
        if (player.aU()) {
            return EntityPose.CROUCHING;
        }
        if (player.F) {
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
        return this.v;
    }
    
    @Override
    public void setRotationYaw(final float rotationYaw) {
        this.v = rotationYaw;
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
        return this.w;
    }
    
    @Override
    public void setRotationPitch(final float rotationPitch) {
        this.w = rotationPitch;
    }
    
    @Override
    public float getPreviousRotationPitch() {
        return this.y;
    }
    
    @Override
    public void setPreviousRotationPitch(final float previousRotationPitch) {
        this.y = previousRotationPitch;
    }
    
    @Override
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return this.labyMod$boundingBox;
    }
    
    @Override
    public FloatVector3 perspectiveVector(final float partialTicks) {
        final bhe look = this.e(partialTicks);
        return new FloatVector3((float)look.b, (float)look.c, (float)look.d);
    }
    
    @Intrinsic
    public float entity$getEyeHeight() {
        return this.by();
    }
    
    @Insert(method = { "setEntityBoundingBox" }, at = @At("TAIL"))
    private void labyMod$setAxisAlignedBoundingBox(final bhb box, final InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float)box.a, (float)box.b, (float)box.c, (float)box.d, (float)box.e, (float)box.f);
    }
    
    private vg labyMod$getEntity() {
        return (vg)this;
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
        return this.shadow$ao();
    }
    
    @Intrinsic
    public boolean entity$isInLava() {
        return this.shadow$au();
    }
    
    @Override
    public Entity getVehicle() {
        return (Entity)this.au;
    }
    
    @Override
    public boolean isOnGround() {
        return this.z;
    }
    
    @Override
    public fy getForgeEntityData() {
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
        return this.a(bcz.h);
    }
    
    @Override
    public boolean isOnFire() {
        return this.shadow$aR();
    }
    
    @Inject(method = { "isEntityInsideOpaqueBlock" }, at = { @At("HEAD") })
    private void labyMod$getViewBlockingStateHeadSetup(final CallbackInfoReturnable<Boolean> cir) {
        if (!this.l.G) {
            return;
        }
        this.labymod$stack.push();
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.PRE));
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.POST));
        this.labymod$offset = this.labymod$stack.transformVector(0.0f, 0.0f, 0.0f, false);
        this.labymod$stack.pop();
    }
    
    @WrapOperation(method = { "isEntityInsideOpaqueBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;floor(D)I", ordinal = 1) })
    private int labyMod$getViewBlockingStateX(final double x, final Operation<Integer> original) {
        if (!this.l.G) {
            return (int)original.call(new Object[] { x });
        }
        return (int)original.call(new Object[] { x - this.labymod$offset.getX() });
    }
    
    @WrapOperation(method = { "isEntityInsideOpaqueBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;floor(D)I", ordinal = 0) })
    private int labyMod$getViewBlockingStateY(final double y, final Operation<Integer> original) {
        if (!this.l.G) {
            return (int)original.call(new Object[] { y });
        }
        return rk.c(y - this.labymod$offset.getY());
    }
    
    @WrapOperation(method = { "isEntityInsideOpaqueBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;floor(D)I", ordinal = 2) })
    private int labyMod$getViewBlockingStateZ(final double z, final Operation<Integer> original) {
        if (!this.l.G) {
            return (int)original.call(new Object[] { z });
        }
        return (int)original.call(new Object[] { z - this.labymod$offset.getZ() });
    }
    
    @Override
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
