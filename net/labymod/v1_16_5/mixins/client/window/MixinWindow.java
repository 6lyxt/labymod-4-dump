// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.window;

import net.labymod.api.util.io.IOUtil;
import net.labymod.api.models.OperatingSystem;
import java.io.InputStream;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dez.class })
public abstract class MixinWindow
{
    @Inject(method = { "setErrorSection" }, at = { @At("TAIL") })
    private void labyMod$firePostStartupInitializeEvent(final String errorSection, final CallbackInfo ci) {
        if (!errorSection.equalsIgnoreCase("Post startup")) {
            return;
        }
        LabyMod.getInstance().eventBus().fire(new GameInitializeEvent(GameInitializeEvent.Lifecycle.POST_STARTUP));
    }
    
    @Inject(method = { "setIcon(Ljava/io/InputStream;Ljava/io/InputStream;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$disableMacOSIcon(final InputStream first, final InputStream second, final CallbackInfo ci) {
        if (OperatingSystem.isOSX()) {
            IOUtil.closeSilent(first);
            IOUtil.closeSilent(second);
            ci.cancel();
        }
    }
}
