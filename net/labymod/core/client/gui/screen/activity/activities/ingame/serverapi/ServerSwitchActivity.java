// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.serverapi;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import java.util.Map;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.serverapi.core.model.supplement.ServerSwitchPrompt;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Activity;

@AutoActivity
@Link("activity/overlay/server-switch.lss")
public class ServerSwitchActivity extends Activity
{
    private final Component title;
    private final String address;
    private final boolean preview;
    private final ServerData currentServerData;
    private final BooleanConsumer acceptConsumer;
    
    public ServerSwitchActivity(final Component title, final String address, final boolean preview, final ServerData currentServerData, final BooleanConsumer acceptConsumer) {
        this.title = title;
        this.address = address;
        this.preview = preview;
        this.currentServerData = currentServerData;
        this.acceptConsumer = acceptConsumer;
    }
    
    public ServerSwitchActivity(final ServerSwitchPrompt prompt, final ServerData currentServerData, final BooleanConsumer acceptConsumer) {
        this(Laby.references().labyModProtocolService().mapComponent(prompt.title()), prompt.getAddress(), prompt.isShowPreview(), currentServerData, acceptConsumer);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget wrapper = new DivWidget();
        wrapper.addId("wrapper");
        final VerticalListWidget<Widget> container = new VerticalListWidget<Widget>();
        container.addId("container");
        final ComponentWidget titleWidget = ComponentWidget.component(this.title);
        titleWidget.addId("title");
        container.addChild(titleWidget);
        final ComponentWidget warningWidget = ComponentWidget.i18n("labymod.activity.serverSwitch.warning", this.address);
        warningWidget.addId("warning");
        container.addChild(warningWidget);
        final CheckBoxWidget checkBoxWidget = new CheckBoxWidget();
        final HorizontalListWidget buttonWrapper = new HorizontalListWidget();
        ((AbstractWidget<Widget>)buttonWrapper).addId("button-wrapper");
        final ButtonWidget stayButton = ButtonWidget.i18n("labymod.activity.serverSwitch.deny");
        stayButton.setPressable(() -> this.submit(checkBoxWidget, false));
        buttonWrapper.addEntry(stayButton);
        final ButtonWidget connectButton = ButtonWidget.i18n("labymod.activity.serverSwitch.accept");
        connectButton.setPressable(() -> this.submit(checkBoxWidget, true));
        buttonWrapper.addEntry(connectButton);
        container.addChild(buttonWrapper);
        final HorizontalListWidget checkBoxWrapper = new HorizontalListWidget();
        ((AbstractWidget<Widget>)checkBoxWrapper).addId("checkbox-wrapper");
        checkBoxWrapper.addEntry(checkBoxWidget);
        final ComponentWidget i18n;
        final ComponentWidget checkBoxLabel = i18n = ComponentWidget.i18n("labymod.activity.serverSwitch.remember", this.currentServerData.address().toString(), this.address);
        final CheckBoxWidget obj = checkBoxWidget;
        Objects.requireNonNull(obj);
        i18n.setPressable(obj::onPress);
        checkBoxWrapper.addEntry(checkBoxLabel);
        container.addChild(checkBoxWrapper);
        ((AbstractWidget<VerticalListWidget<Widget>>)wrapper).addChild(container);
        ((AbstractWidget<DivWidget>)this.document).addChild(wrapper);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (key == Key.ESCAPE) {
            this.submit(null, false);
            return true;
        }
        return super.keyPressed(key, type);
    }
    
    @Override
    public boolean shouldHandleEscape() {
        return true;
    }
    
    private void submit(final CheckBoxWidget rememberCheckBox, final boolean connect) {
        if (rememberCheckBox != null && rememberCheckBox.state() == CheckBoxWidget.State.CHECKED) {
            final Map<String, Boolean> serverSwitchChoices = LabyConfigProvider.INSTANCE.get().other().serverSwitchChoices().get();
            serverSwitchChoices.put(this.currentServerData.actualAddress().toString() + "#" + this.address, Boolean.valueOf(connect));
        }
        this.acceptConsumer.accept(connect);
        this.displayPreviousScreen();
        if (!connect) {
            return;
        }
        this.labyAPI.serverController().joinServer(ConnectableServerData.builder().address(this.address).build());
    }
}
