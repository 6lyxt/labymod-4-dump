// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.screen;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ axu.class })
public abstract class MixinGuiScreenWidgetWatcher
{
    @Shadow
    protected abstract void a(final avs p0);
    
    @Redirect(method = { "mouseClicked" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;actionPerformed(Lnet/minecraft/client/gui/GuiButton;)V"))
    private void labyMod$watchWidget(final axu instance, final avs button) {
        if (!(button instanceof ConvertableMinecraftWidget)) {
            this.a(button);
            return;
        }
        final ConvertableMinecraftWidget<?> convertableMinecraftWidget = (ConvertableMinecraftWidget<?>)button;
        final WidgetWatcher<?> watcher = convertableMinecraftWidget.getWatcher();
        final AbstractWidget<?> widget = (AbstractWidget<?>)watcher.getWidget();
        if (widget == null) {
            this.a(button);
            return;
        }
        final MutableMouse mouse = Laby.labyAPI().minecraft().mouse();
        final boolean isInBounds = widget.bounds().isInRectangle(BoundsType.BORDER, (float)mouse.getX(), (float)mouse.getY());
        if (!widget.isVisible() || !widget.pressable().get() || !isInBounds) {
            return;
        }
        if (watcher.hasReplacement() && isInBounds) {
            widget.onPress();
            return;
        }
        this.a(button);
    }
}
