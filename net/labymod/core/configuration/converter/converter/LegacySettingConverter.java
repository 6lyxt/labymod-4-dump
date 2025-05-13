// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter.converter;

import net.labymod.api.configuration.labymod.main.laby.HotkeysConfig;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.configuration.labymod.main.laby.ingame.ZoomConfig;
import java.util.Map;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.zoom.ZoomTransitionType;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import java.nio.file.Paths;
import java.io.IOException;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.configuration.labymod.main.laby.IngameConfig;
import net.labymod.api.user.shop.CloakPriority;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import net.labymod.api.configuration.labymod.main.laby.other.DiscordConfig;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.UserIndicatorConfig;
import net.labymod.api.configuration.labymod.main.laby.MultiplayerConfig;
import net.labymod.api.Laby;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import com.google.gson.JsonObject;
import net.labymod.api.configuration.converter.LegacyConverter;

public class LegacySettingConverter extends LegacyConverter<JsonObject>
{
    public LegacySettingConverter() {
        super("LabyMod-3.json", JsonObject.class);
    }
    
    @Override
    protected void convert(final JsonObject value) throws Exception {
        this.convertIngameSettings(value);
        this.convertAppearanceSettings(value);
        this.convertMultiplayerSettings(value);
        this.convertOtherSettings(value);
        this.convertHotkeySettings(value);
    }
    
    @Override
    public boolean hasStuffToConvert() {
        return Files.exists(this.path, new LinkOption[0]);
    }
    
    private void convertMultiplayerSettings(final JsonObject jsonObject) {
        this.convertClassicPvPSettings(jsonObject);
        final MultiplayerConfig multiplayer = Laby.labyAPI().config().multiplayer();
        multiplayer.serverList().liveServerList().set(jsonObject.get("serverlistLiveView").getAsBoolean());
        multiplayer.serverList().cooldown().set(jsonObject.get("serverlistLiveViewInterval").getAsInt());
        multiplayer.confirmDisconnect().set(jsonObject.get("confirmDisconnect").getAsBoolean());
        final UserIndicatorConfig userIndicator = multiplayer.userIndicator();
        userIndicator.showUserIndicatorInPlayerList().set(jsonObject.get("revealFamiliarUsers").getAsBoolean());
        userIndicator.showLabyModPlayerPercentageInTabList().set(jsonObject.get("revealFamiliarUsersPercentage").getAsBoolean());
        userIndicator.showCountryFlagInPlayerList().set(jsonObject.get("showLanguageFlags").getAsBoolean());
        final PingConfig ping = multiplayer.ping();
        ping.exact().set(jsonObject.get("tabPing").getAsBoolean());
        ping.exactColored().set(jsonObject.get("tabPing_colored").getAsBoolean());
        multiplayer.serverBanner().set(jsonObject.get("serverBanner").getAsBoolean());
    }
    
    private void convertClassicPvPSettings(final JsonObject jsonObject) {
        final ClassicPvPConfig classicPvP = Laby.labyAPI().config().multiplayer().classicPvP();
        classicPvP.oldRange().set(jsonObject.get("oldRange").getAsBoolean());
        classicPvP.oldSlowdown().set(jsonObject.get("oldSlowdown").getAsBoolean());
        classicPvP.oldItemPosture().set(jsonObject.get("oldItemHold").getAsBoolean());
        classicPvP.oldEquip().enabled().set(jsonObject.get("oldItemSwitch").getAsBoolean());
        classicPvP.oldFood().set(jsonObject.get("oldFood").getAsBoolean());
        classicPvP.oldFishingRod().set(jsonObject.get("oldFishing").getAsBoolean());
        classicPvP.oldSword().set(jsonObject.get("oldSword").getAsBoolean());
        classicPvP.oldHeadRotation().set(jsonObject.get("oldHeadRotation").getAsBoolean());
        classicPvP.oldBackwards().set(jsonObject.get("oldWalking").getAsBoolean());
        classicPvP.oldSneaking().set(jsonObject.get("oldSneaking").getAsBoolean());
        classicPvP.oldHeart().set(jsonObject.get("oldHearts").getAsBoolean());
        classicPvP.oldDamageColor().set(jsonObject.get("oldDamage").getAsBoolean());
        classicPvP.oldHitbox().set(jsonObject.get("oldHitbox").getAsBoolean());
    }
    
    private void convertOtherSettings(final JsonObject jsonObject) {
        final OtherConfig other = Laby.labyAPI().config().other();
        final DiscordConfig discord = other.discord();
        discord.enabled().set(jsonObject.get("discordRichPresence").getAsBoolean());
        discord.showServerAddress().set(jsonObject.get("discordShowIpAddress").getAsBoolean());
        other.window().borderlessWindow().set(jsonObject.get("borderlessWindow").getAsBoolean());
        other.anonymousStatistics().set(jsonObject.get("sendAnonymousStatistics").getAsBoolean());
    }
    
    private void convertAppearanceSettings(final JsonObject jsonObject) {
        final AppearanceConfig appearance = Laby.labyAPI().config().appearance();
        appearance.darkLoadingScreen().set(jsonObject.get("darkMode").getAsBoolean());
        appearance.fadeOutAnimation().set(jsonObject.get("fadeOut").getAsBoolean() ? FadeOutAnimationType.FADING : FadeOutAnimationType.INSTANT);
        appearance.hideMenuBackground().set(!jsonObject.get("guiBackground").getAsBoolean());
        appearance.cleanWindowTitle().set(jsonObject.get("cleanWindowTitle").getAsBoolean());
        appearance.titleScreen().quickPlay().set(jsonObject.get("quickPlay").getAsBoolean());
        appearance.replaceSkinCustomization().set(jsonObject.get("betterSkinCustomization").getAsBoolean());
    }
    
    private void convertIngameSettings(final JsonObject jsonObject) throws IOException {
        final IngameConfig ingame = Laby.labyAPI().config().ingame();
        final AdvancedChatConfig advancedChat = ingame.advancedChat();
        advancedChat.globalBackground().set(!jsonObject.get("fastChat").getAsBoolean());
        advancedChat.fadeInMessages().set(jsonObject.get("chatAnimation").getAsBoolean());
        advancedChat.globalChatLimit().set(jsonObject.get("chatLineLimit").getAsInt());
        final CosmeticsConfig cosmetics = ingame.cosmetics();
        if (jsonObject.get("capePriority").getAsString().equalsIgnoreCase("labymod")) {
            cosmetics.cloakPriority().set(CloakPriority.LABYMOD);
        }
        else {
            cosmetics.cloakPriority().set(CloakPriority.VANILLA);
        }
        cosmetics.renderCosmetics().set(jsonObject.get("cosmetics").getAsBoolean());
        cosmetics.hideCosmeticsInDistance().set(jsonObject.get("cosmeticsHideInDistance").getAsBoolean());
        ingame.emotes().emotes().set(jsonObject.get("emotes").getAsBoolean());
        ingame.showCapeParticles().set(jsonObject.get("capeOriginalParticles").getAsBoolean());
        ingame.showMyName().set(jsonObject.get("showMyName").getAsBoolean());
        ingame.disableSpeedFOV().set(!jsonObject.get("speedFov").getAsBoolean());
        this.convertZoomSettings(jsonObject, ingame);
    }
    
    private void convertZoomSettings(final JsonObject jsonObject, final IngameConfig ingame) throws IOException {
        final Map<String, String> optifineSettings = this.loadMinecraftSettings(Paths.get("optionsof.txt", new String[0]));
        int ofZoomKey = 0;
        try {
            ofZoomKey = Integer.parseInt(optifineSettings.getOrDefault("key_of.key.zoom", "0"));
        }
        catch (final NumberFormatException ex) {}
        final ZoomConfig zoom = ingame.zoom();
        if (ofZoomKey != 0) {
            zoom.zoomKey().set(KeyMapper.getKey(ofZoomKey));
            zoom.zoomHold().set(true);
            zoom.zoomCinematic().enabled().set(true);
            zoom.zoomCinematic().disableAfterTransition().set(false);
            zoom.shouldRenderFirstPersonHand().set(false);
            zoom.scrollToZoom().set(false);
            zoom.zoomDistance().set(4.0);
            zoom.zoomTransition().zoomInType().set(ZoomTransitionType.NO_TRANSITION);
            zoom.zoomTransition().zoomInDuration().set(0L);
            zoom.zoomTransition().zoomOutType().set(ZoomTransitionType.NO_TRANSITION);
            zoom.zoomTransition().zoomOutDuration().set(0L);
        }
        else if (jsonObject.has("zoom")) {
            zoom.zoomHold().set(jsonObject.get("zoomHoldKey").getAsBoolean());
            zoom.zoomCinematic().enabled().set(jsonObject.get("zoomCinematic").getAsBoolean());
            zoom.zoomCinematic().disableAfterTransition().reset();
            zoom.zoomDistance().set((double)jsonObject.get("zoomDivisor").getAsInt());
            if (jsonObject.get("zoom").getAsBoolean()) {
                zoom.zoomKey().set(this.getKey(jsonObject.get("keyZoom").getAsInt()));
            }
            else {
                zoom.zoomKey().set(Key.NONE);
            }
            final String rawTransitionType = jsonObject.get("zoomTransition").getAsString();
            ZoomTransitionType transitionType;
            if (rawTransitionType.equals("smooth")) {
                transitionType = ZoomTransitionType.EASE_IN_OUT;
            }
            else if (rawTransitionType.equals("linear")) {
                transitionType = ZoomTransitionType.LINEAR;
            }
            else {
                transitionType = ZoomTransitionType.NO_TRANSITION;
            }
            zoom.zoomTransition().zoomInType().set(transitionType);
            zoom.zoomTransition().zoomOutType().set(transitionType);
            zoom.zoomTransition().zoomInDuration().reset();
            zoom.zoomTransition().zoomOutDuration().reset();
        }
        else {
            zoom.zoomKey().set(Key.NONE);
        }
    }
    
    private void convertHotkeySettings(final JsonObject jsonObject) {
        final HotkeysConfig hotkeys = Laby.labyAPI().config().hotkeys();
        hotkeys.emoteWheelKey().set(this.getKey(jsonObject.get("keyEmote").getAsInt()));
        hotkeys.widgetEditorKey().set(this.getKey(jsonObject.get("keyModuleEditor").getAsInt()));
        hotkeys.markerKey().set(this.getKey(jsonObject.get("keyMarker").getAsInt()));
        Key keyPlayerMenu = this.getKey(jsonObject.get("keyPlayerMenu").getAsInt());
        if (keyPlayerMenu == Key.NONE) {
            keyPlayerMenu = MouseButton.MIDDLE;
        }
        hotkeys.interactionMenuKey().set(keyPlayerMenu);
    }
}
