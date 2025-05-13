// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.api.client.gui.mouse.MutableMouse;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.Minecraft;
import net.labymod.v1_8_9.client.gui.GuiSliderAccessor;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@Mixin({ avs.class })
public abstract class MixinGuiButton<K extends AbstractWidget<?>> implements ConvertableMinecraftWidget<K>
{
    private final WidgetWatcher<K> labyMod$watcher;
    
    public MixinGuiButton() {
        this.labyMod$watcher = new WidgetWatcher<K>();
    }
    
    @Insert(method = { "drawButton" }, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderWidgetWatcher(final ave minecraft, final int mouseX, final int mouseY, final InsertInfo ci) {
        final Minecraft api = Laby.labyAPI().minecraft();
        api.updateMouse(mouseX, mouseY, mouse -> {
            final Blaze3DGlStatePipeline bridge = Laby.gfx().blaze3DGlStatePipeline();
            bridge.defaultAlphaFunc();
            this.labyMod$watcher.update(this, ((avs)this).j);
            final boolean rendered = this.labyMod$watcher.render(VersionedStackProvider.DEFAULT_STACK, mouse, api.getTickDelta());
            bridge.alphaFunc(516, 0.1f);
            if (rendered) {
                ci.cancel();
                if (this instanceof final GuiSliderAccessor guiSliderAccessor) {
                    guiSliderAccessor.labymod$mouseDragged(minecraft, mouse);
                }
            }
        });
    }
    
    @Inject(method = { "playPressSound" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$playCustomDownSound(final bpz soundManager, final CallbackInfo ci) {
        final K widget = this.labyMod$watcher.getWidget();
        if (widget != null && widget.playInteractionSoundInternal()) {
            ci.cancel();
        }
    }
    
    @Override
    public WidgetWatcher<K> getWatcher() {
        return this.labyMod$watcher;
    }
}
