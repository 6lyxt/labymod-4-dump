// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.gui.components;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;

@Mixin({ fin.class })
public abstract class MixinStringWidget extends MixinAbstractWidget implements StringWidgetAccessor
{
    private boolean labyMod$basedOnTextWidth;
    
    @Inject(method = { "<init>(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/Font;)V" }, at = { @At("TAIL") })
    private void labyMod$onInit(final xp $$0, final fgr $$1, final CallbackInfo ci) {
        this.labyMod$basedOnTextWidth = true;
    }
    
    @Override
    public boolean isBasedOnTextWidth() {
        return this.labyMod$basedOnTextWidth;
    }
    
    @Inject(method = { "renderWidget" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$renderWidget(final fgt graphics, final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        this.getWatcher().update(this, ((fhe)this).y());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final boolean rendered = this.getWatcher().render(((VanillaStackAccessor)graphics.c()).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
}
