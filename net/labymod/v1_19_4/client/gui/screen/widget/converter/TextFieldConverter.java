// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Objects;
import net.labymod.v1_19_4.client.gui.EditBoxAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class TextFieldConverter extends AbstractMinecraftWidgetConverter<eol, TextFieldWidget>
{
    public TextFieldConverter() {
        super(MinecraftWidgetType.TEXT_FIELD);
    }
    
    @Override
    public TextFieldWidget createDefault(final eol source) {
        final EditBoxAccessor accessor = (EditBoxAccessor)source;
        final TextFieldWidget destination = new TextFieldWidget();
        final EditBoxAccessor editBoxAccessor = accessor;
        final TextFieldWidget obj = destination;
        Objects.requireNonNull(obj);
        editBoxAccessor.setValueConsumer(obj::setText);
        destination.setEditable(accessor.isEditable());
        destination.setText(source.b());
        destination.placeholder(this.mapComponent(accessor.getHint()));
        destination.maximalLength(source.y());
        destination.setActive(source.v);
        destination.setCursorIndex(source.v());
        destination.setFocused(source.aD_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final eol source, final TextFieldWidget destination) {
        source.l(destination.getCursorIndex());
        source.w = destination.isVisible();
        source.v = destination.isActive();
        source.c(destination.isEditable());
        if (!Objects.equals(source.b(), destination.getText())) {
            final int pos = destination.getCursorIndex();
            source.a(destination.getText());
            source.l(pos);
            destination.setCursorIndex(pos);
        }
        destination.setFocused(source.aD_());
        this.copyBounds(source, destination);
    }
}
