// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.gui;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.scoreboard.TabListHolder;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.scoreboard.DefaultTabListHolder;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.scoreboard.TabList;

@Mixin({ fph.class })
public class MixinPlayerTabOverlay implements TabList
{
    @Shadow
    private boolean u;
    private final DefaultTabListHolder labyMod$tabListHolder;
    
    public MixinPlayerTabOverlay() {
        this.labyMod$tabListHolder = new DefaultTabListHolder(() -> this.u);
    }
    
    @Insert(method = { "setHeader(Lnet/minecraft/network/chat/Component;)V" }, at = @At("TAIL"))
    private void setHeader(final xv header, final InsertInfo ci) {
        this.labyMod$tabListHolder.setHeader(header);
    }
    
    @Insert(method = { "setFooter(Lnet/minecraft/network/chat/Component;)V" }, at = @At("TAIL"))
    private void setFooter(final xv footer, final InsertInfo ci) {
        this.labyMod$tabListHolder.setFooter(footer);
    }
    
    @Insert(method = { "reset()V" }, at = @At("TAIL"))
    private void reset(final InsertInfo ci) {
        this.labyMod$tabListHolder.reset();
    }
    
    @Insert(method = { "render" }, at = @At("HEAD"), cancellable = true)
    private void shouldRenderWidgetList(final fns graphics, final int screenWidth, final fdd scoreboard, final fcv objective, final InsertInfo ci) {
        if (Laby.labyAPI().config().multiplayer().customPlayerList().get()) {
            ci.cancel();
        }
    }
    
    @NotNull
    @Override
    public TabListHolder holder() {
        return this.labyMod$tabListHolder;
    }
}
