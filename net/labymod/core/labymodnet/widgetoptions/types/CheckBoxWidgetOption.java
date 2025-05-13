// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.widgetoptions.types;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.function.Consumer;
import net.labymod.core.labymodnet.models.CosmeticOption;
import net.labymod.core.labymodnet.widgetoptions.CustomWidgetOption;

public class CheckBoxWidgetOption extends CustomWidgetOption
{
    private static final String DEBOUNCE_ID = "checkbox-debounce";
    private static final String ON = "on";
    private static final String OFF = "off";
    
    public CheckBoxWidgetOption(final CosmeticOption option, final String optionName, final int optionIndex) {
        super(option, optionName, optionIndex);
    }
    
    @Override
    protected void create(final String data, final Consumer<Widget> consumer) {
        final CheckBoxWidget.State state = data.equals("on") ? CheckBoxWidget.State.CHECKED : CheckBoxWidget.State.UNCHECKED;
        final CheckBoxWidget checkBoxWidget = new CheckBoxWidget();
        checkBoxWidget.setState(state);
        checkBoxWidget.setPressable(() -> this.setData("checkbox-debounce", (checkBoxWidget.state() == CheckBoxWidget.State.CHECKED) ? "on" : "off"));
        checkBoxWidget.setHoverComponent(Component.text(this.option.first().getCustomKey()));
        consumer.accept(checkBoxWidget);
    }
}
