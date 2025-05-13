// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.hud;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.Iterator;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.ChainAlignmentSide;
import net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.chain.ChainAttachingLine;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget;

@AutoWidget
public class HudWidgetWidget extends InterpolateWidget
{
    private static final ModifyReason HUD_WIDGET_SELF;
    private final HudWidget<?> hudWidget;
    private final HudWidgetRendererAccessor accessor;
    private final ScaledRectangle scaledRectangle;
    private final HudSize size;
    private final ChainAttachingLine chainAttachingLineTop;
    private final ChainAttachingLine chainAttachingLineBottom;
    private ChainAlignmentSide alignmentVisibility;
    private float prevWidth;
    private float prevHeight;
    private Runnable doubleClickListener;
    private long lastClickedTime;
    private HudWidgetAnchor initializedAnchor;
    
    public HudWidgetWidget(@NotNull final HudWidget<?> hudWidget, final HudWidgetRendererAccessor accessor) {
        super(50.0f);
        this.scaledRectangle = new ScaledRectangle(this);
        this.chainAttachingLineTop = new ChainAttachingLine(ChainAlignmentSide.TOP);
        this.chainAttachingLineBottom = new ChainAttachingLine(ChainAlignmentSide.BOTTOM);
        this.doubleClickListener = null;
        this.lastClickedTime = 0L;
        this.initializedAnchor = null;
        Objects.requireNonNull(hudWidget, "Hud Widget cannot be null");
        this.handleStyleSheet = false;
        this.accessor = accessor;
        this.hudWidget = hudWidget;
        this.size = new HudSize((HudWidgetConfig)this.hudWidget.getConfig());
        this.visible().set(false);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.draggable().set(true);
        this.hoverBoxDelay().set(1000);
        this.hudWidget.initialize(this);
    }
    
    @Override
    public void postInitialize() {
        super.postInitialize();
        this.hudWidget.postInitialize(this);
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.hudWidget.onBoundsChanged(this, previousRect, newRect);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (!this.accessor.isEditor() && this.hudWidget.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        final long timePassedSinceLastClick = TimeUtil.getMillis() - this.lastClickedTime;
        if (timePassedSinceLastClick < 250L) {
            if (this.doubleClickListener != null) {
                this.doubleClickListener.run();
            }
            if (this.accessor.isEditor()) {
                this.accessor.openSettings(this.hudWidget);
            }
        }
        this.lastClickedTime = TimeUtil.getMillis();
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public void render(final ScreenContext context) {
        final boolean editorContext = this.accessor.isEditor();
        if (!this.hudWidget.isHolderEnabled() || (!editorContext && this.accessor.isDebugEnabled() && !this.hudWidget.renderInDebug())) {
            return;
        }
        if (this.hudWidget.isEnabled()) {
            this.hudWidget.updateSize(this, editorContext, this.size);
        }
        final boolean visibility = (this.hudWidget.isVisibleInGame() || editorContext) && this.hudWidget.isEnabled();
        if (this.visible().get() != visibility) {
            this.visible().set(visibility);
            this.accessor.onVisibilityChanged(this.hudWidget);
        }
        if (this.prevWidth != this.size.getActualWidth() || this.prevHeight != this.size.getActualHeight()) {
            this.prevWidth = this.size.getActualWidth();
            this.prevHeight = this.size.getActualHeight();
            this.accessor.onSizeChanged(this.hudWidget);
        }
        super.render(context);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        try {
            context.pushStack();
            final ScaledRectangle bounds = this.scaledBounds();
            final boolean floatingPointPosition = this.accessor.isEditor();
            context.stack().translate(MathHelper.applyFloatingPointPosition(floatingPointPosition, bounds.getX()), MathHelper.applyFloatingPointPosition(floatingPointPosition, bounds.getY()), 0.0f);
            context.mouse().transform(bounds, () -> {
                try {
                    context.pushStack();
                    this.renderHudWidget(context);
                }
                finally {
                    context.popStack();
                }
            });
        }
        finally {
            context.popStack();
        }
    }
    
    private void renderHudWidget(final ScreenContext context) {
        final float scale = this.size.getScale();
        if (scale != 1.0f && !this.hudWidget.handlesScaling()) {
            context.stack().scale(scale, scale, 1.0f);
        }
        this.hudWidget.renderWidget(context.stack(), context.mouse(), context.getTickDelta(), this.accessor.isEditor(), this.size);
    }
    
    @Override
    public void renderOverlay(final ScreenContext context) {
        super.renderOverlay(context);
        final Stack stack = context.stack();
        final float tickDelta = context.getTickDelta();
        stack.push();
        final ScaledRectangle bounds = this.scaledBounds();
        stack.translate(bounds.getX(), bounds.getY(), 0.0f);
        this.chainAttachingLineTop.render(stack, bounds, tickDelta);
        this.chainAttachingLineBottom.render(stack, bounds, tickDelta);
        stack.pop();
    }
    
    @Override
    public void tick() {
        super.tick();
        this.chainAttachingLineTop.tick(this.alignmentVisibility == ChainAlignmentSide.TOP);
        this.chainAttachingLineBottom.tick(this.alignmentVisibility == ChainAlignmentSide.BOTTOM);
        final boolean editor = this.accessor.isEditor();
        if (this.hudWidget.isEnabled() && (editor || this.labyAPI.minecraft().isIngame())) {
            this.hudWidget.tick(editor);
        }
    }
    
    public void setDoubleClickListener(final Runnable doubleClickListener) {
        this.doubleClickListener = doubleClickListener;
    }
    
    @NotNull
    public HudWidget<?> hudWidget() {
        return this.hudWidget;
    }
    
    @Deprecated
    public HudWidget<?> getHudWidget() {
        return this.hudWidget();
    }
    
    public void setAlignmentVisibility(final ChainAlignmentSide visibility) {
        this.alignmentVisibility = visibility;
    }
    
    public ChainAlignmentSide getAlignmentVisibility() {
        return this.alignmentVisibility;
    }
    
    public void setInitializedAnchor(final HudWidgetAnchor initializedAnchor) {
        this.initializedAnchor = initializedAnchor;
    }
    
    public HudWidgetAnchor getInitializedAnchor() {
        return this.initializedAnchor;
    }
    
    public HudWidgetRendererAccessor accessor() {
        return this.accessor;
    }
    
    public void update(final String reason) {
        boolean update = true;
        for (final Widget child : this.children) {
            if (!(child instanceof HudWidget.Updatable)) {
                update = false;
                break;
            }
        }
        if (!update) {
            this.reInitialize();
            return;
        }
        for (final Widget child : this.children) {
            ((HudWidget.Updatable)child).update(reason);
        }
    }
    
    public ScaledRectangle scaledBounds() {
        return this.scaledRectangle;
    }
    
    public HudSize size() {
        return this.size;
    }
    
    public void updateSizeOfWidget() {
        final ScaledRectangle rectangle = this.scaledBounds();
        rectangle.setSize(this.size.getScaledWidth(), this.size.getScaledHeight(), HudWidgetWidget.HUD_WIDGET_SELF);
        rectangle.checkForChanges();
        if (!this.accessor.isEditor() && !this.labyAPI.hudWidgetRegistry().globalHudWidgetConfig().smoothMovement().get()) {
            this.skipInterpolation();
        }
    }
    
    @Deprecated
    @Override
    public Bounds bounds() {
        return super.bounds();
    }
    
    static {
        HUD_WIDGET_SELF = ModifyReason.of("hudWidgetSelf");
    }
}
