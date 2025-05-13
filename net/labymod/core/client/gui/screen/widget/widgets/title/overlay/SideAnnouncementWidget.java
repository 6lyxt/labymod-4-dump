// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.title.overlay;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.core.main.announcement.model.SideBarAnnouncement;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

@AutoWidget
public class SideAnnouncementWidget extends HorizontalListWidget
{
    private final SideBarAnnouncement announcement;
    private ComponentWidget componentWidget;
    
    public SideAnnouncementWidget(final SideBarAnnouncement announcement) {
        this.announcement = announcement;
        ((AbstractWidget<Widget>)this).addId("side-announcement-entry");
        this.setVariable("--announcement-color", announcement.getColor().getRGB());
        this.setVariable("--announcement-hover-color", announcement.getHoverColor().getRGB());
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final IconWidget iconWidget = new IconWidget(Icon.url(this.announcement.getIconUrl()));
        this.addEntry(iconWidget);
        final TextComponent component = Component.text(this.announcement.getTitle());
        this.addEntry(this.componentWidget = ComponentWidget.component(component));
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.isHovered()) {
            this.labyAPI.minecraft().chatExecutor().openUrl(this.announcement.getLink());
        }
        return super.mouseClicked(mouse, mouseButton);
    }
}
