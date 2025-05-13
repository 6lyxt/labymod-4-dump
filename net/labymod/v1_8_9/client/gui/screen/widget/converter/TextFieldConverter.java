// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Objects;
import net.labymod.v1_8_9.client.gui.GuiTextFieldAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class TextFieldConverter extends AbstractMinecraftWidgetConverter<avw, TextFieldWidget>
{
    public TextFieldConverter() {
        super(MinecraftWidgetType.TEXT_FIELD);
    }
    
    @Override
    public TextFieldWidget createDefault(final avw source) {
        final GuiTextFieldAccessor accessor = (GuiTextFieldAccessor)source;
        final TextFieldWidget destination = new TextFieldWidget();
        final GuiTextFieldAccessor guiTextFieldAccessor = accessor;
        final TextFieldWidget obj = destination;
        Objects.requireNonNull(obj);
        guiTextFieldAccessor.setTextConsumer(obj::setText);
        destination.setText(source.b());
        destination.setEditable(((GuiTextFieldAccessor)source).isEnabled());
        destination.maximalLength(source.h());
        destination.setCursorIndex(source.i());
        destination.setFocused(source.m());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final avw source, final TextFieldWidget destination) {
        source.e(destination.isVisible());
        source.c(destination.isEditable());
        source.e(destination.getCursorIndex());
        this.updateText(source, destination);
        destination.setFocused(source.m());
        this.copyBounds(source, destination);
    }
    
    public void updateText(final avw mc, final AbstractWidget<?> widget) {
        if (!(widget instanceof TextFieldWidget)) {
            return;
        }
        this.updateText(mc, (TextFieldWidget)widget);
    }
    
    public void updateText(final avw mc, final TextFieldWidget widget) {
        if (!Objects.equals(mc.b(), widget.getText())) {
            mc.a(widget.getText());
        }
    }
    
    @Override
    public String getWidgetId(final avw source) {
        return String.valueOf(source.d());
    }
}
