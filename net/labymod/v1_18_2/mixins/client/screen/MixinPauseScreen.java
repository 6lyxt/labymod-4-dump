// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ edr.class })
public abstract class MixinPauseScreen extends edw
{
    private boolean isConfirmed;
    
    protected MixinPauseScreen(final qk param0) {
        super(param0);
    }
    
    @Inject(method = { "createPauseMenu" }, at = { @At("HEAD") })
    private void resetState(final CallbackInfo ci) {
        this.isConfirmed = false;
    }
    
    @Inject(method = { "lambda$createPauseMenu$9(Lnet/minecraft/client/gui/components/Button;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void confirmDisconnect(final eae button, final CallbackInfo ci) {
        if (this.e.G() || !LabyMod.getInstance().config().multiplayer().confirmDisconnect().get()) {
            return;
        }
        if (!this.isConfirmed) {
            this.isConfirmed = true;
            TranslatableComponent component = Component.translatable("labymod.activity.menu.button.confirmDisconnect", new Component[0]);
            component = component.style(Style.style(NamedTextColor.RED));
            button.b((qk)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(component));
            ci.cancel();
        }
    }
}
