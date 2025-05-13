// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.models;

import java.util.regex.Matcher;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.Nullable;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import org.intellij.lang.annotations.Language;
import java.util.HashMap;
import java.util.Collection;
import java.util.regex.Pattern;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import net.labymod.api.client.render.font.ComponentRenderer;

public class ServerChat
{
    private static final ComponentRenderer COMPONENT_RENDERER;
    @SerializedName("events")
    private Map<String, String[]> eventMessages;
    private Map<String, String> outgoing;
    @SerializedName("message_formats")
    private String[] messageFormats;
    private transient Pattern[] parsedMessageFormats;
    private transient Collection<EventMessage> parsedEventMessages;
    
    public ServerChat() {
        this.eventMessages = new HashMap<String, String[]>();
        this.outgoing = new HashMap<String, String>();
    }
    
    public boolean hasDataMessage(final String type) {
        return this.eventMessages != null && this.eventMessages.containsKey(type);
    }
    
    public void addEventMessage(final String type, @Language("RegExp") final String[] patterns) {
        if (this.eventMessages == null) {
            this.eventMessages = new HashMap<String, String[]>();
        }
        this.eventMessages.put(type, patterns);
        this.parsedEventMessages = null;
    }
    
    @NotNull
    public Collection<EventMessage> getEventMessages() {
        return (Collection<EventMessage>)(this.parseMessages() ? this.parsedEventMessages : Collections.emptyList());
    }
    
    @NotNull
    public Collection<EventMessage> getEventMessages(final String type) {
        if (!this.parseMessages()) {
            return (Collection<EventMessage>)Collections.emptyList();
        }
        return this.parsedEventMessages.stream().filter(message -> message.type().equals(type)).collect((Collector<? super EventMessage, ?, Collection<EventMessage>>)Collectors.toList());
    }
    
    @NotNull
    public Optional<EventMessage> findEventMessage(final String rawMessage) {
        if (!this.parseMessages()) {
            return Optional.empty();
        }
        for (final EventMessage eventMessage : this.parsedEventMessages) {
            if (eventMessage.pattern().matcher(rawMessage).matches()) {
                return Optional.of(eventMessage);
            }
        }
        return Optional.empty();
    }
    
    private boolean parseMessages() {
        if (this.eventMessages == null || this.eventMessages.isEmpty()) {
            return false;
        }
        if (this.parsedEventMessages == null) {
            this.parsedEventMessages = new HashSet<EventMessage>();
            this.eventMessages.forEach((type, patterns) -> {
                int i = 0;
                for (int length = patterns.length; i < length; ++i) {
                    final String dataMessage = patterns[i];
                    final String rawPattern = dataMessage.replace("%player%", "(.{2,16})");
                    Supplier<Pattern> patternSupplier;
                    if (rawPattern.contains("%self%")) {
                        patternSupplier = (() -> Pattern.compile(rawPattern.replace("%self%", Laby.labyAPI().getName())));
                    }
                    else {
                        final Pattern pattern = Pattern.compile(rawPattern);
                        patternSupplier = (() -> pattern);
                    }
                    this.parsedEventMessages.add(new EventMessage(patternSupplier, type));
                }
                return;
            });
        }
        return true;
    }
    
    public Map<String, String> getOutgoing() {
        return this.outgoing;
    }
    
    @Nullable
    public Pattern[] getMessageFormats() {
        if (this.messageFormats != null && this.parsedMessageFormats == null) {
            this.parsedMessageFormats = new Pattern[this.messageFormats.length];
            for (int i = 0; i < this.messageFormats.length; ++i) {
                this.parsedMessageFormats[i] = Pattern.compile(this.messageFormats[i]);
            }
        }
        return this.parsedMessageFormats;
    }
    
    @Nullable
    public ParsedMessage parseMessage(@NotNull final Component message) {
        return this.parseMessage(ServerChat.COMPONENT_RENDERER.legacySectionSerializer().serialize(message));
    }
    
    @Nullable
    public ParsedMessage parseMessage(@NotNull final String message) {
        final Pattern[] formats = this.getMessageFormats();
        if (formats == null) {
            return null;
        }
        for (final Pattern pattern : formats) {
            final Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                return new ParsedMessage(ParsedMessage.getRegexGroup(matcher, "rank"), ParsedMessage.getRegexGroup(matcher, "sender"), ParsedMessage.getRegexGroup(matcher, "message"), matcher);
            }
        }
        return null;
    }
    
    static {
        COMPONENT_RENDERER = Laby.references().componentRenderer();
    }
    
    public static class EventMessage
    {
        private final Supplier<Pattern> patternSupplier;
        private final String type;
        
        private EventMessage(final Supplier<Pattern> patternSupplier, final String type) {
            this.patternSupplier = patternSupplier;
            this.type = type;
        }
        
        public Pattern pattern() {
            return this.patternSupplier.get();
        }
        
        public String type() {
            return this.type;
        }
    }
    
    public static class ParsedMessage
    {
        private final String rank;
        private final String sender;
        private final String message;
        private final Matcher matcher;
        
        private ParsedMessage(final String rank, final String sender, final String message, final Matcher matcher) {
            this.rank = rank;
            this.sender = sender;
            this.message = message;
            this.matcher = matcher;
        }
        
        @Nullable
        public String getRank() {
            return this.rank;
        }
        
        @Nullable
        public String getSender() {
            return this.sender;
        }
        
        @Nullable
        public String getMessage() {
            return this.message;
        }
        
        @Nullable
        public String getCustomValue(@NotNull final String groupName) {
            return getRegexGroup(this.matcher, groupName);
        }
        
        @Nullable
        private static String getRegexGroup(@NotNull final Matcher matcher, @NotNull final String groupName) {
            try {
                return matcher.group(groupName);
            }
            catch (final IllegalArgumentException ignored) {
                return null;
            }
        }
    }
}
