// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.navigation.elements;

import net.labymod.api.user.GameUser;
import java.util.UUID;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.BuildData;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.navigation.NavigationElement;

public class VersionNavigationElement implements NavigationElement<Widget>
{
    @Override
    public String getWidgetId() {
        return "version";
    }
    
    @Override
    public Widget createWidget(final NavigationWrapper wrapper) {
        final HorizontalListWidget mainWidget = new HorizontalListWidget();
        final UUID clientUniqueId = Laby.labyAPI().getUniqueId();
        final GameUser user = Laby.labyAPI().gameUserService().gameUser(clientUniqueId);
        final boolean isDefaultGroup = user.visibleGroup().isDefault();
        final IconWidget icon = new IconWidget(isDefaultGroup ? Textures.SpriteLabyMod.DEFAULT_WOLF_HIGH_RES : Textures.SpriteLabyMod.WHITE_WOLF_HIGH_RES);
        if (!isDefaultGroup) {
            icon.color().set(ColorFormat.ARGB32.pack(user.displayColor().getValue(), 255));
        }
        mainWidget.addEntry(icon);
        final VerticalListWidget<ComponentWidget> version = (VerticalListWidget<ComponentWidget>)new VerticalListWidget().addId("info");
        final ComponentWidget labymod = ComponentWidget.text("LabyMod " + BuildData.getVersion());
        labymod.addId("labymod");
        version.addChild(labymod);
        final ComponentWidget minecraft = ComponentWidget.text("Minecraft " + Laby.labyAPI().minecraft().getVersion());
        minecraft.addId("minecraft");
        version.addChild(minecraft);
        mainWidget.addEntry(version);
        return mainWidget;
    }
}
