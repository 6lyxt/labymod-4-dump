// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.dummy;

import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;

public class DummyScrollbarWidget extends ScrollbarWidget
{
    private static final ModifyReason DUMMY_SCROLLBAR;
    private float scrollButtonOffset;
    private float contentHeight;
    
    public DummyScrollbarWidget(final Rectangle rectangle, final float contentHeight, final float scrollButtonOffset, final ScrollWidget scrollWidget) {
        super(scrollWidget);
        this.scrollButtonOffset = scrollButtonOffset;
        this.contentHeight = contentHeight;
        this.bounds().set(rectangle, DummyScrollbarWidget.DUMMY_SCROLLBAR);
    }
    
    @Override
    public float getScrollButtonOffset() {
        return this.scrollButtonOffset;
    }
    
    @Override
    protected float getContentHeight() {
        return this.contentHeight;
    }
    
    static {
        DUMMY_SCROLLBAR = ModifyReason.of("dummyScrollbar");
    }
}
