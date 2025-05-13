// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gui.screen.widget.converter;

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

public class ButtonConverter extends AbstractMinecraftWidgetConverter<avs, ButtonWidget>
{
    public ButtonConverter() {
        super(MinecraftWidgetType.BUTTON);
        this.registerMapper(new GuiButtonLanguageMapper());
        this.registerMapper(new GuiLockIconButtonMapper());
    }
    
    @Override
    public ButtonWidget createDefault(final avs source) {
        final ButtonWidget destination = ButtonWidget.text(source.j, null, Pressable.NOOP);
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final avs source, final ButtonWidget destination) {
        if (source.l) {
            source.l = destination.pressable().get();
        }
        destination.setVisible(source.m);
        destination.setEnabled(source.l);
        this.copyBounds(source, destination);
    }
    
    @Override
    public String getWidgetId(final avs source) {
        return String.valueOf(source.k);
    }
    
    static class GuiButtonLanguageMapper implements Mapper<avs, ButtonWidget>
    {
        @Override
        public ButtonWidget map(final avs source) {
            if (!(source instanceof avz)) {
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
    
    static class GuiLockIconButtonMapper implements Mapper<avs, ButtonWidget>
    {
        @Override
        public ButtonWidget map(final avs source) {
            if (!(source instanceof awc)) {
                return null;
            }
            final awc lockIconButton = (awc)source;
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
