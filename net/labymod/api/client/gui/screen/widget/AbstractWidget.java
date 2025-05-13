// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget;

import net.labymod.api.property.Property;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.util.math.vector.Matrix4;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import java.util.ConcurrentModificationException;
import net.labymod.api.client.gui.screen.widget.action.FilterAction;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import net.labymod.api.client.gui.screen.widget.attributes.animation.AttributeAnimation;
import net.labymod.api.client.gui.screen.widget.attributes.animation.AttributeAnimationAccessor;
import net.labymod.api.client.gui.screen.widget.attributes.animation.AttributePatchAnimationAccessor;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import java.util.function.Function;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import java.util.Optional;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.OffsetSide;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import java.util.function.Predicate;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.concurrent.task.Task;
import java.util.Collection;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import java.util.Objects;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.debug.DebugWidgetRenderer;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.widget.util.WidgetUtil;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;
import net.labymod.api.client.gui.screen.activity.Activity;
import java.util.Iterator;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.util.WidgetDataCollector;
import net.labymod.api.Laby;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.property.NotNullPropertyConvention;
import net.labymod.api.property.PropertyConvention;
import net.labymod.api.property.ClampFloatPropertyConvention;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;
import net.labymod.api.util.collection.list.ThreadSafeArrayList;
import net.labymod.api.client.gui.screen.util.StatefulRenderer;
import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.util.WidgetTransformer;
import net.labymod.api.client.gui.screen.widget.renderer.WidgetRendererContext;
import net.labymod.api.client.gui.screen.widget.util.WidgetMeta;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.client.gfx.pipeline.pass.passes.StencilRenderPass;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.screen.widget.size.WidgetSizeList;
import net.labymod.api.client.render.font.RenderableComponent;
import java.util.function.BooleanSupplier;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.attributes.Border;
import net.labymod.api.client.gui.screen.widget.attributes.Shadow;
import net.labymod.api.client.gui.screen.widget.attributes.DirtBackgroundType;
import net.labymod.api.client.gui.screen.widget.attributes.Filter;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.screen.widget.attributes.BoxSizing;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import java.util.Map;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.Lazy;
import net.labymod.api.client.gui.lss.property.PropertyRegistry;
import net.labymod.api.client.gui.mouse.MouseAction;
import net.labymod.api.client.gui.screen.widget.attributes.animation.Animation;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import java.util.List;
import net.labymod.api.LabyAPI;
import java.util.Comparator;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.render.statistics.FrameTimer;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater;

@AutoWidget
public abstract class AbstractWidget<T extends Widget> extends StyledWidget implements WidgetStyleSheetUpdater
{
    protected static final RenderEnvironmentContext RENDER_ENVIRONMENT_CONTEXT;
    private static final Logging LOGGER;
    private static final FrameTimer FRAME_TIMER;
    private static final ModifyReason FIT_SIZE_INIT;
    private static final ModifyReason FULL_PARENT_SIZE;
    private static final ModifyReason MAINTAIN_ASPECT_RATIO;
    private static final ModifyReason LSS_SIZE;
    private static final ModifyReason ALIGNMENT;
    private static final ModifyReason RELATIVE_BOUNDS_TOP;
    private static final ModifyReason RELATIVE_BOUNDS_BOTTOM;
    private static final ModifyReason RELATIVE_BOUNDS_LEFT;
    private static final ModifyReason RELATIVE_BOUNDS_RIGHT;
    private static final ModifyReason MIN_SIZE;
    private static final ModifyReason MAX_SIZE;
    private static final Comparator<? super Widget> DEFAULT_WIDGET_COMPARATOR;
    protected final LabyAPI labyAPI;
    protected final List<T> children;
    private final PositionedBounds bounds;
    protected final Animation animation;
    protected final MouseAction lastMouseClick;
    private final PropertyRegistry propertyRegistry;
    private final Lazy<String> uniqueId;
    private final List<CharSequence> ids;
    private final LssProperty<ThemeRenderer> renderer;
    private final LssProperty<Boolean> pressable;
    private final LssProperty<Boolean> visible;
    private final Map<AttributeState, AttributeStateValue> attributeStates;
    private final Map<AttributeState, AttributeStateValue> staticAttributeStates;
    private final LssProperty<Float> left;
    private final LssProperty<Float> top;
    private final LssProperty<Float> right;
    private final LssProperty<Float> bottom;
    private final LssProperty<Float> marginLeft;
    private final LssProperty<Float> marginTop;
    private final LssProperty<Float> marginRight;
    private final LssProperty<Float> marginBottom;
    private final LssProperty<Float> paddingLeft;
    private final LssProperty<Float> paddingTop;
    private final LssProperty<Float> paddingRight;
    private final LssProperty<Float> paddingBottom;
    private final LssProperty<Float> widthPrecision;
    private final LssProperty<Float> heightPrecision;
    private final LssProperty<BoxSizing> boxSizing;
    private final LssProperty<PriorityLayer> priorityLayer;
    private final LssProperty<WidgetAlignment> alignmentX;
    private final LssProperty<WidgetAlignment> alignmentY;
    private final LssProperty<Float> translateX;
    private final LssProperty<Float> translateY;
    private final LssProperty<Float> zIndex;
    private final LssProperty<Float> scaleX;
    private final LssProperty<Float> scaleY;
    private final LssProperty<Float> opacity;
    private final LssProperty<Float> mouseRenderDistance;
    private final LssProperty<Integer> animationDuration;
    private final LssProperty<CubicBezier> animationTimingFunction;
    private final LssProperty<Boolean> stencilX;
    private final LssProperty<Boolean> stencilY;
    private final LssProperty<Boolean> stencilTranslation;
    private final LssProperty<Boolean> writeToStencilBuffer;
    private final LssProperty<Boolean> useFloatingPointPosition;
    private final LssProperty<Boolean> alwaysFocused;
    private final LssProperty<Boolean> interactableOutside;
    private final LssProperty<Long> destroyDelay;
    private final LssProperty<Boolean> fitOuter;
    private final LssProperty<Boolean> renderChildren;
    private final LssProperty<Boolean> draggable;
    private final LssProperty<Boolean> interactable;
    private final LssProperty<Boolean> distinct;
    private final LssProperty<Boolean> cancelParentHoverComponent;
    private final LssProperty<Boolean> clearDepth;
    private final LssProperty<Boolean> forceVanillaFont;
    private final LssProperty<Filter[]> filter;
    private final LssProperty<Integer> backgroundColor;
    private final LssProperty<Boolean> backgroundAlwaysDirt;
    private final LssProperty<DirtBackgroundType> backgroundDirtType;
    private final LssProperty<Integer> backgroundDirtShift;
    private final LssProperty<Integer> hoverBoxDelay;
    private final LssProperty<Long> backgroundColorTransitionDuration;
    private final LssProperty<Float> fontWeight;
    private final Map<String, Runnable> actionListeners;
    public boolean handleStyleSheet;
    public Shadow shadow;
    public Border border;
    public Icon backgroundImage;
    protected String name;
    protected String qualifiedName;
    protected Parent parent;
    protected boolean inOverlay;
    private BorderRadius borderRadius;
    private Metadata metadata;
    private BooleanSupplier pressListener;
    private RenderableComponent hoverComponent;
    private boolean destroyed;
    private boolean disposed;
    private boolean ticking;
    private long lastActionTime;
    private long lastInitialTime;
    private long lastDestroyTime;
    private long lastActiveChangedTime;
    protected final long widgetCreateTimestamp;
    private boolean outOfBounds;
    protected boolean initialized;
    protected boolean lazy;
    protected boolean pendingInitialize;
    protected boolean pressOnRelease;
    private Comparator<? super Widget> comparator;
    protected float mouseClickedX;
    protected float mouseClickedY;
    protected int childBoundsUpdates;
    private final WidgetSizeList sizes;
    private final Map<String, LssVariable> lssVariables;
    private final StencilRenderPass stencilRenderPass;
    protected boolean shouldUseStencilBuffer;
    @ApiStatus.Experimental
    private int lastBoundsUpdateFrame;
    private final WidgetMeta widgetMeta;
    private final WidgetRendererContext widgetRendererContext;
    private final WidgetTransformer widgetTransformer;
    private ContextMenu contextMenu;
    private Consumer<ContextMenu> contextMenuFactory;
    private DirectPropertyValueAccessor directPropertyValueAccessor;
    @Deprecated(forRemoval = true, since = "4.2.42")
    private final StatefulRenderer.StateProvider legacyRenderProvider;
    @Deprecated(forRemoval = true, since = "4.2.42")
    private final StatefulRenderer.StateProvider legacyRenderWidgetProvider;
    @Deprecated
    private final LssProperty<Integer> backgroundDirtBrightness;
    
    protected AbstractWidget() {
        this.children = new ThreadSafeArrayList<T>();
        this.bounds = new PositionedBounds(this);
        this.animation = new Animation(this);
        this.lastMouseClick = new MouseAction();
        this.uniqueId = Lazy.of(() -> UUID.randomUUID().toString());
        this.ids = new ArrayList<CharSequence>();
        this.renderer = new LssProperty<ThemeRenderer>(null);
        this.pressable = new LssProperty<Boolean>(true);
        this.visible = new LssProperty<Boolean>(true);
        this.attributeStates = new HashMap<AttributeState, AttributeStateValue>();
        this.staticAttributeStates = new HashMap<AttributeState, AttributeStateValue>();
        this.left = new LssProperty<Float>(null);
        this.top = new LssProperty<Float>(null);
        this.right = new LssProperty<Float>(null);
        this.bottom = new LssProperty<Float>(null);
        this.marginLeft = new LssProperty<Float>(0.0f);
        this.marginTop = new LssProperty<Float>(0.0f);
        this.marginRight = new LssProperty<Float>(0.0f);
        this.marginBottom = new LssProperty<Float>(0.0f);
        this.paddingLeft = new LssProperty<Float>(0.0f);
        this.paddingTop = new LssProperty<Float>(0.0f);
        this.paddingRight = new LssProperty<Float>(0.0f);
        this.paddingBottom = new LssProperty<Float>(0.0f);
        this.widthPrecision = new LssProperty<Float>(null);
        this.heightPrecision = new LssProperty<Float>(null);
        this.boxSizing = new LssProperty<BoxSizing>(BoxSizing.CONTENT_BOX);
        this.priorityLayer = new LssProperty<PriorityLayer>(PriorityLayer.NORMAL);
        this.alignmentX = new LssProperty<WidgetAlignment>(WidgetAlignment.LEFT);
        this.alignmentY = new LssProperty<WidgetAlignment>(WidgetAlignment.TOP);
        this.translateX = new LssProperty<Float>(0.0f);
        this.translateY = new LssProperty<Float>(0.0f);
        this.zIndex = new LssProperty<Float>(0.0f);
        this.scaleX = new LssProperty<Float>(1.0f);
        this.scaleY = new LssProperty<Float>(1.0f);
        this.opacity = new LssProperty<Float>(1.0f, new ClampFloatPropertyConvention(0.0f, 1.0f));
        this.mouseRenderDistance = new LssProperty<Float>(0.0f);
        this.animationDuration = new LssProperty<Integer>(0);
        this.animationTimingFunction = new LssProperty<CubicBezier>(CubicBezier.EASE_IN_OUT);
        this.stencilX = new LssProperty<Boolean>(false);
        this.stencilY = new LssProperty<Boolean>(false);
        this.stencilTranslation = new LssProperty<Boolean>(false);
        this.writeToStencilBuffer = new LssProperty<Boolean>(false);
        this.useFloatingPointPosition = new LssProperty<Boolean>(false);
        this.alwaysFocused = new LssProperty<Boolean>(false);
        this.interactableOutside = new LssProperty<Boolean>(false);
        this.destroyDelay = new LssProperty<Long>(0L);
        this.fitOuter = new LssProperty<Boolean>(false);
        this.renderChildren = new LssProperty<Boolean>(true);
        this.draggable = new LssProperty<Boolean>(false);
        this.interactable = new LssProperty<Boolean>(true);
        this.distinct = new LssProperty<Boolean>(true);
        this.cancelParentHoverComponent = new LssProperty<Boolean>(true);
        this.clearDepth = new LssProperty<Boolean>(false);
        this.forceVanillaFont = new LssProperty<Boolean>(false, new NotNullPropertyConvention<Boolean>(false));
        this.filter = new LssProperty<Filter[]>(null);
        this.backgroundColor = new LssProperty<Integer>(0, new NotNullPropertyConvention<Integer>(0));
        this.backgroundAlwaysDirt = new LssProperty<Boolean>(false);
        this.backgroundDirtType = new LssProperty<DirtBackgroundType>(DirtBackgroundType.LIST);
        this.backgroundDirtShift = new LssProperty<Integer>(0);
        this.hoverBoxDelay = new LssProperty<Integer>(0, new NotNullPropertyConvention<Integer>(0));
        this.backgroundColorTransitionDuration = new LssProperty<Long>(0L);
        this.fontWeight = new LssProperty<Float>(0.0f);
        this.actionListeners = new HashMap<String, Runnable>();
        this.handleStyleSheet = true;
        this.parent = null;
        this.metadata = Metadata.create();
        this.ticking = true;
        this.widgetCreateTimestamp = TimeUtil.getMillis();
        this.initialized = false;
        this.lazy = false;
        this.pendingInitialize = false;
        this.pressOnRelease = false;
        this.comparator = AbstractWidget.DEFAULT_WIDGET_COMPARATOR;
        this.mouseClickedX = -1.0f;
        this.mouseClickedY = -1.0f;
        this.childBoundsUpdates = 0;
        this.sizes = new WidgetSizeList();
        this.lssVariables = new HashMap<String, LssVariable>();
        this.stencilRenderPass = new StencilRenderPass();
        this.shouldUseStencilBuffer = true;
        this.lastBoundsUpdateFrame = 0;
        this.widgetMeta = new WidgetMeta();
        this.widgetRendererContext = new WidgetRendererContext(this);
        this.widgetTransformer = new WidgetTransformer(this);
        this.legacyRenderProvider = new StatefulRenderer.StateProvider(false);
        this.legacyRenderWidgetProvider = new StatefulRenderer.StateProvider(false);
        this.backgroundDirtBrightness = new LssProperty<Integer>(0);
        this.labyAPI = Laby.labyAPI();
        this.propertyRegistry = Laby.references().propertyRegistry();
        WidgetDataCollector.collectData(this.getClass(), (qualifiedName, name, directPropertyValueAccessor) -> {
            this.qualifiedName = qualifiedName;
            this.name = name;
            this.directPropertyValueAccessor = directPropertyValueAccessor;
            return;
        });
        this.visible.addChangeListener((type, oldValue, newValue) -> this.allowCustomFont(() -> this.notifyParentChildBoundsChanged(this.bounds.prevRectangle(), this.bounds, false)));
        this.forceVanillaFont.addChangeListener((type, oldValue, newValue) -> this.notifyParentChildBoundsChanged(this.bounds.prevRectangle(), this.bounds, false));
        this.reset();
    }
    
    @Override
    public void reset() {
        this.dispose();
        final LssPropertyResetter resetter = this.propertyRegistry.getPropertyResetter(this);
        if (resetter != null) {
            resetter.reset(this);
        }
        this.initializeThemeRenderer();
        this.border = null;
        this.shadow = null;
        this.borderRadius = null;
        this.childBoundsUpdates = 0;
        this.outOfBounds = false;
        this.disposed = false;
        this.destroyed = false;
        if (this.isDragging()) {
            this.setDragging(false);
        }
        for (final T child : this.children) {
            child.reset();
        }
        ThreadSafe.ensureRenderThread();
        this.children.clear();
        this.sizes.reset();
        super.reset();
    }
    
    private void initializeThemeRenderer() {
        final String rendererName = this.getDefaultRendererName();
        if (rendererName != null) {
            final ThemeRenderer renderer = Laby.references().themeRendererParser().parse(rendererName);
            this.renderer.set(renderer);
        }
    }
    
    @Override
    public void initialize(final Parent parent) {
        this.initializeLazy(parent);
        this.initialized = true;
        this.pendingInitialize = false;
    }
    
    @Override
    public final void initializeLazy(final Parent parent) {
        Laby.references().screenWindowManagement().widgetPreInitialize(this, parent);
        this.loadOverlayState(this.parent = parent);
        this.lastInitialTime = TimeUtil.getMillis();
        this.pendingInitialize = true;
    }
    
    private void updateLazy() {
        if (!this.pendingInitialize || this.parent == null || !this.visible.get()) {
            return;
        }
        if (!this.isOutOfBounds()) {
            this.reInitialize();
        }
    }
    
    private void loadOverlayState(Parent parent) {
        if (parent instanceof Activity && !(parent instanceof ScreenOverlay)) {
            parent = parent.getParent();
        }
        this.inOverlay = (parent != null && ((parent instanceof AbstractWidget && ((AbstractWidget)parent).inOverlay) || parent instanceof ScreenOverlay));
    }
    
    @Override
    public void postInitialize() {
        for (final T child : this.children) {
            if (child.isLazy()) {
                child.initializeLazy(this);
            }
            else {
                child.initialize(this);
            }
            child.postInitialize();
        }
        if (!this.styleSheets.isEmpty()) {
            this.reloadStyleSheets();
        }
    }
    
    @Override
    public void postStyleSheetLoad() {
        for (final T widget : this.children) {
            widget.postStyleSheetLoad();
        }
        this.handleAttributes();
    }
    
    private void applyCustomStyleSheets() {
        this.applyCustomStyleSheetsLocal();
        this.applyCustomStyleSheetsForChildren();
    }
    
    private void applyCustomStyleSheetsLocal() {
        Laby.references().linkMetaLoader().loadMeta(this.getClass(), this::applyStyleSheet);
    }
    
    private void applyCustomStyleSheetsForChildren() {
        for (Widget child : this.children) {
            child = WidgetUtil.unwrapWidget(child);
            if (child instanceof final AbstractWidget abstractWidget) {
                abstractWidget.applyCustomStyleSheets();
            }
        }
    }
    
    @Override
    public void applyStyleSheet(final StyleSheet styleSheet) {
        if (this.handleStyleSheet) {
            super.applyStyleSheet(styleSheet);
        }
        for (final T widget : this.children) {
            widget.applyStyleSheet(styleSheet);
        }
        this.applyCustomStyleSheetsForChildren();
        if (this.handleStyleSheet) {
            this.sortChildren();
        }
        if (this.alwaysFocused.get()) {
            this.setFocused(true);
        }
        this.updateContentBounds();
    }
    
    @Override
    public String getDefaultRendererName() {
        return "Background";
    }
    
    @Override
    public void reInitialize(final Parent parent) {
        ThreadSafe.ensureRenderThread();
        if (!this.initialized && !this.pendingInitialize) {
            return;
        }
        this.allowCustomFont(() -> {
            final boolean wasLazy = this.pendingInitialize;
            this.pendingInitialize = false;
            final StyleSheet[] styleSheets = this.styleSheets.toArray(new StyleSheet[0]);
            this.reset();
            this.initialize(parent);
            this.applyCustomStyleSheets();
            this.postInitialize();
            try {
                this.preventStateUpdate = true;
                final StyleSheet[] array;
                int i = 0;
                for (int length = array.length; i < length; ++i) {
                    final StyleSheet styleSheet = array[i];
                    this.applyStyleSheet(styleSheet);
                }
            }
            finally {
                this.preventStateUpdate = false;
                this.updateState(true);
            }
            this.postStyleSheetLoad();
            this.sortChildren();
            this.updateState(true);
            if (parent instanceof final Widget parentWidget) {
                if (!wasLazy) {
                    parentWidget.sortChildren();
                }
                parentWidget.handleAttributes();
                parentWidget.updateBounds();
            }
        });
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        if (this.parent == null || !this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.POSITION)) {
            this.handleSizeAttributes();
        }
        this.applyMediaRules(true);
        for (final T child : this.children) {
            child.patchAttributes();
            child.handleAttributes();
        }
        this.notifyParentChildBoundsChanged(previousRect, newRect, true);
        if (!previousRect.equalsBounds(newRect)) {
            this.updateContentBounds();
            this.lastBoundsUpdateFrame = AbstractWidget.FRAME_TIMER.getFrame();
        }
    }
    
    private void notifyParentChildBoundsChanged(final Rectangle previousRect, final Rectangle newRect, final boolean count) {
        if (!(this.parent instanceof StyledWidget)) {
            return;
        }
        if (count && ++this.childBoundsUpdates > 1) {
            return;
        }
        ((AbstractWidget)this.parent).onChildBoundsChanged(this.actualWidget, previousRect, newRect);
    }
    
    protected void onChildBoundsChanged(final T child, final Rectangle previousRect, final Rectangle newRect) {
        this.updateContentBounds();
        this.notifyParentChildBoundsChanged(this.bounds.prevRectangle(), this.bounds, true);
    }
    
    @Override
    public void updateState(final boolean force) {
        if (force) {
            this.childBoundsUpdates = 1;
        }
        super.updateState(force);
    }
    
    @Override
    public boolean isUsingFloatingPointPosition() {
        return this.useFloatingPointPosition.get();
    }
    
    @Override
    public void unfocus() {
        for (final T child : this.children) {
            child.unfocus();
        }
        this.setFocused(false);
    }
    
    @Override
    public void render(final ScreenContext context) {
        context.executeFlushGraphics();
        if (this.clearDepth.get()) {
            this.labyAPI.gfxRenderPipeline().gfx().clearDepth();
        }
        final PriorityLayer priorityLayer = this.priorityLayer.get();
        final boolean front = priorityLayer == PriorityLayer.FRONT;
        final boolean veryFront = priorityLayer == PriorityLayer.VERY_FRONT;
        final boolean isFrontLayer = front || veryFront;
        if (isFrontLayer) {
            context.pushStack();
            context.translate(0.0f, 0.0f, front ? 1.0f : 2.0f);
        }
        super.render(context);
        this.prepareWidgetRendering(context);
        final boolean writeToStencilBuffer = this.writeToStencilBuffer.get();
        if (this.isVisible()) {
            if (writeToStencilBuffer) {
                final GFXRenderPipeline pipeline = this.labyAPI.gfxRenderPipeline();
                pipeline.renderToActivityTarget(target -> this.renderWithScissor(context));
                pipeline.clear(pipeline.getActivityRenderTarget());
            }
            else {
                this.renderWithScissor(context);
            }
        }
        else {
            this.setAttributeState(AttributeState.HOVER, false);
        }
        this.childBoundsUpdates = 0;
        if (isFrontLayer) {
            context.popStack();
        }
    }
    
    @Deprecated(forRemoval = true, since = "4.2.42")
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float tickDelta) {
        final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        screenContext.runInContext(stack, mouse, tickDelta, this::render);
    }
    
    private void renderWithScissor(final ScreenContext context) {
        final boolean stencilTranslation = this.stencilTranslation.get();
        if (stencilTranslation) {
            final Scissor scissor = this.labyAPI.gfxRenderPipeline().scissor();
            scissor.push(context.stack(), this.bounds().rectangle(BoundsType.MIDDLE), this.useFloatingPointPosition.get());
            try {
                this.renderInternal(context);
            }
            catch (final Throwable throwable) {
                AbstractWidget.LOGGER.error("Failed to render widget while using scissor ({})", this.getTypeName(), throwable);
            }
            scissor.pop();
        }
        else {
            this.renderInternal(context);
        }
    }
    
    private void renderInternal(final ScreenContext context) {
        final MutableMouse mouse = context.mouse();
        final Bounds bounds = this.bounds();
        final FloatVector2 pivot = this.getPivot();
        final float x = bounds.getX(BoundsType.OUTER);
        final float y = bounds.getY(BoundsType.OUTER);
        final float mouseX = this.widgetTransformer.transformMouseX((float)mouse.getX(), x + pivot.getX());
        final float mouseY = this.widgetTransformer.transformMouseY((float)mouse.getY(), y + pivot.getY());
        final boolean prevHovered = this.isHovered();
        final boolean newHovered = this.isHovered(mouseX, mouseY);
        this.setAttributeState(AttributeState.HOVER, newHovered);
        bounds.checkForChanges();
        if (!this.isInRenderDistance(mouse)) {
            return;
        }
        context.pushStack();
        this.widgetTransformer.applyStackManipulation(context, x, y, pivot);
        final double currentX = mouse.getXDouble();
        final double currentY = mouse.getYDouble();
        mouse.set(mouseX, mouseY);
        final float opacity = this.opacity.get();
        final boolean opaque = opacity > 0.0f && opacity < 1.0f;
        if (opaque) {
            this.widgetMeta.multiplyAlpha(opacity);
        }
        if (opacity > 0.0f) {
            try {
                this.renderWidgetTheme(context);
            }
            catch (final Throwable throwable) {
                AbstractWidget.LOGGER.error("Failed to render widget", throwable);
            }
        }
        if (opaque) {
            this.widgetMeta.revertAlphaState();
        }
        DebugWidgetRenderer.renderDebug(context, this);
        mouse.set(currentX, currentY);
        context.popStack();
    }
    
    protected boolean isHovered(final float mouseX, final float mouseY) {
        final ReasonableMutableRectangle middleRectangle = this.bounds.rectangle(BoundsType.MIDDLE);
        return (!(this.parent instanceof Widget) || ((Widget)this.parent).isHovered()) && middleRectangle.isInRectangle(mouseX, mouseY) && (this.inOverlay || !this.labyAPI.screenOverlayHandler().isOverlayHovered()) && this.canHover();
    }
    
    public FloatVector2 getPivot() {
        return this.widgetTransformer.getPivot();
    }
    
    @Override
    public boolean transformMouse(final MutableMouse mouse, final BooleanSupplier handler) {
        final FloatVector2 pivot = this.getPivot();
        return mouse.set(this.widgetTransformer.transformMouseX((float)mouse.getX(), pivot.getX()), this.widgetTransformer.transformMouseY((float)mouse.getY(), pivot.getY()), handler);
    }
    
    protected void onAttributeStateChanged(final AttributeState state, final boolean newState) {
        this.updateState(true);
    }
    
    protected boolean isInRenderDistance(final Mouse mouse) {
        if (this.mouseRenderDistance.get() != 0.0f) {
            final float dx = this.bounds().getCenterX(BoundsType.OUTER) - mouse.getX();
            final float dy = this.bounds().getCenterY(BoundsType.OUTER) - mouse.getY();
            final float distanceSq = dx * dx + dy * dy;
            final float maxSq = this.mouseRenderDistance.get() * this.mouseRenderDistance.get();
            return distanceSq <= maxSq;
        }
        return true;
    }
    
    @Override
    public boolean hasTabFocus() {
        return false;
    }
    
    protected void renderWidgetTheme(final ScreenContext context) {
        final GFXRenderPipeline renderPipeline = this.labyAPI.gfxRenderPipeline();
        final GFXBridge gfx = renderPipeline.gfx();
        if (this.shouldUseStencilBuffer) {
            final boolean writeToStencilBuffer = this.writeToStencilBuffer.get();
            if (writeToStencilBuffer) {
                gfx.enableStencil();
                this.stencilRenderPass.begin();
                this.renderWidgetTheme(context, false);
                this.stencilRenderPass.end();
            }
            this.renderWidgetTheme(context, true);
            if (writeToStencilBuffer) {
                gfx.disableStencil();
            }
        }
        else {
            final boolean isActivityRenderTarget = renderPipeline.isActivityRenderTarget();
            if (isActivityRenderTarget) {
                gfx.disableStencil();
            }
            this.renderWidgetTheme(context, true);
            if (isActivityRenderTarget) {
                gfx.enableStencil();
            }
        }
    }
    
    private void renderWidgetTheme(final ScreenContext context, final boolean notWriteToStencilBuffer) {
        final boolean useWidgetRenderer = !notWriteToStencilBuffer || !this.writeToStencilBuffer.get();
        final ThemeRenderer themeRenderer = this.renderer.get();
        this.widgetRendererContext.setThemeRenderer(themeRenderer);
        if (useWidgetRenderer) {
            this.widgetRendererContext.renderPre(context);
        }
        if (notWriteToStencilBuffer) {
            final StatefulRenderer.StateProvider legacyRenderWidgetProvider = this.legacyRenderWidgetProvider;
            final BooleanSupplier useLegacy = () -> StatefulRenderer.isWidgetRenderWidgetOverridden(this.getClass());
            final WidgetRendererContext widgetRendererContext = this.widgetRendererContext;
            Objects.requireNonNull(widgetRendererContext);
            StatefulRenderer.tryRender(context, legacyRenderWidgetProvider, useLegacy, widgetRendererContext::renderWidget, c -> this.renderWidget(c.stack(), c.mouse(), c.getTickDelta()));
        }
        if (useWidgetRenderer) {
            this.widgetRendererContext.renderPost(context);
        }
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        if (this.isVisible() && this.renderChildren.get()) {
            boolean sort = false;
            for (int size = this.children.size(), i = 0; i < size; ++i) {
                try {
                    final T widget = this.children.get(i);
                    if (widget != null) {
                        final boolean forceVanillaFont = this.forceVanillaFont.get();
                        final RenderAttributesStack renderAttributesStack = AbstractWidget.RENDER_ENVIRONMENT_CONTEXT.renderAttributesStack();
                        final RenderAttributes attributes = renderAttributesStack.pushAndGet();
                        attributes.setFontWeight(widget.getFontWeight());
                        if (forceVanillaFont) {
                            attributes.setForceVanillaFont(true);
                        }
                        attributes.apply();
                        try {
                            final StatefulRenderer.StateProvider legacyRenderProvider = this.legacyRenderProvider;
                            final BooleanSupplier useLegacy = () -> StatefulRenderer.isWidgetRenderOverridden(this.getClass());
                            final Widget obj = widget;
                            Objects.requireNonNull(obj);
                            StatefulRenderer.tryRender(context, legacyRenderProvider, useLegacy, obj::render, c -> widget.render(c.stack(), c.mouse(), c.getTickDelta()));
                        }
                        catch (final Throwable throwable) {
                            AbstractWidget.LOGGER.error("Failed to render widget ({})", this.getTypeName(), throwable);
                        }
                        renderAttributesStack.pop();
                        if (widget.priorityLayer().wasUpdatedThisFrame()) {
                            sort = true;
                        }
                    }
                }
                catch (final Throwable throwable2) {
                    AbstractWidget.LOGGER.error("Failed to render widget", throwable2);
                }
            }
            if (sort) {
                this.sortChildren();
            }
        }
    }
    
    @Deprecated(forRemoval = true, since = "4.2.42")
    @Override
    public void renderWidget(final Stack stack, final MutableMouse mouse, final float tickDelta) {
        final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        screenContext.runInContext(stack, mouse, tickDelta, this::render);
    }
    
    @Override
    public void tick() {
        final boolean forceVanillaFont = this.forceVanillaFont.get();
        final RenderAttributesStack stack = AbstractWidget.RENDER_ENVIRONMENT_CONTEXT.renderAttributesStack();
        final RenderAttributes attributes = stack.pushAndGet();
        if (forceVanillaFont) {
            attributes.setForceVanillaFont(true);
        }
        attributes.setFontWeight(this.getFontWeight());
        attributes.apply();
        try {
            super.tick();
        }
        catch (final Throwable throwable) {
            AbstractWidget.LOGGER.error("Failed to tick widget ({})", this.getTypeName(), throwable);
        }
        stack.pop();
    }
    
    @Override
    public void renderOverlay(final ScreenContext context) {
        if (this.isVisible() && this.opacity.get() > 0.0f) {
            for (final T widget : this.children) {
                final float opacity = (widget instanceof AbstractWidget) ? ((AbstractWidget)widget).opacity().get() : 1.0f;
                this.widgetMeta.multiplyAlpha(opacity);
                try {
                    widget.renderOverlay(context);
                }
                catch (final Throwable throwable) {
                    AbstractWidget.LOGGER.error("Failed to render widget overlay ({})", widget.getTypeName(), throwable);
                }
                this.widgetMeta.revertAlphaState();
            }
        }
    }
    
    @Override
    public void renderHoverComponent(final ScreenContext context) {
        if (this.isVisible() && this.opacity.get() > 0.0f) {
            boolean renderHover = this.isHoverComponentRendered();
            for (final T widget : this.children) {
                widget.renderHoverComponent(context);
                if (renderHover && this.shouldCancelHoverComponent(widget)) {
                    final AttributeStateValue attributeStateValue = this.attributeStates.get(AttributeState.HOVER);
                    if (attributeStateValue != null) {
                        attributeStateValue.lastUpdate = TimeUtil.getMillis();
                    }
                    renderHover = false;
                }
            }
            if (renderHover && this.hoverComponent != null) {
                Laby.references().tooltipService().renderTooltip(context.stack(), this, context.mouse(), this.hoverComponent);
            }
            else {
                Laby.references().tooltipService().unhover(this);
            }
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        this.lastMouseClick.update(mouseButton);
        if (this.isHovered() && this.shouldFocusOnClick()) {
            this.setFocused(true);
        }
        if (super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        if ((this.isHovered() || (this.isInteractableOutside() && this.bounds.isInRectangle(mouse))) && this.visible.get()) {
            if (mouseButton == MouseButton.RIGHT && this.hasContextMenu() && !this.labyAPI.minecraft().isMouseDown(MouseButton.LEFT)) {
                this.openContextMenu();
                return true;
            }
            if (mouseButton == MouseButton.LEFT) {
                this.mouseClickedX = (float)mouse.getX();
                this.mouseClickedY = (float)mouse.getY();
                if (!this.pressOnRelease) {
                    return this.onPress();
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        return super.mouseReleased(mouse, mouseButton) || (this.pressOnRelease && (this.isHovered() || (this.isInteractableOutside() && this.bounds.isInRectangle(mouse))) && this.visible.get() && mouseButton == MouseButton.LEFT && mouse.getX() == this.mouseClickedX && mouse.getY() == this.mouseClickedY && this.onPress());
    }
    
    @Override
    public boolean onPress() {
        if (this.pressListener != null && this.pressable.get()) {
            final boolean playInteractionSoundAfterHandling = this.playInteractionSoundAfterHandling();
            if (!playInteractionSoundAfterHandling) {
                this.playInteractionSoundInternal();
            }
            final boolean handled = this.pressListener.getAsBoolean();
            if (playInteractionSoundAfterHandling && handled) {
                this.playInteractionSoundInternal();
            }
            return handled;
        }
        return false;
    }
    
    @ApiStatus.Internal
    public boolean playInteractionSoundInternal() {
        final ThemeRenderer themeRenderer = this.renderer.get();
        if (themeRenderer == null || !themeRenderer.hasInteractionSound()) {
            final SoundType interactionSound = this.getInteractionSound();
            if (interactionSound != null) {
                Laby.references().soundService().play(interactionSound);
            }
            return interactionSound != null;
        }
        themeRenderer.playInteractionSound(this);
        return true;
    }
    
    protected boolean shouldFocusOnClick() {
        return true;
    }
    
    protected SoundType getInteractionSound() {
        return null;
    }
    
    @Deprecated
    public Pressable getPressable() {
        if (this.pressListener == null) {
            return null;
        }
        return () -> this.pressListener.getAsBoolean();
    }
    
    public boolean hasPressListener() {
        return this.pressListener != null;
    }
    
    @Override
    public void setPressable(final Pressable pressable) {
        if (pressable == null) {
            this.pressListener = null;
            return;
        }
        this.pressListener = (() -> {
            pressable.press();
            return true;
        });
    }
    
    @Override
    public void setPressListener(final BooleanSupplier pressable) {
        this.pressListener = pressable;
    }
    
    protected boolean playInteractionSoundAfterHandling() {
        return false;
    }
    
    @Override
    public boolean isDragging() {
        return this.isAttributeStateEnabled(AttributeState.DRAGGING);
    }
    
    @Override
    public void setDragging(final boolean dragging) {
        this.setAttributeState(AttributeState.DRAGGING, dragging);
    }
    
    @Override
    public boolean isDraggable() {
        return this.draggable.get();
    }
    
    @Override
    public boolean isInteractable() {
        return this.interactable.get();
    }
    
    @Override
    public boolean isHovered() {
        return this.isAttributeStateEnabled(AttributeState.HOVER);
    }
    
    @Override
    public boolean isInteractableOutside() {
        return this.interactableOutside.get();
    }
    
    protected boolean canHover() {
        return true;
    }
    
    @Override
    public Widget getHighestParentWidget() {
        final Parent parent = this.getParent();
        if (parent instanceof final Widget widget) {
            return widget.getHighestParentWidget();
        }
        return this;
    }
    
    public T getChild(final String id) {
        for (final T child : this.children) {
            if (child.hasId(id)) {
                return child;
            }
        }
        return null;
    }
    
    public Widget getChildRecursive(final String id) {
        final T directChild = this.getChild(id);
        if (directChild != null) {
            return directChild;
        }
        for (final T child : this.children) {
            if (child instanceof final AbstractWidget abstractWidget) {
                final Widget recursiveChild = abstractWidget.getChildRecursive(id);
                if (recursiveChild != null) {
                    return recursiveChild;
                }
                continue;
            }
        }
        return null;
    }
    
    public Widget getUnwrappedChildRecursive(final String id) {
        final Widget child = this.getChildRecursive(id);
        return WidgetUtil.unwrapWidget(child);
    }
    
    public T addChild(final T widget) {
        return this.addChild(this.children.size(), widget);
    }
    
    public T addChild(final T widget, final boolean shouldSort) {
        return this.addChild(this.children.size(), widget, shouldSort);
    }
    
    public T addChild(final int index, final T widget) {
        return this.addChild(index, widget, true);
    }
    
    public T addChild(final int index, final T widget, final boolean shouldSort) {
        ThreadSafe.ensureRenderThread();
        Objects.requireNonNull(widget, "widget must be not null");
        if (widget == this) {
            throw new RuntimeException(this.name + " added itself as child");
        }
        if (this.distinct.get() && this.children.contains(widget)) {
            return widget;
        }
        this.children.add(index, widget);
        widget.onAttachedTo((AbstractWidget<Widget>)this);
        if (shouldSort) {
            this.sortChildren();
        }
        return widget;
    }
    
    public void addChildAsync(final T widget) {
        this.labyAPI.minecraft().executeNextTick(() -> this.addChildInitialized(widget));
    }
    
    public T addChildInitialized(final T widget) {
        return this.addChildInitialized(this.children.size(), widget);
    }
    
    public T addChildInitialized(final T widget, final boolean shouldSort) {
        return this.addChildInitialized(this.children.size(), widget, shouldSort);
    }
    
    public T addChildInitialized(final int index, final T widget) {
        return this.addChildInitialized(index, widget, true);
    }
    
    public T addChildInitialized(final int index, final T widget, final boolean shouldSort) {
        this.addChild(index, widget, shouldSort);
        this.initializeChild(widget);
        this.updateState(true);
        this.updateBounds();
        this.handleAttributes();
        widget.postStyleSheetLoad();
        this.updateContentBounds();
        if (this.parent instanceof final AbstractWidget abstractWidget) {
            abstractWidget.updateState(true);
            ((AbstractWidget)this.parent).updateBounds();
            ((AbstractWidget)this.parent).handleAttributes();
        }
        return widget;
    }
    
    public void addChildren(final Collection<T> widgets, final boolean shouldSort) {
        for (final T widget : widgets) {
            this.addChild(widget, false);
        }
        if (shouldSort) {
            this.sortChildren();
        }
    }
    
    public void addChildrenInitialized(final Collection<T> widgets, final boolean shouldSort) {
        for (final T widget : widgets) {
            this.addChild(widget, false);
        }
        if (shouldSort) {
            this.sortChildren();
        }
        for (final T widget : widgets) {
            this.initializeChild(widget);
        }
        this.updateState(true);
        this.updateBounds();
        this.handleAttributes();
        for (final T widget : widgets) {
            widget.postStyleSheetLoad();
        }
        this.updateContentBounds();
    }
    
    private void initializeChild(final T widget) {
        widget.initialize(this);
        widget.postInitialize();
        for (final StyleSheet styleSheet : this.styleSheets) {
            widget.applyStyleSheet(styleSheet);
        }
        final Widget unwrappedWidget = WidgetUtil.unwrapWidget(widget);
        if (unwrappedWidget instanceof final AbstractWidget abstractWidget) {
            abstractWidget.applyCustomStyleSheets();
        }
        widget.updateState(true);
    }
    
    public boolean removeChild(final String id) {
        return this.removeChildIf(widget -> widget.hasId(id));
    }
    
    public void removeChild(final T widget) {
        if (widget == this) {
            throw new RuntimeException(this.name + " removed itself as child");
        }
        if (widget instanceof AbstractWidget && (long)((AbstractWidget)widget).destroyDelay().get() > 0L) {
            final Task task = Task.builder(() -> this.removeChildImmediately(widget)).delay((long)((AbstractWidget)widget).destroyDelay().get(), TimeUnit.MILLISECONDS).build();
            task.executeOnRenderThread();
        }
        else {
            this.removeChildImmediately(widget);
        }
    }
    
    public void removeChildImmediately(final T widget) {
        ThreadSafe.ensureRenderThread();
        if (this.children.remove(widget)) {
            this.onChildRemove();
        }
    }
    
    private void onChildRemove() {
        this.updateState(true);
        this.updateBounds();
        this.handleAttributes();
        this.updateContentBounds();
    }
    
    public void replaceChild(final T widget, final T replacement) {
        ThreadSafe.ensureRenderThread();
        if (widget == this || replacement == this) {
            throw new RuntimeException(this.name + " replaced itself as child");
        }
        final int index = this.children.indexOf(widget);
        if (index == -1) {
            this.addChild(replacement);
            return;
        }
        this.children.set(index, replacement);
    }
    
    public boolean removeChildIf(final Predicate<T> predicate) {
        ThreadSafe.ensureRenderThread();
        final boolean removed = this.children.removeIf(predicate);
        if (removed) {
            this.onChildRemove();
        }
        return removed;
    }
    
    public <K> boolean removeChildIf(final Class<K> type, final Predicate<K> predicate) {
        return this.removeChildIf(widget -> type.isInstance(widget) && predicate.test(widget));
    }
    
    public <K extends Widget> List<K> findChildrenIf(final Predicate<T> predicate) {
        final List<K> found = new ArrayList<K>();
        for (final T child : this.children) {
            if (predicate.test(child)) {
                found.add((K)child);
            }
        }
        return found;
    }
    
    public <K extends Widget> List<K> findChildrenIf(final Class<K> type, final Predicate<K> predicate) {
        final List<K> found = new ArrayList<K>();
        for (final T child : this.children) {
            if (type.isInstance(child) && predicate.test((K)child)) {
                found.add((K)child);
            }
        }
        return found;
    }
    
    public <K extends Widget> K findFirstChildIf(final Predicate<T> predicate) {
        for (final T child : this.children) {
            if (predicate.test(child)) {
                return (K)child;
            }
        }
        return null;
    }
    
    public void reInitializeChildrenIf(final Predicate<T> predicate) {
        for (final Widget widget : this.findChildrenIf(predicate)) {
            widget.reInitialize();
        }
    }
    
    public <K extends Widget> void reInitializeChildrenIf(final Class<K> type, final Predicate<K> predicate) {
        for (final K widget : this.findChildrenIf(type, predicate)) {
            widget.reInitialize();
        }
    }
    
    @Override
    public void updateBounds() {
        this.bounds().checkForChanges();
        for (final T child : this.children) {
            child.updateBounds();
        }
        this.updateContentBounds();
    }
    
    @Override
    public void handleAttributes() {
        if (!this.handleStyleSheet) {
            return;
        }
        this.handleSizeAttributes();
        if (this.parent == null || !this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.POSITION)) {
            if (this.parent != null) {
                this.handleRelativeBoundsAttributes(this.parent.bounds());
            }
            this.handleAlignmentAttributes();
        }
        else if (this.parent != null) {
            if (!this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.HEIGHT) && this.top.get() != null && this.bottom.get() != null && this.top.get() == 0.0f && this.bottom.get() == 0.0f) {
                this.bounds().setOuterHeight(this.parent.bounds().getHeight(BoundsType.INNER), AbstractWidget.FULL_PARENT_SIZE);
            }
            if (!this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.WIDTH) && this.left.get() != null && this.right.get() != null && this.left.get() == 0.0f && this.right.get() == 0.0f) {
                this.bounds().setOuterWidth(this.parent.bounds().getWidth(BoundsType.INNER), AbstractWidget.FULL_PARENT_SIZE);
            }
        }
        this.handleSizeAttributes();
        this.applyMediaRules(false);
        this.updateContentBounds();
    }
    
    protected void handleSizeAttributes() {
        final float minWidth = this.getSizeOr(SizeType.MIN, WidgetSide.WIDTH, 0.0f);
        final float minHeight = this.getSizeOr(SizeType.MIN, WidgetSide.HEIGHT, 0.0f);
        final Float width = this.getSize(SizeType.ACTUAL, WidgetSide.WIDTH);
        final Float height = this.getSize(SizeType.ACTUAL, WidgetSide.HEIGHT);
        final float maxWidth = this.getSizeOr(SizeType.MAX, WidgetSide.WIDTH, Float.MAX_VALUE);
        final float maxHeight = this.getSizeOr(SizeType.MAX, WidgetSide.HEIGHT, Float.MAX_VALUE);
        if (width != null && (this.left.get() == null || this.right.get() == null) && (this.parent == null || !this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.WIDTH))) {
            this.bounds().setWidth(Math.max(width, minWidth), AbstractWidget.LSS_SIZE);
        }
        if (height != null && (this.top.get() == null || this.bottom.get() == null) && (this.parent == null || !this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.HEIGHT))) {
            this.bounds().setHeight(Math.max(height, minHeight), AbstractWidget.LSS_SIZE);
        }
        this.handleMinAttributes(minWidth, minHeight, AbstractWidget.MIN_SIZE);
        this.handleMaxAttributes(maxWidth, maxHeight, AbstractWidget.MAX_SIZE);
    }
    
    private void handleAlignmentAttributes() {
        if (this.parent == null) {
            return;
        }
        final Bounds bounds = this.bounds();
        final float thisWidth = bounds.getWidth(BoundsType.OUTER);
        final float thisHeight = bounds.getHeight(BoundsType.OUTER);
        if (thisWidth != 0.0f) {
            if (this.alignmentX().get() == WidgetAlignment.CENTER) {
                float centerX;
                if (this.left.get() != null) {
                    centerX = this.parent.bounds().getLeft(BoundsType.INNER) + this.left.get();
                }
                else if (this.right.get() != null) {
                    centerX = this.parent.bounds().getRight(BoundsType.INNER) - this.right.get();
                }
                else {
                    centerX = this.parent.bounds().getCenterX(BoundsType.INNER);
                }
                final float outerX = centerX - thisWidth / 2.0f;
                bounds.setOuterX(this.transformFloatingPoint(outerX), AbstractWidget.ALIGNMENT);
            }
            if (this.alignmentX().get() == WidgetAlignment.RIGHT) {
                final float rightX = this.parent.bounds().getRight(BoundsType.INNER) - this.right.getOrDefault(0.0f);
                bounds.setOuterX(rightX - thisWidth, AbstractWidget.ALIGNMENT);
            }
        }
        if (thisHeight != 0.0f) {
            if (this.alignmentY().get() == WidgetAlignment.CENTER) {
                float centerY;
                if (this.top.get() != null) {
                    centerY = this.parent.bounds().getTop(BoundsType.INNER) + this.top.get();
                }
                else if (this.bottom.get() != null) {
                    centerY = this.parent.bounds().getBottom(BoundsType.INNER) - this.bottom.get();
                }
                else {
                    centerY = this.parent.bounds().getCenterY(BoundsType.INNER);
                }
                final float outerY = centerY - thisHeight / 2.0f;
                bounds.setOuterY(this.transformFloatingPoint(outerY), AbstractWidget.ALIGNMENT);
            }
            if (this.alignmentY().get() == WidgetAlignment.BOTTOM) {
                final float bottomY = this.parent.bounds().getBottom(BoundsType.INNER) - this.bottom.getOrDefault(0.0f);
                bounds.setOuterY(bottomY - thisHeight, AbstractWidget.ALIGNMENT);
            }
        }
    }
    
    protected void handleRelativeBoundsAttributes(final Bounds parentBounds) {
        final Bounds bounds = this.bounds();
        if (this.top.get() != null) {
            bounds.setOuterY(this.applyHeightPrecision(parentBounds.getY(BoundsType.INNER) + this.top.get()), AbstractWidget.RELATIVE_BOUNDS_TOP);
        }
        if (this.bottom.get() != null) {
            if (this.top.get() != null) {
                bounds.setOuterHeight(this.applyHeightPrecision(parentBounds.getHeight(BoundsType.INNER) - (this.bottom.get() + this.top.get())), AbstractWidget.RELATIVE_BOUNDS_BOTTOM);
            }
            bounds.setOuterY(this.applyHeightPrecision(parentBounds.getBottom(BoundsType.INNER) - bounds.getHeight(BoundsType.OUTER) - this.bottom.get()), AbstractWidget.RELATIVE_BOUNDS_BOTTOM);
        }
        if (this.left.get() != null) {
            bounds.setOuterX(this.applyWidthPrecision(parentBounds.getX(BoundsType.INNER) + this.left.get()), AbstractWidget.RELATIVE_BOUNDS_LEFT);
        }
        if (this.right.get() != null) {
            if (this.left.get() != null) {
                bounds.setOuterWidth(this.applyWidthPrecision(parentBounds.getWidth(BoundsType.INNER) - (this.right.get() + this.left.get())), AbstractWidget.RELATIVE_BOUNDS_RIGHT);
            }
            bounds.setOuterX(this.applyWidthPrecision(parentBounds.getRight(BoundsType.INNER) - bounds.getWidth(BoundsType.OUTER) - this.right.get()), AbstractWidget.RELATIVE_BOUNDS_RIGHT);
        }
    }
    
    private float applyWidthPrecision(final float value) {
        final Float precision = this.widthPrecision.get();
        if (precision == null || precision <= 0.0f) {
            return this.transformFloatingPoint(value);
        }
        return this.transformFloatingPoint((int)(value / precision) * precision);
    }
    
    private float applyHeightPrecision(final float value) {
        final Float precision = this.heightPrecision.get();
        if (precision == null || precision <= 0.0f) {
            return this.transformFloatingPoint(value);
        }
        return this.transformFloatingPoint((int)(value / precision) * precision);
    }
    
    protected void handleMinAttributes(final float minWidth, final float minHeight, final ModifyReason reason) {
        final WidgetSize minWidthSize = this.sizes.getSize(SizeType.MIN, WidgetSide.WIDTH);
        final WidgetSize minHeightSize = this.sizes.getSize(SizeType.MIN, WidgetSide.HEIGHT);
        this.handleMinWidth((minWidthSize != null && minWidthSize.percentage()) ? BoundsType.OUTER : BoundsType.INNER, minWidth, reason);
        this.handleMinHeight((minHeightSize != null && minHeightSize.percentage()) ? BoundsType.OUTER : BoundsType.INNER, minHeight, reason);
    }
    
    private void handleMinWidth(final BoundsType type, float minWidth, final ModifyReason reason) {
        final Bounds bounds = this.bounds();
        minWidth += bounds.getHorizontalOffset(type);
        if (minWidth <= 0.0f || bounds.getWidth(type) >= minWidth) {
            return;
        }
        final float difference = minWidth - bounds.getWidth(type);
        bounds.setWidth(minWidth, type, reason);
        float offset = 0.0f;
        switch (this.alignmentX().get()) {
            case CENTER: {
                offset = difference / 2.0f;
                break;
            }
            case RIGHT: {
                offset = difference;
                break;
            }
        }
        if (offset != 0.0f) {
            bounds.setX(bounds.getX(type) - this.transformFloatingPoint(offset), type, reason);
        }
    }
    
    private void handleMinHeight(final BoundsType type, float minHeight, final ModifyReason reason) {
        final Bounds bounds = this.bounds();
        minHeight += bounds.getVerticalOffset(type);
        if (minHeight <= 0.0f || bounds.getHeight(type) >= minHeight) {
            return;
        }
        final float difference = minHeight - bounds.getHeight(type);
        bounds.setHeight(minHeight, type, reason);
        float offset = 0.0f;
        switch (this.alignmentY().get()) {
            case CENTER: {
                offset = difference / 2.0f;
                break;
            }
            case RIGHT: {
                offset = difference;
                break;
            }
        }
        if (offset != 0.0f) {
            bounds.setY(bounds.getY(type) - this.transformFloatingPoint(offset), type, reason);
        }
    }
    
    protected void handleMaxAttributes(final float maxWidth, final float maxHeight, final ModifyReason reason) {
        boolean realign = false;
        final Bounds bounds = this.bounds();
        if (maxWidth > 0.0f && bounds.getWidth() > maxWidth) {
            final float difference = bounds.getWidth(BoundsType.OUTER) - maxWidth;
            bounds.setWidth(maxWidth, reason);
            realign = true;
        }
        if (maxHeight > 0.0f && bounds.getHeight() > maxHeight) {
            final float difference = bounds.getHeight(BoundsType.OUTER) - maxHeight;
            bounds.setHeight(maxHeight, reason);
            realign = true;
        }
        if (realign) {
            this.handleAlignmentAttributes();
        }
        if (this.widthPrecision.get() != null && this.widthPrecision.get() > 0.0f) {
            bounds.setWidth(this.applyWidthPrecision(bounds.getWidth(BoundsType.INNER)), reason);
        }
        if (this.heightPrecision.get() != null && this.heightPrecision.get() > 0.0f) {
            bounds.setHeight(this.applyHeightPrecision(bounds.getHeight(BoundsType.INNER)), reason);
        }
    }
    
    protected float transformFloatingPoint(final float value) {
        return this.transformFloatingPoint(this, value);
    }
    
    protected float transformFloatingPoint(final AbstractWidget<?> widget, final float value) {
        return this.transformFloatingPoint(widget.useFloatingPointPosition().get(), value);
    }
    
    protected float transformFloatingPoint(final boolean useFloatingPoint, final float value) {
        return MathHelper.applyFloatingPointPosition(useFloatingPoint, value);
    }
    
    @Override
    public Float getMargin(final OffsetSide side) {
        switch (side) {
            case LEFT: {
                return this.marginLeft.get();
            }
            case TOP: {
                return this.marginTop.get();
            }
            case RIGHT: {
                return this.marginRight.get();
            }
            case BOTTOM: {
                return this.marginBottom.get();
            }
            default: {
                return 0.0f;
            }
        }
    }
    
    @Override
    public Float getPadding(final OffsetSide side) {
        switch (side) {
            case LEFT: {
                return this.paddingLeft.get();
            }
            case TOP: {
                return this.paddingTop.get();
            }
            case RIGHT: {
                return this.paddingRight.get();
            }
            case BOTTOM: {
                return this.paddingBottom.get();
            }
            default: {
                return 0.0f;
            }
        }
    }
    
    @Override
    public Float getBorder(final OffsetSide side) {
        if (this.border == null) {
            return 0.0f;
        }
        switch (side) {
            case LEFT: {
                return this.border.getLeftWidth();
            }
            case TOP: {
                return this.border.getTopWidth();
            }
            case RIGHT: {
                return this.border.getRightWidth();
            }
            case BOTTOM: {
                return this.border.getBottomWidth();
            }
            default: {
                return 0.0f;
            }
        }
    }
    
    @Override
    public void setActionListener(final Runnable listener) {
        this.setActionListener("main", listener);
    }
    
    @Override
    public void setActionListener(final String id, final Runnable listener) {
        this.actionListeners.put(id, listener);
    }
    
    @Override
    public void removeActionListener(final String id) {
        this.actionListeners.remove(id);
    }
    
    @Override
    public boolean isFocused() {
        return this.isAttributeStateEnabled(AttributeState.FOCUS);
    }
    
    @Override
    public void setFocused(final boolean focused) {
        this.setAttributeState(AttributeState.FOCUS, focused || this.alwaysFocused.get());
    }
    
    @Override
    public boolean isVisible() {
        return this.visible.get();
    }
    
    @Override
    public void setVisible(final boolean visible) {
        this.visible.set(visible);
    }
    
    @Override
    public <W extends Widget> W addId(final String... ids) {
        boolean added = false;
        for (final String id : ids) {
            final boolean v = this.addIdInternal(id);
            if (!added) {
                added = v;
            }
        }
        if (this.initialized && added) {
            this.reloadStyleSheets();
        }
        return (W)this;
    }
    
    @Override
    public <W extends Widget> W addId(final String id) {
        if (this.addIdInternal(id) && this.initialized) {
            this.reloadStyleSheets();
        }
        return (W)this;
    }
    
    private boolean addIdInternal(final String id) {
        if (this.ids.contains(id)) {
            return false;
        }
        this.ids.add(id);
        return true;
    }
    
    @Override
    public boolean hasId(final CharSequence id) {
        return this.ids.contains(id);
    }
    
    @Override
    public void removeId(final String id) {
        final boolean removed = this.ids.remove(id);
        if (removed && this.initialized) {
            this.reloadStyleSheets();
        }
    }
    
    @Override
    public <W extends Widget> W replaceId(final String currentId, final String newId) {
        final boolean removed = this.ids.remove(currentId);
        if (removed) {
            return this.addId(newId);
        }
        return (W)this;
    }
    
    @Override
    public String getUniqueId() {
        return this.uniqueId.get();
    }
    
    @Override
    public List<CharSequence> getIds() {
        return this.ids;
    }
    
    @Override
    public String getTypeName() {
        return this.name;
    }
    
    @Override
    public String getQualifiedName() {
        return this.qualifiedName;
    }
    
    @Override
    public Parent getParent() {
        return this.parent;
    }
    
    public boolean hasWriteToStencilBuffer() {
        boolean writeToStencilBuffer = this.writeToStencilBuffer.get();
        if (writeToStencilBuffer) {
            return true;
        }
        for (Parent parent = this.parent; parent != null; parent = parent.getParent()) {
            if (!(parent instanceof AbstractWidget)) {
                break;
            }
            if (((AbstractWidget)parent).writeToStencilBuffer().get()) {
                writeToStencilBuffer = true;
                break;
            }
        }
        return writeToStencilBuffer;
    }
    
    @Override
    public Bounds bounds() {
        return this.bounds;
    }
    
    @Override
    public Animation animation() {
        return this.animation;
    }
    
    @Override
    public void playAnimation(final String id, final Runnable finishHandler) {
        Optional<StyleRule> found = Optional.empty();
        for (final StyleSheet sheet : this.styleSheets) {
            final StyleRule keyframes = sheet.getRule("keyframes", id);
            if (keyframes != null) {
                found = Optional.of(keyframes);
                break;
            }
        }
        final List<StyleBlock> blocks = found.map((Function<? super StyleRule, ? extends List<StyleBlock>>)StyleRule::getBlocks).orElse(null);
        if (blocks == null) {
            if (finishHandler != null) {
                this.labyAPI.minecraft().executeNextTick(finishHandler);
            }
            return;
        }
        final Map<String, Map<Long, AttributePatch>> patches = new HashMap<String, Map<Long, AttributePatch>>();
        for (final StyleBlock block : blocks) {
            final String stage = block.getRawSelector().trim();
            if (stage.isEmpty()) {
                continue;
            }
            final int percentage = stage.equalsIgnoreCase("from") ? 0 : (stage.equalsIgnoreCase("to") ? 100 : Integer.parseInt(stage.replace("%", "")));
            final long currentMillis = (long)(percentage / 100.0 * this.animationDuration.get());
            final Map<String, StyleInstructions> currentPatches = block.generateAttributePatches(this, 1);
            final String key;
            final AttributePatch patch;
            currentPatches.forEach((key, patch) -> patches.computeIfAbsent(key, s -> new HashMap()).put(currentMillis, patch.patch()));
        }
        if (patches.isEmpty()) {
            if (finishHandler != null) {
                this.labyAPI.minecraft().executeNextTick(finishHandler);
            }
            return;
        }
        for (final Map.Entry<String, Map<Long, AttributePatch>> entry : patches.entrySet()) {
            final String key = entry.getKey();
            final Map<Long, AttributePatch> currentPatches2 = entry.getValue();
            boolean seen = false;
            Map.Entry<Long, AttributePatch> best = null;
            final Comparator<Map.Entry<Long, AttributePatch>> comparator = Comparator.comparingLong(Map.Entry::getKey);
            for (final Map.Entry<Long, AttributePatch> longAttributePatchEntry : currentPatches2.entrySet()) {
                if (!seen || comparator.compare(longAttributePatchEntry, best) < 0) {
                    seen = true;
                    best = longAttributePatchEntry;
                }
            }
            final AttributePatch min = seen ? ((best == null) ? null : best.getValue()) : null;
            final AttributeAnimation<AttributePatch> animation = this.animation.createCustom(key, (AttributeAnimationAccessor<AttributePatch>)new AttributePatchAnimationAccessor(key, this, min));
            for (final Map.Entry<Long, AttributePatch> attributePatch : currentPatches2.entrySet()) {
                final Long timestamp = attributePatch.getKey();
                final AttributePatch patch = attributePatch.getValue();
                animation.addKeyframe(patch, timestamp, this.animationTimingFunction.get());
            }
        }
        this.styleInstructions.removeIf(i -> i.selector().buildSelector().equals("#" + this.getUniqueId()));
        this.animation.getAttributeAnimations().values().iterator().next().setFinishHandler(() -> {
            patches.keySet().iterator();
            final Iterator iterator6;
            while (iterator6.hasNext()) {
                final String key2 = iterator6.next();
                this.animation.remove(key2);
            }
            patches.values().iterator();
            final Iterator iterator7;
            while (iterator7.hasNext()) {
                final Map<Long, AttributePatch> map = iterator7.next();
                boolean seen2 = false;
                Map.Entry<Long, AttributePatch> best2 = null;
                final Comparator<Map.Entry<Long, AttributePatch>> comparator2 = Comparator.comparingLong(Map.Entry::getKey);
                map.entrySet().iterator();
                final Iterator iterator8;
                while (iterator8.hasNext()) {
                    final Map.Entry<Long, AttributePatch> longAttributePatchEntry2 = iterator8.next();
                    if (!seen2 || comparator2.compare(longAttributePatchEntry2, best2) > 0) {
                        seen2 = true;
                        best2 = longAttributePatchEntry2;
                    }
                }
                if (seen2 && best2 != null) {
                    this.addAttributePatch(this.labyAPI.styleHelper().createSelector("#" + this.getUniqueId()), best2.getValue(), 0);
                }
            }
            if (finishHandler != null) {
                finishHandler.run();
            }
            return;
        });
        this.animation.start();
    }
    
    @Override
    public void playAnimation(final String id) {
        this.playAnimation(id, null);
    }
    
    @Override
    public LssProperty<ThemeRenderer> renderer() {
        return this.renderer;
    }
    
    @Override
    public List<T> getChildren() {
        return this.children;
    }
    
    @Deprecated
    public List<T> getGenericChildren() {
        return this.children;
    }
    
    @Override
    public final float getContentSize(final BoundsType type, final WidgetSide side) {
        return (side == WidgetSide.WIDTH) ? this.getContentWidth(type) : this.getContentHeight(type);
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        final boolean useRelativeBounds = this.parent == null || (!this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.POSITION) && !this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.WIDTH));
        float minX = useRelativeBounds ? this.left.getOrDefault(2.1474836E9f) : 2.1474836E9f;
        float maxX = useRelativeBounds ? this.right.getOrDefault(-2.1474836E9f) : -2.1474836E9f;
        for (final T child : this.children) {
            if (child.isVisible()) {
                if (!this.isVisibleForContentBounds(child)) {
                    continue;
                }
                final Float childWidth = this.getFitWidth(child);
                if (childWidth == null) {
                    continue;
                }
                final float childLeft = child.bounds().getLeft(BoundsType.OUTER);
                minX = Math.min(minX, childLeft);
                maxX = Math.max(maxX, childLeft + childWidth);
            }
        }
        if (minX == 2.1474836E9f) {
            return 0.0f;
        }
        return this.bounds().getHorizontalOffset(type) + (maxX - minX);
    }
    
    protected Float getFitWidth(final T child) {
        Float childWidth = null;
        final WidgetSize width = child.sizes().getSize(SizeType.ACTUAL, WidgetSide.WIDTH);
        final Bounds childBounds = child.bounds();
        if ((width != null && width.percentage()) || (child instanceof AbstractWidget && ((AbstractWidget)child).left.get() != null && ((AbstractWidget)child).right.get() != null)) {
            final WidgetSize minWidth = child.sizes().getSize(SizeType.MIN, WidgetSide.WIDTH);
            if (minWidth != null && !minWidth.percentage()) {
                childWidth = minWidth.value() + childBounds.getHorizontalOffset(BoundsType.OUTER);
            }
        }
        else {
            childWidth = childBounds.getWidth(BoundsType.OUTER);
        }
        return childWidth;
    }
    
    protected float getDefaultContentWidth(final BoundsType type) {
        return this.getSizeOr(SizeType.MIN, WidgetSide.WIDTH, 0.0f);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        final boolean useRelativeBounds = this.parent == null || (!this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.POSITION) && !this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.HEIGHT));
        float minY = useRelativeBounds ? this.top.getOrDefault(2.1474836E9f) : 2.1474836E9f;
        float maxY = useRelativeBounds ? this.bottom.getOrDefault(-2.1474836E9f) : -2.1474836E9f;
        for (final T child : this.children) {
            if (child.isVisible()) {
                if (!this.isVisibleForContentBounds(child)) {
                    continue;
                }
                final Float childHeight = this.getFitHeight(child);
                if (childHeight == null) {
                    continue;
                }
                final float childTop = child.bounds().getTop(BoundsType.OUTER);
                minY = Math.min(minY, childTop);
                maxY = Math.max(maxY, childTop + childHeight);
            }
        }
        if (minY == 2.1474836E9f) {
            return 0.0f;
        }
        return this.bounds().getVerticalOffset(type) + (maxY - minY);
    }
    
    protected Float getFitHeight(final T child) {
        Float childHeight = null;
        final WidgetSize height = child.sizes().getSize(SizeType.ACTUAL, WidgetSide.HEIGHT);
        final Bounds childBounds = child.bounds();
        if ((height != null && height.percentage()) || (child instanceof AbstractWidget && ((AbstractWidget)child).top.get() != null && ((AbstractWidget)child).bottom.get() != null)) {
            final WidgetSize minHeight = child.sizes().getSize(SizeType.MIN, WidgetSide.HEIGHT);
            if (minHeight != null && !minHeight.percentage()) {
                childHeight = minHeight.value() + childBounds.getVerticalOffset(BoundsType.OUTER);
            }
        }
        else {
            childHeight = childBounds.getHeight(BoundsType.OUTER);
        }
        return childHeight;
    }
    
    protected float getDefaultContentHeight(final BoundsType type) {
        return this.getSizeOr(SizeType.MIN, WidgetSide.HEIGHT, 0.0f);
    }
    
    @Nullable
    protected Float predictWidth() {
        final Bounds bounds = this.bounds();
        if (this.parent != null && this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.WIDTH)) {
            return bounds.getWidth();
        }
        return this.hasSize(WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT) ? this.getSize(SizeType.MAX, WidgetSide.WIDTH) : Float.valueOf(bounds.getWidth());
    }
    
    @Nullable
    protected Float predictHeight() {
        final Bounds bounds = this.bounds();
        if (this.parent != null && this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.HEIGHT)) {
            return bounds.getHeight();
        }
        return this.hasSize(WidgetSide.HEIGHT, WidgetSize.Type.FIT_CONTENT) ? this.getSize(SizeType.MAX, WidgetSide.HEIGHT) : Float.valueOf(bounds.getHeight());
    }
    
    @Override
    public WidgetSizeList sizes() {
        return this.sizes;
    }
    
    @Override
    public void setSize(final SizeType type, final WidgetSide side, final WidgetSize size) {
        this.sizes.setSize(type, side, size);
    }
    
    @Override
    public Float getSize(final SizeType type, final WidgetSide side) {
        final WidgetSize size = this.sizes.getSize(type, side);
        if (size == null) {
            return null;
        }
        final float value = size.computeValue(this, side);
        return this.useFloatingPointPosition.get() ? value : ((float)MathHelper.floor(value));
    }
    
    @Override
    public boolean hasSize(final SizeType type, final WidgetSide side, final WidgetSize.Type sizeType) {
        final WidgetSize size = this.sizes.getSize(type, side);
        return size != null && size.type() == sizeType;
    }
    
    @Override
    public boolean hasAnySize(final WidgetSide side) {
        return this.sizes.hasAnySize(side);
    }
    
    @Override
    public boolean isPercentageSize(final SizeType type, final WidgetSide side) {
        final WidgetSize size = this.sizes.getSize(type, side);
        return size != null && size.percentage();
    }
    
    protected boolean isVisibleForContentBounds(final T child) {
        return true;
    }
    
    @Override
    public LssProperty<BoxSizing> boxSizing() {
        return this.boxSizing;
    }
    
    @Override
    public String toString() {
        return this.getTypeName();
    }
    
    @Override
    public Parent getRoot() {
        return (this.parent != null) ? this.parent.getRoot() : null;
    }
    
    @Override
    public boolean isActive() {
        return this.isAttributeStateEnabled(AttributeState.ACTIVE);
    }
    
    @Override
    public void setActive(final boolean active) {
        if (this.isActive() == active) {
            return;
        }
        this.setAttributeState(AttributeState.ACTIVE, active);
        this.lastActiveChangedTime = TimeUtil.getMillis();
        this.updateState(false);
    }
    
    @Override
    public boolean isTicking() {
        return this.ticking;
    }
    
    public void setTicking(final boolean ticking) {
        this.ticking = ticking;
    }
    
    @Override
    public boolean hasHoverComponent() {
        return this.hoverComponent != null;
    }
    
    @Override
    public void setHoverComponent(final Component component) {
        this.hoverComponent = ((component == null) ? null : RenderableComponent.of(component, 200.0f, HorizontalAlignment.LEFT));
    }
    
    @Override
    public void setHoverComponent(final Component component, final float maxWidth) {
        this.hoverComponent = ((component == null) ? null : RenderableComponent.of(component, maxWidth, HorizontalAlignment.LEFT));
    }
    
    @Override
    public void setHoverRenderableComponent(final RenderableComponent renderableComponent) {
        this.hoverComponent = renderableComponent;
    }
    
    public void openContextMenu() {
        final ContextMenu contextMenu = this.getContextMenu();
        if (contextMenu == null) {
            throw new IllegalStateException("Cannot open context menu, no context menu has been set");
        }
        contextMenu.open(this);
    }
    
    public void closeContextMenu() {
        this.contextMenu.close();
    }
    
    @Override
    public void sortChildren() {
        boolean requiresSort = false;
        for (final Widget widget : this.getChildren()) {
            widget.sortChildren();
            if (widget.priorityLayer().get() != PriorityLayer.NORMAL || widget.getSortingValue() != 0) {
                requiresSort = true;
            }
        }
        if (requiresSort) {
            this.getChildren().sort(this.comparator);
        }
    }
    
    public void setComparator(final Comparator<? super Widget> comparator) {
        this.comparator = comparator;
    }
    
    @Override
    public float getTranslateX() {
        return this.translateX.get();
    }
    
    @Override
    public void setTranslateX(final float translateX) {
        this.translateX.set(translateX);
    }
    
    @Override
    public float getTranslateY() {
        return this.translateY.get();
    }
    
    @Override
    public void setTranslateY(final float translateY) {
        this.translateY.set(translateY);
    }
    
    public long getLastActionTime() {
        return this.lastActionTime;
    }
    
    @Override
    public float getScaleX() {
        return this.scaleX.get();
    }
    
    @Override
    public void setScaleX(final float scaleX) {
        this.scaleX.set(scaleX);
    }
    
    @Override
    public float getScaleY() {
        return this.scaleY.get();
    }
    
    @Override
    public void setScaleY(final float scaleY) {
        this.scaleY.set(scaleY);
    }
    
    @Override
    public void setScale(final float scale) {
        this.setScaleX(scale);
        this.setScaleY(scale);
    }
    
    public void resetScale() {
        this.scaleX.reset();
        this.scaleY.reset();
    }
    
    @Override
    public float getEffectiveWidth() {
        final Float maxWidth = this.getSize(SizeType.MAX, WidgetSide.WIDTH);
        final float contentWidth = this.getContentWidth(BoundsType.OUTER);
        if (maxWidth != null && maxWidth < contentWidth) {
            return maxWidth;
        }
        return contentWidth;
    }
    
    @Override
    public float getEffectiveHeight() {
        final Float maxHeight = this.getSize(SizeType.MAX, WidgetSide.HEIGHT);
        final float contentHeight = this.getContentHeight(BoundsType.OUTER);
        if (maxHeight != null && maxHeight < contentHeight) {
            return maxHeight;
        }
        return contentHeight;
    }
    
    @Override
    public boolean isSelected() {
        return this.isAttributeStateEnabled(AttributeState.SELECTED);
    }
    
    @Override
    public void setSelected(final boolean selected) {
        this.setAttributeState(AttributeState.SELECTED, selected);
    }
    
    @Override
    public boolean isCancelParentHoverComponent() {
        return this.cancelParentHoverComponent().get();
    }
    
    @Override
    public Rectangle childrenRegion() {
        final MutableRectangle rectangle = Rectangle.extendable();
        for (final T child : this.children) {
            if (child.isVisible()) {
                rectangle.extend(child.bounds());
                rectangle.extend(child.childrenRegion());
            }
        }
        return rectangle;
    }
    
    @Override
    public <K extends Widget> void traverse(final Collection<K> output, final FilterAction filterAction) {
        for (final T child : this.children) {
            if (filterAction.shouldInclude(child)) {
                output.add((K)child);
            }
            child.traverse(output, filterAction);
        }
    }
    
    @Override
    public List<T> getChildrenAt(final int x, final int y) {
        final List<T> children = new ArrayList<T>();
        for (final T child : this.children) {
            if (child.bounds().isInRectangle((float)x, (float)y)) {
                children.add(child);
            }
        }
        return children;
    }
    
    @Override
    public void dispose() {
        try {
            for (final T child : this.children) {
                child.dispose();
            }
        }
        catch (final ConcurrentModificationException e) {
            e.printStackTrace();
            for (final Widget child2 : this.children.toArray(new Widget[0])) {
                child2.dispose();
            }
        }
        this.disposed = true;
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.metadata = metadata;
    }
    
    @Override
    public Metadata metadata() {
        return this.metadata;
    }
    
    public void refreshActionTime() {
        this.lastActionTime = TimeUtil.getMillis();
    }
    
    protected void callActionListeners() {
        this.callActionListeners(false);
    }
    
    protected void callActionListeners(final boolean children) {
        for (final Runnable listener : this.actionListeners.values()) {
            listener.run();
        }
        if (!children) {
            this.lastActionTime = TimeUtil.getMillis();
            return;
        }
        for (T child : this.children) {
            child = (T)WidgetUtil.unwrapWidget(child);
            if (child instanceof final AbstractWidget abstractWidget) {
                abstractWidget.callActionListeners(children);
            }
        }
        this.lastActionTime = TimeUtil.getMillis();
    }
    
    public boolean hasBorder() {
        return this.border != null && this.border.isSet();
    }
    
    public boolean hasBorderRadius() {
        return this.borderRadius != null && this.borderRadius.isSet();
    }
    
    @Nullable
    public BorderRadius getBorderRadius() {
        return this.borderRadius;
    }
    
    public void setBorderRadius(@Nullable final BorderRadius borderRadius) {
        this.borderRadius = borderRadius;
    }
    
    public void setLazy(final boolean lazy) {
        this.lazy = lazy;
    }
    
    @Override
    public boolean isLazy() {
        return this.lazy;
    }
    
    public long getLastInitialTime() {
        return this.lastInitialTime;
    }
    
    public long getLastDestroyTime() {
        return this.lastDestroyTime;
    }
    
    public long getLastActiveChangedTime() {
        return this.lastActiveChangedTime;
    }
    
    @Override
    public void destroy() {
        this.children.forEach(Widget::destroy);
        this.destroyed = true;
        this.lastDestroyTime = TimeUtil.getMillis();
    }
    
    @Override
    public boolean isDisposed() {
        return this.disposed;
    }
    
    @Override
    public boolean isDestroyed() {
        return this.destroyed;
    }
    
    @Override
    public <K extends Widget> void onAttachedTo(final AbstractWidget<K> parent) {
    }
    
    public LssProperty<Integer> animationDuration() {
        return this.animationDuration;
    }
    
    public LssProperty<Boolean> draggable() {
        return this.draggable;
    }
    
    public LssProperty<Boolean> interactable() {
        return this.interactable;
    }
    
    public LssProperty<Boolean> pressable() {
        return this.pressable;
    }
    
    public LssProperty<Boolean> visible() {
        return this.visible;
    }
    
    public LssProperty<Float> left() {
        return this.left;
    }
    
    public LssProperty<Float> top() {
        return this.top;
    }
    
    public LssProperty<Float> right() {
        return this.right;
    }
    
    public LssProperty<Float> bottom() {
        return this.bottom;
    }
    
    public LssProperty<Float> marginLeft() {
        return this.marginLeft;
    }
    
    public LssProperty<Float> marginTop() {
        return this.marginTop;
    }
    
    public LssProperty<Float> marginRight() {
        return this.marginRight;
    }
    
    public LssProperty<Float> marginBottom() {
        return this.marginBottom;
    }
    
    public LssProperty<Float> paddingLeft() {
        return this.paddingLeft;
    }
    
    public LssProperty<Float> paddingTop() {
        return this.paddingTop;
    }
    
    public LssProperty<Float> paddingRight() {
        return this.paddingRight;
    }
    
    public LssProperty<Float> paddingBottom() {
        return this.paddingBottom;
    }
    
    public LssProperty<Float> widthPrecision() {
        return this.widthPrecision;
    }
    
    public LssProperty<Float> heightPrecision() {
        return this.heightPrecision;
    }
    
    @Override
    public LssProperty<PriorityLayer> priorityLayer() {
        return this.priorityLayer;
    }
    
    @Override
    public LssProperty<WidgetAlignment> alignmentX() {
        return this.alignmentX;
    }
    
    @Override
    public LssProperty<WidgetAlignment> alignmentY() {
        return this.alignmentY;
    }
    
    public LssProperty<Float> translateX() {
        return this.translateX;
    }
    
    public LssProperty<Float> translateY() {
        return this.translateY;
    }
    
    public LssProperty<Float> zIndex() {
        return this.zIndex;
    }
    
    public LssProperty<Float> scaleX() {
        return this.scaleX;
    }
    
    public LssProperty<Float> scaleY() {
        return this.scaleY;
    }
    
    public LssProperty<Float> opacity() {
        return this.opacity;
    }
    
    public LssProperty<Float> mouseRenderDistance() {
        return this.mouseRenderDistance;
    }
    
    public LssProperty<Boolean> stencilX() {
        return this.stencilX;
    }
    
    public LssProperty<Boolean> stencilY() {
        return this.stencilY;
    }
    
    public void setStencil(final boolean stencil) {
        this.stencilX.set(stencil);
        this.stencilY.set(stencil);
    }
    
    public void setPressOnRelease(final boolean pressOnRelease) {
        this.pressOnRelease = pressOnRelease;
    }
    
    public LssProperty<Boolean> stencilTranslation() {
        return this.stencilTranslation;
    }
    
    public LssProperty<Boolean> writeToStencilBuffer() {
        return this.writeToStencilBuffer;
    }
    
    public LssProperty<Boolean> useFloatingPointPosition() {
        return this.useFloatingPointPosition;
    }
    
    public LssProperty<Boolean> alwaysFocused() {
        return this.alwaysFocused;
    }
    
    public LssProperty<Boolean> interactableOutside() {
        return this.interactableOutside;
    }
    
    public LssProperty<Long> destroyDelay() {
        return this.destroyDelay;
    }
    
    public LssProperty<Boolean> fitOuter() {
        return this.fitOuter;
    }
    
    public LssProperty<Boolean> distinct() {
        return this.distinct;
    }
    
    public LssProperty<Boolean> cancelParentHoverComponent() {
        return this.cancelParentHoverComponent;
    }
    
    public LssProperty<Boolean> renderChildren() {
        return this.renderChildren;
    }
    
    public LssProperty<Integer> backgroundColor() {
        return this.backgroundColor;
    }
    
    public LssProperty<Long> backgroundColorTransitionDuration() {
        return this.backgroundColorTransitionDuration;
    }
    
    public LssProperty<Boolean> backgroundAlwaysDirt() {
        return this.backgroundAlwaysDirt;
    }
    
    public LssProperty<Integer> backgroundDirtShift() {
        return this.backgroundDirtShift;
    }
    
    public LssProperty<DirtBackgroundType> backgroundDirtType() {
        return this.backgroundDirtType;
    }
    
    public LssProperty<Float> fontWeight() {
        return this.fontWeight;
    }
    
    public LssProperty<CubicBezier> animationTimingFunction() {
        return this.animationTimingFunction;
    }
    
    public LssProperty<Filter[]> filter() {
        return this.filter;
    }
    
    public LssProperty<Integer> hoverBoxDelay() {
        return this.hoverBoxDelay;
    }
    
    public LssProperty<Boolean> clearDepth() {
        return this.clearDepth;
    }
    
    public LssProperty<Boolean> forceVanillaFont() {
        return this.forceVanillaFont;
    }
    
    @Override
    public float getFontWeight() {
        return this.fontWeight.get();
    }
    
    public boolean isHoverComponentRendered() {
        if (this.hoverComponent == null || !this.isHovered()) {
            return false;
        }
        final long hoverTimestamp = this.getAttributeStateToggleTimestamp(AttributeState.HOVER);
        long draggingTimestamp = 0L;
        if (this.isAttributeStateEnabled(AttributeState.ENABLED)) {
            draggingTimestamp = this.getAttributeStateToggleTimestamp(AttributeState.DRAGGING);
        }
        long timestamp;
        if (draggingTimestamp > hoverTimestamp) {
            timestamp = draggingTimestamp;
        }
        else {
            timestamp = hoverTimestamp;
        }
        return timestamp + this.hoverBoxDelay.get() < TimeUtil.getMillis();
    }
    
    protected RenderableComponent getHoverComponent() {
        return this.hoverComponent;
    }
    
    @Override
    public boolean isOutOfBounds() {
        return this.outOfBounds;
    }
    
    @Override
    public void setOutOfBounds(final boolean outOfBounds) {
        this.outOfBounds = outOfBounds;
        if (outOfBounds) {
            this.attributeStates.remove(AttributeState.HOVER);
        }
        for (final T child : this.children) {
            child.setOutOfBounds(outOfBounds);
        }
    }
    
    @Override
    public Collection<AttributeState> getAttributeStates() {
        return this.attributeStates.keySet();
    }
    
    @Override
    public long getAttributeStateToggleTimestamp(final AttributeState state) {
        AttributeStateValue value;
        if (state.isStaticState()) {
            value = this.staticAttributeStates.get(state);
        }
        else {
            value = this.attributeStates.get(state);
        }
        return (value != null) ? value.lastUpdate : this.widgetCreateTimestamp;
    }
    
    @Override
    public boolean isAttributeStateEnabled(final AttributeState state) {
        AttributeStateValue value;
        if (state.isStaticState()) {
            value = this.staticAttributeStates.get(state);
        }
        else {
            value = this.attributeStates.get(state);
        }
        return value != null && value.enabled;
    }
    
    protected void setStaticAttributeState(final AttributeState state) {
        if (!state.isStaticState()) {
            throw new IllegalArgumentException("AttributeState \"" + String.valueOf(state) + "\" is not static");
        }
        this.staticAttributeStates.put(state, new AttributeStateValue(true));
    }
    
    @Override
    public void setAttributeState(final AttributeState state, final boolean enabled) {
        if (state.isStaticState()) {
            throw new IllegalArgumentException("setAttributeState() may only be used with states that are not static (state.isStaticState())");
        }
        AttributeStateValue value = this.attributeStates.get(state);
        boolean changed;
        if (value == null) {
            value = new AttributeStateValue(enabled);
            this.attributeStates.put(state, value);
            changed = enabled;
        }
        else {
            changed = value.setEnabled(enabled);
        }
        if (changed) {
            this.onAttributeStateChanged(state, enabled);
        }
    }
    
    public WidgetReference displayInOverlay(final Widget widget) {
        return this.displayInOverlay(this.getStyleSheets(), widget);
    }
    
    public WidgetReference displayInOverlay(final List<StyleSheet> styles) {
        return this.displayInOverlay(styles, this);
    }
    
    public WidgetReference displayInOverlay(final List<StyleSheet> styles, final Widget widget) {
        final Parent root = this.getRoot();
        return this.labyAPI.screenOverlayHandler().displayInOverlay((root instanceof LabyScreen) ? ((LabyScreen)root) : null, styles, widget);
    }
    
    public Theme getTheme() {
        return this.labyAPI.themeService().currentTheme();
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
        super.updateLssVariable(variable);
        for (final T child : this.children) {
            child.updateLssVariable(variable);
        }
    }
    
    @Override
    public void forceUpdateLssVariable(final LssVariable variable) {
        super.forceUpdateLssVariable(variable);
        for (final T child : this.children) {
            child.forceUpdateLssVariable(variable);
        }
    }
    
    @Override
    public boolean shouldHandleEscape() {
        for (final T child : this.children) {
            if (child.shouldHandleEscape()) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean hasContextMenu() {
        return (this.contextMenu != null && !this.contextMenu.entries().isEmpty()) || this.contextMenuFactory != null;
    }
    
    @Nullable
    @Override
    public DirectPropertyValueAccessor getDirectPropertyValueAccessor() {
        return this.directPropertyValueAccessor;
    }
    
    @Nullable
    public ContextMenu getContextMenu() {
        this.callContextMenuFactory();
        return this.contextMenu;
    }
    
    public void setContextMenu(final ContextMenu contextMenu) {
        this.contextMenu = contextMenu;
    }
    
    public ContextMenu createContextMenu() {
        return this.contextMenu = new ContextMenu();
    }
    
    public void createContextMenuLazy(final Consumer<ContextMenu> factory) {
        this.contextMenuFactory = factory;
    }
    
    private void callContextMenuFactory() {
        if (this.contextMenu != null || this.contextMenuFactory == null) {
            return;
        }
        this.contextMenuFactory.accept(this.createContextMenu());
        this.contextMenuFactory = null;
    }
    
    protected void updateContentBounds() {
        this.handleSizeAttributes();
    }
    
    public boolean isInitialized() {
        return this.initialized;
    }
    
    public boolean isPendingInitialize() {
        return this.pendingInitialize;
    }
    
    protected void allowCustomFont(final Runnable runnable) {
        final Parent root = this.getRoot();
        final LabyScreen screen = (root instanceof LabyScreen) ? ((LabyScreen)root) : null;
        final ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
        screenCustomFontStack.push(screen);
        runnable.run();
        screenCustomFontStack.pop(screen);
    }
    
    protected boolean shouldCancelHoverComponent(Widget widget) {
        for (final Widget child : widget.getChildren()) {
            if (this.shouldCancelHoverComponent(child)) {
                return true;
            }
        }
        if (widget instanceof final WrappedWidget wrappedWidget) {
            widget = wrappedWidget.childWidget();
        }
        boolean hasHoverComponent;
        if (widget instanceof AbstractWidget) {
            final AbstractWidget<?> abstractWidget = (AbstractWidget<?>)widget;
            hasHoverComponent = abstractWidget.isHoverComponentRendered();
        }
        else {
            hasHoverComponent = (widget.isHovered() && widget.hasHoverComponent());
        }
        return hasHoverComponent && widget.isCancelParentHoverComponent();
    }
    
    public WidgetRendererContext widgetRendererContext() {
        return this.widgetRendererContext;
    }
    
    public WidgetTransformer widgetTransformer() {
        return this.widgetTransformer;
    }
    
    @ApiStatus.Experimental
    protected int getLastBoundsUpdateFrame() {
        return this.lastBoundsUpdateFrame;
    }
    
    @ApiStatus.Experimental
    protected boolean didBoundsChangeThisFrame() {
        return this.lastBoundsUpdateFrame == AbstractWidget.FRAME_TIMER.getFrame();
    }
    
    @Deprecated
    public LssProperty<Integer> backgroundDirtBrightness() {
        return this.backgroundDirtBrightness;
    }
    
    @Deprecated
    @NotNull
    public ContextMenu contextMenu() {
        final ContextMenu contextMenu = this.getContextMenu();
        if (contextMenu == null) {
            this.contextMenu = new ContextMenu();
        }
        return this.contextMenu;
    }
    
    private void prepareWidgetRendering(final ScreenContext context) {
        this.labyAPI.gfxRenderPipeline().overlappingTranslator().translate(this, context.stack());
        if (this.renderer().get() == null) {
            this.initializeThemeRenderer();
        }
        if (this.isLazy()) {
            this.updateLazy();
        }
        this.bounds().checkForChanges();
        this.animation().apply(context.getTickDelta());
    }
    
    static {
        RENDER_ENVIRONMENT_CONTEXT = Laby.references().renderEnvironmentContext();
        LOGGER = Logging.getLogger();
        FRAME_TIMER = Laby.references().frameTimer();
        FIT_SIZE_INIT = ModifyReason.of("fitSizeInit");
        FULL_PARENT_SIZE = ModifyReason.of("fullParentSize");
        MAINTAIN_ASPECT_RATIO = ModifyReason.of("maintainAspectRatio");
        LSS_SIZE = ModifyReason.of("lssSize");
        ALIGNMENT = ModifyReason.of("alignment");
        RELATIVE_BOUNDS_TOP = ModifyReason.of("relativeBoundsTop");
        RELATIVE_BOUNDS_BOTTOM = ModifyReason.of("relativeBoundsBottom");
        RELATIVE_BOUNDS_LEFT = ModifyReason.of("relativeBoundsLeft");
        RELATIVE_BOUNDS_RIGHT = ModifyReason.of("relativeBoundsRight");
        MIN_SIZE = ModifyReason.of("minSize");
        MAX_SIZE = ModifyReason.of("maxSize");
        DEFAULT_WIDGET_COMPARATOR = ((a, b) -> Integer.compare(a.priorityLayer().get().ordinal(), b.priorityLayer().get().ordinal()) * 2 + Integer.compare(a.getSortingValue(), b.getSortingValue()));
    }
    
    private static class AttributeStateValue
    {
        private boolean enabled;
        private long lastUpdate;
        
        public AttributeStateValue(final boolean enabled) {
            this.enabled = enabled;
            this.lastUpdate = TimeUtil.getMillis();
        }
        
        public boolean setEnabled(final boolean enabled) {
            if (this.enabled != enabled) {
                this.enabled = enabled;
                this.lastUpdate = TimeUtil.getMillis();
                return true;
            }
            return false;
        }
    }
}
