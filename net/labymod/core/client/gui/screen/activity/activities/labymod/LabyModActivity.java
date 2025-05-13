// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod;

import net.labymod.api.service.Registry;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.navigation.NavigationRegistry;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;
import net.labymod.api.client.gui.navigation.NavigationElement;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.ShopActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.WidgetsEditorActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.AddonsActivity;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.DefaultComponentTab;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.SettingsActivity;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.activity.types.TabbedActivity;

@Links({ @Link("activity/labymod.lss"), @Link("activity/settings.lss") })
@AutoActivity
public class LabyModActivity extends TabbedActivity
{
    public LabyModActivity() {
        ((Registry<DefaultComponentTab>)this).register("settings", new DefaultComponentTab(Component.translatable("labymod.activity.labyMod.settings.name", new Component[0]), new SettingsActivity(Laby.labyAPI().coreSettingRegistry())));
        ((Registry<DefaultComponentTab>)this).register("addons", new DefaultComponentTab(Component.translatable("labymod.addons.name", new Component[0]), AddonsActivity::new));
        ((Registry<DefaultComponentTab>)this).register("widgets", new DefaultComponentTab(Component.translatable("labymod.hudWidgetEditor.title", new Component[0]), WidgetsEditorActivity::new));
        ((Registry<DefaultComponentTab>)this).register("player", new DefaultComponentTab(Component.translatable("labymod.activity.playerCustomization.name", new Component[0]), PlayerActivity::new));
        ((Registry<DefaultComponentTab>)this).register("shop", new DefaultComponentTab(Component.translatable("labymod.activity.shop.name", new Component[0]), ShopActivity::new));
    }
    
    @Nullable
    public static LabyModActivity getFromNavigationRegistry() {
        final NavigationRegistry navigationRegistry = Laby.references().navigationRegistry();
        final NavigationElement<?> navigationElement = navigationRegistry.getById("labymod");
        if (!(navigationElement instanceof ScreenNavigationElement)) {
            return null;
        }
        final ScreenNavigationElement screenNavigationElement = (ScreenNavigationElement)navigationElement;
        final ScreenInstance screen = screenNavigationElement.getScreen();
        if (!(screen instanceof LabyModActivity)) {
            return null;
        }
        return (LabyModActivity)screen;
    }
}
