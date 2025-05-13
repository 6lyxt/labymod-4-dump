// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.chat.config;

import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import java.util.Iterator;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.Collection;
import java.util.Collections;
import org.spongepowered.include.com.google.common.collect.Lists;
import java.util.List;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.configuration.loader.Config;

public class ChatWindowConfig extends Config
{
    private float width;
    private float height;
    private VerticalAlignment verticalAnchor;
    private HorizontalAlignment horizontalAnchor;
    private BoundsPosition boundsPosition;
    private float x;
    private float y;
    private int focusedTab;
    private List<RootChatTabConfig> tabs;
    
    public ChatWindowConfig(final RootChatTabConfig... chatTabs) {
        this.width = 300.0f;
        this.height = 150.0f;
        this.verticalAnchor = VerticalAlignment.BOTTOM;
        this.horizontalAnchor = HorizontalAlignment.LEFT;
        this.boundsPosition = BoundsPosition.INSIDE;
        this.x = 0.0f;
        this.y = 0.0f;
        this.focusedTab = 0;
        Collections.addAll(this.tabs = Lists.newArrayList(), chatTabs);
    }
    
    public ChatWindowConfig() {
        this.width = 300.0f;
        this.height = 150.0f;
        this.verticalAnchor = VerticalAlignment.BOTTOM;
        this.horizontalAnchor = HorizontalAlignment.LEFT;
        this.boundsPosition = BoundsPosition.INSIDE;
        this.x = 0.0f;
        this.y = 0.0f;
        this.focusedTab = 0;
        (this.tabs = Lists.newArrayList()).add(this.createDefaultTab());
    }
    
    public MutableRectangle getPosition(final Bounds documentBounds) {
        final boolean inside = this.boundsPosition != BoundsPosition.OUTSIDE;
        final Rectangle document = inside ? documentBounds : documentBounds.rectangle(BoundsType.MIDDLE);
        float top;
        float bottom;
        if (this.verticalAnchor == VerticalAlignment.TOP) {
            top = (inside ? MathHelper.clamp(document.getMaxY() / 100.0f * this.y, document.getY(), document.getMaxY()) : (document.getY() + this.y));
            bottom = top + this.height;
        }
        else if (this.verticalAnchor == VerticalAlignment.CENTER) {
            top = MathHelper.clamp(document.getCenterY() - this.height / 2.0f, document.getY(), document.getMaxY());
            bottom = MathHelper.clamp(document.getCenterY() + this.height / 2.0f, document.getY(), document.getMaxY());
        }
        else {
            bottom = (inside ? MathHelper.clamp(document.getMaxY() / 100.0f * (100.0f - this.y), document.getY(), document.getMaxY()) : (document.getMaxY() - this.y));
            top = bottom - this.height;
        }
        float left;
        float right;
        if (this.horizontalAnchor == HorizontalAlignment.LEFT) {
            left = (inside ? MathHelper.clamp(document.getMaxX() / 100.0f * this.x, document.getX(), document.getMaxX()) : (document.getX() + this.x));
            right = left + this.width;
        }
        else if (this.horizontalAnchor == HorizontalAlignment.CENTER) {
            left = MathHelper.clamp(document.getCenterX() - this.width / 2.0f, document.getX(), document.getMaxX());
            right = MathHelper.clamp(document.getCenterX() + this.width / 2.0f, document.getX(), document.getMaxX());
        }
        else {
            right = (inside ? MathHelper.clamp(document.getMaxX() / 100.0f * (100.0f - this.x), document.getX(), document.getMaxX()) : (document.getMaxX() - this.x));
            left = right - this.width;
        }
        if (left < document.getLeft()) {
            left = document.getLeft();
        }
        if (right > document.getRight()) {
            right = document.getRight();
        }
        if (top < document.getTop()) {
            top = document.getTop();
        }
        if (bottom > document.getBottom()) {
            bottom = document.getBottom();
        }
        final MutableRectangle rectangle = new DefaultRectangle();
        rectangle.setBounds((float)(int)left, (float)(int)top, (float)(int)right, (float)(int)bottom);
        return rectangle;
    }
    
    public void setPosition(final Bounds documentBounds, final Rectangle rectangle) {
        if (documentBounds.isInRectangle(rectangle)) {
            this.boundsPosition = BoundsPosition.INSIDE;
        }
        else {
            this.boundsPosition = BoundsPosition.OUTSIDE;
        }
        final boolean inside = this.boundsPosition != BoundsPosition.OUTSIDE;
        final Rectangle document = inside ? documentBounds : documentBounds.rectangle(BoundsType.MIDDLE);
        this.horizontalAnchor = this.horizontalAnchor(document, rectangle);
        this.verticalAnchor = this.verticalAnchor(document, rectangle);
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
        if (inside) {
            this.x = this.getHorizontalPercentage(this.horizontalAnchor, document, rectangle);
            this.y = this.getVerticalPercentage(this.verticalAnchor, document, rectangle);
        }
        else {
            this.x = ((this.horizontalAnchor == HorizontalAlignment.LEFT) ? (rectangle.getX() - document.getX()) : (document.getMaxX() - rectangle.getMaxX()));
            this.y = ((this.verticalAnchor == VerticalAlignment.TOP) ? (rectangle.getY() - document.getY()) : (document.getMaxY() - rectangle.getMaxY()));
        }
    }
    
    public void setPosition(final float x, final float y, final HorizontalAlignment horizontalAnchor, final VerticalAlignment verticalAnchor) {
        this.horizontalAnchor = horizontalAnchor;
        this.verticalAnchor = verticalAnchor;
        this.x = x;
        this.y = y;
    }
    
    public HorizontalAlignment horizontalAnchor(final Rectangle document, final Rectangle bounds) {
        if (document.getWidth() / 2.0f == bounds.getCenterX()) {
            return HorizontalAlignment.CENTER;
        }
        if (document.getWidth() / 2.0f > bounds.getCenterX()) {
            return HorizontalAlignment.LEFT;
        }
        return HorizontalAlignment.RIGHT;
    }
    
    public VerticalAlignment verticalAnchor(final Rectangle document, final Rectangle bounds) {
        if (document.getHeight() / 2.0f == bounds.getCenterY()) {
            return VerticalAlignment.CENTER;
        }
        if (document.getHeight() / 2.0f > bounds.getCenterY()) {
            return VerticalAlignment.TOP;
        }
        return VerticalAlignment.BOTTOM;
    }
    
    public float getHorizontalPercentage(final HorizontalAlignment anchor, final Rectangle document, final Rectangle bounds) {
        final float documentX = document.getX() + document.getWidth();
        final float boundsX = (anchor == HorizontalAlignment.LEFT) ? bounds.getX() : bounds.getMaxX();
        final float percentage = Math.min(100.0f, Math.max(0.0f, 100.0f / documentX * boundsX));
        return (anchor == HorizontalAlignment.RIGHT) ? (100.0f - percentage) : percentage;
    }
    
    public float getVerticalPercentage(final VerticalAlignment anchor, final Rectangle document, final Rectangle bounds) {
        final float documentY = document.getY() + document.getHeight();
        final float boundsY = (anchor == VerticalAlignment.TOP) ? bounds.getY() : bounds.getMaxY();
        final float percentage = Math.min(100.0f, Math.max(0.0f, 100.0f / documentY * boundsY));
        return (anchor == VerticalAlignment.BOTTOM) ? (100.0f - percentage) : percentage;
    }
    
    public List<RootChatTabConfig> getTabs() {
        return this.tabs;
    }
    
    public void setTabs(final List<RootChatTabConfig> tabs) {
        this.tabs = tabs;
    }
    
    public int getFocusedTabIndex() {
        return this.focusedTab;
    }
    
    public RootChatTabConfig getFocusedTab() {
        return this.tabs.get(this.focusedTab);
    }
    
    public void setFocusedTab(final int index) {
        this.focusedTab = index;
    }
    
    public boolean tabExists(final int index) {
        boolean exists = false;
        for (final RootChatTabConfig tab : this.tabs) {
            if (tab.index().get() == index) {
                exists = true;
                break;
            }
        }
        return exists;
    }
    
    public void checkForFocusedTab() {
        if (!this.tabExists(this.focusedTab)) {
            if (this.tabs.isEmpty()) {
                this.tabs.add(this.createDefaultTab());
                this.focusedTab = 0;
            }
            else {
                this.focusedTab = this.tabs.get(0).index().get();
            }
        }
    }
    
    public float getHeight() {
        return this.height;
    }
    
    private RootChatTabConfig createDefaultTab() {
        return new RootChatTabConfig(0, RootChatTabConfig.Type.SERVER, new GeneralChatTabConfig(""));
    }
    
    public enum BoundsPosition
    {
        OUTSIDE, 
        INSIDE;
    }
}
