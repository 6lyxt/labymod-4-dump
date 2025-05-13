// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter.converter;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.configuration.labymod.chat.ChatConfigAccessor;
import java.util.Iterator;
import com.google.gson.JsonArray;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.core.client.chat.advanced.DefaultChatWindow;
import net.labymod.core.main.LabyMod;
import net.labymod.core.client.chat.advanced.DefaultAdvancedChatController;
import java.util.List;
import java.util.Map;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.filter.ChatFilter;
import java.util.HashMap;
import com.google.gson.JsonElement;
import net.labymod.core.configuration.converter.models.LegacyChatFilter;
import java.util.ArrayList;
import com.google.gson.JsonObject;
import net.labymod.api.configuration.converter.LegacyConverter;

public class LegacyChatFilterConverter extends LegacyConverter<JsonObject>
{
    private static final float DUMMY_WINDOW_WIDTH = 960.0f;
    private static final float DUMMY_WINDOW_HEIGHT = 540.0f;
    private static final float CHAT_WIDTH = 300.0f;
    private static final float CHAT_HEIGHT = 150.0f;
    private final LegacySettingConverter settingConverter;
    
    public LegacyChatFilterConverter(final LegacySettingConverter settingConverter) {
        super("filters.json", JsonObject.class);
        this.settingConverter = settingConverter;
    }
    
    @Override
    protected void convert(final JsonObject jsonObject) throws Exception {
        final JsonArray filters = jsonObject.getAsJsonArray("filters");
        final List<LegacyChatFilter> legacyChatFilters = new ArrayList<LegacyChatFilter>();
        for (final JsonElement chatFilter : filters) {
            legacyChatFilters.add(this.fromJson(chatFilter, (Class<? extends LegacyChatFilter>)LegacyChatFilter.class));
        }
        final Map<LegacyChatFilter, ChatFilter> legacyFilters = new HashMap<LegacyChatFilter, ChatFilter>();
        for (final LegacyChatFilter legacyChatFilter : legacyChatFilters) {
            final ChatFilter filter = legacyChatFilter.convert();
            if (filter != null) {
                legacyFilters.put(legacyChatFilter, filter);
            }
        }
        final ChatConfigAccessor chatConfig = Laby.labyAPI().chatProvider().chatConfigAccessor();
        final boolean hasSecondaryChat = legacyChatFilters.stream().anyMatch(LegacyChatFilter::shouldDisplayInSecondChat);
        final ChatWindowConfig mainChat = new ChatWindowConfig();
        ChatWindowConfig secondaryChat = null;
        if (hasSecondaryChat) {
            secondaryChat = new ChatWindowConfig(new RootChatTabConfig[] { new RootChatTabConfig(0, RootChatTabConfig.Type.CUSTOM, new GeneralChatTabConfig("Second chat")) });
        }
        for (final Map.Entry<LegacyChatFilter, ChatFilter> entry : legacyFilters.entrySet()) {
            final LegacyChatFilter legacyFilter = entry.getKey();
            final ChatFilter filter2 = entry.getValue();
            final ChatWindowConfig targetWindow = legacyFilter.shouldDisplayInSecondChat() ? secondaryChat : mainChat;
            targetWindow.getTabs().get(0).config().filters().get().add(filter2);
        }
        final JsonObject labySettings = this.settingConverter.getValue();
        if (labySettings != null) {
            this.convertChatSettings(labySettings, mainChat, secondaryChat);
        }
        chatConfig.getWindows().clear();
        final DefaultAdvancedChatController advancedChatController = (DefaultAdvancedChatController)LabyMod.references().advancedChatController();
        advancedChatController.getWindows().clear();
        advancedChatController.addWindow(new DefaultChatWindow(mainChat));
        if (hasSecondaryChat) {
            advancedChatController.addWindow(new DefaultChatWindow(secondaryChat));
        }
    }
    
    private void convertChatSettings(final JsonObject labySettings, final ChatWindowConfig mainChat, final ChatWindowConfig secondaryChat) {
        final boolean swapPositions = false;
        final ChatWindowConfig leftChat = swapPositions ? secondaryChat : mainChat;
        final ChatWindowConfig rightChat = swapPositions ? mainChat : secondaryChat;
        final Bounds window = new PositionedBounds(0.0f, 0.0f, 960.0f, 540.0f);
        if (leftChat != null) {
            leftChat.setPosition(window, Rectangle.relative(0.0f, 390.0f, 300.0f, 150.0f));
        }
        if (rightChat != null) {
            final float width = labySettings.get("secondChatWidth").getAsFloat();
            final float height = labySettings.get("secondChatHeight").getAsFloat();
            rightChat.setPosition(window, Rectangle.relative(960.0f - width, 540.0f - height, width, height));
        }
    }
    
    @Override
    public boolean hasStuffToConvert() {
        return this.getValue() != null && this.getValue().getAsJsonArray("filters").size() != 0;
    }
}
