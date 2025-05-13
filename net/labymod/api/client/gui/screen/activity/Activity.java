// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity;

import net.labymod.api.client.gui.element.CompositeElement;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.DocumentIdentifier;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaRule;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;
import net.labymod.api.client.gui.screen.activity.activities.OldOverlayWidgetActivity;
import net.labymod.api.client.gui.screen.key.MouseButton;
import java.util.Collection;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import java.util.function.Function;
import java.util.Objects;
import java.util.Optional;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.function.Predicate;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.event.client.gui.screen.ActivityInitializeEvent;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.activities.WorkInProgressActivity;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.ThreadSafe;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.util.StatefulRenderer;
import net.labymod.api.event.method.ListenerRegistry;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.List;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import java.util.Map;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.client.gui.lss.meta.LinkMetaLoader;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;

@Link(value = "style.lss", priority = -127)
public abstract class Activity extends ElementActivity<Document> implements LssVariableHolder
{
    private final ResourcesReloadWatcher reloadWatcher;
    private final LinkMetaLoader metaLoader;
    private final StyleSheetLoader styleSheetLoader;
    private final Map<String, LssVariable> lssVariables;
    private final List<StyleSheet> stylesheets;
    private final List<Runnable> initializeRunnables;
    private final List<Runnable> closeRunnables;
    private final ListenerRegistry listenerRegistry;
    @Deprecated(forRemoval = true, since = "4.2.42")
    private final StatefulRenderer.StateProvider legacyRenderProvider;
    private String boundBranch;
    private Theme theme;
    protected boolean open;
    protected ActivityAnimationController.ActivityTransformer transformer;
    
    public Activity() {
        this(true);
    }
    
    public Activity(final boolean handleStyleSheet) {
        this.reloadWatcher = Laby.references().resourcesReloadWatcher();
        this.lssVariables = new HashMap<String, LssVariable>();
        this.stylesheets = new ArrayList<StyleSheet>();
        this.initializeRunnables = new ArrayList<Runnable>();
        this.closeRunnables = new ArrayList<Runnable>();
        this.listenerRegistry = Laby.references().subscribeMethodResolver().resolve(this);
        this.legacyRenderProvider = new StatefulRenderer.StateProvider(false);
        this.boundBranch = null;
        this.metaLoader = Laby.references().linkMetaLoader();
        this.styleSheetLoader = Laby.references().styleSheetLoader();
        ((Document)super.document).handleStyleSheet = handleStyleSheet;
        this.internalLoadMarkup();
        StatefulRenderer.registerActivityAnalyzer(this.getClass());
    }
    
    @Override
    protected Document createDocument() {
        return new Document(this);
    }
    
    public void loadMeta() {
        this.theme = this.labyAPI.themeService().currentTheme();
        this.stylesheets.clear();
        this.metaLoader.injectMeta(this);
    }
    
    public void reload() {
        ThreadSafe.ensureRenderThread();
        super.load(this.parent);
    }
    
    public void preload() {
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        this.resize(window.getScaledWidth(), window.getScaledHeight());
        this.load(new ScreenRendererWidget());
    }
    
    @Override
    public void initialize(final Parent parent) {
        if (this.boundBranch != null && !Laby.labyAPI().getBranchName().equals(this.boundBranch)) {
            this.redirectScreen(new WorkInProgressActivity(this.boundBranch));
            return;
        }
        this.loadMeta();
        ((Document)this.document).reset();
        this.updateMediaIdentifiers();
        super.initialize(parent);
        for (final Runnable initializeRunnable : this.initializeRunnables) {
            if (initializeRunnable == null) {
                continue;
            }
            initializeRunnable.run();
        }
    }
    
    @Override
    protected void postInitialize() {
        super.postInitialize();
        Laby.fireEvent(new ActivityInitializeEvent(this));
        for (final StyleSheet stylesheet : this.stylesheets) {
            ((Document)this.document).applyStyleSheet(stylesheet);
        }
        this.postStyleSheetLoad();
    }
    
    protected void postStyleSheetLoad() {
        ((Document)this.document).postStyleSheetLoad();
    }
    
    @Override
    public void render(final ScreenContext context) {
        if (this.transformer != null) {
            this.transformer.render(context.getTickDelta());
        }
        Laby.references().documentHandler().collectActivity(this);
        Laby.references().screenWindowManagement().preRenderActivity(context.stack(), context.mouse(), this);
        context.pushStack();
        if (!(this instanceof AbstractLayerActivity)) {
            this.transformActivity(context);
        }
        StatefulRenderer.tryRender(context, this.legacyRenderProvider, () -> StatefulRenderer.isActivityRenderOverridden(this.getClass()), x$0 -> ElementActivity.this.render(x$0), c -> this.render(c.stack(), c.mouse(), c.getTickDelta()));
        context.popStack();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.42")
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float tickDelta) {
        super.render(stack, mouse, tickDelta);
    }
    
    protected void transformActivity(final ScreenContext environment) {
        if (this.transformer == null || !this.transformer.isActive()) {
            return;
        }
        this.transformer.controller().transform(environment.stack(), environment.mouse(), environment.getTickDelta(), this, this.transformer);
    }
    
    @Override
    public boolean renderBackground(final ScreenContext context) {
        return true;
    }
    
    @Override
    public void renderOverlay(final ScreenContext context) {
        super.renderOverlay(context);
    }
    
    @Override
    public void renderHoverComponent(final ScreenContext context) {
        super.renderHoverComponent(context);
    }
    
    @Override
    public void resize(final int width, final int height) {
        super.resize(width, height);
        this.updateMediaIdentifiers();
    }
    
    @Override
    public void onOpenScreen() {
        Laby.references().activityController().addOpenActivity(this);
        this.open = true;
        super.onOpenScreen();
        Laby.fireEvent(new ActivityOpenEvent(this));
        if (this.transformer != null) {
            this.transformer.initialize();
        }
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        Laby.references().activityController().removeOpenActivity(this);
        ((Document)this.document).dispose();
        for (final Runnable closeAction : this.closeRunnables) {
            closeAction.run();
        }
        this.closeRunnables.clear();
        this.open = false;
        if (this.transformer != null) {
            this.transformer.dispose();
            this.transformer = null;
        }
    }
    
    @Override
    public void tick() {
        super.tick();
    }
    
    protected Optional<Widget> getFirstWidget(final Predicate<Widget> predicate) {
        for (final Widget widget : ((Document)this.document).getChildren()) {
            if (predicate.test(widget)) {
                return Optional.of(widget);
            }
        }
        return Optional.empty();
    }
    
    protected <T extends Widget> Optional<T> getFirstWidget(final Class<T> widgetClass) {
        Objects.requireNonNull(widgetClass);
        final Optional<Widget> firstWidget = this.getFirstWidget(widgetClass::isInstance);
        Objects.requireNonNull(widgetClass);
        return (Optional<T>)firstWidget.map((Function<? super Widget, ?>)widgetClass::cast);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        final boolean result = super.keyPressed(key, type);
        if (!result && key == Key.TAB) {
            final Widget focused = this.getFocused((Widget)this.document);
            ((Document)this.document).unfocus();
            this.focusNext(focused, (Widget)this.document, false);
        }
        return result;
    }
    
    @Override
    public boolean displayPreviousScreen() {
        if ((this.previousScreen == null || this.previousScreen == this) && this.parent instanceof ScreenRendererWidget) {
            final ParentScreen outerScreen = (ParentScreen)this.parent.getRoot();
            return !(outerScreen instanceof Activity) || ((Activity)outerScreen).displayPreviousScreen();
        }
        if (this.previousScreen instanceof LabyScreenAccessor && ((LabyScreenAccessor)this.previousScreen).screen() == this) {
            this.labyAPI.minecraft().minecraftWindow().displayScreenRaw(null);
            return true;
        }
        return super.displayPreviousScreen();
    }
    
    @Override
    public void doScreenAction(final String action, final Map<String, Object> parameters) {
        if (action.equals("getActivityTree") && parameters.containsKey("consumer")) {
            final Consumer<Activity> consumer = parameters.get("consumer");
            consumer.accept(this);
        }
        this.document.doScreenAction(action, parameters);
    }
    
    public void addInitializeRunnable(final Runnable initializeRunnable) {
        Objects.requireNonNull(initializeRunnable, "initializeRunnable must not be null");
        this.initializeRunnables.add(initializeRunnable);
    }
    
    public void addCloseRunnable(final Runnable closeRunnable) {
        Objects.requireNonNull(closeRunnable, "closeRunnable must not be null");
        this.closeRunnables.add(closeRunnable);
    }
    
    public void setTransformer(final ActivityAnimationController.ActivityTransformer transformer) {
        this.transformer = transformer;
    }
    
    public void addStyle(final String path) {
        final String namespace = this.labyAPI.getNamespace(this);
        this.addStyle(namespace, path);
    }
    
    public void addStyle(final String namespace, final String path) {
        final ThemeFile file = this.labyAPI.themeService().currentTheme().file(namespace, "lss/" + path).normalize();
        final StyleSheet style = this.styleSheetLoader.load(file);
        if (style != null) {
            this.addStyle(style);
        }
    }
    
    public void addStyle(final StyleSheet style) {
        if (style == null) {
            return;
        }
        if (!this.stylesheets.contains(style)) {
            this.stylesheets.add(style);
        }
    }
    
    public void reloadMarkup() {
        this.stylesheets.clear();
        if (((Document)this.document).handleStyleSheet) {
            this.loadMeta();
        }
    }
    
    public void reloadStyles() {
        final List<StyleSheet> styleSheets = new ArrayList<StyleSheet>(this.stylesheets);
        this.stylesheets.clear();
        final Theme theme = this.labyAPI.themeService().currentTheme();
        for (final StyleSheet styleSheet : styleSheets) {
            final ThemeFile file = styleSheet.file().forTheme(theme).normalize();
            final StyleSheet reloadedStyleSheet = this.styleSheetLoader.load(file);
            if (reloadedStyleSheet != null) {
                this.addStyle(reloadedStyleSheet);
            }
        }
    }
    
    private Widget getFocused(final Widget widget) {
        if (widget.isFocused() && widget.hasTabFocus()) {
            return widget;
        }
        for (final Widget child : widget.getChildren()) {
            final Widget childFocused = this.getFocused(child);
            if (childFocused != null) {
                return childFocused;
            }
        }
        return null;
    }
    
    private Boolean focusNext(final Widget focused, final Widget widget, Boolean canFocus) {
        if ((focused == null && widget.hasTabFocus()) || (canFocus && widget.hasTabFocus())) {
            widget.setFocused(true);
            return null;
        }
        if (widget.hasTabFocus() && widget == focused) {
            canFocus = true;
        }
        for (final Widget child : widget.getChildren()) {
            canFocus = this.focusNext(focused, child, canFocus);
            if (canFocus == null) {
                break;
            }
        }
        return canFocus;
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        return super.mouseReleased(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        ((Document)this.document).unfocus();
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        return super.mouseScrolled(mouse, scrollDelta);
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    public List<StyleSheet> getStylesheets() {
        return this.stylesheets;
    }
    
    @Override
    public String getNamespace() {
        return super.getNamespace();
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void redirectScreen(final ScreenInstance screen) {
        if (screen instanceof final Activity activity) {
            activity.previousScreen = this.previousScreen;
        }
        this.displayScreen(screen);
    }
    
    public void overlayClosed(final OldOverlayWidgetActivity overlay) {
        this.overlayUpdated(null);
    }
    
    public Collection<OldOverlayWidgetActivity> getOverlays() {
        return this.labyAPI.screenOverlayHandler().overlays().stream().filter((Predicate<? super Object>)new Predicate<ScreenOverlay>(this) {
            @Override
            public boolean test(final ScreenOverlay overlay) {
                return overlay instanceof OldOverlayWidgetActivity;
            }
        }).map((Function<? super Object, ?>)new Function<ScreenOverlay, OldOverlayWidgetActivity>(this) {
            @Override
            public OldOverlayWidgetActivity apply(final ScreenOverlay overlay) {
                return (OldOverlayWidgetActivity)overlay;
            }
        }).filter((Predicate<? super Object>)new Predicate<OldOverlayWidgetActivity>() {
            @Override
            public boolean test(final OldOverlayWidgetActivity overlay) {
                return overlay.getOrigin() == Activity.this;
            }
        }).collect((Collector<? super Object, ?, Collection<OldOverlayWidgetActivity>>)Collectors.toList());
    }
    
    public boolean hasOverlay(final OldOverlayWidgetActivity overlay) {
        return this.labyAPI.screenOverlayHandler().overlays().stream().filter((Predicate<? super Object>)new Predicate<ScreenOverlay>(this) {
            @Override
            public boolean test(final ScreenOverlay o) {
                return !o.isClosing() && o.isActive();
            }
        }).anyMatch((Predicate<? super Object>)new Predicate<ScreenOverlay>(this) {
            @Override
            public boolean test(final ScreenOverlay o) {
                return o == overlay;
            }
        });
    }
    
    public boolean hasOverlay(final Class<? extends OldOverlayWidgetActivity> overlayClass) {
        return this.labyAPI.screenOverlayHandler().overlays().stream().filter((Predicate<? super Object>)new Predicate<ScreenOverlay>(this) {
            @Override
            public boolean test(final ScreenOverlay o) {
                return !o.isClosing() && o.isActive();
            }
        }).anyMatch((Predicate<? super Object>)new Predicate<ScreenOverlay>(this) {
            @Override
            public boolean test(final ScreenOverlay overlay) {
                return overlay.getClass() == overlayClass;
            }
        });
    }
    
    public void overlayUpdated(final OldOverlayWidgetActivity overlay) {
    }
    
    private void internalLoadMarkup() {
        this.reloadWatcher.addInitializeListener(new Runnable() {
            @Override
            public void run() {
                if (ElementActivity.this.document().handleStyleSheet) {
                    Activity.this.loadMeta();
                }
            }
        }, true);
    }
    
    public ListenerRegistry listenerRegistry() {
        return this.listenerRegistry;
    }
    
    private void updateMediaIdentifiers() {
        for (final StyleSheet stylesheet : ((Document)this.document).getStyleSheets()) {
            for (final MediaRule mediaRule : stylesheet.getMediaRules()) {
                if (mediaRule.getIdentifier() instanceof DocumentIdentifier) {
                    mediaRule.getIdentifier().updateRectangle(((Document)this.document).bounds().rectangle(BoundsType.MIDDLE));
                }
            }
        }
    }
    
    public WidgetReference displayInOverlay(final List<StyleSheet> styles, final Widget widget) {
        return this.labyAPI.screenOverlayHandler().displayInOverlay(this, styles, widget);
    }
    
    protected Theme theme() {
        return this.theme;
    }
    
    protected <T extends LabyScreen> T generic() {
        return (T)this;
    }
    
    protected void bindToBranch(final String branchName) {
        this.boundBranch = branchName;
    }
    
    public boolean listenForEvents() {
        return true;
    }
    
    @Override
    public LssVariableHolder getParentVariableHolder() {
        return (this.parent instanceof LssVariableHolder) ? this.parent : null;
    }
    
    @Override
    public Map<String, LssVariable> getLssVariables() {
        return this.lssVariables;
    }
    
    @Override
    public void updateLssVariable(final LssVariable variable) {
        ((Document)this.document).updateLssVariable(variable);
    }
    
    @Override
    public void forceUpdateLssVariable(final LssVariable variable) {
        ((Document)this.document).forceUpdateLssVariable(variable);
    }
    
    @Override
    public boolean shouldDocumentHandleKey(final Key key, final InputType type) {
        return key != Key.ESCAPE || this.shouldHandleEscape();
    }
    
    public boolean shouldHandleEscape() {
        for (final Widget child : ((Document)this.document).getChildren()) {
            if (child.shouldHandleEscape()) {
                return true;
            }
        }
        return false;
    }
}
