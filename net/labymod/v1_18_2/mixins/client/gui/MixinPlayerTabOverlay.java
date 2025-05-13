// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.gui;

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

@Mixin({ eau.class })
public class MixinPlayerTabOverlay implements TabList
{
    @Shadow
    private boolean s;
    private final DefaultTabListHolder labyMod$tabListHolder;
    
    public MixinPlayerTabOverlay() {
        this.labyMod$tabListHolder = new DefaultTabListHolder(() -> this.s);
    }
    
    @Insert(method = { "setHeader(Lnet/minecraft/network/chat/Component;)V" }, at = @At("TAIL"))
    private void setHeader(final qk header, final InsertInfo ci) {
        this.labyMod$tabListHolder.setHeader(header);
    }
    
    @Insert(method = { "setFooter(Lnet/minecraft/network/chat/Component;)V" }, at = @At("TAIL"))
    private void setFooter(final qk footer, final InsertInfo ci) {
        this.labyMod$tabListHolder.setFooter(footer);
    }
    
    @Insert(method = { "reset()V" }, at = @At("TAIL"))
    private void reset(final InsertInfo ci) {
        this.labyMod$tabListHolder.reset();
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;ILnet/minecraft/world/scores/Scoreboard;Lnet/minecraft/world/scores/Objective;)V" }, at = @At("HEAD"), cancellable = true)
    private void shouldRenderWidgetList(final dtm poseStack, final int screenWidth, final dqm scoreboard, final dqj objective, final InsertInfo ci) {
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
