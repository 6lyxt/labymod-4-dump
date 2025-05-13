// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Objects;
import net.labymod.v1_21_1.client.gui.EditBoxAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class TextFieldConverter extends AbstractMinecraftWidgetConverter<fiv, TextFieldWidget>
{
    public TextFieldConverter() {
        super(MinecraftWidgetType.TEXT_FIELD);
    }
    
    @Override
    public TextFieldWidget createDefault(final fiv source) {
        final EditBoxAccessor accessor = (EditBoxAccessor)source;
        final TextFieldWidget destination = new TextFieldWidget();
        final EditBoxAccessor editBoxAccessor = accessor;
        final TextFieldWidget obj = destination;
        Objects.requireNonNull(obj);
        editBoxAccessor.setValueConsumer(obj::setText);
        destination.setEditable(accessor.isEditable());
        destination.setText(source.a());
        destination.maximalLength(source.j());
        destination.placeholder(this.mapComponent(accessor.getHint()));
        destination.setActive(source.j);
        destination.setCursorIndex(source.e());
        destination.setFocused(source.aO_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final fiv source, final TextFieldWidget destination) {
        source.e(destination.getCursorIndex());
        source.k = destination.isVisible();
        source.j = destination.isActive();
        source.e(destination.isEditable());
        if (!Objects.equals(source.a(), destination.getText())) {
            final int pos = destination.getCursorIndex();
            source.a(destination.getText());
            source.e(pos);
            destination.setCursorIndex(pos);
        }
        destination.setFocused(source.aO_());
        this.copyBounds(source, destination);
    }
}
