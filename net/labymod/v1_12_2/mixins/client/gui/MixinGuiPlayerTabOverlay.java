// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

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

@Mixin({ bjq.class })
public class MixinGuiPlayerTabOverlay implements TabList
{
    @Shadow
    private boolean k;
    private final DefaultTabListHolder labyMod$tabListHolder;
    
    public MixinGuiPlayerTabOverlay() {
        this.labyMod$tabListHolder = new DefaultTabListHolder(() -> this.k);
    }
    
    @Insert(method = { "setHeader(Lnet/minecraft/util/text/ITextComponent;)V" }, at = @At("TAIL"))
    private void labyMod$setHeader(final hh header, final InsertInfo ci) {
        this.labyMod$tabListHolder.setHeader(header);
    }
    
    @Insert(method = { "setFooter(Lnet/minecraft/util/text/ITextComponent;)V" }, at = @At("TAIL"))
    private void labyMod$setFooter(final hh footer, final InsertInfo ci) {
        this.labyMod$tabListHolder.setFooter(footer);
    }
    
    @Insert(method = { "resetFooterHeader()V" }, at = @At("TAIL"))
    private void labyMod$reset(final InsertInfo ci) {
        this.labyMod$tabListHolder.reset();
    }
    
    @Insert(method = { "renderPlayerlist(ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreObjective;)V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$shouldRenderWidgetList(final int screenWidth, final bhk scoreboard, final bhg objective, final InsertInfo ci) {
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
