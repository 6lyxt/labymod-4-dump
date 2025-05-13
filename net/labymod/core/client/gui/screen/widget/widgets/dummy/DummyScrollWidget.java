// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.dummy;

import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;

public class DummyScrollWidget extends ScrollWidget
{
    private DummyScrollWidget(final VerticalListWidget<?> listWidget) {
        super(listWidget);
    }
    
    private DummyScrollWidget(final Widget widget, final ListSession<?> session) {
        super(widget, session);
    }
    
    public DummyScrollWidget() {
        super(new VerticalListWidget<Object>());
    }
    
    @Override
    public boolean isScrollbarRequired() {
        return true;
    }
}
