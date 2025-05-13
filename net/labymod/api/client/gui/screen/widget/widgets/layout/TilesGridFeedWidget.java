// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import net.labymod.api.client.gui.lss.property.LssProperty;
import java.util.function.Consumer;
import java.util.function.BiPredicate;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@AutoWidget
public class TilesGridFeedWidget<T extends Widget> extends TilesGridWidget<T>
{
    private final BiPredicate<TilesGridFeedWidget<T>, Consumer<T>> refresh;
    private final LssProperty<Float> refreshRadius;
    private boolean doRefresh;
    
    public TilesGridFeedWidget(final BiPredicate<TilesGridFeedWidget<T>, Consumer<T>> refresh) {
        this.refreshRadius = new LssProperty<Float>(-1.0f);
        this.doRefresh = true;
        this.refresh = refresh;
    }
    
    @Override
    public void updateVisibility(final ListWidget<?> list, final Parent parent) {
        super.updateVisibility(list, parent);
        if (!this.doRefresh || !(parent instanceof ScrollWidget)) {
            return;
        }
        final Bounds bounds = this.bounds();
        final Bounds parentBounds = parent.bounds();
        final ListSession<?> session = ((ScrollWidget)parent).session();
        final float height = bounds.getHeight();
        if (height <= 0.0f) {
            return;
        }
        final float radius = this.refreshRadius.isDefaultValue() ? (this.getTileHeight() * 2.0f) : this.refreshRadius.get();
        if (session.getScrollPositionY() >= height - parentBounds.getHeight() - radius) {
            this.doRefresh = false;
            if (this.refresh.test(this, this::addTileInitialized)) {
                this.updateTiles();
                this.doRefresh = true;
            }
        }
    }
    
    public LssProperty<Float> refreshRadius() {
        return this.refreshRadius;
    }
    
    public void doRefresh(final boolean doRefresh) {
        this.doRefresh = doRefresh;
        if (doRefresh) {
            this.updateVisibility(this, this.parent);
        }
    }
}
