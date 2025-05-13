// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Objects;
import net.labymod.v1_17_1.client.gui.EditBoxAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class TextFieldConverter extends AbstractMinecraftWidgetConverter<dxi, TextFieldWidget>
{
    public TextFieldConverter() {
        super(MinecraftWidgetType.TEXT_FIELD);
    }
    
    @Override
    public TextFieldWidget createDefault(final dxi source) {
        final EditBoxAccessor accessor = (EditBoxAccessor)source;
        final TextFieldWidget destination = new TextFieldWidget();
        final EditBoxAccessor editBoxAccessor = accessor;
        final TextFieldWidget obj = destination;
        Objects.requireNonNull(obj);
        editBoxAccessor.setValueConsumer(obj::setText);
        destination.setEditable(accessor.isEditable());
        destination.setText(source.b());
        destination.maximalLength(source.r());
        destination.setActive(source.o);
        destination.setCursorIndex(source.o());
        destination.setFocused(source.h());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final dxi source, final TextFieldWidget destination) {
        source.i(destination.getCursorIndex());
        source.p = destination.isVisible();
        source.o = destination.isActive();
        source.g(destination.isEditable());
        if (!Objects.equals(source.b(), destination.getText())) {
            final int pos = destination.getCursorIndex();
            source.a(destination.getText());
            source.i(pos);
            destination.setCursorIndex(pos);
        }
        destination.setFocused(source.h());
        this.copyBounds(source, destination);
    }
}
