// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.gui.screen.widget.converter;

import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import com.google.common.collect.UnmodifiableIterator;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabLayoutWidget;
import net.labymod.v1_21_4.client.gui.TabNavigationBarAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class TabLayoutConverter extends AbstractMinecraftWidgetConverter<fqz, AbstractWidget<?>>
{
    public TabLayoutConverter() {
        super(MinecraftWidgetType.TAB_NAVIGATION);
    }
    
    @Override
    public AbstractWidget<?> createDefault(final fqz source) {
        final TabNavigationBarAccessor accessor = (TabNavigationBarAccessor)source;
        final fqy tabManager = accessor.getTabManager();
        final fqx currentTab = tabManager.a();
        final TabLayoutWidget tabLayoutWidget = new TabLayoutWidget();
        ((AbstractWidget<Widget>)tabLayoutWidget).addId("converted");
        for (final fqx tab : accessor.getTabs()) {
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
    public void update(final fqz source, final AbstractWidget<?> destination) {
        final TabNavigationBarAccessor accessor = (TabNavigationBarAccessor)source;
        final fqy tabManager = accessor.getTabManager();
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
        final ftb rectangle = source.J();
        destination.bounds().setOuterPosition((float)rectangle.d(), (float)rectangle.b(), TabLayoutConverter.COPY_MINECRAFT_BOUNDS);
        destination.bounds().setOuterSize((float)rectangle.g(), (float)rectangle.h(), TabLayoutConverter.COPY_MINECRAFT_BOUNDS);
    }
}
