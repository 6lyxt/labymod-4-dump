// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.screen;

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

@Mixin({ eow.class })
public abstract class MixinPauseScreen extends epb
{
    private boolean isConfirmed;
    
    protected MixinPauseScreen(final ss param0) {
        super(param0);
    }
    
    @Inject(method = { "createPauseMenu" }, at = { @At("HEAD") })
    private void resetState(final CallbackInfo ci) {
        this.isConfirmed = false;
    }
    
    @Inject(method = { "lambda$createPauseMenu$5(Lnet/minecraft/client/gui/components/Button;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void confirmDisconnect(final eky button, final CallbackInfo ci) {
        if (this.e.Q() || !LabyMod.getInstance().config().multiplayer().confirmDisconnect().get()) {
            return;
        }
        if (!this.isConfirmed) {
            this.isConfirmed = true;
            TranslatableComponent component = Component.translatable("labymod.activity.menu.button.confirmDisconnect", new Component[0]);
            component = component.style(Style.style(NamedTextColor.RED));
            button.b((ss)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(component));
            ci.cancel();
        }
    }
}
