// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.camera;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.DoubleVector3;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.labymod.api.Laby;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.core.util.camera.spline.position.Location;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.core.util.camera.spline.Spline;
import net.labymod.api.client.world.MinecraftCamera;

public class CinematicCamera implements MinecraftCamera
{
    private final Spline spline;
    private final FloatMatrix4 projectionMatrix;
    private final FloatMatrix4 viewMatrix;
    private final FloatMatrix4 previousProjectionMatrix;
    private final FloatMatrix4 previousViewMatrix;
    private final Int2ObjectMap<Location> positionModifiers;
    private final float fov;
    private final float near;
    private final float far;
    private CubicBezier cubicBezier;
    private long timePassed;
    private long duration;
    
    public CinematicCamera(final float fov) {
        this(fov, 0.05f, 1024.0f);
    }
    
    public CinematicCamera(final float fov, final float near, final float far) {
        this.spline = new Spline();
        this.projectionMatrix = FloatMatrix4.newIdentity();
        this.viewMatrix = FloatMatrix4.newIdentity();
        this.previousProjectionMatrix = FloatMatrix4.newIdentity();
        this.previousViewMatrix = FloatMatrix4.newIdentity();
        this.positionModifiers = (Int2ObjectMap<Location>)new Int2ObjectOpenHashMap();
        this.fov = fov;
        this.near = near;
        this.far = far;
    }
    
    public void teleport(final Location position) {
        this.moveTo(0L, CubicBezier.LINEAR, position);
    }
    
    public void restart() {
        this.timePassed = 0L;
    }
    
    public void moveTo(final long duration, final CubicBezier cubicBezier, final Location... keyframes) {
        final Location source = this.location();
        this.spline.reset();
        this.spline.add(source);
        for (final Location position : keyframes) {
            this.spline.add(position);
        }
        this.spline.calculate();
        this.timePassed = 0L;
        this.duration = duration;
        this.cubicBezier = cubicBezier;
    }
    
    public void setup(final float left, final float top, final float right, final float bottom, final float partialTicks) {
        this.previousViewMatrix.set(this.viewMatrix);
        this.viewMatrix.identity();
        final Location location = this.location();
        for (final Location modification : this.positionModifiers.values()) {
            this.modifyViewMatrixRotationRoll(this.viewMatrix, modification);
        }
        this.modifyViewMatrixRotationRoll(this.viewMatrix, location);
        for (final Location modification : this.positionModifiers.values()) {
            this.modifyViewMatrixRotationPitch(this.viewMatrix, modification);
        }
        this.modifyViewMatrixRotationPitch(this.viewMatrix, location);
        for (final Location modification : this.positionModifiers.values()) {
            this.modifyViewMatrixRotationYaw(this.viewMatrix, modification, false);
        }
        this.modifyViewMatrixRotationYaw(this.viewMatrix, location, true);
        for (final Location modification : this.positionModifiers.values()) {
            this.modifyViewMatrixPosition(this.viewMatrix, modification, false);
        }
        this.modifyViewMatrixPosition(this.viewMatrix, location, true);
        final float scale = (right - left) / (bottom - top);
        this.previousProjectionMatrix.set(this.projectionMatrix);
        this.projectionMatrix.setPerspective(this.fov, scale, this.near, this.far);
        final GFXRenderPipeline pipeline = Laby.references().gfxRenderPipeline();
        pipeline.setProjectionMatrix(this.projectionMatrix);
        pipeline.setModelViewMatrix(FloatMatrix4.createTranslateMatrix(0.0f, 0.0f, getZLevel()));
    }
    
    private void modifyViewMatrixPosition(final FloatMatrix4 viewMatrix, final Location position, final boolean main) {
        float x = (float)position.getX();
        float y = (float)position.getY();
        float z = (float)position.getZ();
        if (main) {
            x *= -1.0f;
            y *= -1.0f;
            z *= -1.0f;
        }
        viewMatrix.multiplyWithTranslation(x, y, z);
    }
    
    private void modifyViewMatrixRotation(final FloatMatrix4 viewMatrix, final Location position, final boolean main) {
        final float roll = (float)position.getRoll();
        final float pitch = (float)position.getPitch();
        final float yaw = (float)position.getYaw();
        viewMatrix.multiply(FloatVector3.ZP.rotationDegrees(roll));
        viewMatrix.multiply(FloatVector3.XP.rotationDegrees(pitch));
        viewMatrix.multiply(FloatVector3.YP.rotationDegrees(yaw));
        if (main) {
            viewMatrix.multiply(FloatVector3.YP.rotationDegrees(180.0f));
        }
    }
    
    private void modifyViewMatrixRotationYaw(final FloatMatrix4 viewMatrix, final Location position, final boolean main) {
        viewMatrix.multiply(FloatVector3.YP.rotationDegrees((float)position.getYaw()));
        if (main) {
            viewMatrix.multiply(FloatVector3.YP.rotationDegrees(180.0f));
        }
    }
    
    private void modifyViewMatrixRotationPitch(final FloatMatrix4 viewMatrix, final Location position) {
        viewMatrix.multiply(FloatVector3.XP.rotationDegrees((float)position.getPitch()));
    }
    
    private void modifyViewMatrixRotationRoll(final FloatMatrix4 viewMatrix, final Location position) {
        viewMatrix.multiply(FloatVector3.ZP.rotationDegrees((float)position.getRoll()));
    }
    
    public void update(final float left, final float top, final float right, final float bottom, final float tickDelta) {
        this.timePassed += (long)TimeUtil.convertDeltaToMilliseconds(tickDelta);
    }
    
    public FloatMatrix4 viewMatrix() {
        return this.viewMatrix;
    }
    
    public FloatMatrix4 previousViewMatrix() {
        return this.previousViewMatrix;
    }
    
    public FloatMatrix4 projectionMatrix() {
        return this.projectionMatrix;
    }
    
    public FloatMatrix4 previousProjectionMatrix() {
        return this.previousProjectionMatrix;
    }
    
    public Location location() {
        return this.spline.isValid() ? this.spline.get(this.getProgress()) : new Location();
    }
    
    public Location positionModifier(final int channel) {
        Location location = (Location)this.positionModifiers.get(channel);
        if (location == null) {
            location = new Location();
            this.positionModifiers.put(channel, (Object)location);
        }
        return location;
    }
    
    @NotNull
    @Override
    public FloatVector3 deprecated$position() {
        return new FloatVector3(this.location().position());
    }
    
    @NotNull
    @Override
    public DoubleVector3 position() {
        return this.location().position();
    }
    
    @NotNull
    @Override
    public Quaternion rotation() {
        return this.location().rotation();
    }
    
    public float getProgress() {
        final float progress = (this.duration == 0L || this.timePassed >= this.duration) ? 1.0f : (this.timePassed / (float)this.duration);
        return (float)this.cubicBezier.curve(progress);
    }
    
    public static float getZLevel() {
        if (MinecraftVersions.V1_21_2_pre2.orNewer()) {
            return 11000.0f;
        }
        if (MinecraftVersions.V1_21_2_pre1.orNewer()) {
            return 10000.0f;
        }
        if (MinecraftVersions.V1_20_pre3.orNewer()) {
            return 11000.0f;
        }
        if (MinecraftVersions.V1_13.orNewer()) {
            return 2000.0f;
        }
        return 0.0f;
    }
}
