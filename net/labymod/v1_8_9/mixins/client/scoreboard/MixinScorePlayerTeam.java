// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.scoreboard;

import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Collection;
import net.labymod.api.client.component.Component;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamUpdateEvent;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.scoreboard.ScoreboardTeam;

@Mixin({ aul.class })
public abstract class MixinScorePlayerTeam implements ScoreboardTeam
{
    private final ScoreboardTeamUpdateEvent updateEvent;
    @Final
    @Shadow
    private String b;
    @Shadow
    private String e;
    @Shadow
    private String f;
    private Component prefix;
    private String rawPrefix;
    private Component suffix;
    private String rawSuffix;
    
    public MixinScorePlayerTeam() {
        this.updateEvent = new ScoreboardTeamUpdateEvent(this);
        this.prefix = Component.empty();
        this.rawPrefix = "";
        this.suffix = Component.empty();
        this.rawSuffix = "";
    }
    
    @Shadow
    public abstract Collection<String> d();
    
    @Inject(method = { "setNamePrefix" }, at = { @At("TAIL") })
    private void labyMod$updatePlayerPrefix(final String prefix, final CallbackInfo callback) {
        this.prefix = LegacyComponentSerializer.legacySection().deserialize(prefix);
        this.rawPrefix = prefix;
        ave.A().a(() -> Laby.fireEvent(this.updateEvent));
    }
    
    @Inject(method = { "setNameSuffix" }, at = { @At("TAIL") })
    private void labyMod$updatePlayerSuffix(final String suffix, final CallbackInfo callback) {
        this.suffix = LegacyComponentSerializer.legacySection().deserialize(suffix);
        this.rawSuffix = suffix;
        ave.A().a(() -> Laby.fireEvent(this.updateEvent));
    }
    
    @Inject(method = { "setNamePrefix" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/scoreboard/ScorePlayerTeam;namePrefixSPT:Ljava/lang/String;", shift = At.Shift.BEFORE) }, cancellable = true)
    private void labyMod$cancelScoreboardPrefixUpdate(final String newPrefix, final CallbackInfo ci) {
        if (this.e.equals(newPrefix)) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "setNameSuffix" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$cancelScoreboardSuffixUpdate(final String newPrefix, final CallbackInfo ci) {
        if (Objects.equals(this.f, newPrefix)) {
            ci.cancel();
        }
    }
    
    @NotNull
    @Override
    public String getTeamName() {
        return this.b;
    }
    
    @NotNull
    @Override
    public Collection<String> getEntries() {
        return this.d();
    }
    
    @Override
    public boolean hasEntry(@NotNull final String name) {
        return this.d().contains(name);
    }
    
    @NotNull
    @Override
    public Component getPrefix() {
        return this.prefix;
    }
    
    @NotNull
    @Override
    public Component getSuffix() {
        return this.suffix;
    }
    
    @NotNull
    @Override
    public Component formatDisplayName(@NotNull final Component component) {
        return LegacyComponentSerializer.legacySection().deserialize(this.rawPrefix + LegacyComponentSerializer.legacySection().serialize(component) + this.rawSuffix);
    }
}
