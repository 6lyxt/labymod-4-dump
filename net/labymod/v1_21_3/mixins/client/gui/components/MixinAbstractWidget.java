// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.gui.components;

import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.gui.AbstractWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@Mixin({ fod.class })
public abstract class MixinAbstractWidget<K extends AbstractWidget<?>> implements ConvertableMinecraftWidget<K>, AbstractWidgetAccessor
{
    @Shadow
    protected int g;
    @Shadow
    protected int h;
    private final WidgetWatcher<K> labyMod$watcher;
    private int labyMod$lastFrame;
    
    public MixinAbstractWidget() {
        this.labyMod$watcher = new WidgetWatcher<K>();
    }
    
    @Shadow
    public abstract boolean C();
    
    @Shadow
    public abstract boolean B();
    
    @Shadow
    public abstract int D();
    
    @Shadow
    public abstract int E();
    
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
    private void labyMod$playCustomDownSound(final hgn soundManager, final CallbackInfo ci) {
        final K widget = this.labyMod$watcher.getWidget();
        if (widget != null && widget.playInteractionSoundInternal()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "setX" }, at = { @At("HEAD") })
    private void labyMod$invalidateX(final int x, final CallbackInfo ci) {
        if (this.D() != x) {
            this.labyMod$invalidate();
        }
    }
    
    @Inject(method = { "setY" }, at = { @At("HEAD") })
    private void labyMod$invalidateY(final int y, final CallbackInfo ci) {
        if (this.E() != y) {
            this.labyMod$invalidate();
        }
    }
    
    @Inject(method = { "setWidth" }, at = { @At("HEAD") })
    private void labyMod$invalidateWidth(final int width, final CallbackInfo ci) {
        if (this.g != width) {
            this.labyMod$invalidate();
        }
    }
    
    @Inject(method = { "setHeight" }, at = { @At("HEAD") })
    private void labyMod$invalidateHeight(final int height, final CallbackInfo ci) {
        if (this.h != height) {
            this.labyMod$invalidate();
        }
    }
    
    @Inject(method = { "setSize" }, at = { @At("HEAD") })
    private void labyMod$invalidateSize(final int width, final int height, final CallbackInfo ci) {
        if (this.g != width || this.h != height) {
            this.labyMod$invalidate();
        }
    }
    
    @Unique
    private void labyMod$invalidate() {
        final int currentFrame = Laby.references().frameTimer().getFrame();
        if (this.labyMod$lastFrame == currentFrame) {
            return;
        }
        this.labyMod$lastFrame = currentFrame;
        this.labyMod$watcher.invalidate();
    }
    
    @Override
    public WidgetWatcher<K> getWatcher() {
        return this.labyMod$watcher;
    }
    
    @Override
    public void setHeight(final int height) {
        this.h = height;
    }
}
