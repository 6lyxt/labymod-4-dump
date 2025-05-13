// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.v1_8_9.client.gui.screen.widget.converter.TextFieldConverter;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.Minecraft;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.gui.GuiTextFieldAccessor;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@Mixin({ avw.class })
public abstract class MixinGuiTextField<K extends AbstractWidget<?>> implements ConvertableMinecraftWidget<K>, GuiTextFieldAccessor
{
    private final WidgetWatcher<K> labyMod$watcher;
    private Consumer<String> labyMod$textConsumer;
    @Final
    @Shadow
    @Mutable
    private int i;
    @Final
    @Shadow
    @Mutable
    private int j;
    @Shadow
    public int f;
    @Shadow
    public int a;
    @Shadow
    private boolean o;
    @Shadow
    private String k;
    @Shadow
    private boolean q;
    
    public MixinGuiTextField() {
        this.labyMod$watcher = new WidgetWatcher<K>();
    }
    
    @Shadow
    public abstract void b(final boolean p0);
    
    @Insert(method = { "drawTextBox" }, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderWidgetWatcher(final InsertInfo ci) {
        final Minecraft api = Laby.labyAPI().minecraft();
        this.labyMod$watcher.update(this, null);
        final boolean rendered = this.labyMod$watcher.render(VersionedStackProvider.DEFAULT_STACK, api.mouse(), api.getTickDelta());
        if (rendered) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "mouseClicked" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$mouseClicked(final int mouseX, final int mouseY, final int button, final CallbackInfo cir) {
        final AbstractWidgetConverter<?, ?> widgetConverter = this.labyMod$watcher.getWidgetConverter();
        if (widgetConverter instanceof AbstractMinecraftWidgetConverter && this.getWatcher().getWidget() != null) {
            final AbstractMinecraftWidgetConverter converter = (AbstractMinecraftWidgetConverter)widgetConverter;
            final boolean hovered = mouseX >= this.a && mouseX < this.a + this.i && mouseY >= this.f && mouseY < this.f + this.j;
            final K widget = this.getWatcher().getWidget();
            if (this.o) {
                this.b(hovered);
                widget.setFocused(hovered);
            }
            Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
                final MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
                if (mouseButton == null) {
                    return;
                }
                else {
                    converter.mouseClicked(widget, mouse, mouseButton);
                    return;
                }
            });
            cir.cancel();
        }
    }
    
    @Inject(method = { "textboxKeyTyped" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$charTyped(final char c, final int var0, final CallbackInfoReturnable<Boolean> cir) {
        final AbstractWidgetConverter<?, ?> widgetConverter = this.labyMod$watcher.getWidgetConverter();
        final K widget = this.getWatcher().getWidget();
        if (widgetConverter instanceof AbstractMinecraftWidgetConverter && widget != null) {
            final AbstractMinecraftWidgetConverter converter = (AbstractMinecraftWidgetConverter)widgetConverter;
            final Key key = DefaultKeyMapper.lastPressed();
            if (key == null) {
                return;
            }
            final InputType inputType = KeyMapper.getInputType(key);
            if (key.isAction() || inputType != InputType.CHARACTER || !f.a(c)) {
                final boolean keyPressed = converter.keyPressed(widget, key, inputType);
                if (keyPressed && converter instanceof TextFieldConverter) {
                    ((TextFieldConverter)converter).updateText((avw)this, widget);
                }
                cir.setReturnValue((Object)keyPressed);
                return;
            }
            final boolean charTyped = converter.charTyped(widget, key, c);
            if (charTyped && converter instanceof TextFieldConverter) {
                ((TextFieldConverter)converter).updateText((avw)this, widget);
            }
            cir.setReturnValue((Object)charTyped);
        }
    }
    
    @Override
    public WidgetWatcher<K> getWatcher() {
        return this.labyMod$watcher;
    }
    
    @Override
    public void setWidth(final int width) {
        this.i = width;
    }
    
    @Override
    public void setHeight(final int height) {
        this.j = height;
    }
    
    @Override
    public int getHeight() {
        return this.j;
    }
    
    @Override
    public boolean isEnabled() {
        return this.q;
    }
    
    @Inject(method = { "setText" }, at = { @At("HEAD") })
    private void labyMod$callValueConsumer(final String value, final CallbackInfo ci) {
        if (this.labyMod$textConsumer == null) {
            return;
        }
        this.labyMod$textConsumer.accept(value);
    }
    
    @Override
    public void setTextConsumer(final Consumer<String> consumer) {
        this.labyMod$textConsumer = consumer;
    }
}
