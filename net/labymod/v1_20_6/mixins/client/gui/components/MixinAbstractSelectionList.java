// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.gui.components;

import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fhb.class })
public abstract class MixinAbstractSelectionList extends fgy
{
    public MixinAbstractSelectionList(final int $$0, final int $$1, final int $$2, final int $$3, final xp $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }
    
    @Inject(method = { "renderListSeparators" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$hideListSeparators(final CallbackInfo info) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (theme.metadata().getBoolean("hide_list_separators")) {
            info.cancel();
        }
    }
    
    @WrapOperation(method = { "renderWidget" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderListBackground(Lnet/minecraft/client/gui/GuiGraphics;)V") })
    private void labyMod$renderBackground(final fhb instance, final fgt guiGraphics, final Operation<Void> original) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (theme.metadata().getBoolean("hide_default_background")) {
            return;
        }
        original.call(new Object[] { instance, guiGraphics });
    }
    
    @WrapOperation(method = { "renderWidget" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderListItems(Lnet/minecraft/client/gui/GuiGraphics;IIF)V") })
    public void labyMod$renderList(final fhb instance, final fgt graphics, final int mouseX, final int mouseY, final float partialTicks, final Operation<Void> original) {
        final Scissor scissor = Laby.labyAPI().gfxRenderPipeline().scissor();
        try {
            scissor.push(((VanillaStackAccessor)graphics.c()).stack(), (float)this.D(), (float)this.v());
            original.call(new Object[] { instance, graphics, mouseX, mouseY, partialTicks });
        }
        finally {
            scissor.pop();
        }
    }
}
