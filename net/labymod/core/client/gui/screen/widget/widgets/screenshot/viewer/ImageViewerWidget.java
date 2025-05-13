// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.screenshot.viewer;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
@Link("activity/screenshot/image-viewer.lss")
public class ImageViewerWidget extends SimpleWidget
{
    private Icon icon;
    
    public ImageViewerWidget(final Icon icon) {
        this.icon = icon;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final IconWidget iconWidget = new IconWidget(this.icon);
        iconWidget.addId("image");
        ((AbstractWidget<IconWidget>)this).addChild(iconWidget);
    }
    
    public void display(final Icon icon) {
        this.icon = icon;
        this.reInitialize();
    }
}
