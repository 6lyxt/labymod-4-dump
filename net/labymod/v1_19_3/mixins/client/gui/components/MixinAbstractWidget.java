// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.gui.components;

import net.labymod.api.client.gui.mouse.MutableMouse;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.gui.AbstractWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@Mixin({ ekw.class })
public abstract class MixinAbstractWidget<K extends AbstractWidget<?>> implements ConvertableMinecraftWidget<K>, AbstractWidgetAccessor
{
    @Shadow
    protected int k;
    @Shadow
    protected int l;
    @Shadow
    public int a;
    @Shadow
    public int b;
    private final WidgetWatcher<K> labyMod$watcher;
    
    public MixinAbstractWidget() {
        this.labyMod$watcher = new WidgetWatcher<K>();
    }
    
    @Shadow
    protected abstract void d(final boolean p0);
    
    @Insert(method = { "renderButton" }, at = @At("HEAD"), cancellable = true)
    public void render(final eed poseStack, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.getWatcher().update(this, ((ekw)this).o());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final boolean rendered = this.getWatcher().render(((VanillaStackAccessor)poseStack).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
    
    @Inject(method = { "mouseClicked" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$mouseClicked(final double mouseX, final double mouseY, final int button, final CallbackInfoReturnable<Boolean> ci) {
        final K widget = this.labyMod$watcher.getWidget();
        if (widget == null) {
            return;
        }
        final boolean isInBounds = widget.bounds().isInRectangle(BoundsType.BORDER, (float)mouseX, (float)mouseY);
        if (!widget.isVisible() || !widget.pressable().get() || !isInBounds) {
            ci.setReturnValue((Object)false);
            return;
        }
        if (this.labyMod$watcher.hasReplacement() && isInBounds) {
            widget.onPress();
            ci.setReturnValue((Object)true);
        }
    }
    
    @Inject(method = { "playDownSound" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$playCustomDownSound(final fsr soundManager, final CallbackInfo ci) {
        final K widget = this.labyMod$watcher.getWidget();
        if (widget != null && widget.playInteractionSoundInternal()) {
            ci.cancel();
        }
    }
    
    @Override
    public WidgetWatcher<K> getWatcher() {
        return this.labyMod$watcher;
    }
    
    @Override
    public void setHeight(final int height) {
        this.l = height;
    }
}
