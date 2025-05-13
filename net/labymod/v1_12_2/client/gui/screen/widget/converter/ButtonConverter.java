// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.util.function.Mapper;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class ButtonConverter extends AbstractMinecraftWidgetConverter<bja, ButtonWidget>
{
    public ButtonConverter() {
        super(MinecraftWidgetType.BUTTON);
        this.registerMapper(new GuiButtonLanguageMapper());
        this.registerMapper(new GuiLockIconButtonMapper());
    }
    
    @Override
    public ButtonWidget createDefault(final bja source) {
        final ButtonWidget destination = ButtonWidget.text(source.j, null, Pressable.NOOP);
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final bja source, final ButtonWidget destination) {
        if (source.l) {
            source.l = destination.pressable().get();
        }
        destination.setVisible(source.m);
        destination.setEnabled(source.l);
        this.copyBounds(source, destination);
    }
    
    @Override
    public String getWidgetId(final bja source) {
        return String.valueOf(source.k);
    }
    
    static class GuiButtonLanguageMapper implements Mapper<bja, ButtonWidget>
    {
        @Override
        public ButtonWidget map(final bja source) {
            if (!(source instanceof bji)) {
                return null;
            }
            final int size = source.b();
            final ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
            final Icon icon = Icon.sprite(widgetsTexture, 0, 106, size, size, 256, 256);
            icon.setHoverOffset(0, size);
            final ButtonWidget button = ButtonWidget.text("", icon, Pressable.NOOP);
            ((AbstractWidget<Widget>)button).addId("icon-button-language");
            return button;
        }
    }
    
    static class GuiLockIconButtonMapper implements Mapper<bja, ButtonWidget>
    {
        @Override
        public ButtonWidget map(final bja source) {
            if (!(source instanceof bjl)) {
                return null;
            }
            final bjl lockIconButton = (bjl)source;
            final int size = source.b();
            final ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
            final boolean locked = !lockIconButton.c();
            final boolean enabled = lockIconButton.l;
            final Icon icon = Icon.sprite(widgetsTexture, locked ? 20 : 0, 146 + (enabled ? 0 : (size * 2)), size, size, 256, 256);
            if (enabled) {
                icon.setHoverOffset(0, size);
            }
            final ButtonWidget button = ButtonWidget.text("", icon, Pressable.NOOP);
            ((AbstractWidget<Widget>)button).addId("lock-icon-button");
            return button;
        }
    }
}
