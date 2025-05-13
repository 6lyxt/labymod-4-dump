// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.action;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.VerticalAlignment;
import java.util.Iterator;
import net.labymod.api.util.time.TimeUtil;
import java.util.ArrayList;
import java.util.List;

public class ListSession<T>
{
    private final Equalizer<T> comparator;
    private float scrollPositionY;
    private float renderScrollPositionY;
    private float maxScrollPositionY;
    private float contentHeight;
    private T selectedEntry;
    private float previousScrollPositionY;
    private long lastScrollTime;
    private final List<ScrollListener> listeners;
    private EntrySwapper entrySwapper;
    
    public ListSession() {
        this(null);
    }
    
    public ListSession(final Equalizer<T> comparator) {
        this.maxScrollPositionY = -1.0f;
        this.previousScrollPositionY = 0.0f;
        this.listeners = new ArrayList<ScrollListener>();
        this.comparator = comparator;
    }
    
    public static ListSession<?> create() {
        return new ListSession<Object>();
    }
    
    public boolean compareSelectedTo(final T widget) {
        return this.selectedEntry != null && ((this.comparator != null) ? this.comparator.equals(this.selectedEntry, widget) : widget.equals(this.selectedEntry));
    }
    
    public void scroll(final float delta) {
        this.lastScrollTime = TimeUtil.getMillis();
        this.previousScrollPositionY = this.scrollPositionY;
        this.scrollPositionY += delta;
        for (final ScrollListener listener : this.listeners) {
            listener.onScrolled(delta);
        }
    }
    
    public T getSelectedEntry() {
        return this.selectedEntry;
    }
    
    public void setSelectedEntry(final T selectedEntry) {
        this.selectedEntry = selectedEntry;
    }
    
    public float getScrollPositionY() {
        return this.scrollPositionY;
    }
    
    public float getPreviousScrollPositionY() {
        return this.previousScrollPositionY;
    }
    
    public void setScrollPositionY(final float scrollPositionY) {
        this.scrollPositionY = scrollPositionY;
    }
    
    public void setPreviousScrollPositionY(final float previousScrollPositionY) {
        this.previousScrollPositionY = previousScrollPositionY;
    }
    
    public float getRenderScrollPositionY() {
        return this.renderScrollPositionY;
    }
    
    public void skipAnimation() {
        this.previousScrollPositionY = this.scrollPositionY;
    }
    
    public void setEntrySwapper(final EntrySwapper entrySwapper) {
        this.entrySwapper = entrySwapper;
    }
    
    public EntrySwapper getEntrySwapper() {
        return this.entrySwapper;
    }
    
    public boolean hasSelectedEntry() {
        return this.selectedEntry != null;
    }
    
    public void addListener(final ScrollListener listener) {
        this.listeners.add(listener);
    }
    
    public void removeListener(final ScrollListener listener) {
        this.listeners.remove(listener);
    }
    
    public List<ScrollListener> getListeners() {
        return this.listeners;
    }
    
    public void scrollToBottom() {
        if (!this.isScrollbarRequired()) {
            return;
        }
        this.scroll(this.maxScrollPositionY - this.scrollPositionY);
    }
    
    public boolean isScrolledToBottom() {
        return this.isScrollbarRequired() && this.scrollPositionY >= this.maxScrollPositionY;
    }
    
    public boolean isScrollbarRequired() {
        return this.maxScrollPositionY >= 0.0f;
    }
    
    public float maxScrollPositionY() {
        return this.maxScrollPositionY;
    }
    
    public float getContentHeight() {
        return this.contentHeight;
    }
    
    public void updateBounds(final float contentHeight, final float maxScrollPositionY, final VerticalAlignment fixedPosition) {
        this.contentHeight = contentHeight;
        this.maxScrollPositionY = maxScrollPositionY;
        if (this.scrollPositionY > maxScrollPositionY) {
            this.setScrollPositionY(maxScrollPositionY);
        }
        if (this.scrollPositionY < 0.0f) {
            this.setScrollPositionY(0.0f);
        }
        if (fixedPosition != null) {
            switch (fixedPosition) {
                case TOP: {
                    this.setScrollPositionY(0.0f);
                    break;
                }
                case CENTER: {
                    this.setScrollPositionY(maxScrollPositionY / 2.0f);
                    break;
                }
                case BOTTOM: {
                    this.setScrollPositionY(maxScrollPositionY);
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + String.valueOf(fixedPosition));
                }
            }
        }
        final boolean interpolateScroll = Laby.labyAPI().themeService().currentTheme().metadata().getBoolean("interpolate_scroll");
        if (interpolateScroll) {
            final float timePassed = (float)(TimeUtil.getMillis() - this.lastScrollTime);
            final float percentage = MathHelper.sigmoid(MathHelper.clamp(0.01f * timePassed, 0.0f, 1.0f));
            final float scrollDifference = this.scrollPositionY - this.previousScrollPositionY;
            this.renderScrollPositionY = this.previousScrollPositionY + scrollDifference * percentage;
        }
        else {
            this.renderScrollPositionY = this.scrollPositionY;
        }
    }
    
    @Deprecated
    public float getInterpolatedScrollPositionY() {
        return this.getRenderScrollPositionY();
    }
    
    @Deprecated
    public void updateBounds(final float maxScrollPositionY, final VerticalAlignment fixedPosition) {
        this.updateBounds(0.0f, maxScrollPositionY, fixedPosition);
    }
    
    public interface ScrollListener
    {
        void onScrolled(final float p0);
    }
    
    public interface EntrySwapper
    {
        void swap(final int p0, final int p1);
    }
}
