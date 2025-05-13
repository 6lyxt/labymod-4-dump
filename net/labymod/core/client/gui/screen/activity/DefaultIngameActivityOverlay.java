// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity;

import java.util.function.UnaryOperator;
import java.util.Collection;
import net.labymod.core.addon.AddonClassLoader;
import java.util.Comparator;
import java.util.Optional;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.event.client.world.WorldEnterEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.Locale;
import java.util.Objects;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.event.client.render.ScreenRenderEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.mouse.MutableMouse;
import java.util.Iterator;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.Minecraft;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.LabyAPI;
import java.util.Map;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import java.util.List;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay;

@Singleton
@Implements(IngameActivityOverlay.class)
public class DefaultIngameActivityOverlay implements IngameActivityOverlay
{
    private final List<IngameOverlayActivity> activities;
    private final List<IngameOverlayActivity> unmodifiableActivities;
    private final Map<ClassLoader, List<IngameOverlayActivity>> activitiesByAddon;
    private final LabyAPI labyAPI;
    private final ScreenCustomFontStack screenCustomFontStack;
    
    @Inject
    public DefaultIngameActivityOverlay(final LabyAPI labyAPI, final EventBus eventBus, final ScreenCustomFontStack screenCustomFontStack) {
        this.activities = new ArrayList<IngameOverlayActivity>();
        this.unmodifiableActivities = Collections.unmodifiableList((List<? extends IngameOverlayActivity>)this.activities);
        this.activitiesByAddon = new HashMap<ClassLoader, List<IngameOverlayActivity>>();
        this.labyAPI = labyAPI;
        this.screenCustomFontStack = screenCustomFontStack;
        eventBus.registerListener(this);
    }
    
    @Subscribe
    public void render(final IngameOverlayRenderEvent event) {
        if (event.phase() != Phase.POST || this.activities.isEmpty()) {
            return;
        }
        final Minecraft minecraft = this.labyAPI.minecraft();
        final float delta = minecraft.getTickDelta();
        final Window window = minecraft.minecraftWindow();
        final Stack stack = event.stack();
        this.clearDepthBuffer();
        for (final IngameOverlayActivity activity : this.activities) {
            if (activity.isVisible()) {
                if (event.isGuiHidden() && !activity.isRenderedHidden()) {
                    continue;
                }
                this.screenCustomFontStack.push(activity);
                if (activity.bounds().getWidth() != window.getScaledWidth() || activity.bounds().getHeight() != window.getScaledHeight()) {
                    activity.resize(window.getScaledWidth(), window.getScaledHeight());
                }
                if (!activity.isOpen()) {
                    activity.load(window);
                    activity.onOpenScreen();
                }
                final boolean input = activity.isAcceptingInput();
                final MutableMouse mouse = input ? minecraft.absoluteMouse().mutable() : Mouse.OUT_OF_BOUNDS.mutable();
                final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
                screenContext.runInContext(stack, mouse, delta, context -> this.renderActivity(false, activity, context));
                this.endBlaze3DBufferSource();
                this.screenCustomFontStack.pop(activity);
                this.clearDepthBuffer();
            }
        }
    }
    
    @Subscribe
    public void onScreenRender(final ScreenRenderEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        final Minecraft minecraft = this.labyAPI.minecraft();
        final MutableMouse mouse = minecraft.absoluteMouse().mutable();
        final float tickDelta = minecraft.getTickDelta();
        for (final IngameOverlayActivity activity : this.activities) {
            if (!activity.isVisible()) {
                continue;
            }
            this.screenCustomFontStack.push(activity);
            final boolean input = activity.isAcceptingInput();
            final MutableMouse overlayMouse = input ? mouse : Mouse.OUT_OF_BOUNDS.mutable();
            final Stack stack = event.stack();
            final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
            screenContext.runInContext(stack, overlayMouse, tickDelta, context -> this.renderActivity(true, activity, context));
            this.endBlaze3DBufferSource();
            this.screenCustomFontStack.pop(activity);
        }
    }
    
    private void renderActivity(final boolean overlay, final IngameOverlayActivity activity, final ScreenContext context) {
        try {
            if (overlay) {
                activity.renderOverlay(context);
                activity.renderHoverComponent(context);
            }
            else {
                activity.render(context);
            }
        }
        catch (final Throwable throwable) {
            final GameCrashReport report = GameCrashReport.forThrowable("Rendering Ingame Activity " + (overlay ? "(Overlay)" : ""), throwable);
            final GameCrashReport.Category addCategory;
            final GameCrashReport.Category activityDetails = addCategory = report.addCategory("Activity Details");
            final String s = "Activity name";
            Objects.requireNonNull(activity);
            addCategory.setDetail(s, activity::getName);
            activityDetails.setDetail("Activity bounds", () -> {
                final Bounds bounds = activity.bounds();
                return String.format(Locale.ROOT, "X: %f, Y: %f, Width: %f, Height: %f", bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
            });
            report.crashGame();
        }
    }
    
    private void clearDepthBuffer() {
        Laby.gfx().clearDepth();
    }
    
    private void endBlaze3DBufferSource() {
        Laby.labyAPI().gfxRenderPipeline().gfx().blaze3DBufferSource().endBuffer();
    }
    
    @Subscribe
    public void unfocusOnClose(final ScreenDisplayEvent event) {
        if (event.getScreen() != null) {
            return;
        }
        for (final IngameOverlayActivity activity : this.activities) {
            if (activity.isAcceptingInput()) {
                this.screenCustomFontStack.push(activity);
                activity.document().unfocus();
                this.screenCustomFontStack.pop(activity);
            }
        }
    }
    
    @Subscribe
    public void tick(final GameTickEvent event) {
        if (event.phase() != Phase.POST || this.activities.isEmpty()) {
            return;
        }
        for (final IngameOverlayActivity activity : this.activities) {
            if (!activity.isOpen()) {
                continue;
            }
            this.screenCustomFontStack.push(activity);
            activity.tick();
            this.screenCustomFontStack.pop(activity);
        }
    }
    
    @Subscribe
    public void initializeActivities(final WorldEnterEvent event) {
        for (final IngameOverlayActivity activity : this.activities) {
            if (!activity.isOpen()) {
                continue;
            }
            this.screenCustomFontStack.push(activity);
            final Window window = this.labyAPI.minecraft().minecraftWindow();
            activity.resize(window.getScaledWidth(), window.getScaledHeight());
            activity.reload();
            this.screenCustomFontStack.pop(activity);
        }
    }
    
    @Subscribe
    public void updateScreenSize(final ScreenResizeEvent event) {
        for (final IngameOverlayActivity activity : this.activities) {
            if (!activity.isOpen()) {
                continue;
            }
            this.screenCustomFontStack.push(activity);
            activity.resize(event.getWidth(), event.getHeight());
            activity.reload();
            this.screenCustomFontStack.pop(activity);
        }
    }
    
    @Override
    public List<IngameOverlayActivity> getActivities() {
        return this.unmodifiableActivities;
    }
    
    @Override
    public List<IngameOverlayActivity> getActivities(final LoadedAddon addon) {
        return this.activitiesByAddon.getOrDefault(addon.getClassLoader(), Collections.emptyList());
    }
    
    @Override
    public Optional<IngameOverlayActivity> getActivity(final Class<? extends IngameOverlayActivity> clazz) {
        for (final IngameOverlayActivity activity : this.activities) {
            if (activity.getClass() == clazz) {
                return Optional.of(activity);
            }
        }
        return Optional.empty();
    }
    
    @Override
    public void registerActivity(final IngameOverlayActivity activity) {
        this.activities.add(activity);
        this.activities.sort(Comparator.comparingInt(IngameOverlayActivity::getPriority));
        if (activity.getClass().getClassLoader() instanceof final AddonClassLoader addonClassLoader) {
            this.activitiesByAddon.computeIfAbsent(addonClassLoader, a -> new ArrayList()).add(activity);
        }
    }
    
    @Override
    public void unregisterActivity(final IngameOverlayActivity activity) {
        this.activities.remove(activity);
        this.activities.sort(Comparator.comparingInt(IngameOverlayActivity::getPriority));
        this.activitiesByAddon.values().removeIf(activities -> activities.contains(activity));
        this.activitiesByAddon.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        if (activity.isOpen()) {
            activity.onCloseScreen();
        }
    }
    
    @Override
    public void unregisterActivities(final LoadedAddon addon) {
        final List<IngameOverlayActivity> activities = this.activitiesByAddon.remove(addon.getClassLoader());
        if (activities == null) {
            return;
        }
        this.activities.removeAll(activities);
        for (final IngameOverlayActivity activity : activities) {
            if (!activity.isOpen()) {
                continue;
            }
            activity.onCloseScreen();
        }
    }
    
    @Override
    public void replaceActivities(final UnaryOperator<IngameOverlayActivity> operator) {
        this.activities.replaceAll(activity -> {
            final IngameOverlayActivity replaced = (IngameOverlayActivity)operator.apply(activity);
            this.activitiesByAddon.values().iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final List<IngameOverlayActivity> addonActivities = iterator.next();
                if (addonActivities.remove(activity)) {
                    addonActivities.add(replaced);
                }
            }
            return replaced;
        });
    }
}
