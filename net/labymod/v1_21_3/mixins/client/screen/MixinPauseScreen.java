// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.screen;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ftt.class })
public abstract class MixinPauseScreen extends fty
{
    @Unique
    private boolean labyMod$confirmDisconnect;
    
    protected MixinPauseScreen(final xv $$0) {
        super($$0);
    }
    
    @Inject(method = { "lambda$createPauseMenu$8" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$confirmDisconnect(final fof button, final CallbackInfo ci) {
        if (!LabyMod.getInstance().config().multiplayer().confirmDisconnect().get()) {
            return;
        }
        if (!this.labyMod$confirmDisconnect) {
            this.labyMod$confirmDisconnect = true;
            final TranslatableComponent component = Component.translatable("labymod.activity.menu.button.confirmDisconnect", NamedTextColor.RED);
            button.b((xv)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(component));
            ci.cancel();
        }
    }
}
