// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab;

import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.component.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.chat.DefaultChatProvider;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.client.chat.autotext.AutoTextService;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.activity.types.chatinput.ChatInputTabSettingActivity;

@Link("activity/chat/input/auto-text.lss")
@AutoActivity
public class AutoTextActivity extends ChatInputTabSettingActivity<FlexibleContentWidget>
{
    private final AutoTextService autoTextService;
    private AutoTextEntry original;
    private AutoTextEntry editing;
    
    public AutoTextActivity() {
        this.autoTextService = Laby.references().autoTextService();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((AbstractWidget<AbstractWidget<?>>)this.document).addChild(this.createWindow());
    }
    
    @NotNull
    private AbstractWidget<?> createWindow() {
        this.contentWidget = (T)new FlexibleContentWidget();
        ((AbstractWidget<Widget>)this.contentWidget).addId("window");
        final DivWidget titleBar = new DivWidget();
        titleBar.addId("title-bar");
        final ComponentWidget title = ComponentWidget.component(Component.translatable("labymod.chatInput.tab.autotext.name", new Component[0]));
        title.addId("title");
        ((AbstractWidget<ComponentWidget>)titleBar).addChild(title);
        final ButtonWidget button = ButtonWidget.icon((this.editing == null) ? Textures.SpriteCommon.SMALL_ADD : Textures.SpriteCommon.SMALL_CHECKED);
        ((AbstractWidget<Widget>)button).addId((this.editing == null) ? "add" : "save");
        button.setPressable(() -> {
            if (this.editing == null) {
                this.original = null;
                this.editing = new AutoTextEntry();
                this.reload();
                return;
            }
            else {
                if (this.editing.isConfigured()) {
                    this.autoTextService.removeEntry((this.original == null) ? this.editing : this.original);
                    this.autoTextService.addEntry(this.editing);
                    ((DefaultChatProvider)this.labyAPI.chatProvider()).autoTextConfigProvider().save();
                }
                this.original = null;
                this.editing = null;
                this.reload();
                return;
            }
        });
        ((AbstractWidget<ButtonWidget>)titleBar).addChild(button);
        ((FlexibleContentWidget)this.contentWidget).addContent(titleBar);
        final DivWidget contentWrapper = new DivWidget();
        contentWrapper.addId("content-wrapper");
        if (this.editing == null) {
            final VerticalListWidget<Widget> list = new VerticalListWidget<Widget>();
            list.addId("entries");
            for (final AutoTextEntry entry : this.autoTextService.getEntries()) {
                list.addChild(this.createEntry(entry));
            }
            final ScrollWidget scrollWidget = new ScrollWidget(list);
            scrollWidget.addId("scroll-widget");
            ((AbstractWidget<ScrollWidget>)contentWrapper).addChild(scrollWidget);
        }
        else {
            final ScreenRendererWidget screen = new ScreenRendererWidget(true);
            screen.addId("settings");
            final SettingContentActivity settings = new SettingContentActivity(this.editing.asRegistry("autotext").translationId("chatInput.tab.autotext"), false);
            settings.setHeaderType(SettingContentActivity.HeaderType.FIXED_IN_CHILDREN);
            screen.displayScreen(settings);
            ((AbstractWidget<ScreenRendererWidget>)contentWrapper).addChild(screen);
        }
        ((FlexibleContentWidget)this.contentWidget).addFlexibleContent(contentWrapper);
        return (AbstractWidget)this.contentWidget;
    }
    
    private Widget createEntry(final AutoTextEntry entry) {
        final DivWidget list = new DivWidget();
        list.addId("entry");
        String displayName = entry.displayName().get();
        if (displayName.isEmpty()) {
            displayName = entry.message().get();
        }
        final ComponentWidget keyWidget = ComponentWidget.component(Component.text(displayName, NamedTextColor.GREEN));
        keyWidget.addId("display-name");
        ((AbstractWidget<ComponentWidget>)list).addChild(keyWidget);
        final IconWidget delete = new IconWidget(Textures.SpriteCommon.SMALL_X);
        ((AbstractWidget<IconWidget>)list).addChild(delete).addId("delete");
        delete.setPressable(() -> {
            this.autoTextService.removeEntry(entry);
            ((DefaultChatProvider)this.labyAPI.chatProvider()).autoTextConfigProvider().save();
            this.reload();
            return;
        });
        list.setPressable(() -> {
            this.original = entry;
            this.editing = entry.copy();
            if (this.editing.displayName().get().isEmpty()) {
                this.editing.displayName().set(this.editing.message().get());
            }
            this.reload();
            return;
        });
        return list;
    }
}
