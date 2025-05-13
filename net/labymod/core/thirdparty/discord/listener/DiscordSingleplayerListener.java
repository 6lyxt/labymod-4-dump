// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.thirdparty.discord.listener;

import net.labymod.api.util.function.Functional;
import java.math.RoundingMode;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.thirdparty.discord.DiscordActivity;
import net.labymod.api.event.client.world.WorldLeaveEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.Minecraft;
import java.util.Objects;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldService;
import net.labymod.api.LabyAPI;
import java.text.DecimalFormat;

public final class DiscordSingleplayerListener
{
    private static final DecimalFormat HEALTH_FORMAT;
    private final LabyAPI labyAPI;
    private final SharedLanWorldService lanWorldService;
    private final DefaultDiscordApp richPresence;
    private String lastState;
    private boolean lastLanHost;
    private float lastHealth;
    private String lastBiome;
    private GameMode lastGameMode;
    
    public DiscordSingleplayerListener() {
        this.labyAPI = Laby.labyAPI();
        this.lanWorldService = LabyMod.references().sharedLanWorldService();
        this.richPresence = (DefaultDiscordApp)Laby.references().discordApp();
    }
    
    @Subscribe
    public void onPostTick(final GameTickEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        final Minecraft minecraft = this.labyAPI.minecraft();
        if (minecraft == null || !minecraft.isIngame() || !minecraft.isSingleplayer()) {
            return;
        }
        if (this.lastLanHost != this.lanWorldService.isHost()) {
            this.lastLanHost = this.lanWorldService.isHost();
            if (this.lastLanHost) {
                this.updateState("Sharing LAN world", true);
            }
            else {
                this.updateState("Exploring " + this.lastBiome + " - " + this.getHealth(), true);
            }
            return;
        }
        final ClientPlayer clientPlayer = minecraft.getClientPlayer();
        if (clientPlayer == null) {
            return;
        }
        final float health = clientPlayer.getHealth();
        final GameMode gameMode = clientPlayer.gameMode();
        final ClientWorld clientWorld = minecraft.clientWorld();
        final String biome = clientWorld.getReadableBiomeName();
        if (this.lastHealth == health && this.lastGameMode == gameMode && Objects.equals(this.lastBiome, biome)) {
            return;
        }
        boolean forceUpdate = false;
        this.lastHealth = health;
        if (this.lastGameMode != gameMode) {
            this.lastGameMode = gameMode;
            forceUpdate = true;
        }
        this.lastBiome = biome;
        this.updateState("Exploring " + this.lastBiome + " - " + this.getHealth(), forceUpdate);
    }
    
    @Subscribe
    public void onWorldLeave(final WorldLeaveEvent event) {
        final Minecraft minecraft = this.labyAPI.minecraft();
        if (minecraft == null || !minecraft.isIngame() || !minecraft.isSingleplayer()) {
            return;
        }
        this.richPresence.displayDefaultActivity();
    }
    
    private void updateState(final String state, final boolean forceUpdate) {
        if (!forceUpdate && Objects.equals(this.lastState, state)) {
            return;
        }
        this.lastState = state;
        final DiscordActivity displayedActivity = this.richPresence.getDisplayedActivity();
        if (displayedActivity == null || displayedActivity.isCustom()) {
            return;
        }
        final DiscordActivity activity = DiscordActivity.builder(this, displayedActivity).state(state).details(this.labyAPI.minecraft().getClientPlayer().gameMode().getName()).build();
        this.richPresence.displayActivity(activity);
    }
    
    @NotNull
    private String getHealth() {
        final float health = this.lastHealth / 2.0f;
        return (health > 0.0f) ? (DiscordSingleplayerListener.HEALTH_FORMAT.format(health) + " heart" + ((health == 1.0f) ? "" : "s")) : "Dead";
    }
    
    static {
        HEALTH_FORMAT = Functional.of(new DecimalFormat("#.#"), format -> format.setRoundingMode(RoundingMode.CEILING));
    }
}
