// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.converter;

import net.labymod.api.client.gui.hud.position.HorizontalHudWidgetAlignment;
import net.labymod.api.util.bounds.area.RectangleAreaPosition;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import java.util.Iterator;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.hudwidget.text.Formatting;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import com.google.gson.JsonElement;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;

public class LegacyModuleConverter extends LegacyConverter<JsonObject>
{
    private static final JsonPrimitive INGAME;
    private final Map<String, LegacyModuleEntry> legacyModules;
    
    protected LegacyModuleConverter() {
        super("modules.json", JsonObject.class);
        this.legacyModules = new HashMap<String, LegacyModuleEntry>();
    }
    
    protected void registerModule(@NotNull final String legacyModuleName, @Nullable final String hudWidgetId, @Nullable final BiConsumer<Map<String, String>, HudWidgetConfig> attributesConfigFiller) {
        this.legacyModules.put(legacyModuleName, new LegacyModuleEntry(legacyModuleName, hudWidgetId, (module, attributes, config) -> {
            if (attributesConfigFiller != null) {
                attributesConfigFiller.accept(attributes, config);
            }
        }));
    }
    
    protected void registerModule(@NotNull final String legacyModuleName, @NotNull final String hudWidgetId) {
        this.registerModule(legacyModuleName, hudWidgetId, null);
    }
    
    protected void registerModule(@NotNull final String legacyModuleName, @NotNull final BiConsumer<JsonObject, Map<String, String>> attributesConsumer) {
        this.legacyModules.put(legacyModuleName, new LegacyModuleEntry(legacyModuleName, null, (module, attributes, config) -> attributesConsumer.accept(module, attributes)));
    }
    
    @Override
    protected void convert(final JsonObject jsonObject) throws Exception {
        final JsonObject modules = jsonObject.getAsJsonObject("modules");
        for (final LegacyModuleEntry entry : this.legacyModules.values()) {
            final String legacyModuleName = entry.legacyModuleName;
            final JsonObject object = modules.getAsJsonObject(legacyModuleName);
            if (object == null) {
                continue;
            }
            final Map<String, String> attributes = new HashMap<String, String>();
            for (final Map.Entry<String, JsonElement> attr : object.getAsJsonObject("attributes").entrySet()) {
                attributes.put(attr.getKey(), attr.getValue().getAsString());
            }
            final HudWidget<?> hudWidget = entry.getHudWidget();
            if (hudWidget == null) {
                entry.configFiller.accept(object, attributes, null);
            }
            else {
                final HudWidgetConfig config = (HudWidgetConfig)hudWidget.getConfig();
                final String rawRegion = object.getAsJsonArray("regions").get(0).getAsString();
                final String rawAlignment = object.getAsJsonArray("alignment").get(0).getAsString();
                final float x = object.getAsJsonArray("x").get(0).getAsFloat();
                final float y = object.getAsJsonArray("y").get(0).getAsFloat();
                config.setEnabled(object.getAsJsonArray("enabled").contains((JsonElement)LegacyModuleConverter.INGAME));
                config.setAreaIdentifier(this.mapRegion(rawRegion));
                config.horizontalAlignment().set(this.mapAlignment(rawAlignment));
                config.setX(x);
                config.setY(y);
                config.setParentId(this.findParentHudWidgetIdRecursive(modules, object));
                config.scale().set(object.get("scale").getAsInt() / 100.0f);
                final HudWidgetDropzone dropzone = this.getDropzone(legacyModuleName, attributes);
                config.setDropzoneId((dropzone != null) ? dropzone.getId() : null);
                config.useGlobal().set(false);
                if (config instanceof final TextHudWidgetConfig textConfig) {
                    if (attributes.containsKey("customKey")) {
                        textConfig.customLabel().set(attributes.get("customKey"));
                    }
                    if (attributes.containsKey("prefixColor")) {
                        textConfig.labelColor().set(Color.of(Integer.parseInt(attributes.get("prefixColor"))));
                    }
                    if (attributes.containsKey("bracketsColor")) {
                        textConfig.bracketColor().set(Color.of(Integer.parseInt(attributes.get("bracketsColor"))));
                    }
                    if (attributes.containsKey("valueColor")) {
                        textConfig.valueColor().set(Color.of(Integer.parseInt(attributes.get("valueColor"))));
                    }
                    if (attributes.containsKey("formatting")) {
                        Formatting formatting = null;
                        switch (Integer.parseInt(attributes.get("formatting"))) {
                            case 1: {
                                formatting = Formatting.COLON;
                                break;
                            }
                            case 2: {
                                formatting = Formatting.BRACKETS;
                                break;
                            }
                            case 4: {
                                formatting = Formatting.HYPHEN;
                                break;
                            }
                            default: {
                                formatting = Formatting.SQUARE_BRACKETS;
                                break;
                            }
                        }
                        textConfig.formatting().set(formatting);
                    }
                }
                entry.configFiller.accept(object, attributes, config);
                config.useGlobal().set(!object.get("useExtendedSettings").getAsBoolean());
            }
        }
        Laby.references().hudWidgetRegistry().saveConfig();
        Laby.references().hudWidgetRegistry().updateHudWidgets("config_converter");
    }
    
    private HudWidgetDropzone getDropzone(final String legacyModuleName, final Map<String, String> attributes) {
        if (attributes.containsKey("itemSlot")) {
            switch (Integer.parseInt(attributes.get("itemSlot"))) {
                case 1: {
                    return NamedHudWidgetDropzones.ITEM_TOP_LEFT;
                }
                case 2: {
                    return NamedHudWidgetDropzones.ITEM_MIDDLE_LEFT;
                }
                case 3: {
                    return NamedHudWidgetDropzones.ITEM_BOTTOM_LEFT;
                }
                case 4: {
                    return NamedHudWidgetDropzones.ITEM_TOP_RIGHT;
                }
                case 5: {
                    return NamedHudWidgetDropzones.ITEM_MIDDLE_RIGHT;
                }
                case 6: {
                    return NamedHudWidgetDropzones.ITEM_BOTTOM_RIGHT;
                }
                default: {
                    return null;
                }
            }
        }
        else {
            if (!legacyModuleName.equals("ScoreboardModule") || !attributes.containsKey("slot")) {
                return null;
            }
            switch (Integer.parseInt(attributes.get("slot"))) {
                case 1: {
                    return NamedHudWidgetDropzones.SCOREBOARD_LEFT;
                }
                default: {
                    return NamedHudWidgetDropzones.SCOREBOARD_RIGHT;
                }
            }
        }
    }
    
    private String findParentHudWidgetIdRecursive(@NotNull final JsonObject modules, @NotNull final JsonObject currentModule) {
        final JsonElement listedAfter = currentModule.get("listedAfter");
        if (listedAfter == null || !listedAfter.isJsonPrimitive()) {
            return null;
        }
        final String parentLegacyName = listedAfter.getAsString();
        final String globalHudWidgetId = this.findGlobalHudWidgetId(parentLegacyName);
        if (globalHudWidgetId != null) {
            return globalHudWidgetId;
        }
        final JsonObject parentModule = modules.getAsJsonObject(parentLegacyName);
        if (parentModule != null) {
            return this.findParentHudWidgetIdRecursive(modules, parentModule);
        }
        return null;
    }
    
    protected String findGlobalHudWidgetId(@NotNull final String legacyModuleName) {
        for (final LegacyConverter<?> converter : Laby.references().legacyConfigConverter().getConverters()) {
            if (!(converter instanceof LegacyModuleConverter)) {
                continue;
            }
            final LegacyModuleEntry entry = ((LegacyModuleConverter)converter).legacyModules.get(legacyModuleName);
            if (entry != null) {
                return entry.hudWidgetId;
            }
        }
        return null;
    }
    
    private RectangleAreaPosition mapRegion(final String region) {
        switch (region) {
            case "BOTTOM_LEFT": {
                return RectangleAreaPosition.BOTTOM_LEFT;
            }
            case "BOTTOM_CENTER": {
                return RectangleAreaPosition.BOTTOM_CENTER;
            }
            case "BOTTOM_RIGHT": {
                return RectangleAreaPosition.BOTTOM_RIGHT;
            }
            case "TOP_LEFT": {
                return RectangleAreaPosition.TOP_LEFT;
            }
            case "TOP_CENTER": {
                return RectangleAreaPosition.TOP_CENTER;
            }
            case "TOP_RIGHT": {
                return RectangleAreaPosition.TOP_RIGHT;
            }
            case "CENTER_LEFT": {
                return RectangleAreaPosition.MIDDLE_LEFT;
            }
            case "CENTER": {
                return RectangleAreaPosition.MIDDLE_CENTER;
            }
            case "CENTER_RIGHT": {
                return RectangleAreaPosition.MIDDLE_RIGHT;
            }
            default: {
                return RectangleAreaPosition.TOP_LEFT;
            }
        }
    }
    
    private HorizontalHudWidgetAlignment mapAlignment(final String alignment) {
        switch (alignment) {
            case "LEFT": {
                return HorizontalHudWidgetAlignment.LEFT;
            }
            case "RIGHT": {
                return HorizontalHudWidgetAlignment.RIGHT;
            }
            default: {
                return HorizontalHudWidgetAlignment.AUTO;
            }
        }
    }
    
    @Override
    public boolean hasStuffToConvert() {
        if (this.getValue() == null) {
            return false;
        }
        final JsonObject modules = this.getValue().getAsJsonObject("modules");
        if (modules == null) {
            return false;
        }
        for (final String legacyModuleName : this.legacyModules.keySet()) {
            if (modules.has(legacyModuleName)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        INGAME = new JsonPrimitive("INGAME");
    }
    
    private class LegacyModuleEntry
    {
        private final String legacyModuleName;
        private final String hudWidgetId;
        private final LegacyModuleConsumer configFiller;
        
        private LegacyModuleEntry(final LegacyModuleConverter legacyModuleConverter, final String legacyModuleName, final String hudWidgetId, final LegacyModuleConsumer configFiller) {
            this.legacyModuleName = legacyModuleName;
            this.hudWidgetId = hudWidgetId;
            this.configFiller = configFiller;
        }
        
        private HudWidget<?> getHudWidget() {
            if (this.hudWidgetId == null) {
                return null;
            }
            final HudWidget<?> hudWidget = Laby.references().hudWidgetRegistry().getById(this.hudWidgetId);
            if (hudWidget == null) {
                throw new IllegalArgumentException("Unknown hud widget: " + this.hudWidgetId);
            }
            return hudWidget;
        }
    }
    
    private interface LegacyModuleConsumer
    {
        void accept(@NotNull final JsonObject p0, @NotNull final Map<String, String> p1, @Nullable final HudWidgetConfig p2);
    }
}
