// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Objects;
import net.labymod.v1_20_2.client.gui.EditBoxAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class TextFieldConverter extends AbstractMinecraftWidgetConverter<esz, TextFieldWidget>
{
    public TextFieldConverter() {
        super(MinecraftWidgetType.TEXT_FIELD);
    }
    
    @Override
    public TextFieldWidget createDefault(final esz source) {
        final EditBoxAccessor accessor = (EditBoxAccessor)source;
        final TextFieldWidget destination = new TextFieldWidget();
        final EditBoxAccessor editBoxAccessor = accessor;
        final TextFieldWidget obj = destination;
        Objects.requireNonNull(obj);
        editBoxAccessor.setValueConsumer(obj::setText);
        destination.setEditable(accessor.isEditable());
        destination.setText(source.a());
        destination.maximalLength(source.w());
        destination.placeholder(this.mapComponent(accessor.getHint()));
        destination.setActive(source.i);
        destination.setCursorIndex(source.f());
        destination.setFocused(source.aC_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final esz source, final TextFieldWidget destination) {
        source.k(destination.getCursorIndex());
        source.j = destination.isVisible();
        source.i = destination.isActive();
        source.e(destination.isEditable());
        if (!Objects.equals(source.a(), destination.getText())) {
            final int pos = destination.getCursorIndex();
            source.a(destination.getText());
            source.k(pos);
            destination.setCursorIndex(pos);
        }
        destination.setFocused(source.aC_());
        this.copyBounds(source, destination);
    }
}
