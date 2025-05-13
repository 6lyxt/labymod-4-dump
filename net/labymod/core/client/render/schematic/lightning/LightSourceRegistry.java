// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.lightning;

import java.util.function.Function;
import net.labymod.api.client.gfx.shader.uniform.Uniform1FArray;
import net.labymod.api.client.gfx.shader.uniform.UniformFloatVector3Array;
import net.labymod.api.client.render.matrix.Stack;
import java.util.Iterator;
import java.util.Comparator;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.math.vector.FloatVector3;
import java.util.List;

public final class LightSourceRegistry
{
    public static final int MAX_LIGHTS = 48;
    private static LightSourceRegistry instance;
    private final List<LightSource> lightSources;
    private final FloatVector3 previousCameraPosition;
    private final ConfigProperty<Boolean> coloredLightSetting;
    
    public LightSourceRegistry() {
        this.previousCameraPosition = new FloatVector3();
        this.lightSources = new ArrayList<LightSource>();
        this.coloredLightSetting = Laby.labyAPI().config().appearance().dynamicBackground().coloredLight();
    }
    
    public static LightSourceRegistry getInstance() {
        if (LightSourceRegistry.instance == null) {
            LightSourceRegistry.instance = new LightSourceRegistry();
        }
        return LightSourceRegistry.instance;
    }
    
    public void sort(final FloatVector3 cameraPosition) {
        if (this.previousCameraPosition.equals(cameraPosition)) {
            return;
        }
        this.previousCameraPosition.set(cameraPosition);
        this.lightSources.sort(Comparator.comparingDouble(value -> {
            final FloatVector3 position = value.getPosition();
            final float horizontalDistance = MathHelper.square(position.getX() - cameraPosition.getX()) + MathHelper.square(position.getZ() - cameraPosition.getZ());
            final float verticalDistance = MathHelper.square(position.getY() - cameraPosition.getY());
            return (double)(verticalDistance * 100.0f + horizontalDistance);
        }));
    }
    
    public void addLightSource(final LightSource lightSource) {
        if (this.lightSources.contains(lightSource)) {
            return;
        }
        this.lightSources.add(lightSource);
        this.previousCameraPosition.set(0.0f, 0.0f, 0.0f);
    }
    
    public void remove(final PointLightSource source) {
        this.lightSources.remove(source);
    }
    
    public void reset() {
        this.lightSources.clear();
        this.previousCameraPosition.set(0.0f, 0.0f, 0.0f);
    }
    
    public void tick() {
        for (final LightSource lightSource : this.lightSources) {
            lightSource.tick();
        }
    }
    
    public void render(final Stack stack, final float partialTicks) {
    }
    
    public void uploadPointLights(final UniformFloatVector3Array lightSourcePosition, final UniformFloatVector3Array lightSourceColor, final Uniform1FArray lightSourceConstant, final Uniform1FArray lightSourceLinear, final Uniform1FArray lightSourceQuadratic) {
        this.upload(lightSourcePosition, PointLightSource::getPosition);
        this.upload(lightSourceColor, pointLightSource -> this.coloredLightSetting.get() ? pointLightSource.getColor() : PointLightSource.WHITE);
        this.upload(lightSourceConstant, PointLightSource::getConstant);
        this.upload(lightSourceLinear, PointLightSource::getLinear);
        this.upload(lightSourceQuadratic, PointLightSource::getQuadratic);
    }
    
    private <T extends LightSource> void upload(final Uniform1FArray uniform, final Object2FloatFunction<T> mapper) {
        for (int i = 0; i < this.lightSources.size() && i < 48; ++i) {
            uniform.update(i, mapper.apply((T)this.lightSources.get(i)));
        }
    }
    
    private <T extends LightSource> void upload(final UniformFloatVector3Array uniform, final Function<T, FloatVector3> mapper) {
        for (int i = 0; i < this.lightSources.size() && i < 48; ++i) {
            uniform.update(i, mapper.apply((T)this.lightSources.get(i)));
        }
    }
    
    @FunctionalInterface
    private interface Object2FloatFunction<K>
    {
        float apply(final K p0);
    }
}
