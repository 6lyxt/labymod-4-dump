// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.serverapi;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.serverapi.core.model.supplement.InputPrompt;
import net.labymod.api.client.component.Component;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Activity;

@AutoActivity
@Link("activity/overlay/input-prompt.lss")
public class InputPromptActivity extends Activity
{
    private final Consumer<String> onSubmit;
    private final Component title;
    private final Component placeholder;
    private final int maxLength;
    private String value;
    
    public InputPromptActivity(final Component title, final Component placeholder, final String defaultValue, final int maxLength, final Consumer<String> onSubmit) {
        this.value = "";
        this.title = title;
        this.placeholder = placeholder;
        this.onSubmit = onSubmit;
        this.maxLength = maxLength;
        if (defaultValue != null) {
            this.value = defaultValue;
        }
    }
    
    public InputPromptActivity(final InputPrompt prompt, final Consumer<String> onSubmit) {
        this(Laby.references().labyModProtocolService().mapComponent(prompt.title()), Laby.references().labyModProtocolService().mapComponent(prompt.getPlaceholder()), prompt.getDefaultValue(), prompt.getMaxLength(), onSubmit);
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
        final TextFieldWidget textFieldWidget = new TextFieldWidget();
        textFieldWidget.addId("input");
        textFieldWidget.setText(this.value, true);
        textFieldWidget.maximalLength((this.maxLength == 0) ? 128 : this.maxLength);
        textFieldWidget.updateListener(text -> this.value = text);
        if (this.placeholder != null) {
            textFieldWidget.placeholder(this.placeholder);
        }
        container.addChild(textFieldWidget);
        final DivWidget buttonWrapper = new DivWidget();
        buttonWrapper.addId("button-wrapper");
        final ButtonWidget confirmButton = ButtonWidget.component(Component.text("Submit"));
        ((AbstractWidget<Widget>)confirmButton).addId("confirm");
        confirmButton.setPressable(() -> {
            this.onSubmit.accept(textFieldWidget.getText());
            this.displayPreviousScreen();
            return;
        });
        ((AbstractWidget<ButtonWidget>)buttonWrapper).addChild(confirmButton);
        container.addChild(buttonWrapper);
        ((AbstractWidget<VerticalListWidget<Widget>>)wrapper).addChild(container);
        ((AbstractWidget<DivWidget>)this.document).addChild(wrapper);
    }
}
