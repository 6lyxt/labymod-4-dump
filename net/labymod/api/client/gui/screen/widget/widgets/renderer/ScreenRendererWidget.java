// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.renderer;

import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import java.util.Map;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import java.nio.file.Path;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.LabyScreen;
import java.util.Iterator;
import java.util.Deque;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.ArrayDeque;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.Parent;
import java.util.ArrayList;
import net.labymod.api.client.gui.lss.property.LssProperty;
import java.util.function.Predicate;
import net.labymod.api.client.gui.screen.ScreenInstance;
import java.util.function.Consumer;
import java.util.List;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class ScreenRendererWidget extends AbstractWidget<Widget> implements ParentScreen
{
    private final List<Consumer<ScreenInstance>> preDisplayListeners;
    private final List<Consumer<ScreenInstance>> postDisplayListeners;
    private Predicate<ScreenInstance> previousScreenHandler;
    private ScreenInstance screen;
    private ScreenInstance originScreen;
    private final boolean forwardStyle;
    private final LssProperty<Boolean> interactable;
    private boolean initialized;
    private boolean screenInitialized;
    private ScreenInstance queuedScreen;
    private boolean queuedScreenInitialize;
    
    public ScreenRendererWidget() {
        this(false);
    }
    
    public ScreenRendererWidget(final boolean forwardStyle) {
        this.preDisplayListeners = new ArrayList<Consumer<ScreenInstance>>();
        this.postDisplayListeners = new ArrayList<Consumer<ScreenInstance>>();
        this.interactable = new LssProperty<Boolean>(true);
        this.forwardStyle = forwardStyle;
    }
    
    @Override
    public void initialize(final Parent parent) {
        if (!this.initialized && this.screen != null) {
            this.screen.onOpenScreen();
        }
        super.initialize(parent);
        this.forwardStylesheets();
    }
    
    @Override
    public void dispose() {
        if (this.screen != null && this.screenInitialized) {
            this.screen.onCloseScreen();
            this.screenInitialized = false;
        }
        this.initialized = false;
        super.dispose();
    }
    
    private void forwardStylesheets() {
        if (this.parent == null || !(this.screen instanceof Activity)) {
            return;
        }
        final Deque<ScreenRendererWidget> widgets = new ArrayDeque<ScreenRendererWidget>();
        Parent current = this;
        do {
            if (current instanceof final ScreenRendererWidget screenRendererWidget) {
                widgets.offerFirst(screenRendererWidget);
            }
        } while ((current = current.getParent()) != null);
        boolean forwarded = false;
        for (final ScreenRendererWidget widget : widgets) {
            final Parent root = widget.getRoot();
            if (!(root instanceof Activity)) {
                continue;
            }
            if (widget.forwardStyle) {
                forwarded = true;
            }
            if (!forwarded) {
                continue;
            }
            for (final StyleSheet stylesheet : ((Activity)root).getStylesheets()) {
                ((Activity)this.screen).addStyle(stylesheet);
            }
        }
    }
    
    private void init() {
        this.initialized = true;
        if (this.screen != null) {
            if (this.screen instanceof final LabyScreen labyScreen) {
                labyScreen.load(this);
            }
            else {
                this.screen.initialize(this);
            }
            this.screenInitialized = true;
        }
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        if (this.isDragging()) {
            return;
        }
        final Bounds bounds = this.bounds();
        if (this.screen != null && !newRect.isRoundedSizeEqual(previousRect) && bounds.hasSize()) {
            this.screen.resize((int)bounds.getWidth(BoundsType.INNER), (int)bounds.getHeight(BoundsType.INNER));
        }
    }
    
    @Override
    public void postInitialize() {
        super.postInitialize();
        if (this.screen instanceof final Activity activity) {
            activity.addInitializeRunnable(this::forwardStylesheets);
        }
        this.init();
    }
    
    public void initializeScreen(final ScreenInstance screen) {
        if (screen == null) {
            this.displayScreen(null, true);
            return;
        }
        this.queuedScreen = screen;
        this.queuedScreenInitialize = true;
    }
    
    @Override
    public void displayScreen(final ScreenInstance screen) {
        if (screen == null) {
            this.displayScreen(null, false);
            return;
        }
        this.queuedScreen = screen;
        this.queuedScreenInitialize = false;
    }
    
    private void displayScreen(final ScreenInstance screen, final boolean initialize) {
        if (this.screen != null) {
            this.screen.onCloseScreen();
        }
        if (!initialize) {
            this.preDisplayListeners.forEach(new Consumer<Consumer<ScreenInstance>>(this) {
                @Override
                public void accept(final Consumer<ScreenInstance> consumer) {
                    consumer.accept(screen);
                }
            });
        }
        this.screen = screen;
        if (this.originScreen == null) {
            this.originScreen = screen;
        }
        final Bounds bounds = this.bounds();
        if (this.screen != null && bounds.hasSize()) {
            this.screen.resize((int)bounds.getWidth(BoundsType.INNER), (int)bounds.getHeight(BoundsType.INNER));
        }
        this.forwardStylesheets();
        if (!initialize && bounds.hasSize()) {
            this.init();
        }
        if (screen != null && this.initialized) {
            screen.onOpenScreen();
        }
        if (!initialize) {
            this.postDisplayListeners.forEach(new Consumer<Consumer<ScreenInstance>>(this) {
                @Override
                public void accept(final Consumer<ScreenInstance> consumer) {
                    consumer.accept(screen);
                }
            });
        }
    }
    
    public Object getPreviousScreen() {
        return this.originScreen;
    }
    
    @Override
    public ScreenWrapper currentScreen() {
        if (this.screen == null) {
            return null;
        }
        if (this.screen instanceof final ScreenWrapper screenWrapper) {
            return screenWrapper;
        }
        return this.labyAPI.minecraft().wrapScreen(this.screen);
    }
    
    @Override
    public LabyScreen currentLabyScreen() {
        return (this.screen instanceof LabyScreen) ? ((LabyScreen)this.screen) : null;
    }
    
    @Override
    public String getCurrentScreenName() {
        if (this.screen == null) {
            return null;
        }
        final String currentScreenName = Laby.labyAPI().screenService().getScreenNameByClass(this.screen.getClass());
        if (currentScreenName == null) {
            final ScreenInstance screen = this.screen;
            if (screen instanceof final Activity activity) {
                return activity.getName();
            }
        }
        return currentScreenName;
    }
    
    @Override
    public Object mostInnerScreen() {
        return (this.screen != null) ? this.screen.mostInnerScreen() : null;
    }
    
    @Override
    public void displayScreenRaw(final Object screen) {
        this.displayScreen((ScreenInstance)screen);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        if (this.queuedScreen != null) {
            this.displayScreen(this.queuedScreen, this.queuedScreenInitialize);
            this.queuedScreen = null;
        }
        if (this.isScreenInitialized() && this.isVisible()) {
            this.labyAPI.minecraft().minecraftWindow().transform(this.parent, context.stack(), this.bounds(), () -> this.applyScreen(context.mouse(), () -> {
                final ScreenInstance screen = this.screen;
                final ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
                try {
                    screenCustomFontStack.push(screen);
                    screen.render(context);
                }
                finally {
                    screenCustomFontStack.pop(screen);
                }
                return true;
            }));
            Laby.gfx().clearDepth();
        }
        super.renderWidget(context);
    }
    
    @Override
    public void renderOverlay(final ScreenContext context) {
        final MutableMouse originalMouse = context.mouse().copy().mutable();
        if (this.isScreenInitialized() && this.isVisible()) {
            this.labyAPI.minecraft().minecraftWindow().transform(this.parent, context.stack(), this.bounds(), new Runnable() {
                @Override
                public void run() {
                    ScreenRendererWidget.this.applyScreen(context.mouse(), new BooleanSupplier() {
                        @Override
                        public boolean getAsBoolean() {
                            ScreenRendererWidget.this.screen.renderOverlay(context);
                            return true;
                        }
                    });
                }
            });
        }
        context.setMouse(originalMouse);
        super.renderOverlay(context);
    }
    
    @Override
    public void renderHoverComponent(final ScreenContext context) {
        if (this.isScreenInitialized() && this.isVisible()) {
            this.screen.renderHoverComponent(context);
        }
        super.renderHoverComponent(context);
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.isScreenInitialized()) {
            this.screen.tick();
        }
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        return this.isScreenInitialized() && this.isVisible() && this.interactable.get() && this.applyScreen(mouse, new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return ScreenRendererWidget.this.screen.mouseReleased(mouse, mouseButton);
            }
        });
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return this.isScreenInitialized() && this.isVisible() && this.interactable.get() && this.applyScreen(mouse, new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return ScreenRendererWidget.this.screen.mouseClicked(mouse, mouseButton);
            }
        });
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        return this.isScreenInitialized() && this.isVisible() && this.interactable.get() && this.applyScreen(mouse, new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return ScreenRendererWidget.this.screen.mouseScrolled(mouse, scrollDelta);
            }
        });
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        return this.isScreenInitialized() && this.isVisible() && this.interactable.get() && this.applyScreen(mouse, new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return ScreenRendererWidget.this.screen.mouseDragged(mouse, button, deltaX, deltaY);
            }
        });
    }
    
    @Override
    public boolean fileDropped(final MutableMouse mouse, final List<Path> paths) {
        return this.isScreenInitialized() && this.isVisible() && this.interactable.get() && this.applyScreen(mouse, new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return ScreenRendererWidget.this.screen.fileDropped(mouse, paths);
            }
        });
    }
    
    private boolean applyScreen(final MutableMouse mouse, final BooleanSupplier transformHandler) {
        final Bounds bounds = this.bounds();
        return mouse.set(mouse.getXDouble() - bounds.getX(BoundsType.INNER), mouse.getYDouble() - bounds.getY(BoundsType.INNER), transformHandler);
    }
    
    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
        if (this.screen != null) {
            this.screen.onVisibilityChanged(visible);
        }
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        return this.isScreenInitialized() && this.isVisible() && this.interactable.get() && this.screen.keyPressed(key, type);
    }
    
    @Override
    public boolean keyReleased(final Key key, final InputType type) {
        return this.isScreenInitialized() && this.isVisible() && this.interactable.get() && this.screen.keyReleased(key, type);
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        return this.isScreenInitialized() && this.isVisible() && this.interactable.get() && this.screen.charTyped(key, character);
    }
    
    @Override
    public void doScreenAction(final String action, final Map<String, Object> parameters) {
        super.doScreenAction(action, parameters);
        if (this.isScreenInitialized() && this.isVisible() && this.interactable.get()) {
            this.screen.doScreenAction(action, parameters);
        }
    }
    
    @Override
    public void updateBounds() {
        super.updateBounds();
        if (this.screen instanceof final Activity activity) {
            activity.document().updateBounds();
        }
    }
    
    @Override
    public void updateState(final boolean force) {
        super.updateState(force);
        if (this.screen instanceof final Activity activity) {
            activity.document().updateState(force);
        }
    }
    
    @Override
    public void unfocus() {
        super.unfocus();
        if (this.screen instanceof final Activity activity) {
            activity.document().unfocus();
        }
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        if (this.screen instanceof final Activity activity) {
            return activity.document().getContentHeight(BoundsType.OUTER);
        }
        return super.getContentHeight(type);
    }
    
    @Override
    public void updateLssVariable(final LssVariable variable) {
        super.updateLssVariable(variable);
        if (this.screen instanceof final Activity activity) {
            activity.updateLssVariable(variable);
        }
    }
    
    @Override
    public void forceUpdateLssVariable(final LssVariable variable) {
        super.forceUpdateLssVariable(variable);
        if (this.screen instanceof final Activity activity) {
            activity.forceUpdateLssVariable(variable);
        }
    }
    
    @Override
    public boolean shouldHandleEscape() {
        if (this.screen instanceof final Activity activity) {
            return activity.shouldHandleEscape();
        }
        return super.shouldHandleEscape();
    }
    
    @Override
    public void renderExtraDebugInformation() {
        super.renderExtraDebugInformation();
        final String currentScreenName = this.getCurrentScreenName();
        if (currentScreenName == null) {
            return;
        }
        LabyImGui.sameLine(0.0f, 0.0f);
        LabyImGui.text(" [" + currentScreenName, -2368696, -12417035);
        if (LabyImGui.isItemClicked()) {
            Laby.references().documentHandler().setSelectedActivity(currentScreenName);
        }
    }
    
    @Override
    public LssProperty<Boolean> interactable() {
        return this.interactable;
    }
    
    public ScreenInstance getScreen() {
        return this.screen;
    }
    
    private boolean isScreenInitialized() {
        return this.screen != null && this.screenInitialized;
    }
    
    public boolean handlesPreviousScreen() {
        return (this.previousScreenHandler == null) ? (!this.labyAPI.minecraft().isIngame()) : this.previousScreenHandler.test(this.screen);
    }
    
    public void setPreviousScreenHandler(final Predicate<ScreenInstance> handler) {
        this.previousScreenHandler = handler;
    }
    
    public void addDisplayListener(final Consumer<ScreenInstance> callback) {
        this.addPostDisplayListener(callback);
    }
    
    public void addPreDisplayListener(final Consumer<ScreenInstance> callback) {
        this.preDisplayListeners.add(callback);
    }
    
    public void addPostDisplayListener(final Consumer<ScreenInstance> callback) {
        this.postDisplayListeners.add(callback);
    }
    
    public void setScreenFrom(final ScreenRendererWidget screenRenderer) {
        this.preDisplayListeners.forEach(new Consumer<Consumer<ScreenInstance>>(this) {
            @Override
            public void accept(final Consumer<ScreenInstance> consumer) {
                consumer.accept(screenRenderer.getScreen());
            }
        });
        this.screen = screenRenderer.getScreen();
        this.screenInitialized = screenRenderer.screenInitialized;
        if (this.originScreen == null) {
            this.originScreen = this.screen;
        }
        this.postDisplayListeners.forEach(new Consumer<Consumer<ScreenInstance>>() {
            @Override
            public void accept(final Consumer<ScreenInstance> consumer) {
                consumer.accept(ScreenRendererWidget.this.screen);
            }
        });
    }
}
