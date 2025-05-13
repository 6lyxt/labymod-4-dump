// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.Laby;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.client.world.ExtendedMinecraftCamera;

public final class ClientCamera implements ExtendedMinecraftCamera
{
    public static final ClientCamera INSTANCE;
    private float eyeHeightOld;
    private float eyeHeight;
    private final FloatVector3 positionEyes;
    private final FloatVector3 lookVector;
    private final FloatVector3 from;
    private final FloatVector3 to;
    private final FloatVector3 hitVector;
    private final FloatVector3 cameraPosition;
    private final FloatVector3 cameraRenderPosition;
    private final Quaternion rotation;
    private final DoubleVector3 position;
    private final DoubleVector3 renderPosition;
    private final DoubleVector3 fromD;
    private final DoubleVector3 toD;
    private final DoubleVector3 lookVectorD;
    private final DoubleVector3 hitVectorD;
    
    private ClientCamera() {
        this.positionEyes = new FloatVector3();
        this.lookVector = new FloatVector3();
        this.from = new FloatVector3();
        this.to = new FloatVector3();
        this.hitVector = new FloatVector3();
        this.cameraPosition = new FloatVector3();
        this.cameraRenderPosition = new FloatVector3();
        this.rotation = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
        this.position = new DoubleVector3();
        this.renderPosition = new DoubleVector3();
        this.fromD = new DoubleVector3();
        this.toD = new DoubleVector3();
        this.lookVectorD = new DoubleVector3();
        this.hitVectorD = new DoubleVector3();
        Laby.references().eventBus().registerListener(this);
    }
    
    @Subscribe
    public void onTick(@NotNull final GameTickEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        final pk renderViewEntity = ave.A().ac();
        if (renderViewEntity == null) {
            return;
        }
        this.eyeHeightOld = this.eyeHeight;
        this.eyeHeight += (renderViewEntity.aS() - this.eyeHeight) * 0.5f;
    }
    
    public float getEyeHeight() {
        return MathHelper.lerp(this.eyeHeight, this.eyeHeightOld, Laby.labyAPI().minecraft().getPartialTicks());
    }
    
    @NotNull
    @Override
    public FloatVector3 deprecated$position() {
        final pk renderEntity = ave.A().ac();
        if (renderEntity == null) {
            this.positionEyes.set(0.0f, 0.0f, 0.0f);
            return this.positionEyes;
        }
        final aui eyesVec = renderEntity.e(1.0f);
        this.positionEyes.set((float)eyesVec.a, (float)eyesVec.b, (float)eyesVec.c);
        final aui lookVec = renderEntity.ap();
        this.lookVector.set((float)lookVec.a, (float)lookVec.b, (float)lookVec.c);
        final Perspective perspective = Laby.labyAPI().minecraft().options().perspective();
        if (perspective == Perspective.FIRST_PERSON) {
            return this.positionEyes;
        }
        double distance = 4.0;
        final boolean frontView = perspective == Perspective.THIRD_PERSON_FRONT;
        final double maxX = this.positionEyes.getX() - this.lookVector.getX() * distance * (frontView ? -1 : 1);
        final double maxY = this.positionEyes.getY() - this.lookVector.getY() * distance * (frontView ? -1 : 1);
        final double maxZ = this.positionEyes.getZ() - this.lookVector.getZ() * distance * (frontView ? -1 : 1);
        for (int i = 0; i < 8; ++i) {
            final float offsetX = ((i & 0x1) * 2 - 1) * 0.1f;
            final float offsetY = ((i >> 1 & 0x1) * 2 - 1) * 0.1f;
            final float offsetZ = ((i >> 2 & 0x1) * 2 - 1) * 0.1f;
            this.from.set(this.positionEyes);
            this.to.set((float)maxX, (float)maxY, (float)maxZ);
            this.from.add(offsetX, offsetY, offsetZ);
            this.to.add(offsetX, offsetY, offsetZ);
            final auh target = ave.A().f.a(new aui((double)this.from.getX(), (double)this.from.getY(), (double)this.from.getZ()), new aui((double)this.to.getX(), (double)this.to.getY(), (double)this.to.getZ()));
            if (target != null) {
                this.hitVector.set((float)target.c.a, (float)target.c.b, (float)target.c.c);
                final double distanceToCollision = this.hitVector.distance(this.positionEyes);
                if (distanceToCollision < distance) {
                    distance = distanceToCollision;
                }
            }
        }
        this.cameraPosition.set((float)(this.positionEyes.getX() - this.lookVector.getX() * distance * (frontView ? -1 : 1)), (float)(this.positionEyes.getY() - this.lookVector.getY() * distance * (frontView ? -1 : 1)), (float)(this.positionEyes.getZ() - this.lookVector.getZ() * distance * (frontView ? -1 : 1)));
        return this.cameraPosition;
    }
    
    @NotNull
    @Override
    public FloatVector3 deprecated$renderPosition() {
        final pk renderEntity = ave.A().ac();
        if (renderEntity == null) {
            this.positionEyes.set(0.0f, 0.0f, 0.0f);
            return this.positionEyes;
        }
        final float partialTicks = Laby.labyAPI().minecraft().getPartialTicks();
        this.cameraRenderPosition.set((float)MathHelper.lerp(renderEntity.s, renderEntity.p, partialTicks), (float)MathHelper.lerp(renderEntity.t, renderEntity.q, partialTicks), (float)MathHelper.lerp(renderEntity.u, renderEntity.r, partialTicks));
        return this.cameraRenderPosition;
    }
    
    @NotNull
    @Override
    public DoubleVector3 position() {
        final pk renderEntity = ave.A().ac();
        if (renderEntity == null) {
            this.position.set(0.0, 0.0, 0.0);
            return this.position;
        }
        final aui eyesVec = renderEntity.e(1.0f);
        this.position.set(eyesVec.a, eyesVec.b, eyesVec.c);
        final aui lookVec = renderEntity.ap();
        this.lookVectorD.set((float)lookVec.a, (float)lookVec.b, (float)lookVec.c);
        final Perspective perspective = Laby.labyAPI().minecraft().options().perspective();
        if (perspective == Perspective.FIRST_PERSON) {
            return this.position;
        }
        double distance = 4.0;
        final boolean frontView = perspective == Perspective.THIRD_PERSON_FRONT;
        final double maxX = this.position.getX() - this.lookVectorD.getX() * distance * (frontView ? -1 : 1);
        final double maxY = this.position.getY() - this.lookVectorD.getY() * distance * (frontView ? -1 : 1);
        final double maxZ = this.position.getZ() - this.lookVectorD.getZ() * distance * (frontView ? -1 : 1);
        for (int i = 0; i < 8; ++i) {
            final float offsetX = ((i & 0x1) * 2 - 1) * 0.1f;
            final float offsetY = ((i >> 1 & 0x1) * 2 - 1) * 0.1f;
            final float offsetZ = ((i >> 2 & 0x1) * 2 - 1) * 0.1f;
            this.fromD.set(this.position);
            this.toD.set(maxX, maxY, maxZ);
            this.fromD.add(offsetX, offsetY, offsetZ);
            this.toD.add(offsetX, offsetY, offsetZ);
            final auh target = ave.A().f.a(new aui(this.fromD.getX(), this.fromD.getY(), this.fromD.getZ()), new aui(this.toD.getX(), this.toD.getY(), this.toD.getZ()));
            if (target != null) {
                this.hitVectorD.set(target.c.a, target.c.b, target.c.c);
                final double distanceToCollision = this.hitVectorD.distance(this.position);
                if (distanceToCollision < distance) {
                    distance = distanceToCollision;
                }
            }
        }
        this.position.set(this.position.getX() - this.lookVectorD.getX() * distance * (frontView ? -1 : 1), this.position.getY() - this.lookVectorD.getY() * distance * (frontView ? -1 : 1), this.position.getZ() - this.lookVectorD.getZ() * distance * (frontView ? -1 : 1));
        return this.position;
    }
    
    @NotNull
    @Override
    public DoubleVector3 renderPosition() {
        final pk renderEntity = ave.A().ac();
        if (renderEntity == null) {
            this.renderPosition.set(0.0, 0.0, 0.0);
            return this.renderPosition;
        }
        final float partialTicks = Laby.labyAPI().minecraft().getPartialTicks();
        this.renderPosition.set(MathHelper.lerp(renderEntity.s, renderEntity.p, partialTicks), MathHelper.lerp(renderEntity.t, renderEntity.q, partialTicks), MathHelper.lerp(renderEntity.u, renderEntity.r, partialTicks));
        return this.renderPosition;
    }
    
    @NotNull
    @Override
    public Quaternion rotation() {
        final biu renderManager = ave.A().af();
        final Perspective perspective = Laby.labyAPI().minecraft().options().perspective();
        final float modifier = (perspective == Perspective.THIRD_PERSON_FRONT) ? -1.0f : 1.0f;
        this.rotation.set(0.0f, 0.0f, 0.0f, 1.0f);
        this.rotation.multiply(FloatVector3.YP.rotationDegrees(-renderManager.e));
        this.rotation.multiply(FloatVector3.XP.rotationDegrees(renderManager.f * modifier));
        return this.rotation;
    }
    
    @Override
    public float getEyeHeight(final float partialTicks) {
        final Entity entity = Laby.labyAPI().minecraft().getCameraEntity();
        return (entity == null) ? 0.0f : entity.aS();
    }
    
    static {
        INSTANCE = new ClientCamera();
    }
}
