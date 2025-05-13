// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.gui.screen.widget.converter;

import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import com.google.common.collect.UnmodifiableIterator;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabLayoutWidget;
import net.labymod.v1_20_2.client.gui.TabNavigationBarAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class TabLayoutConverter extends AbstractMinecraftWidgetConverter<eus, AbstractWidget<?>>
{
    public TabLayoutConverter() {
        super(MinecraftWidgetType.TAB_NAVIGATION);
    }
    
    @Override
    public AbstractWidget<?> createDefault(final eus source) {
        final TabNavigationBarAccessor accessor = (TabNavigationBarAccessor)source;
        final eur tabManager = accessor.getTabManager();
        final euq currentTab = tabManager.a();
        final TabLayoutWidget tabLayoutWidget = new TabLayoutWidget();
        ((AbstractWidget<Widget>)tabLayoutWidget).addId("converted");
        for (final euq tab : accessor.getTabs()) {
            final TabWidget tabWidget = new TabWidget(accessor.versionedTabOf(tab));
            tabWidget.setActive(currentTab == tab);
            tabWidget.setPressListener(() -> {
                if (tabManager.a() == tab) {
                    return false;
                }
                else {
                    tabManager.a(tab, false);
                    return true;
                }
            });
            tabLayoutWidget.addEntry(tabWidget);
        }
        final DivWidget divWidget = new DivWidget();
        ((AbstractWidget<TabLayoutWidget>)divWidget).addChild(tabLayoutWidget);
        return divWidget;
    }
    
    @Override
    public void update(final eus source, final AbstractWidget<?> destination) {
        final TabNavigationBarAccessor accessor = (TabNavigationBarAccessor)source;
        final eur tabManager = accessor.getTabManager();
        final TabNavigationBarAccessor.VersionedTab currentTab = accessor.versionedTabOf(tabManager.a());
        final Widget menu = (Widget)destination.getChild("menu");
        if (menu instanceof final TabLayoutWidget tabLayoutWidget) {
            for (final HorizontalListEntry child : tabLayoutWidget.getChildren()) {
                final Widget widget = child.childWidget();
                if (widget instanceof final TabWidget tabWidget) {
                    tabWidget.setActive(tabWidget.getTab() == currentTab);
                }
            }
        }
        final ews rectangle = source.s();
        destination.bounds().setOuterPosition((float)rectangle.d(), (float)rectangle.b(), TabLayoutConverter.COPY_MINECRAFT_BOUNDS);
        destination.bounds().setOuterSize((float)rectangle.g(), (float)rectangle.h(), TabLayoutConverter.COPY_MINECRAFT_BOUNDS);
    }
}
