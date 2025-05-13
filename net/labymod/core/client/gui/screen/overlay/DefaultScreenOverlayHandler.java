// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.overlay;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.client.gui.screen.activity.activities.ingame.NotificationOverlay;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetScreenOverlay;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.activity.activities.OldOverlayWidgetActivity;
import java.util.function.Consumer;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import java.util.Comparator;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.input.CharacterTypedEvent;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.KeyboardUser;
import net.labymod.api.event.client.input.KeyEvent;
import java.nio.file.Path;
import net.labymod.api.event.client.gui.screen.FileDroppedEvent;
import net.labymod.api.event.client.input.MouseScrollEvent;
import net.labymod.api.event.client.input.MouseDragEvent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.MouseUser;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.Minecraft;
import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import javax.inject.Inject;
import java.util.concurrent.CopyOnWriteArrayList;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;
import java.util.List;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler;

@Singleton
@Implements(ScreenOverlayHandler.class)
public final class DefaultScreenOverlayHandler implements ScreenOverlayHandler
{
    private final List<ScreenOverlay> overlays;
    private final LabyAPI labyAPI;
    private final ScreenTreeTopRegistry screenTreeTopRegistry;
    private final ScreenCustomFontStack screenCustomFontStack;
    
    @Inject
    public DefaultScreenOverlayHandler(final LabyAPI labyAPI, final ScreenTreeTopRegistry screenTreeTopRegistry, final ScreenCustomFontStack screenCustomFontStack) {
        this.labyAPI = labyAPI;
        this.screenTreeTopRegistry = screenTreeTopRegistry;
        this.screenCustomFontStack = screenCustomFontStack;
        this.overlays = new CopyOnWriteArrayList<ScreenOverlay>();
        labyAPI.eventBus().registerListener(this);
    }
    
    @Subscribe(-64)
    public void registerDefaultOverlays(final GameInitializeEvent event) {
        if (event.getLifecycle() == GameInitializeEvent.Lifecycle.POST_GAME_STARTED) {
            this.registerDefaultOverlays();
        }
    }
    
    @Subscribe
    public void onGameRender(final GameRenderEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        final Minecraft minecraft = this.labyAPI.minecraft();
        final MutableMouse mouse = minecraft.mouse();
        final float tickDelta = minecraft.getTickDelta();
        final Stack stack = event.stack();
        try {
            stack.push();
            final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
            screenContext.runInContext(stack, mouse, tickDelta, this::renderScreenOverlay);
        }
        finally {
            stack.pop();
        }
        final GFXRenderPipeline pipeline = Laby.labyAPI().gfxRenderPipeline();
        final RenderTarget target = pipeline.getActivityRenderTarget();
        if (target.isApplied()) {
            pipeline.clear(target);
        }
    }
    
    private void renderScreenOverlay(final ScreenContext context) {
        for (final ScreenOverlay overlay : this.overlays()) {
            if (!overlay.isActive()) {
                continue;
            }
            if (overlay.isHideGui() && this.isHideGui()) {
                continue;
            }
            this.screenCustomFontStack.push(overlay);
            Laby.gfx().clearDepth();
            try {
                overlay.render(context);
                overlay.renderOverlay(context);
                overlay.renderHoverComponent(context);
            }
            catch (final Throwable throwable) {
                final GameCrashReport report = GameCrashReport.forThrowable("Rendering Screen Overlay", throwable);
                final GameCrashReport.Category addCategory;
                final GameCrashReport.Category screenOverlayDetails = addCategory = report.addCategory("Screen Overlay Details");
                final String s = "Screen Overlay name";
                final ScreenOverlay obj = overlay;
                Objects.requireNonNull(obj);
                addCategory.setDetail(s, obj::getName);
                screenOverlayDetails.setDetail("Screen Overlay bounds", () -> {
                    final Bounds bounds = overlay.bounds();
                    return String.format(Locale.ROOT, "X: %f, Y: %f, Width: %f, Height: %f", bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
                });
                report.crashGame();
            }
            this.screenCustomFontStack.pop(overlay);
        }
    }
    
    @Subscribe
    public void onMouseButton(final MouseButtonEvent event) {
        switch (event.action()) {
            case CLICK: {
                this.forEachReversed(overlay -> {
                    if (!overlay.isActive() || event.isCancelled()) {
                        return;
                    }
                    else {
                        final MutableMouse mouse = event.mouse();
                        final MouseButton button = event.button();
                        this.screenCustomFontStack.push(overlay);
                        this.screenTreeTopRegistry.mouseClicked(ScreenPhase.PRE, overlay, mouse, button);
                        final boolean res = overlay.mouseClicked(mouse, button);
                        this.screenTreeTopRegistry.mouseClicked(ScreenPhase.POST, overlay, mouse, button);
                        this.screenCustomFontStack.pop(overlay);
                        if (res) {
                            event.setCancelled(true);
                        }
                        return;
                    }
                });
                break;
            }
            case RELEASE: {
                this.forEachReversed(overlay -> {
                    if (!overlay.isActive() || event.isCancelled()) {
                        return;
                    }
                    else {
                        final MutableMouse mouse2 = event.mouse();
                        final MouseButton button2 = event.button();
                        this.screenCustomFontStack.push(overlay);
                        this.screenTreeTopRegistry.mouseReleased(ScreenPhase.PRE, overlay, mouse2, button2);
                        final boolean res2 = overlay.mouseReleased(mouse2, button2);
                        this.screenTreeTopRegistry.mouseReleased(ScreenPhase.POST, overlay, mouse2, button2);
                        this.screenCustomFontStack.pop(overlay);
                        if (res2) {
                            event.setCancelled(true);
                        }
                        return;
                    }
                });
                break;
            }
        }
    }
    
    @Subscribe
    public void onMouseDrag(final MouseDragEvent event) {
        this.forEachReversed(overlay -> {
            if (overlay.isActive() && !event.isCancelled()) {
                final MutableMouse mouse = event.mouse();
                final MouseButton button = event.button();
                final double deltaX = event.deltaX();
                final double deltaY = event.deltaY();
                this.screenCustomFontStack.push(overlay);
                this.screenTreeTopRegistry.mouseDragged(ScreenPhase.PRE, overlay, mouse, button, deltaX, deltaY);
                final boolean res = overlay.mouseDragged(mouse, button, deltaX, deltaY);
                this.screenTreeTopRegistry.mouseDragged(ScreenPhase.POST, overlay, mouse, button, deltaX, deltaY);
                this.screenCustomFontStack.pop(overlay);
                if (res) {
                    event.setCancelled(true);
                }
            }
        });
    }
    
    @Subscribe
    public void onMouseScroll(final MouseScrollEvent event) {
        this.forEachReversed(overlay -> {
            if (overlay.isActive() && !event.isCancelled()) {
                final MutableMouse mouse = event.mouse();
                final float delta = event.delta();
                this.screenCustomFontStack.push(overlay);
                this.screenTreeTopRegistry.mouseScrolled(ScreenPhase.PRE, overlay, mouse, delta);
                final boolean res = overlay.mouseScrolled(mouse, delta);
                this.screenTreeTopRegistry.mouseScrolled(ScreenPhase.POST, overlay, mouse, delta);
                this.screenCustomFontStack.pop(overlay);
                if (res) {
                    event.setCancelled(true);
                }
            }
        });
    }
    
    @Subscribe
    public void onFileDrop(final FileDroppedEvent event) {
        this.forEachReversed(overlay -> {
            if (overlay.isActive() && !event.isCancelled()) {
                final MutableMouse mouse = event.mouse();
                final List<Path> paths = event.paths();
                this.screenCustomFontStack.push(overlay);
                this.screenTreeTopRegistry.fileDropped(ScreenPhase.PRE, overlay, mouse, paths);
                final boolean res = overlay.fileDropped(mouse, paths);
                this.screenTreeTopRegistry.fileDropped(ScreenPhase.POST, overlay, mouse, paths);
                this.screenCustomFontStack.pop(overlay);
                if (res) {
                    event.setCancelled(true);
                }
            }
        });
    }
    
    @Subscribe
    public void onKey(final KeyEvent event) {
        if (event.key() instanceof MouseButton) {
            return;
        }
        final Key key = event.key();
        final InputType type = event.inputType();
        switch (event.state()) {
            case UNPRESSED: {
                for (final ScreenOverlay overlay : this.overlays()) {
                    if (overlay.isActive()) {
                        if (event.isCancelled()) {
                            continue;
                        }
                        this.screenCustomFontStack.push(overlay);
                        this.screenTreeTopRegistry.keyReleased(ScreenPhase.PRE, overlay, key, type);
                        overlay.keyReleased(key, type);
                        this.screenTreeTopRegistry.keyReleased(ScreenPhase.POST, overlay, key, type);
                        this.screenCustomFontStack.pop(overlay);
                    }
                }
                break;
            }
            case PRESS: {
                final ScreenOverlay overlay;
                this.forEachReversed(overlay -> {
                    if (!overlay.isActive() || event.isCancelled()) {
                        return;
                    }
                    else {
                        this.screenCustomFontStack.push(overlay);
                        this.screenTreeTopRegistry.keyPressed(ScreenPhase.PRE, overlay, key, type);
                        final boolean res = overlay.keyPressed(key, type);
                        this.screenTreeTopRegistry.keyPressed(ScreenPhase.POST, overlay, key, type);
                        this.screenCustomFontStack.pop(overlay);
                        if (res) {
                            event.setCancelled(true);
                        }
                        return;
                    }
                });
                break;
            }
        }
    }
    
    @Subscribe
    public void onCharacterTyped(final CharacterTypedEvent event) {
        this.forEachReversed(overlay -> {
            if (overlay.isActive() && !event.isCancelled()) {
                final Key key = event.key();
                final char character = event.getCharacter();
                this.screenCustomFontStack.push(overlay);
                this.screenTreeTopRegistry.charTyped(ScreenPhase.PRE, overlay, key, character);
                final boolean res = overlay.charTyped(key, character);
                this.screenTreeTopRegistry.charTyped(ScreenPhase.POST, overlay, key, character);
                this.screenCustomFontStack.pop(overlay);
                if (res) {
                    event.setCancelled(true);
                }
            }
        });
    }
    
    @Subscribe
    public void onGameTick(final GameTickEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        for (final ScreenOverlay overlay : this.overlays()) {
            if (!overlay.isActive()) {
                continue;
            }
            this.screenCustomFontStack.push(overlay);
            this.checkWindowSize(window, overlay);
            overlay.tick();
            this.screenCustomFontStack.pop(overlay);
        }
    }
    
    @Subscribe
    public void onWindowResize(final WindowResizeEvent event) {
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        final int absoluteScaledWidth = window.getAbsoluteScaledWidth();
        final int absoluteScaledHeight = window.getAbsoluteScaledHeight();
        for (final ScreenOverlay overlay : this.overlays()) {
            this.screenCustomFontStack.push(overlay);
            this.resizeOverlay(window, overlay, absoluteScaledWidth, absoluteScaledHeight);
            this.screenCustomFontStack.pop(overlay);
        }
    }
    
    @Override
    public void registerOverlay(final ScreenOverlay overlay) {
        this.overlays.add(overlay);
        this.overlays.sort(Comparator.comparingInt(ScreenOverlay::getZLayer));
    }
    
    @Override
    public void unregisterOverlay(final ScreenOverlay overlay) {
        if (!this.overlays.remove(overlay)) {
            return;
        }
        overlay.onCloseScreen();
    }
    
    @Nullable
    @Override
    public <T extends ScreenOverlay> T screenOverlay(final Class<T> screenOverlayClass) {
        for (final ScreenOverlay overlay : this.overlays) {
            if (overlay.getClass().equals(screenOverlayClass)) {
                return (T)overlay;
            }
        }
        return null;
    }
    
    @Override
    public <T extends ScreenOverlay> Optional<T> findScreenOverlay(final Class<T> screenOverlayClass) {
        final T overlay = this.screenOverlay(screenOverlayClass);
        return (overlay == null) ? Optional.empty() : Optional.of(overlay);
    }
    
    @Override
    public List<ScreenOverlay> overlays() {
        return this.overlays;
    }
    
    @Override
    public void forEachReversed(final Consumer<ScreenOverlay> consumer) {
        for (int i = this.overlays.size() - 1; i >= 0; --i) {
            consumer.accept(this.overlays.get(i));
        }
    }
    
    @Override
    public boolean isOverlayHovered() {
        for (final ScreenOverlay overlay : this.overlays) {
            if (overlay.isActive() && overlay instanceof OldOverlayWidgetActivity && ((OldOverlayWidgetActivity)overlay).isHovered()) {
                return true;
            }
            if (!overlay.isActive()) {
                continue;
            }
            for (final Widget child : overlay.document().getChildren()) {
                if (child.isVisible() && child.isHovered()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean isActive(final Function<ScreenOverlay, Boolean> overlayFunction) {
        if (overlayFunction == null) {
            return false;
        }
        for (final ScreenOverlay overlay : this.overlays()) {
            if (overlayFunction.apply(overlay)) {
                return overlay.isActive();
            }
        }
        return false;
    }
    
    private void registerDefaultOverlays() {
        this.registerOverlay(new WidgetScreenOverlay());
        this.registerOverlay(new NotificationOverlay());
    }
    
    private void checkWindowSize(final Window window, final ScreenOverlay overlay) {
        final Bounds bounds = overlay.bounds();
        final int absoluteScaledWidth = window.getAbsoluteScaledWidth();
        final int absoluteScaledHeight = window.getAbsoluteScaledHeight();
        if (bounds.getWidth() != absoluteScaledWidth || bounds.getHeight() != absoluteScaledHeight) {
            this.resizeOverlay(window, overlay, absoluteScaledWidth, absoluteScaledHeight);
        }
    }
    
    private void resizeOverlay(final Window window, final ScreenOverlay overlay, final int absoluteScaledWidth, final int absoluteScaledHeight) {
        overlay.resize(absoluteScaledWidth, absoluteScaledHeight);
        overlay.load(window);
    }
    
    private boolean isHideGui() {
        final Minecraft minecraft = this.labyAPI.minecraft();
        return minecraft.isIngame() && minecraft.options().isHideGUI();
    }
}
