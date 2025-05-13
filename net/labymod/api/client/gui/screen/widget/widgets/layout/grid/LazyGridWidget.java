// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout.grid;

import net.labymod.api.property.Property;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import java.util.function.IntFunction;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.SessionedListWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class LazyGridWidget<T extends AbstractWidget<?>> extends SessionedListWidget<Widget>
{
    private static final ModifyReason REASON;
    private final LssProperty<Integer> tilesPerLine;
    private final LssProperty<Boolean> dynamicTilesPerLine;
    private final LssProperty<Float> tileHeight;
    private final LssProperty<Float> tileWidth;
    private final LssProperty<Float> tileSize;
    private final LssProperty<Float> spaceBetweenEntries;
    private final Int2ReferenceOpenHashMap<T> widgets;
    private IntFunction<T> widgetSupplier;
    private int totalEntries;
    private float height;
    private int actualTilesPerLine;
    private boolean postStyleSheetLoad;
    private boolean updateNextRenderCall;
    private float actualTileHeight;
    private float actualTileWidth;
    
    public LazyGridWidget(@NotNull final ListSession<T> session, final int totalEntries, @Nullable final IntFunction<T> widgetSupplier) {
        super((ListSession<Widget>)session);
        this.tilesPerLine = new LssProperty<Integer>(2);
        this.dynamicTilesPerLine = new LssProperty<Boolean>(false);
        this.tileHeight = new LssProperty<Float>(-1.0f);
        this.tileWidth = new LssProperty<Float>(-1.0f);
        this.tileSize = new LssProperty<Float>(-1.0f);
        this.spaceBetweenEntries = new LssProperty<Float>(2.0f);
        this.widgets = (Int2ReferenceOpenHashMap<T>)new Int2ReferenceOpenHashMap();
        this.actualTilesPerLine = -1;
        this.totalEntries = totalEntries;
        this.widgetSupplier = widgetSupplier;
        this.translateX().addChangeListener((type, oldValue, newValue) -> this.updateVisibility());
        this.translateY().addChangeListener((type, oldValue, newValue) -> this.updateVisibility());
    }
    
    public LazyGridWidget(@NotNull final ListSession<T> session, final int totalEntries) {
        this(session, totalEntries, null);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        if (this.actualTilesPerLine < 0) {
            this.update();
        }
        return this.height + this.bounds().getVerticalOffset(type);
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.update();
    }
    
    @Override
    public void initialize(final Parent parent) {
        this.widgets.clear();
        this.children.clear();
        this.actualTilesPerLine = -1;
        this.postStyleSheetLoad = false;
        super.initialize(parent);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        if (this.updateNextRenderCall) {
            this.update();
            this.updateNextRenderCall = false;
        }
        super.renderWidget(context);
    }
    
    @Override
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        this.postStyleSheetLoad = true;
    }
    
    @Override
    public void updateVisibility(final ListWidget<?> listWidget, final Parent parent) {
        super.updateVisibility(listWidget, parent);
        final float spaceBetween = this.spaceBetweenEntries.get();
        final int tilesPerLine = this.actualTilesPerLine;
        final float tileHeight = this.actualTileHeight;
        final float tileWidth = this.actualTileWidth;
        if (tilesPerLine <= 0 || tileHeight <= 0.0f || tileWidth <= 0.0f) {
            return;
        }
        final Bounds bounds = this.bounds();
        final float parentHeight = this.parent.bounds().getHeight();
        final float translateY = this.session.getScrollPositionY();
        final int startLine = MathHelper.floor(translateY / (tileHeight + spaceBetween));
        final int endLine = MathHelper.ceil((translateY + parentHeight) / (tileHeight + spaceBetween));
        int startTile = startLine * tilesPerLine - tilesPerLine;
        int endTile = endLine * tilesPerLine - 1 + tilesPerLine;
        if (endTile > this.totalEntries) {
            endTile = this.totalEntries;
        }
        if (startTile < 0) {
            startTile = 0;
        }
        if (endTile < 0) {
            endTile = 0;
        }
        int created = 0;
        int visible = 0;
        for (int i = 0; i < this.totalEntries; ++i) {
            T widget = (T)this.widgets.get(i);
            if (i < startTile || i > endTile || (widget == null && this.widgetSupplier == null)) {
                if (widget != null) {
                    widget.visible().set(false);
                }
            }
            else {
                final boolean newWidget = widget == null;
                if (newWidget) {
                    widget = this.widgetSupplier.apply(i);
                    if (widget == null) {
                        break;
                    }
                    this.widgets.put(i, (Object)widget);
                    ++created;
                }
                ++visible;
                widget.visible().set(true);
                widget.bounds().setOuterPosition(bounds.getX() + i % tilesPerLine * (tileWidth + spaceBetween), bounds.getY() + i / tilesPerLine * (tileHeight + spaceBetween), LazyGridWidget.REASON);
                widget.bounds().setOuterSize(tileWidth, tileHeight, LazyGridWidget.REASON);
                if (newWidget) {
                    this.addChildInitialized(widget, false);
                }
            }
        }
    }
    
    protected float getTileHeight() {
        return this.actualTileHeight;
    }
    
    protected float getTileWidth() {
        return this.actualTileWidth;
    }
    
    protected int getTilesPerLine() {
        return this.actualTilesPerLine;
    }
    
    private void updateVisibility() {
        this.updateVisibility(this, this.parent);
    }
    
    @Override
    protected void reloadStyleSheets(final boolean updateParent) {
        this.postStyleSheetLoad = false;
        super.reloadStyleSheets(updateParent);
    }
    
    protected void update() {
        if (!this.postStyleSheetLoad) {
            return;
        }
        float tileHeight = this.tileHeight.get();
        float tileWidth = this.tileWidth.get();
        final float tileSize = this.tileSize.get();
        final float spaceBetween = this.spaceBetweenEntries.get();
        int tilesPerLine = this.tilesPerLine.get();
        final boolean dynamicTilesPerLine = this.dynamicTilesPerLine.get();
        if (tileSize != -1.0f) {
            tileHeight = tileSize;
            tileWidth = tileSize;
        }
        final Bounds bounds = this.bounds();
        float width = bounds.getWidth();
        if (this.hasSize(SizeType.ACTUAL, WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT) || this.hasSize(SizeType.MIN, WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT)) {
            width = this.parent.bounds().getWidth();
        }
        if (width <= 0.0f) {
            return;
        }
        if (tileWidth == -1.0f || tileHeight == -1.0f) {
            final boolean dynamicWidth = tileWidth == -1.0f;
            boolean dynamicHeight = tileHeight == -1.0f;
            if (dynamicWidth) {
                tileWidth = 0.0f;
            }
            if (dynamicHeight) {
                tileHeight = 0.0f;
            }
            if (dynamicTilesPerLine) {
                for (final T child : this.widgets.values()) {
                    if (dynamicWidth) {
                        float childWidth;
                        if (child.hasSize(SizeType.ACTUAL, WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT) || !child.hasAnySize(WidgetSide.WIDTH)) {
                            childWidth = child.getContentWidth(BoundsType.OUTER);
                        }
                        else {
                            childWidth = child.bounds().getWidth(BoundsType.OUTER);
                        }
                        tileWidth = Math.max(tileWidth, childWidth);
                    }
                }
                dynamicHeight = false;
            }
            else if (dynamicWidth) {
                tileWidth = (width - (tilesPerLine - 1) * spaceBetween) / tilesPerLine;
            }
            if (dynamicHeight) {
                tileHeight = tileWidth;
            }
        }
        if (dynamicTilesPerLine) {
            tilesPerLine = Math.max(1, MathHelper.floor(width / (tileWidth + spaceBetween)));
        }
        this.actualTilesPerLine = tilesPerLine;
        this.actualTileHeight = tileHeight;
        this.actualTileWidth = tileWidth;
        if (tilesPerLine <= 0) {
            this.updateVisibility();
            return;
        }
        final int rows = MathHelper.ceil(this.totalEntries / (float)tilesPerLine);
        this.height = this.applyHeight(rows * tileHeight + (rows - 1) * spaceBetween);
        if (bounds.getHeight() != this.height) {
            bounds.setHeight(this.height, LazyGridWidget.REASON);
        }
        if (bounds.getWidth() != width) {
            bounds.setWidth(width, LazyGridWidget.REASON);
        }
        this.updateVisibility();
    }
    
    protected float applyHeight(final float height) {
        return height;
    }
    
    protected void updateNextRenderCall() {
        this.updateNextRenderCall = true;
    }
    
    public LazyGridWidget<T> widgetSupplier(@Nullable final IntFunction<T> widgetSupplier) {
        this.widgetSupplier = widgetSupplier;
        return this;
    }
    
    @Nullable
    public IntFunction<T> getWidgetSupplier() {
        return this.widgetSupplier;
    }
    
    public LazyGridWidget<T> totalEntries(final int totalEntries) {
        final int prevEntries = this.totalEntries;
        this.totalEntries = totalEntries;
        if (totalEntries < prevEntries) {
            this.reInitialize();
        }
        else {
            this.updateNextRenderCall();
        }
        return this;
    }
    
    public int getTotalEntries() {
        return this.totalEntries;
    }
    
    public LssProperty<Integer> tilesPerLine() {
        return this.tilesPerLine;
    }
    
    public LssProperty<Boolean> dynamicTilesPerLine() {
        return this.dynamicTilesPerLine;
    }
    
    public LssProperty<Float> tileHeight() {
        return this.tileHeight;
    }
    
    public LssProperty<Float> tileWidth() {
        return this.tileWidth;
    }
    
    public LssProperty<Float> tileSize() {
        return this.tileSize;
    }
    
    public LssProperty<Float> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }
    
    static {
        REASON = ModifyReason.of("LazyGridWidget");
    }
}
