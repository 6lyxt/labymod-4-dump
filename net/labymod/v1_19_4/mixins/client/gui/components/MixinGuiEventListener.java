// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.gui.components;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import java.util.function.BiFunction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ epl.class })
public interface MixinGuiEventListener
{
    default boolean handleConvertedWidget(final BiFunction<AbstractWidgetConverter, AbstractWidget<?>, Boolean> converterFunction) {
        if (this instanceof ConvertableMinecraftWidget) {
            final ConvertableMinecraftWidget<?> convertable = (ConvertableMinecraftWidget<?>)this;
            final WidgetWatcher<?> watcher = convertable.getWatcher();
            final AbstractWidgetConverter<?, ?> widgetConverter = watcher.getWidgetConverter();
            return widgetConverter != null && converterFunction.apply(widgetConverter, (AbstractWidget<?>)watcher.getWidget());
        }
        return false;
    }
    
    @Overwrite
    default boolean a(final double mouseX, final double mouseY, final int button) {
        return this.handleConvertedWidget((converter, widget) -> this.minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
            return mouseButton != null && converter.mouseClicked(widget, mouse, mouseButton);
        }));
    }
    
    @Overwrite
    default boolean b(final double mouseX, final double mouseY, final int button) {
        return this.handleConvertedWidget((converter, widget) -> this.minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
            return mouseButton != null && converter.mouseReleased(widget, mouse, mouseButton);
        }));
    }
    
    @Overwrite
    default boolean a(final double mouseX, final double mouseY, final double delta) {
        return this.handleConvertedWidget((converter, widget) -> this.minecraft().updateMouse(mouseX, mouseY, mouse -> converter.mouseScrolled(widget, mouse, delta)));
    }
    
    @Overwrite
    default boolean a(final double mouseX, final double mouseY, final int button, final double deltaX, final double deltaY) {
        return this.handleConvertedWidget((converter, widget) -> this.minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
            return mouseButton != null && converter.mouseDragged(widget, mouse, mouseButton, deltaX, deltaY);
        }));
    }
    
    @Overwrite
    default boolean a(final char c, final int param1) {
        return this.handleConvertedWidget((converter, widget) -> converter.charTyped(widget, DefaultKeyMapper.lastPressed(), c));
    }
    
    @Overwrite
    default boolean a(final int keyCode, final int param1, final int param2) {
        return this.handleConvertedWidget((converter, widget) -> {
            final Key key = DefaultKeyMapper.lastPressed();
            return converter.keyPressed(widget, key, KeyMapper.getInputType(key));
        });
    }
    
    @Overwrite
    default boolean b(final int keyCode, final int param1, final int param2) {
        return this.handleConvertedWidget((converter, widget) -> {
            final Key key = DefaultKeyMapper.lastReleased();
            return converter.keyReleased(widget, key, KeyMapper.getInputType(key));
        });
    }
    
    default Minecraft minecraft() {
        return Laby.labyAPI().minecraft();
    }
}
