// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.filter;

import net.labymod.api.event.Subscribe;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.core.main.LabyMod;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.event.client.chat.advanced.AdvancedChatTabMessageEvent;
import net.labymod.api.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.regex.PatternSyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.chat.ChatMessage;
import javax.inject.Inject;
import java.util.ArrayList;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.chat.filter.ChatFilter;
import java.util.List;
import net.labymod.api.client.resources.sound.MinecraftSounds;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.chat.filter.FilterChatService;

@Singleton
@Implements(FilterChatService.class)
public class DefaultFilterChatService implements FilterChatService
{
    private final MinecraftSounds sounds;
    private final List<ChatFilter> matchingChatFilters;
    
    @Inject
    public DefaultFilterChatService(final EventBus eventBus, final MinecraftSounds sounds) {
        this.matchingChatFilters = new ArrayList<ChatFilter>();
        this.sounds = sounds;
        eventBus.registerListener(this);
    }
    
    @Override
    public ChatFilter filter(final List<ChatFilter> filters, final ChatMessage chatMessage) {
        if (filters.isEmpty()) {
            return null;
        }
        this.refreshMatchingFilters(filters, chatMessage);
        if (this.matchingChatFilters.isEmpty()) {
            return null;
        }
        return this.matchingChatFilters.get(0);
    }
    
    private void refreshMatchingFilters(final List<ChatFilter> filters, final ChatMessage chatMessage) {
        ThreadSafe.ensureRenderThread();
        this.matchingChatFilters.clear();
        final String message = chatMessage.getPlainText();
        final String messageJson = chatMessage.getSerializedText();
        for (final ChatFilter filter : filters) {
            boolean filtered = false;
            final boolean advanced = filter.advanced().get();
            if (advanced) {
                if (this.matchesRegEx(message, messageJson, filter, filter.includeRegEx().get())) {
                    filtered = true;
                }
            }
            else {
                for (final TagInputWidget.TagCollection.Tag tag : filter.getIncludedTags().getTags()) {
                    final String content = tag.getContent();
                    if (this.searchNormal(filter, message, content) || this.searchTooltip(filter, messageJson, content)) {
                        filtered = true;
                        break;
                    }
                }
            }
            if (filtered) {
                if (advanced) {
                    if (this.matchesRegEx(message, messageJson, filter, filter.excludeRegEx().get())) {
                        filtered = false;
                    }
                }
                else {
                    for (final TagInputWidget.TagCollection.Tag tag : filter.getExcludedTags().getTags()) {
                        final String content = tag.getContent();
                        if (this.searchNormal(filter, message, content) || this.searchTooltip(filter, messageJson, content)) {
                            filtered = false;
                            break;
                        }
                    }
                }
            }
            if (!filtered) {
                continue;
            }
            this.matchingChatFilters.add(filter);
        }
    }
    
    private boolean matchesRegEx(final String message, final String messageJson, final ChatFilter filter, final String regex) {
        if (regex == null || regex.isEmpty() || messageJson == null) {
            return false;
        }
        final Pattern compile = this.compile(filter.caseSensitive().get(), regex);
        if (compile == null) {
            return false;
        }
        final Matcher messageMatcher = compile.matcher(message);
        final Matcher messageJsonMatcher = compile.matcher(messageJson);
        return messageMatcher.matches() || (filter.shouldFilterTooltip().get() && messageJsonMatcher.matches());
    }
    
    @Nullable
    private Pattern compile(final boolean caseSensitive, final String regEx) {
        try {
            return caseSensitive ? Pattern.compile(regEx, 2) : Pattern.compile(regEx);
        }
        catch (final PatternSyntaxException exception) {
            return null;
        }
    }
    
    private boolean searchNormal(@NotNull final ChatFilter filter, final String message, final String content) {
        return filter.caseSensitive().get() ? message.contains(content) : StringUtil.containsIgnoreCase(message, content);
    }
    
    private boolean searchTooltip(@NotNull final ChatFilter filter, final String messageJson, final String content) {
        return messageJson != null && filter.shouldFilterTooltip().get() && (filter.caseSensitive().get() ? messageJson.contains(content) : StringUtil.containsIgnoreCase(messageJson, content));
    }
    
    @Subscribe(64)
    public void applyChatFilter(final AdvancedChatTabMessageEvent event) {
        final AdvancedChatMessage message = event.message();
        final ChatMessage chatMessage = message.chatMessage();
        final List<ChatFilter> tabFilters = event.tab().config().filters().get();
        final boolean noFilters = tabFilters.isEmpty();
        if (!noFilters) {
            this.refreshMatchingFilters(tabFilters, chatMessage);
        }
        if (noFilters || this.matchingChatFilters.isEmpty()) {
            if (noFilters || event.tab().rootConfig().type().get() == RootChatTabConfig.Type.SERVER) {
                for (final ChatWindow window : LabyMod.references().advancedChatController().getWindows()) {
                    for (final ChatTab tab : window.getTabs()) {
                        final GeneralChatTabConfig config = tab.config();
                        if (this.filter(config.filters().get(), chatMessage) != null) {
                            event.setCancelled(true);
                            break;
                        }
                    }
                }
            }
            else {
                event.setCancelled(true);
            }
            return;
        }
        boolean playedSound = false;
        boolean changedBackground = false;
        for (final ChatFilter filter : this.matchingChatFilters) {
            if (filter.shouldHideMessage().get()) {
                event.setCancelled(true);
                return;
            }
            if (!playedSound && filter.shouldPlaySound().get()) {
                playedSound = true;
                this.sounds.playChatFilterSound();
            }
            if (changedBackground || !filter.shouldChangeBackground().get()) {
                continue;
            }
            changedBackground = true;
            message.metadata().set("custom_background", filter.backgroundColor().get());
        }
    }
}
