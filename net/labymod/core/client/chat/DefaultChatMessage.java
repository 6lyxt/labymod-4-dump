// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat;

import net.labymod.api.models.Implements;
import net.labymod.core.main.LabyMod;
import java.util.function.Consumer;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import java.util.Objects;
import net.labymod.api.client.component.IconComponent;
import java.util.Collection;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ChatMessageUpdateEvent;
import net.labymod.api.util.ThreadSafe;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.Laby;
import net.labymod.api.event.client.chat.ChatMessageGuessSenderEvent;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.chat.ChatTrustLevel;
import net.labymod.api.client.options.ChatVisibility;
import java.util.UUID;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.chat.ChatController;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.chat.ChatMessage;

public class DefaultChatMessage implements ChatMessage
{
    private static final Logging LOGGER;
    private static final InternalChatModifier CHAT_MODIFIER;
    private static final ChatController CONTROLLER;
    private static final ComponentRenderer COMPONENT_RENDERER;
    private final UUID messageId;
    private final long timestamp;
    private final ChatVisibility visibility;
    private final ChatTrustLevel chatTrustLevel;
    private final Component originalComponent;
    private String originalPlainText;
    private String originalFormattedText;
    private long lastModifiedTimestamp;
    private Component component;
    private String plainText;
    private Boolean containsIcon;
    private String formattedText;
    private boolean triedSerialization;
    private String serializedText;
    private final UUID senderUniqueId;
    private final GameProfile senderProfile;
    private boolean messageSent;
    private Metadata metadata;
    
    public DefaultChatMessage(final UUID messageId, final long timestamp, final Component originalComponent, final Component component, final ChatVisibility visibility, final ChatTrustLevel chatTrustLevel) {
        this(messageId, timestamp, originalComponent, component, visibility, chatTrustLevel, null);
    }
    
    public DefaultChatMessage(final UUID messageId, final long timestamp, final Component originalComponent, final Component component, final ChatVisibility visibility, final ChatTrustLevel chatTrustLevel, final UUID senderUniqueId) {
        this.lastModifiedTimestamp = -1L;
        this.metadata = Metadata.create();
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.originalComponent = originalComponent;
        this.component = component;
        this.visibility = visibility;
        this.chatTrustLevel = chatTrustLevel;
        final ChatMessageGuessSenderEvent event = Laby.fireEvent(new ChatMessageGuessSenderEvent(originalComponent, this.getPlainText(), senderUniqueId));
        this.senderUniqueId = event.getSenderUniqueId();
        this.senderProfile = event.getSenderProfile();
    }
    
    public DefaultChatMessage(final UUID messageId, final long timestamp, final Component originalComponent, final Component component, final ChatVisibility visibility, final ChatTrustLevel chatTrustLevel, final UUID senderUniqueId, final GameProfile senderProfile) {
        this.lastModifiedTimestamp = -1L;
        this.metadata = Metadata.create();
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.originalComponent = originalComponent;
        this.component = component;
        this.visibility = visibility;
        this.chatTrustLevel = chatTrustLevel;
        this.senderUniqueId = senderUniqueId;
        this.senderProfile = senderProfile;
    }
    
    @NotNull
    @Override
    public ChatVisibility visibility() {
        return this.visibility;
    }
    
    @NotNull
    @Override
    public ChatTrustLevel trustLevel() {
        return this.chatTrustLevel;
    }
    
    @NotNull
    @Override
    public Component originalComponent() {
        return this.originalComponent;
    }
    
    @NotNull
    @Override
    public String getOriginalPlainText() {
        if (this.originalPlainText == null) {
            this.originalPlainText = this.loadPlainText(this.originalComponent, false);
        }
        return this.originalPlainText;
    }
    
    @NotNull
    @Override
    public String getOriginalFormattedText() {
        if (this.originalFormattedText == null) {
            this.originalFormattedText = this.loadFormattedText(this.originalComponent);
        }
        return this.originalFormattedText;
    }
    
    @NotNull
    @Override
    public Component component() {
        return this.component;
    }
    
    @NotNull
    @Override
    public String getPlainText() {
        if (this.plainText == null) {
            this.plainText = this.loadPlainText(this.component, true);
        }
        return this.plainText;
    }
    
    @Override
    public boolean containsIcon() {
        if (this.containsIcon == null) {
            this.plainText = this.loadPlainText(this.component, true);
        }
        return this.containsIcon;
    }
    
    @NotNull
    @Override
    public String getFormattedText() {
        if (this.formattedText == null) {
            this.formattedText = this.loadFormattedText(this.component);
        }
        return this.formattedText;
    }
    
    @Nullable
    @Override
    public String getSerializedText() {
        if (this.serializedText == null && !this.triedSerialization) {
            this.triedSerialization = true;
            try {
                this.serializedText = GsonComponentSerializer.gson().serialize(this.component);
            }
            catch (final Exception e) {
                DefaultChatMessage.LOGGER.warn("An error occurred while serializing the chat message. {}: {}", e.getClass().getSimpleName(), e.getMessage());
            }
        }
        return this.serializedText;
    }
    
    public UUID getCachedSender() {
        return this.senderUniqueId;
    }
    
    @Nullable
    @Override
    public GameProfile getSenderProfile() {
        return this.senderProfile;
    }
    
    @Nullable
    @Override
    public UUID getSenderUniqueId() {
        return this.senderUniqueId;
    }
    
    @Override
    public void edit(@NotNull final Component component) {
        if (!this.messageSent) {
            this.component = component;
            this.reset();
            return;
        }
        ThreadSafe.ensureRenderThread();
        final Component prevComponent = this.component;
        if (Laby.fireEvent(new ChatMessageUpdateEvent(Phase.PRE, this, prevComponent, component)).isCancelled()) {
            return;
        }
        this.component = component;
        this.reset();
        this.containsIcon = null;
        this.lastModifiedTimestamp = TimeUtil.getMillis();
        DefaultChatMessage.CHAT_MODIFIER.editMessage(this, component);
        Laby.fireEvent(new ChatMessageUpdateEvent(Phase.POST, this, prevComponent, component));
    }
    
    private void reset() {
        this.formattedText = null;
        this.plainText = null;
        this.serializedText = null;
        this.triedSerialization = false;
    }
    
    @Override
    public void delete() {
        if (!this.messageSent) {
            throw new IllegalStateException("Cannot delete a message that has not been sent yet. In the ChatReceiveEvent, use event.setCancelled(true) instead");
        }
        if (this.wasDeleted()) {
            throw new IllegalStateException("This message has already been deleted");
        }
        ThreadSafe.ensureRenderThread();
        final Component prevComponent = this.component;
        if (Laby.fireEvent(new ChatMessageUpdateEvent(Phase.PRE, this, prevComponent, null)).isCancelled()) {
            return;
        }
        DefaultChatMessage.CHAT_MODIFIER.deleteMessage(this);
        DefaultChatMessage.CONTROLLER.getMessages().remove(this);
        Laby.fireEvent(new ChatMessageUpdateEvent(Phase.POST, this, prevComponent, null));
    }
    
    @Override
    public long timestamp() {
        return this.timestamp;
    }
    
    @Override
    public long lastModifiedTimestamp() {
        return this.lastModifiedTimestamp;
    }
    
    @NotNull
    @Override
    public UUID messageId() {
        return this.messageId;
    }
    
    @Override
    public boolean wasDeleted() {
        return CollectionHelper.noneMatch(DefaultChatMessage.CONTROLLER.getMessages(), m -> m.messageId().equals(this.messageId));
    }
    
    @Override
    public Metadata metadata() {
        return this.metadata;
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.metadata = metadata;
    }
    
    public void setMessageSent(final boolean messageSent) {
        this.messageSent = messageSent;
    }
    
    public DefaultChatMessage copy() {
        final DefaultChatMessage defaultChatMessage = new DefaultChatMessage(this.messageId, this.timestamp, this.originalComponent, this.component, this.visibility, this.chatTrustLevel, this.senderUniqueId, this.senderProfile);
        defaultChatMessage.originalFormattedText = this.originalFormattedText;
        defaultChatMessage.originalPlainText = this.originalPlainText;
        defaultChatMessage.formattedText = this.formattedText;
        defaultChatMessage.plainText = this.plainText;
        return defaultChatMessage;
    }
    
    private String loadPlainText(final Component component, final boolean loadIcon) {
        final ComponentFlattener flattener = Laby.references().componentRenderer().getColorStrippingFlattener();
        final StringBuilder builder = new StringBuilder();
        final ComponentFlattener componentFlattener = flattener;
        final Consumer<Component> componentConsumer = c -> {
            if (loadIcon && c instanceof IconComponent) {
                this.containsIcon = (boolean)(1 != 0);
            }
            return;
        };
        final StringBuilder obj = builder;
        Objects.requireNonNull(obj);
        componentFlattener.flatten(component, componentConsumer, obj::append);
        if (loadIcon && this.containsIcon == null) {
            this.containsIcon = false;
        }
        return builder.toString();
    }
    
    private String loadFormattedText(final Component component) {
        return DefaultChatMessage.COMPONENT_RENDERER.legacySectionSerializer().serialize(component);
    }
    
    static {
        LOGGER = Logging.create(ChatMessage.class);
        CHAT_MODIFIER = LabyMod.references().internalChatModifier();
        CONTROLLER = Laby.references().chatController();
        COMPONENT_RENDERER = Laby.references().componentRenderer();
    }
    
    @Implements(ChatMessage.Builder.class)
    public static class Builder extends ChatMessage.Builder
    {
        @Override
        public ChatMessage build() {
            Objects.requireNonNull(this.component, "Component cannot be null");
            Objects.requireNonNull(this.visibility, "ChatVisibility cannot be null");
            if (this.timestamp == -1L) {
                this.timestamp = TimeUtil.getMillis();
            }
            if (this.trustLevel == null) {
                this.trustLevel = ((this.visibility == ChatVisibility.COMMANDS_ONLY) ? ChatTrustLevel.SYSTEM : ChatTrustLevel.SECURE);
            }
            return new DefaultChatMessage(UUID.randomUUID(), this.timestamp, this.component, this.component, this.visibility, this.trustLevel, this.sender);
        }
    }
}
