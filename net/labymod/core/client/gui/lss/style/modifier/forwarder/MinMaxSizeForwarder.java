// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;

public class MinMaxSizeForwarder implements Forwarder
{
    @Override
    public boolean requiresForwarding(final Widget widget, final String key) {
        return key.equals("minWidth") || key.equals("maxWidth") || key.equals("minHeight") || key.equals("maxHeight");
    }
    
    @Override
    public void forward(final Widget widget, final String key, final ProcessedObject object) {
        final WidgetSize size = (WidgetSize)object.value();
        this.setSize(widget, key, size);
    }
    
    @Override
    public void reset(final Widget widget, final String key) {
        this.setSize(widget, key, null);
    }
    
    private void setSize(final Widget widget, final String key, final WidgetSize size) {
        switch (key) {
            case "minWidth": {
                widget.setSize(SizeType.MIN, WidgetSide.WIDTH, size);
                break;
            }
            case "maxWidth": {
                widget.setSize(SizeType.MAX, WidgetSide.WIDTH, size);
                break;
            }
            case "minHeight": {
                widget.setSize(SizeType.MIN, WidgetSide.HEIGHT, size);
                break;
            }
            case "maxHeight": {
                widget.setSize(SizeType.MAX, WidgetSide.HEIGHT, size);
                break;
            }
        }
    }
    
    @Override
    public Class<?> getType(final Widget widget, final String key, final String value) {
        return WidgetSize.class;
    }
    
    @Override
    public int getPriority() {
        return 1;
    }
}
