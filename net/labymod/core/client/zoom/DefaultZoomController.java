// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.zoom;

import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.RenderHandEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.zoom.ZoomTransitionType;
import net.labymod.api.event.client.entity.player.FieldOfViewTickEvent;
import net.labymod.api.event.client.entity.player.ClientHotbarSlotChangeEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.FieldOfViewModifierEvent;
import net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomTransitionConfig;
import javax.inject.Inject;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.screen.key.HotkeyService;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.laby.ingame.ZoomConfig;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.zoom.ZoomController;

@Singleton
@Implements(ZoomController.class)
public class DefaultZoomController implements ZoomController
{
    private final LabyAPI labyAPI;
    private final ZoomConfig settings;
    private boolean zoomActive;
    private int lastTickZoomChange;
    private boolean cameraModified;
    private boolean cinematicCameraModified;
    private int currentTicks;
    private float distanceScrollOffset;
    private boolean hadCinematicCameraActive;
    
    @Inject
    public DefaultZoomController(final LabyAPI labyAPI) {
        this.zoomActive = false;
        this.lastTickZoomChange = -1;
        this.cameraModified = false;
        this.cinematicCameraModified = false;
        this.currentTicks = 0;
        this.distanceScrollOffset = 0.0f;
        this.hadCinematicCameraActive = false;
        this.labyAPI = labyAPI;
        this.settings = labyAPI.config().ingame().zoom();
        labyAPI.eventBus().registerListener(this);
        Laby.references().hotkeyService().register("zoom", this.settings.zoomKey(), () -> this.settings.zoomHold().get() ? HotkeyService.Type.HOLD : HotkeyService.Type.TOGGLE, this::updateZoomState);
    }
    
    private void updateZoomState(final boolean active) {
        if (this.zoomActive == active) {
            return;
        }
        this.zoomActive = active;
        final ZoomTransitionConfig transitionConfig = this.settings.zoomTransition();
        final int ticksElapsed = this.currentTicks - this.lastTickZoomChange;
        final int durationTicks = Math.toIntExact(this.zoomActive ? transitionConfig.zoomInDuration().get() : transitionConfig.zoomOutDuration().get()) / 50;
        this.lastTickZoomChange = ((transitionConfig.enabled().get() && ticksElapsed < durationTicks) ? (this.currentTicks - (durationTicks - ticksElapsed)) : this.currentTicks);
        this.cameraModified = true;
        if (this.settings.zoomCinematic().enabled().get()) {
            this.cinematicCameraModified = true;
            this.labyAPI.minecraft().options().setSmoothCamera(true);
        }
    }
    
    @Subscribe
    public void onFieldOfViewModifier(final FieldOfViewModifierEvent event) {
        if (this.zoomActive) {
            final double distance = this.labyAPI.config().ingame().zoom().zoomDistance().get() + this.distanceScrollOffset;
            final float modifiedFov = event.getFieldOfView() / (float)distance;
            event.setFieldOfView(modifiedFov);
        }
        else if (this.cameraModified) {
            final float progress = this.getTransitionProgress();
            if (progress == 0.0f) {
                this.cameraModified = false;
                this.labyAPI.minecraft().requestChunkUpdate();
            }
        }
        if (!this.zoomActive && (!this.cameraModified || !this.settings.zoomCinematic().disableAfterTransition().get())) {
            if (this.cinematicCameraModified) {
                this.cinematicCameraModified = false;
                this.labyAPI.minecraft().options().setSmoothCamera(this.hadCinematicCameraActive);
            }
            this.hadCinematicCameraActive = this.labyAPI.minecraft().options().isSmoothCamera();
        }
    }
    
    @Subscribe
    public void onScroll(final ClientHotbarSlotChangeEvent event) {
        if ((!this.zoomActive && !this.cameraModified) || !this.settings.scrollToZoom().get()) {
            return;
        }
        if (event.delta() != 0.0f) {
            event.setCancelled(true);
            final float scrollSpeed = (this.distanceScrollOffset <= -1.5f) ? 0.05f : 0.25f;
            final float scrollDelta = scrollSpeed * event.delta();
            final float vanillaFov = (float)this.labyAPI.minecraft().options().getFov();
            final double zoomDistance = this.labyAPI.config().ingame().zoom().zoomDistance().get();
            final double distance = zoomDistance + this.distanceScrollOffset + scrollDelta;
            final float modifier = 1.0f - 1.0f / (float)distance;
            final float fov = vanillaFov * (1.0f - modifier);
            if (fov >= 10.0f && fov <= 130.0f) {
                this.distanceScrollOffset = (float)(distance - zoomDistance);
            }
        }
    }
    
    @Subscribe
    public void onFieldOfViewTick(final FieldOfViewTickEvent event) {
        this.currentTicks = event.getTick();
        if (!this.zoomActive && !this.cameraModified) {
            this.distanceScrollOffset = 0.0f;
            return;
        }
        final ZoomTransitionConfig transitionConfig = this.settings.zoomTransition();
        final ConfigProperty<ZoomTransitionType> transition = this.zoomActive ? transitionConfig.zoomInType() : transitionConfig.zoomOutType();
        final boolean hasTransition = transitionConfig.enabled().get() && transition.get() != ZoomTransitionType.NO_TRANSITION;
        if (!this.zoomActive && !hasTransition) {
            this.cameraModified = false;
            event.setFov(event.getModifier());
            return;
        }
        CubicBezier cubicBezier = transition.get().getCubicBezier();
        if (cubicBezier == null || !hasTransition) {
            cubicBezier = CubicBezier.LINEAR;
        }
        final double zoomDistance = this.labyAPI.config().ingame().zoom().zoomDistance().get();
        final double distance = zoomDistance + this.distanceScrollOffset;
        float progress = 1.0f;
        if (hasTransition) {
            progress = this.getTransitionProgress();
        }
        float modifier = (float)((1.0 - 1.0 / distance) * cubicBezier.curve(progress));
        final float vanillaFov = (float)this.labyAPI.minecraft().options().getFov();
        final float fov = vanillaFov * (1.0f - modifier);
        final float clampedFov = MathHelper.clamp(fov, 10.0f, 130.0f);
        modifier = 1.0f - clampedFov / vanillaFov;
        if (hasTransition) {
            event.setOldFov(event.getFov());
        }
        else {
            event.setModifier(modifier);
            event.setOldFov(1.0f - modifier);
        }
        event.setFov(1.0f - modifier);
        event.setOverwriteVanilla(true);
    }
    
    @Subscribe
    public void onPreRenderHand(final RenderHandEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        if ((this.zoomActive || this.cameraModified) && !this.settings.shouldRenderFirstPersonHand().get()) {
            event.setCancelled(true);
        }
    }
    
    private float getTransitionProgress() {
        final int ticksElapsed = this.currentTicks - this.lastTickZoomChange;
        final ZoomTransitionConfig transitionConfig = this.settings.zoomTransition();
        final int durationTicks = Math.toIntExact(this.zoomActive ? transitionConfig.zoomInDuration().get() : transitionConfig.zoomOutDuration().get()) / 50;
        if (!transitionConfig.enabled().get() || durationTicks == 0) {
            return this.zoomActive ? 0.0f : 1.0f;
        }
        if (ticksElapsed > durationTicks) {
            return this.zoomActive ? 1.0f : 0.0f;
        }
        final float progress = ticksElapsed / (float)durationTicks;
        return this.zoomActive ? progress : (1.0f - progress);
    }
    
    @Override
    public boolean isZoomActive() {
        return this.zoomActive || this.cameraModified;
    }
}
