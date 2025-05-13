// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.title;

import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.Laby;
import net.labymod.api.event.client.gui.screen.title.TitleScreenRenderEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.core.client.gui.screen.widget.widgets.title.header.MinecraftLogoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.title.ui.MainMenuButtonsWidget;
import net.labymod.core.client.gui.screen.widget.widgets.title.background.PanoramaWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.client.gui.screen.widget.widgets.title.overlay.MainOverlayWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class MainMenuWidget extends AbstractWidget<Widget>
{
    private final AbstractBootLogoRenderer logo;
    private final DynamicBackgroundController background;
    private final boolean fadeIn;
    private long timeFadeStart;
    private DivWidget opacityGroupWidget;
    private MainOverlayWidget overlayWidget;
    
    public MainMenuWidget(final AbstractBootLogoRenderer logo, final DynamicBackgroundController background, final boolean fadeIn) {
        this.logo = logo;
        this.background = background;
        this.fadeIn = fadeIn;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final boolean dynamicBackgroundEnabled = DynamicBackgroundController.isEnabled();
        if (!dynamicBackgroundEnabled) {
            final PanoramaWidget panoramaWidget = new PanoramaWidget();
            panoramaWidget.addId("panorama");
            ((AbstractWidget<PanoramaWidget>)this).addChild(panoramaWidget);
        }
        (this.opacityGroupWidget = new DivWidget()).addId("ui");
        final MainMenuButtonsWidget buttonsWidget = new MainMenuButtonsWidget();
        buttonsWidget.addId("buttons");
        ((AbstractWidget<MainMenuButtonsWidget>)this.opacityGroupWidget).addChild(buttonsWidget);
        final MinecraftLogoWidget logoWidget = MinecraftLogoWidget.create();
        logoWidget.addId("logo");
        ((AbstractWidget<MinecraftLogoWidget>)this.opacityGroupWidget).addChild(logoWidget);
        (this.overlayWidget = new MainOverlayWidget()).addId("overlay");
        ((AbstractWidget<MainOverlayWidget>)this.opacityGroupWidget).addChild(this.overlayWidget);
        ((AbstractWidget<DivWidget>)this).addChild(this.opacityGroupWidget);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        final Bounds bounds = this.bounds();
        final float left = bounds.getLeft();
        final float top = bounds.getTop();
        final float right = bounds.getRight();
        final float bottom = bounds.getBottom();
        if (!DynamicBackgroundController.isEnabled()) {
            if (this.fadeIn) {
                this.updateFadeIn();
            }
            super.renderWidget(context);
            return;
        }
        final Stack stack = context.stack();
        final float tickDelta = context.getTickDelta();
        this.logo.renderBackground(stack, left, top, right, bottom, tickDelta);
        this.background.renderTick(stack, left, top, right, bottom, tickDelta);
        if (this.background.isLoaded() && this.background.isOpeningPlayed() && this.background.isInTransition()) {
            this.background.renderUIInWorld(stack, left, top, right, bottom, () -> this.logo.renderForeground(stack, left, top, right, bottom, tickDelta, false));
        }
        else {
            this.logo.renderForeground(stack, left, top, right, bottom, tickDelta, true);
        }
        this.background.render(stack, left, top, right, bottom, tickDelta);
        if (this.background.isOpeningPlayed()) {
            this.transformUI(stack, left, top, right, bottom, () -> {
                stack.push();
                stack.translate(0.0f, 0.0f, 10.0f);
                final MutableMouse mouse = context.mouse();
                Laby.fireEvent(new TitleScreenRenderEvent(Phase.PRE, stack, mouse, tickDelta));
                super.renderWidget(context);
                Laby.fireEvent(new TitleScreenRenderEvent(Phase.POST, stack, mouse, tickDelta));
                stack.pop();
            });
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return (DynamicBackgroundController.isEnabled() && this.background.isLoaded() && this.background.isOpeningPlayed() && this.background.isOpeningPlaying() && this.background.isInTransition() && this.background.getTransitionProgress() < 0.6) || super.mouseClicked(mouse, mouseButton);
    }
    
    private void updateFadeIn() {
        if (this.timeFadeStart == 0L && this.fadeIn) {
            this.timeFadeStart = TimeUtil.getMillis();
        }
        final float timePassed = (TimeUtil.getMillis() - this.timeFadeStart) / 1000.0f;
        final float opacity = MathHelper.clamp(timePassed - 1.0f, 0.0f, 1.0f);
        this.opacityGroupWidget.opacity().set(opacity);
    }
    
    private void transformUI(final Stack stack, final float left, final float top, final float right, final float bottom, final Runnable runnable) {
        if (!this.background.isOpeningPlaying()) {
            runnable.run();
            return;
        }
        final CinematicCamera camera = this.background.getSchematicRenderer().camera();
        final Location titleScreen = DynamicBackgroundController.POS_TITLE_SCREEN;
        final Location position = camera.location();
        final float distance = (float)Math.sqrt(Math.pow(titleScreen.getX() - position.getX(), 2.0) + Math.pow(titleScreen.getY() - position.getY(), 2.0) + Math.pow(titleScreen.getZ() - position.getZ(), 2.0));
        if (distance == 0.0f) {
            runnable.run();
            return;
        }
        final float width = right - left;
        final float height = bottom - top;
        final float startScale = 5.0f;
        final float endScale = 1.0f;
        final float fadeInProgress = (float)Math.max(CubicBezier.EASE_IN_OUT.curve(distance / 100.0f - 0.05f + 0.1f) - 0.004999999888241291, 0.0);
        final float scale = MathHelper.lerp(startScale, endScale, fadeInProgress);
        stack.push();
        stack.translate(width / 2.0f, height / 2.0f, 0.0f);
        stack.scale(scale, scale, 1.0f);
        final float rotX = (float)(titleScreen.getPitch() - position.getPitch()) * fadeInProgress;
        final float rotY = (float)(titleScreen.getYaw() - position.getYaw()) * fadeInProgress;
        final float rotZ = (float)(titleScreen.getRoll() - position.getRoll()) * fadeInProgress;
        stack.rotate(rotZ, 0.0f, 0.0f, 1.0f);
        stack.rotate(rotX, 1.0f, 0.0f, 0.0f);
        stack.rotate(rotY, 0.0f, 1.0f, 0.0f);
        stack.translate(-width / 2.0f, -height / 2.0f, 0.0f);
        final float alpha = Math.max(1.0f - fadeInProgress * 3.0f, 0.0f);
        this.labyAPI.renderPipeline().setAlpha(alpha, runnable);
        stack.pop();
    }
    
    @Override
    public void tick() {
        super.tick();
        this.background.tick();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        if (this.background != null) {
            this.background.dispose();
        }
    }
    
    public DynamicBackgroundController world() {
        return this.background;
    }
    
    public AbstractBootLogoRenderer logo() {
        return this.logo;
    }
    
    public void onLabyConnectPlay() {
        if (this.overlayWidget == null) {
            return;
        }
        this.overlayWidget.reInitialize();
    }
}
