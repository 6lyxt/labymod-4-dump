// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration;

import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import com.google.gson.JsonObject;
import net.labymod.api.configuration.converter.LegacyConverter;
import java.util.Collection;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.IngameConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.ZoomConfig;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.core.addon.DefaultAddonService;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.configuration.labymod.main.laby.MultiplayerConfig;
import net.labymod.api.event.labymod.config.ConfigurationVersionUpdateEvent;

public class ConfigurationVersionUpdateListener
{
    private static final String SHOW_CURRENT_SERVER_INFO_KEY = "showCurrentServerInfo";
    
    @Subscribe
    public void onConfigurationVersionUpdate(final ConfigurationVersionUpdateEvent event) {
        final Class<? extends Config> configClass = event.getConfigClass();
        final int usedVersion = event.getUsedVersion();
        if (MultiplayerConfig.class.isAssignableFrom(configClass)) {
            if (usedVersion == -1) {
                this.updateMultiplayerInitialVersion(event);
            }
            if (usedVersion <= 1) {
                this.updateMultiplayerVersionOneToTwo(event);
            }
        }
        if (RootChatTabConfig.class.isAssignableFrom(configClass) && usedVersion <= 1) {
            this.updateChatTabInitialVersion(event);
        }
        if (OtherConfig.class.isAssignableFrom(configClass) && usedVersion <= 1) {
            final Collection<LegacyConverter<?>> converters = Laby.references().legacyConfigConverter().getConverters();
            final JsonObject jsonObject = event.getJsonObject();
            final JsonArray jsonArray = new JsonArray();
            jsonArray.add((JsonElement)new JsonPrimitive("labymod"));
            for (final LoadedAddon loadedAddon : DefaultAddonService.getInstance().getLoadedAddons()) {
                jsonArray.add((JsonElement)new JsonPrimitive(loadedAddon.info().getNamespace()));
            }
            jsonObject.add("conversionAskedNamespaces", (JsonElement)jsonArray);
            event.setJsonObject(jsonObject);
        }
        if (AppearanceConfig.class.isAssignableFrom(configClass) && usedVersion <= 1) {
            this.updateAppearanceInitialVersion(event);
        }
        if (ZoomConfig.class.isAssignableFrom(configClass) && usedVersion <= 1) {
            this.updateZoomInitialVersion(event);
        }
        if (IngameConfig.class.isAssignableFrom(configClass) && usedVersion <= 1) {
            this.updateIngameInitialVersion(event);
        }
        if (LabyConfig.class.isAssignableFrom(configClass) && usedVersion <= 1) {
            this.updateLabyInitialVersion(event);
        }
        if (LabyConfig.class.isAssignableFrom(configClass) && usedVersion <= 2) {
            this.updateLabyTwoToThreeVersion(event);
        }
    }
    
    private void updateMultiplayerVersionOneToTwo(final ConfigurationVersionUpdateEvent event) {
        final JsonObject multiplayerConfig = (event.getEditedJsonObject() == null) ? event.getJsonObject() : event.getEditedJsonObject();
        final JsonObject tabListConfig = new JsonObject();
        if (multiplayerConfig.has("customPlayerList")) {
            tabListConfig.addProperty("enabled", Boolean.valueOf(multiplayerConfig.get("customPlayerList").getAsBoolean()));
        }
        if (multiplayerConfig.has("serverBanner")) {
            tabListConfig.addProperty("serverBanner", Boolean.valueOf(multiplayerConfig.get("serverBanner").getAsBoolean()));
        }
        if (multiplayerConfig.has("ping")) {
            final JsonObject pingConfig = multiplayerConfig.get("ping").getAsJsonObject();
            final JsonObject tabListPingConfig = new JsonObject();
            if (pingConfig.has("renderPing")) {
                tabListPingConfig.addProperty("exact", Boolean.valueOf(pingConfig.get("renderPing").getAsBoolean()));
            }
            if (pingConfig.has("exactPingColored")) {
                tabListPingConfig.addProperty("exactColored", Boolean.valueOf(pingConfig.get("exactPingColored").getAsBoolean()));
            }
            tabListConfig.add("ping", (JsonElement)tabListPingConfig);
        }
        if (multiplayerConfig.has("userIndicator")) {
            final JsonObject userIndicatorConfig = multiplayerConfig.get("userIndicator").getAsJsonObject();
            if (userIndicatorConfig.has("showUserIndicatorInPlayerList")) {
                tabListConfig.addProperty("labyModBadge", Boolean.valueOf(userIndicatorConfig.get("showUserIndicatorInPlayerList").getAsBoolean()));
            }
            if (userIndicatorConfig.has("showLabyModPlayerPercentageInTabList")) {
                tabListConfig.addProperty("labyModPercentage", Boolean.valueOf(userIndicatorConfig.get("showLabyModPlayerPercentageInTabList").getAsBoolean()));
            }
        }
        multiplayerConfig.add("tabList", (JsonElement)tabListConfig);
        event.setJsonObject(multiplayerConfig);
    }
    
    private void updateAppearanceInitialVersion(final ConfigurationVersionUpdateEvent event) {
        final JsonObject appearanceConfig = event.getJsonObject();
        final JsonObject customTitleScreenObject = new JsonObject();
        if (appearanceConfig.has("customTitleScreen")) {
            customTitleScreenObject.addProperty("enabled", Boolean.valueOf(appearanceConfig.get("customTitleScreen").getAsBoolean()));
            appearanceConfig.remove("customTitleScreen");
        }
        if (appearanceConfig.has("socialMediaLinks")) {
            customTitleScreenObject.addProperty("socialMediaLinks", appearanceConfig.get("socialMediaLinks").getAsString());
        }
        appearanceConfig.add("customTitleScreen", (JsonElement)customTitleScreenObject);
        event.setJsonObject(appearanceConfig);
    }
    
    private void updateZoomInitialVersion(final ConfigurationVersionUpdateEvent event) {
        final JsonObject zoomConfig = event.getJsonObject();
        if (!zoomConfig.has("zoomTransition") && zoomConfig.has("zoomInType") && zoomConfig.has("zoomInDuration") && zoomConfig.has("zoomOutType") && zoomConfig.has("zoomOutDuration")) {
            final JsonObject obj = new JsonObject();
            obj.addProperty("enabled", Boolean.valueOf(true));
            obj.addProperty("zoomInType", zoomConfig.get("zoomInType").getAsString());
            obj.addProperty("zoomInDuration", (Number)zoomConfig.get("zoomInDuration").getAsLong());
            obj.addProperty("zoomOutType", zoomConfig.get("zoomOutType").getAsString());
            obj.addProperty("zoomOutDuration", (Number)zoomConfig.get("zoomOutDuration").getAsLong());
            zoomConfig.add("zoomTransition", (JsonElement)obj);
        }
        event.setJsonObject(zoomConfig);
    }
    
    private void updateChatTabInitialVersion(final ConfigurationVersionUpdateEvent event) {
        final JsonObject jsonObject = event.getJsonObject();
        if (!jsonObject.has("config")) {
            return;
        }
        final JsonObject configObject = jsonObject.getAsJsonObject("config");
        if (!configObject.has("filters")) {
            return;
        }
        JsonObject generalConfig;
        if (configObject.has("general")) {
            generalConfig = configObject.getAsJsonObject("general");
        }
        else {
            generalConfig = new JsonObject();
            configObject.add("general", (JsonElement)generalConfig);
        }
        generalConfig.add("filters", configObject.get("filters"));
        final JsonObject copy = generalConfig.deepCopy();
        jsonObject.remove("config");
        jsonObject.add("config", (JsonElement)copy);
        event.setJsonObject(jsonObject);
    }
    
    private void updateMultiplayerInitialVersion(final ConfigurationVersionUpdateEvent event) {
        final JsonObject jsonObject = event.getJsonObject();
        if (jsonObject.has("showCurrentServerInfo") && jsonObject.get("showCurrentServerInfo").isJsonPrimitive()) {
            final JsonPrimitive showCurrentServerInfo = jsonObject.get("showCurrentServerInfo").getAsJsonPrimitive();
            if (showCurrentServerInfo.isBoolean()) {
                final boolean showCurrentServerInfoValue = showCurrentServerInfo.getAsBoolean();
                if (showCurrentServerInfoValue) {
                    jsonObject.addProperty("showCurrentServerInfo", MultiplayerConfig.ServerInfoPosition.BELOW_BUTTONS.name());
                }
                else {
                    jsonObject.addProperty("showCurrentServerInfo", MultiplayerConfig.ServerInfoPosition.DISABLED.name());
                }
                event.setJsonObject(jsonObject);
            }
        }
    }
    
    private void updateIngameInitialVersion(final ConfigurationVersionUpdateEvent event) {
        final JsonObject ingameConfig = event.getJsonObject();
        if (ingameConfig.has("performance")) {
            final JsonObject performanceConfig = ingameConfig.getAsJsonObject("performance");
            if (performanceConfig.has("fastWorldLoading") && !ingameConfig.has("fastWorldLoading")) {
                ingameConfig.addProperty("fastWorldLoading", Boolean.valueOf(performanceConfig.get("fastWorldLoading").getAsBoolean()));
            }
        }
        if (ingameConfig.has("vanilla")) {
            final JsonObject vanillaConfig = ingameConfig.getAsJsonObject("vanilla");
            if (vanillaConfig.has("clearTitles") && !ingameConfig.has("clearTitles")) {
                ingameConfig.addProperty("clearTitles", Boolean.valueOf(vanillaConfig.get("clearTitles").getAsBoolean()));
            }
        }
        event.setJsonObject(ingameConfig);
    }
    
    private void updateLabyInitialVersion(final ConfigurationVersionUpdateEvent event) {
        final JsonObject labyConfig = event.getJsonObject();
        if (labyConfig.has("ingame") && labyConfig.has("appearance")) {
            final JsonObject ingameConfig = labyConfig.getAsJsonObject("ingame");
            final JsonObject appearanceConfig = labyConfig.getAsJsonObject("appearance");
            if (ingameConfig.has("performance")) {
                final JsonObject performanceConfig = ingameConfig.getAsJsonObject("performance");
                if (performanceConfig.has("blurQuality") && !appearanceConfig.has("blurQuality")) {
                    appearanceConfig.addProperty("blurQuality", performanceConfig.get("blurQuality").getAsString());
                    event.setJsonObject(labyConfig);
                }
            }
        }
    }
    
    private void updateLabyTwoToThreeVersion(final ConfigurationVersionUpdateEvent event) {
        final JsonObject config = event.getJsonObject();
        this.migrate(config, "/ingame/clearTitles", "/multiplayer/clearTitles");
        this.migrate(config, "/ingame/replaceSkinCustomization", "/appearance/replaceSkinCustomization");
        this.migrate(config, "/appearance/blurQuality", "/ingame/motionBlur/blurQuality");
        this.migrate(config, "/other/borderlessWindow", "/other/window/borderlessWindow");
        this.migrate(config, "/other/restoreResolution", "/other/window/restoreWindowResolution");
        event.setJsonObject(config);
    }
    
    private void migrate(final JsonObject config, final String oldPath, final String newPath) {
        final JsonObject oldObject = this.getPath(config, oldPath);
        final JsonObject newObject = this.getPath(config, newPath);
        final String lastOldComponent = this.getEntryName(oldPath);
        final String lastNewComponent = this.getEntryName(newPath);
        final JsonElement oldElement = oldObject.get(lastOldComponent);
        if (oldElement == null) {
            return;
        }
        newObject.add(lastNewComponent, oldElement);
    }
    
    private JsonObject getPath(final JsonObject config, final String path) {
        final String[] split = path.split("/");
        JsonElement current = (JsonElement)config;
        for (int i = 0; i < split.length; ++i) {
            final String component = split[i];
            if (!component.isEmpty()) {
                if (i != split.length - 1) {
                    if (!current.isJsonObject()) {
                        throw new IllegalStateException("Expected JsonObject at " + path);
                    }
                    final JsonObject currentObject = current.getAsJsonObject();
                    if (!currentObject.has(component)) {
                        currentObject.add(component, (JsonElement)new JsonObject());
                    }
                    current = (JsonElement)currentObject.getAsJsonObject(component);
                }
            }
        }
        return current.getAsJsonObject();
    }
    
    private String getEntryName(final String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
