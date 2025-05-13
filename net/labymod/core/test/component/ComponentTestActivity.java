// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.component;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.builder.StyleableBuilder;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.core.test.TestActivity;

@Link("test/test-menu.lss")
@AutoActivity
public class ComponentTestActivity extends TestActivity
{
    private static boolean DEBUG_IDEA_15514;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final VerticalListWidget<ComponentWidget> list = new VerticalListWidget<ComponentWidget>();
        list.addId("texts");
        list.addChild(ComponentWidget.component(Component.translatable("%", new Component[0]).arguments(Component.text(""))));
        list.addChild(ComponentWidget.component(Component.translatable("Hello, %s", new Component[0]).arguments(Component.text(this.labyAPI.getName()))));
        list.addChild(ComponentWidget.i18n("Test, %s %s %s", "1", "2", "3"));
        list.addChild(ComponentWidget.component(((BaseComponent<Component>)Component.empty().append(Component.icon(Icon.head("derrop"), ((StyleableBuilder<T, Style.Builder>)Style.builder()).hoverEvent(HoverEvent.showText(Component.text("test"))).build())).append(Component.text("test", ((StyleableBuilder<T, Style.Builder>)Style.builder()).hoverEvent(HoverEvent.showText(Component.text("asdf"))).build()))).append(Component.text("asdf"))));
        list.addChild(ComponentWidget.component(Component.keybind("key.forward")));
        list.addChild(ComponentWidget.text("Test the ScoreComponent with \"/scoreboard objectives add test dummy\", \"/scoreboard players set PLAYER test 10\" and in 1.17+ \"/scoreboard objectives setdisplay sidebar test\""));
        list.addChild(ComponentWidget.component(Component.score(this.labyAPI.getName(), "test")));
        if (ComponentTestActivity.DEBUG_IDEA_15514) {
            list.removeChildIf(componentWidget -> true);
            list.addChild(ComponentWidget.text("(FancyTheme) GommeHD.net MoneyMaker ActionBar Message:"));
            list.addChild(ComponentWidget.text("§bNext level: §8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502§8§l\u2502 §e0§8/10"));
        }
        ((AbstractWidget<VerticalListWidget<ComponentWidget>>)this.document).addChild(list);
    }
    
    static {
        ComponentTestActivity.DEBUG_IDEA_15514 = false;
    }
}
