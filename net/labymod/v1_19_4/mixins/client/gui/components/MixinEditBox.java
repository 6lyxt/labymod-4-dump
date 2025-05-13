// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.gui.components;

import net.labymod.api.client.gui.mouse.MutableMouse;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.key.Key;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import java.util.function.Consumer;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_4.client.gui.EditBoxAccessor;

@Mixin({ eol.class })
public abstract class MixinEditBox extends MixinAbstractWidget implements EditBoxAccessor
{
    @Shadow
    private boolean A;
    @Shadow
    private boolean B;
    @Shadow
    private tj M;
    private Consumer<String> labyMod$valueConsumer;
    
    @Shadow
    public abstract void b_(final boolean p0);
    
    @Insert(method = { "renderWidget" }, at = @At("HEAD"), cancellable = true)
    public void render(final ehe poseStack, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.getWatcher().update(this, ((enz)this).k());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final boolean rendered = this.getWatcher().render(((VanillaStackAccessor)poseStack).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
    
    @Inject(method = { "mouseClicked" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$editBoxMouseClicked(final double mouseX, final double mouseY, final int button, final CallbackInfoReturnable<Boolean> cir) {
        final WidgetWatcher<?> watcher = this.getWatcher();
        final AbstractWidget widget = (AbstractWidget)watcher.getWidget();
        if (widget == null) {
            return;
        }
        final AbstractWidgetConverter converter = watcher.getWidgetConverter();
        final int x = ((eol)this).p();
        final int y = ((eol)this).q();
        final int width = ((eol)this).j();
        final int height = ((eol)this).h();
        final boolean hovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
        if (this.A) {
            this.b_(hovered);
            widget.setFocused(hovered);
        }
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> cir.setReturnValue((Object)converter.mouseClicked(widget, mouse, DefaultKeyMapper.pressMouse(button))));
    }
    
    @Inject(method = { "charTyped" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$charTyped(final char c, final int param1, final CallbackInfoReturnable<Boolean> cir) {
        final WidgetWatcher<?> watcher = this.getWatcher();
        final AbstractWidget widget = (AbstractWidget)watcher.getWidget();
        if (widget == null) {
            return;
        }
        final Key key = DefaultKeyMapper.lastPressed();
        final AbstractWidgetConverter converter = watcher.getWidgetConverter();
        cir.setReturnValue((Object)converter.charTyped(widget, key, c));
    }
    
    @Inject(method = { "keyPressed" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$keyPressed(final int keyCode, final int param1, final int param2, final CallbackInfoReturnable<Boolean> cir) {
        final WidgetWatcher<?> watcher = this.getWatcher();
        final AbstractWidget widget = (AbstractWidget)watcher.getWidget();
        if (widget == null) {
            return;
        }
        final Key key = DefaultKeyMapper.lastPressed();
        final AbstractWidgetConverter converter = watcher.getWidgetConverter();
        cir.setReturnValue((Object)converter.keyPressed(widget, key, KeyMapper.getInputType(key)));
    }
    
    @Inject(method = { "onValueChange" }, at = { @At("HEAD") })
    private void labyMod$callValueConsumer(final String value, final CallbackInfo ci) {
        if (this.labyMod$valueConsumer == null) {
            return;
        }
        this.labyMod$valueConsumer.accept(value);
    }
    
    @Override
    public void setValueConsumer(final Consumer<String> valueConsumer) {
        this.labyMod$valueConsumer = valueConsumer;
    }
    
    @Override
    public boolean isEditable() {
        return this.B;
    }
    
    @Override
    public tj getHint() {
        return this.M;
    }
}
