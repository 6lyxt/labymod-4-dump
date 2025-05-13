// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Objects;
import net.labymod.v1_19_2.client.gui.EditBoxAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class TextFieldConverter extends AbstractMinecraftWidgetConverter<ehx, TextFieldWidget>
{
    public TextFieldConverter() {
        super(MinecraftWidgetType.TEXT_FIELD);
    }
    
    @Override
    public TextFieldWidget createDefault(final ehx source) {
        final EditBoxAccessor accessor = (EditBoxAccessor)source;
        final TextFieldWidget destination = new TextFieldWidget();
        final EditBoxAccessor editBoxAccessor = accessor;
        final TextFieldWidget obj = destination;
        Objects.requireNonNull(obj);
        editBoxAccessor.setValueConsumer(obj::setText);
        destination.setEditable(accessor.isEditable());
        destination.setText(source.b());
        destination.maximalLength(source.s());
        destination.setActive(source.p);
        destination.setCursorIndex(source.p());
        destination.setFocused(source.n());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final ehx source, final TextFieldWidget destination) {
        source.i(destination.getCursorIndex());
        source.q = destination.isVisible();
        source.p = destination.isActive();
        source.g(destination.isEditable());
        if (!Objects.equals(source.b(), destination.getText())) {
            final int pos = destination.getCursorIndex();
            source.a(destination.getText());
            source.i(pos);
            destination.setCursorIndex(pos);
        }
        destination.setFocused(source.n());
        this.copyBounds(source, destination);
    }
}
