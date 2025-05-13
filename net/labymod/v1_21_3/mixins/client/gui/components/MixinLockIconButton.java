// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.gui.components;

import net.labymod.api.client.gui.mouse.MutableMouse;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fow.class })
public abstract class MixinLockIconButton extends MixinAbstractWidget
{
    @Insert(method = { "renderWidget" }, at = @At("HEAD"), cancellable = true)
    public void render(final fns graphics, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.getWatcher().update(this, ((fod)this).z());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final boolean rendered = this.getWatcher().render(((VanillaStackAccessor)graphics.c()).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
}
