// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.chat;

import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.transform.FadingWidget;

@AutoWidget
public class ChatLineWidget extends FadingWidget
{
    public ChatLineWidget(final Widget widget, final long duration) {
        super(widget, TimeUtil.getMillis() + duration);
    }
}
