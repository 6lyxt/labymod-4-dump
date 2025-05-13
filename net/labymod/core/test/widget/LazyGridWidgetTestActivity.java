// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.widget;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.SessionedListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridFeedWidget;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.core.test.TestActivity;

@Link("test/lazy-grid-widget.lss")
@AutoActivity
public class LazyGridWidgetTestActivity extends TestActivity
{
    private static final ListSession<DummyWidget> GRID_SESSION;
    private static final ListSession<DummyWidget> LAZY_GRID_SESSION;
    private static int gridSize;
    private static int lazyGridSize;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final FlexibleContentWidget contentWidget = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)contentWidget).addId("content-wrapper");
        contentWidget.addFlexibleContent(this.createWrapper("Lazy Grid Widget", () -> new LazyGridWidget((ListSession<AbstractWidget>)LazyGridWidgetTestActivity.GRID_SESSION, LazyGridWidgetTestActivity.gridSize, i -> {
            final DummyWidget dummyWidget = new DummyWidget(i + 1);
            return dummyWidget;
        }), (gridWidget, buttonWrapper) -> {
            final ButtonWidget loadMoreButton = ButtonWidget.text("Load more");
            loadMoreButton.setPressable(() -> gridWidget.totalEntries(LazyGridWidgetTestActivity.gridSize += 50));
            buttonWrapper.addEntry(loadMoreButton);
            final ButtonWidget resetSizeButton = ButtonWidget.text("Reset Size");
            resetSizeButton.setPressable(() -> gridWidget.totalEntries(LazyGridWidgetTestActivity.gridSize = 150));
            buttonWrapper.addEntry(resetSizeButton);
            return;
        }));
        contentWidget.addFlexibleContent(this.createWrapper("Lazy Grid Feed Widget", () -> new LazyGridFeedWidget((ListSession<AbstractWidget>)LazyGridWidgetTestActivity.LAZY_GRID_SESSION, LazyGridWidgetTestActivity.lazyGridSize, (i, feed) -> Task.builder(() -> feed.setTotalEntries(LazyGridWidgetTestActivity.lazyGridSize += 50)).delay(1L, TimeUnit.SECONDS).build().executeOnRenderThread(), i -> {
            if (i < LazyGridWidgetTestActivity.lazyGridSize) {
                final DummyWidget dummyWidget2 = new DummyWidget(i + 1);
                return dummyWidget2;
            }
            else {
                return null;
            }
        }), (gridWidget, buttonWrapper) -> {
            final ButtonWidget loadMoreButton2 = ButtonWidget.text("Load more Sync");
            loadMoreButton2.setPressable(() -> gridWidget.totalEntries(LazyGridWidgetTestActivity.lazyGridSize += 50));
            buttonWrapper.addEntry(loadMoreButton2);
            final ButtonWidget loadMoreAsyncButton = ButtonWidget.text("Load more Async");
            loadMoreAsyncButton.setPressable(() -> {
                gridWidget.fetching();
                Task.builder(() -> gridWidget.totalEntries(LazyGridWidgetTestActivity.lazyGridSize += 50)).delay(1L, TimeUnit.SECONDS).build().executeOnRenderThread();
                gridWidget.totalEntries(LazyGridWidgetTestActivity.lazyGridSize);
                return;
            });
            buttonWrapper.addEntry(loadMoreAsyncButton);
            final ButtonWidget doneButton = ButtonWidget.text("Done");
            doneButton.setPressable(() -> gridWidget.done(!gridWidget.isDone()));
            buttonWrapper.addEntry(doneButton);
            final ButtonWidget resetSizeButton2 = ButtonWidget.text("Reset Size");
            resetSizeButton2.setPressable(() -> gridWidget.totalEntries(LazyGridWidgetTestActivity.lazyGridSize = 0));
            buttonWrapper.addEntry(resetSizeButton2);
            return;
        }));
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(contentWidget);
    }
    
    private <T extends LazyGridWidget<?>> Widget createWrapper(final String title, final Supplier<T> widgetSupplier, final BiConsumer<T, HorizontalListWidget> buttonConsumer) {
        final FlexibleContentWidget wrapper = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)wrapper).addId("grid-wrapper");
        wrapper.addContent(ComponentWidget.text(title));
        final T grid = widgetSupplier.get();
        grid.addId("grid");
        wrapper.addFlexibleContent(new ScrollWidget(grid));
        final HorizontalListWidget buttonWrapper = new HorizontalListWidget();
        ((AbstractWidget<Widget>)buttonWrapper).addId("button-wrapper");
        buttonConsumer.accept(grid, buttonWrapper);
        wrapper.addContent(buttonWrapper);
        return wrapper;
    }
    
    static {
        GRID_SESSION = new ListSession<DummyWidget>();
        LAZY_GRID_SESSION = new ListSession<DummyWidget>();
        LazyGridWidgetTestActivity.gridSize = 150;
        LazyGridWidgetTestActivity.lazyGridSize = 150;
    }
    
    @AutoWidget
    public static class DummyWidget extends AbstractWidget<Widget>
    {
        private final int index;
        
        public DummyWidget(final int index) {
            this.index = index;
        }
        
        @Override
        public void initialize(final Parent parent) {
            super.initialize(parent);
            final ComponentWidget componentWidget = ComponentWidget.text("" + this.index);
            ((AbstractWidget<ComponentWidget>)this).addChild(componentWidget);
        }
    }
}
