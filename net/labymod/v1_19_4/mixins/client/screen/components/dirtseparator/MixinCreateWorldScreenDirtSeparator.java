// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.screen.components.dirtseparator;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ exn.class })
public class MixinCreateWorldScreenDirtSeparator extends etd
{
    protected MixinCreateWorldScreenDirtSeparator(final tj $$0) {
        super($$0);
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", shift = At.Shift.BEFORE) }, cancellable = true)
    private void labyMod$skipDirtSeparator(final ehe stack, final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        final Theme currentTheme = Laby.references().themeService().currentTheme();
        if (!currentTheme.metadata().get("show_dirt_separator", true)) {
            ci.cancel();
            super.a(stack, mouseX, mouseY, partialTicks);
        }
    }
}
