// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout.grid;

import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.Color;
import org.jetbrains.annotations.Nullable;
import java.util.function.IntFunction;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class LazyGridFeedWidget<T extends AbstractWidget<?>> extends LazyGridWidget<T>
{
    private static final ModifyReason REASON;
    private final LssProperty<Float> refreshRadius;
    private final LssProperty<Float> loadingTextGap;
    private final LssProperty<String> loadingText;
    private final LssProperty<Boolean> removeLoadingText;
    private final LssProperty<Integer> loadingColor;
    private final Feed feed;
    private boolean fetching;
    private boolean done;
    private boolean forceRemoveLoadingText;
    private final LazyGridFeedLoader loader;
    private int loaderCalledWithEntries;
    
    public LazyGridFeedWidget(@NotNull final ListSession<T> session, final int initialEntries, @NotNull final LazyGridFeedLoader loader, @Nullable final IntFunction<T> widgetSupplier) {
        super(session, initialEntries);
        this.refreshRadius = new LssProperty<Float>(-1.0f);
        this.loadingTextGap = new LssProperty<Float>(10.0f);
        this.loadingText = new LssProperty<String>("labymod.misc.loading");
        this.removeLoadingText = new LssProperty<Boolean>(false);
        this.loadingColor = new LssProperty<Integer>(Color.GRAY.get());
        this.loaderCalledWithEntries = -1;
        this.widgetSupplier(widgetSupplier);
        this.loader = loader;
        this.loaderCalledWithEntries = initialEntries - 1;
        this.feed = new Feed() {
            @Override
            public void setFetching() {
                LazyGridFeedWidget.this.fetching();
            }
            
            @Override
            public void setDone() {
                LazyGridFeedWidget.this.fetching();
            }
            
            @Override
            public void setTotalEntries(final int totalEntries) {
                LazyGridFeedWidget.this.totalEntries(totalEntries);
            }
            
            @Override
            public void addEntries(final int entries) {
                LazyGridFeedWidget.this.totalEntries(LazyGridFeedWidget.this.getTotalEntries() + entries);
            }
        };
    }
    
    public LazyGridFeedWidget(@NotNull final ListSession<T> session, final int initialEntries, @NotNull final LazyGridFeedLoader loader) {
        this(session, initialEntries, loader, null);
    }
    
    @Override
    public LazyGridFeedWidget<T> widgetSupplier(@Nullable final IntFunction<T> widgetSupplier) {
        if (widgetSupplier == null) {
            super.widgetSupplier(null);
            return this;
        }
        super.widgetSupplier(i -> {
            final T widget = widgetSupplier.apply(i);
            if (widget == null) {
                this.fetching();
            }
            return widget;
        });
        return this;
    }
    
    @Override
    protected float applyHeight(float height) {
        if (this.forceRemoveLoadingText || !this.fetching || this.done || this.loadingText.get() == null || this.removeLoadingText.get()) {
            final Widget child;
            this.removeChildIf(child -> child.hasId("grid-feed-fetching-component"));
            return super.applyHeight(height);
        }
        Widget loadingTextWidget = null;
        final Iterator<T> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            final Widget child = iterator.next();
            if (child.hasId("grid-feed-fetching-component")) {
                loadingTextWidget = child;
                break;
            }
        }
        final boolean createNew = loadingTextWidget == null;
        if (loadingTextWidget == null) {
            final ComponentWidget component = ComponentWidget.i18n(this.loadingText.get());
            component.addId("grid-feed-fetching-component");
            component.textColor().set(this.loadingColor.get());
            component.alignmentX().set(WidgetAlignment.CENTER);
            loadingTextWidget = component;
        }
        final float gap = this.loadingTextGap.get();
        loadingTextWidget.bounds().setPosition(this.bounds().getCenterX(), height + gap * 2.0f, LazyGridFeedWidget.REASON);
        if (createNew) {
            this.addChildInitialized(loadingTextWidget);
        }
        height += loadingTextWidget.bounds().getHeight(BoundsType.OUTER) + gap * 2.0f;
        return super.applyHeight(height);
    }
    
    @Override
    public void updateVisibility(final ListWidget<?> listWidget, final Parent parent) {
        if (this.fetching || this.done || !(parent instanceof ScrollWidget)) {
            super.updateVisibility(listWidget, parent);
            return;
        }
        final Bounds bounds = this.bounds();
        final Bounds parentBounds = parent.bounds();
        final ListSession<?> session = ((ScrollWidget)parent).session();
        final float height = bounds.getHeight();
        final float radius = this.refreshRadius.isDefaultValue() ? (this.getTileHeight() * 2.0f) : this.refreshRadius.get();
        if (height <= 0.0f || session.getScrollPositionY() >= height - parentBounds.getHeight() - radius) {
            this.fetching();
            if (this.fetching || this.done) {
                return;
            }
        }
        super.updateVisibility(listWidget, parent);
    }
    
    @Override
    public LazyGridFeedWidget<T> totalEntries(final int totalEntries) {
        ThreadSafe.ensureRenderThread();
        if (this.getTotalEntries() == totalEntries) {
            return this;
        }
        if (totalEntries <= this.loaderCalledWithEntries) {
            this.loaderCalledWithEntries = totalEntries - 1;
        }
        this.fetching(false);
        super.totalEntries(totalEntries);
        return this;
    }
    
    public LazyGridFeedWidget<T> forceRemoveLoadingText() {
        return this.forceRemoveLoadingText(true);
    }
    
    public LazyGridFeedWidget<T> forceRemoveLoadingText(final boolean forceRemoveLoadingText) {
        if (this.forceRemoveLoadingText == forceRemoveLoadingText) {
            return this;
        }
        this.forceRemoveLoadingText = forceRemoveLoadingText;
        this.updateNextRenderCall();
        return this;
    }
    
    public LazyGridFeedWidget<T> fetching() {
        return this.fetching(true);
    }
    
    public LazyGridFeedWidget<T> fetching(final boolean fetching) {
        ThreadSafe.ensureRenderThread();
        if (this.fetching == fetching) {
            return this;
        }
        this.fetching = fetching;
        if (this.fetching) {
            this.callLoader();
        }
        this.updateNextRenderCall();
        return this;
    }
    
    private void callLoader() {
        ThreadSafe.ensureRenderThread();
        if (this.done) {
            return;
        }
        final int entry = this.getTotalEntries();
        if (this.loaderCalledWithEntries >= entry) {
            return;
        }
        this.loaderCalledWithEntries = entry;
        this.loader.initialize(entry, this.feed);
    }
    
    public boolean isFetching() {
        return this.fetching;
    }
    
    public LazyGridFeedWidget<T> done() {
        return this.done(true);
    }
    
    public LazyGridFeedWidget<T> done(final boolean done) {
        ThreadSafe.ensureRenderThread();
        if (this.done == done) {
            return this;
        }
        this.done = done;
        this.updateNextRenderCall();
        return this;
    }
    
    public boolean isDone() {
        return this.done;
    }
    
    public LssProperty<Float> refreshRadius() {
        return this.refreshRadius;
    }
    
    public LssProperty<Float> loadingTextGap() {
        return this.loadingTextGap;
    }
    
    public LssProperty<String> loadingText() {
        return this.loadingText;
    }
    
    public LssProperty<Integer> loadingColor() {
        return this.loadingColor;
    }
    
    public LssProperty<Boolean> removeLoadingText() {
        return this.removeLoadingText;
    }
    
    static {
        REASON = ModifyReason.of("LazyGridFeedWidget");
    }
    
    public interface LazyGridFeedLoader
    {
        void initialize(final int p0, final Feed p1);
    }
    
    public interface Feed
    {
        void setFetching();
        
        void setDone();
        
        void setTotalEntries(final int p0);
        
        void addEntries(final int p0);
    }
}
