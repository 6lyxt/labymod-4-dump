// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.scoreboard;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.v1_19_3.client.network.chat.MutableComponentAccessor;
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

@Mixin({ eba.class })
public abstract class MixinPlayerTeam implements ScoreboardTeam
{
    private final ScoreboardTeamUpdateEvent updateEvent;
    @Shadow
    private m m;
    @Shadow
    private ss g;
    @Shadow
    private ss h;
    
    public MixinPlayerTeam() {
        this.updateEvent = new ScoreboardTeamUpdateEvent(this);
    }
    
    @Shadow
    public abstract Collection<String> g();
    
    @Shadow
    public abstract String b();
    
    @Shadow
    public abstract ss e();
    
    @Shadow
    public abstract ss f();
    
    @Inject(method = { "setPlayerPrefix" }, at = { @At("TAIL") })
    private void updatePlayerPrefix(final ss prefix, final CallbackInfo callback) {
        ejf.N().execute(() -> Laby.fireEvent(this.updateEvent));
    }
    
    @Inject(method = { "setPlayerSuffix" }, at = { @At("TAIL") })
    private void updatePlayerSuffix(final ss suffix, final CallbackInfo callback) {
        ejf.N().execute(() -> Laby.fireEvent(this.updateEvent));
    }
    
    @Inject(method = { "setPlayerPrefix" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$cancelScoreboardPrefixUpdate(final ss newPrefix, final CallbackInfo ci) {
        if (this.g == ((newPrefix == null) ? st.a : newPrefix)) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "setPlayerSuffix" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$cancelScoreboardSuffixUpdate(final ss newSuffix, final CallbackInfo ci) {
        if (this.h == ((newSuffix == null) ? st.a : newSuffix)) {
            ci.cancel();
        }
    }
    
    @NotNull
    @Override
    public String getTeamName() {
        return this.b();
    }
    
    @NotNull
    @Override
    public Collection<String> getEntries() {
        return this.g();
    }
    
    @Override
    public boolean hasEntry(@NotNull final String name) {
        return this.g().contains(name);
    }
    
    @NotNull
    @Override
    public Component getPrefix() {
        return ((MutableComponentAccessor)this.e()).getLabyComponent();
    }
    
    @NotNull
    @Override
    public Component getSuffix() {
        return ((MutableComponentAccessor)this.f()).getLabyComponent();
    }
    
    @NotNull
    @Override
    public Component formatDisplayName(@NotNull final Component component) {
        Style style = component.style();
        if (this.m != m.v && style.getColor() == null) {
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
