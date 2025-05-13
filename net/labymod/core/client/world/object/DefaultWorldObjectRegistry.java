// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.object;

import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.object.fog.FogSettings;
import java.util.Iterator;
import net.labymod.api.client.gfx.pipeline.Blaze3DFog;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.KeyValue;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.world.object.WorldObjectRegistry;
import net.labymod.api.client.world.object.WorldObject;
import net.labymod.api.service.DefaultRegistry;

@Singleton
@Implements(WorldObjectRegistry.class)
public class DefaultWorldObjectRegistry extends DefaultRegistry<WorldObject> implements WorldObjectRegistry
{
    private final RenderEnvironmentContext renderEnvironmentContext;
    private final IngameActivityOverlay activityOverlay;
    private WorldObjectOverlay overlay;
    
    public DefaultWorldObjectRegistry(final EventBus eventBus, final IngameActivityOverlay activityOverlay, final RenderEnvironmentContext renderEnvironmentContext) {
        this.renderEnvironmentContext = renderEnvironmentContext;
        this.activityOverlay = activityOverlay;
        eventBus.registerListener(this);
    }
    
    @Override
    protected void onRegister(final KeyValue<WorldObject> value) {
        ThreadSafe.ensureRenderThread();
        this.worldObjectOverlay().addObject(value.getValue());
    }
    
    @Override
    protected void onUnregister(final KeyValue<WorldObject> value) {
        ThreadSafe.ensureRenderThread();
        this.worldObjectOverlay().removeObject(value.getValue());
    }
    
    @Subscribe
    public void renderWorld(final RenderWorldEvent event) {
        if (this.getElements().isEmpty()) {
            return;
        }
        KeyValue<WorldObject> element = null;
        this.getElements().removeIf(element -> {
            final WorldObject obj = element.getValue();
            if (obj.shouldRemove()) {
                obj.onRemove();
                this.onUnregister(element);
                return true;
            }
            else {
                return false;
            }
        });
        final float partialTicks = event.getPartialTicks();
        final MinecraftCamera cam = event.camera();
        final DoubleVector3 camPos = cam.renderPosition();
        final int previousPackedLight = this.renderEnvironmentContext.getPackedLight();
        final GFXBridge bridge = Laby.gfx();
        bridge.storeBlaze3DStates();
        final Blaze3DFog blaze3DFog = bridge.blaze3DGlStatePipeline().blaze3DFog();
        final Iterator<KeyValue<WorldObject>> iterator = this.getElements().iterator();
        while (iterator.hasNext()) {
            element = iterator.next();
            final WorldObject object = element.getValue();
            if (!object.shouldRender()) {
                continue;
            }
            final FogSettings fogSettings = object.fogSettings();
            this.applyFogSettings(blaze3DFog, fogSettings);
            final DoubleVector3 prevLocation = object.previousPosition();
            final DoubleVector3 location = object.position();
            final double x = location.lerpX(prevLocation, partialTicks) - camPos.getX();
            final double y = location.lerpY(prevLocation, partialTicks) - camPos.getY() + 9.999999747378752E-5;
            final double z = location.lerpZ(prevLocation, partialTicks) - camPos.getZ();
            final Stack stack = event.stack();
            stack.push();
            stack.translate(x, y, z);
            stack.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            bridge.storeBlaze3DStates();
            bridge.enableBlend();
            bridge.defaultBlend();
            this.renderEnvironmentContext.setPackedLight(15728880);
            if (object.isSeeThrough()) {
                bridge.disableDepth();
                object.renderInWorld(cam, stack, x, y, z, partialTicks, true);
                bridge.enableDepth();
            }
            bridge.enableDepth();
            bridge.depthFunc(515);
            object.renderInWorld(cam, stack, x, y, z, partialTicks, false);
            stack.pop();
            bridge.restoreBlaze3DStates();
        }
        blaze3DFog.enable();
        this.renderEnvironmentContext.setPackedLight(previousPackedLight);
        bridge.restoreBlaze3DStates();
    }
    
    private void applyFogSettings(final Blaze3DFog fog, final FogSettings fogSettings) {
        if (fogSettings.isFog()) {
            fog.enable();
            fog.setStartDistance(fogSettings.getStartDistance());
            fog.setEndDistance(fogSettings.getEndDistance());
            final float[] colorBuffer = fogSettings.getColorBuffer();
            fog.setColor(colorBuffer[0], colorBuffer[1], colorBuffer[2], colorBuffer[3]);
        }
        else {
            fog.disable();
        }
    }
    
    private WorldObjectOverlay worldObjectOverlay() {
        if (this.overlay == null) {
            this.overlay = new WorldObjectOverlay();
            this.activityOverlay.registerActivity(this.overlay);
        }
        return this.overlay;
    }
}
