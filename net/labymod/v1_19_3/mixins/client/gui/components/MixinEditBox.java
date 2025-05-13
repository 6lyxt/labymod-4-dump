// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.gui.components;

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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import java.util.function.Consumer;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_3.client.gui.EditBoxAccessor;

@Mixin({ elh.class })
public abstract class MixinEditBox extends MixinAbstractWidget implements EditBoxAccessor
{
    @Shadow
    private boolean y;
    @Shadow
    private boolean z;
    @Shadow
    private ss K;
    private eed labyMod$poseStack;
    private Consumer<String> labyMod$valueConsumer;
    
    @Insert(method = { "renderButton" }, at = @At("HEAD"), cancellable = true)
    @Override
    public void render(final eed poseStack, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.labyMod$poseStack = poseStack;
        this.getWatcher().update(this, ((ekw)this).o());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final boolean rendered = this.getWatcher().render(((VanillaStackAccessor)poseStack).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
    
    @Redirect(method = { "renderHighlight" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private eeh labyMod$addStack(final edy instance, final double x, final double y, final double z) {
        return instance.a(this.labyMod$poseStack.c().a(), (float)x, (float)y, (float)z);
    }
    
    @Inject(method = { "mouseClicked" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$editBoxMouseClicked(final double mouseX, final double mouseY, final int button, final CallbackInfoReturnable<Boolean> cir) {
        final WidgetWatcher<?> watcher = this.getWatcher();
        final AbstractWidget widget = (AbstractWidget)watcher.getWidget();
        if (widget == null) {
            return;
        }
        final AbstractWidgetConverter converter = watcher.getWidgetConverter();
        final int x = ((elh)this).r();
        final int y = ((elh)this).s();
        final int width = ((elh)this).n();
        final int height = ((elh)this).i();
        final boolean hovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
        if (this.y) {
            this.d(hovered);
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
        return this.z;
    }
    
    @Override
    public ss getHint() {
        return this.K;
    }
}
