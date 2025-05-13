// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.scoreboard;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.v1_21_5.client.network.chat.MutableComponentAccessor;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Collection;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamUpdateEvent;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.scoreboard.ScoreboardTeam;

@Mixin({ fhc.class })
public abstract class MixinPlayerTeam implements ScoreboardTeam
{
    private final ScoreboardTeamUpdateEvent updateEvent;
    @Shadow
    private o m;
    @Shadow
    private xg g;
    @Shadow
    private xg h;
    
    public MixinPlayerTeam() {
        this.updateEvent = new ScoreboardTeamUpdateEvent(this);
    }
    
    @Shadow
    public abstract Collection<String> h();
    
    @Shadow
    public abstract String c();
    
    @Shadow
    public abstract xg f();
    
    @Shadow
    public abstract xg g();
    
    @Inject(method = { "setPlayerPrefix" }, at = { @At("TAIL") })
    private void updatePlayerPrefix(final xg prefix, final CallbackInfo callback) {
        fqq.Q().execute(() -> Laby.fireEvent(this.updateEvent));
    }
    
    @Inject(method = { "setPlayerSuffix" }, at = { @At("TAIL") })
    private void updatePlayerSuffix(final xg suffix, final CallbackInfo callback) {
        fqq.Q().execute(() -> Laby.fireEvent(this.updateEvent));
    }
    
    @Inject(method = { "setPlayerPrefix" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$cancelScoreboardPrefixUpdate(final xg newPrefix, final CallbackInfo ci) {
        if (this.g == ((newPrefix == null) ? yn.c : newPrefix)) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "setPlayerSuffix" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$cancelScoreboardSuffixUpdate(final xg newSuffix, final CallbackInfo ci) {
        if (this.h == ((newSuffix == null) ? yn.c : newSuffix)) {
            ci.cancel();
        }
    }
    
    @NotNull
    @Override
    public String getTeamName() {
        return this.c();
    }
    
    @NotNull
    @Override
    public Collection<String> getEntries() {
        return this.h();
    }
    
    @Override
    public boolean hasEntry(@NotNull final String name) {
        return this.h().contains(name);
    }
    
    @NotNull
    @Override
    public Component getPrefix() {
        return ((MutableComponentAccessor)this.f()).getLabyComponent();
    }
    
    @NotNull
    @Override
    public Component getSuffix() {
        return ((MutableComponentAccessor)this.g()).getLabyComponent();
    }
    
    @NotNull
    @Override
    public Component formatDisplayName(@NotNull final Component component) {
        Style style = component.style();
        if (this.m != o.v && style.getColor() == null) {
            switch (this.m) {
                case a: {
                    style = style.color(NamedTextColor.BLACK);
                    break;
                }
                case b: {
                    style = style.color(NamedTextColor.DARK_BLUE);
                    break;
                }
                case c: {
                    style = style.color(NamedTextColor.DARK_GREEN);
                    break;
                }
                case d: {
                    style = style.color(NamedTextColor.DARK_AQUA);
                    break;
                }
                case e: {
                    style = style.color(NamedTextColor.DARK_RED);
                    break;
                }
                case f: {
                    style = style.color(NamedTextColor.DARK_PURPLE);
                    break;
                }
                case g: {
                    style = style.color(NamedTextColor.GOLD);
                    break;
                }
                case h: {
                    style = style.color(NamedTextColor.GRAY);
                    break;
                }
                case i: {
                    style = style.color(NamedTextColor.DARK_GRAY);
                    break;
                }
                case j: {
                    style = style.color(NamedTextColor.BLUE);
                    break;
                }
                case k: {
                    style = style.color(NamedTextColor.GREEN);
                    break;
                }
                case l: {
                    style = style.color(NamedTextColor.AQUA);
                    break;
                }
                case m: {
                    style = style.color(NamedTextColor.RED);
                    break;
                }
                case n: {
                    style = style.color(NamedTextColor.LIGHT_PURPLE);
                    break;
                }
                case o: {
                    style = style.color(NamedTextColor.YELLOW);
                    break;
                }
                case p: {
                    style = style.color(NamedTextColor.WHITE);
                    break;
                }
                case q: {
                    style = style.decorate(TextDecoration.OBFUSCATED);
                    break;
                }
                case r: {
                    style = style.decorate(TextDecoration.BOLD);
                    break;
                }
                case s: {
                    style = style.decorate(TextDecoration.STRIKETHROUGH);
                    break;
                }
                case t: {
                    style = style.decorate(TextDecoration.UNDERLINED);
                    break;
                }
                case u: {
                    style = style.decorate(TextDecoration.ITALIC);
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + String.valueOf(this.m));
                }
            }
        }
        return ((BaseComponent<Component>)Component.empty().append(this.getPrefix()).append(component.style(style))).append(this.getSuffix());
    }
}
