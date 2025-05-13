// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat;

import net.labymod.api.reference.annotation.Referenceable;
import java.util.UUID;
import net.labymod.api.mojang.GameProfile;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.options.ChatVisibility;
import net.labymod.api.Laby;
import net.labymod.api.metadata.MetadataExtension;

public interface ChatMessage extends MetadataExtension
{
    default Builder builder() {
        return Laby.references().chatMessageBuilder();
    }
    
    @NotNull
    ChatVisibility visibility();
    
    @NotNull
    ChatTrustLevel trustLevel();
    
    @NotNull
    Component originalComponent();
    
    @NotNull
    String getOriginalPlainText();
    
    @NotNull
    String getOriginalFormattedText();
    
    @NotNull
    Component component();
    
    @NotNull
    String getPlainText();
    
    boolean containsIcon();
    
    @NotNull
    String getFormattedText();
    
    @Nullable
    String getSerializedText();
    
    @Nullable
    GameProfile getSenderProfile();
    
    @Nullable
    UUID getSenderUniqueId();
    
    void edit(@NotNull final Component p0);
    
    void delete();
    
    long timestamp();
    
    long lastModifiedTimestamp();
    
    @NotNull
    UUID messageId();
    
    boolean wasDeleted();
    
    @Referenceable
    public abstract static class Builder
    {
        protected Component component;
        protected ChatVisibility visibility;
        protected ChatTrustLevel trustLevel;
        protected UUID sender;
        protected long timestamp;
        
        protected Builder() {
            this.timestamp = -1L;
        }
        
        public Builder component(final Component component) {
            this.component = component;
            return this;
        }
        
        public Builder timestamp(final long timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        
        public Builder visibility(final ChatVisibility visibility) {
            this.visibility = visibility;
            return this;
        }
        
        public Builder trustLevel(final ChatTrustLevel trustLevel) {
            this.trustLevel = trustLevel;
            return this;
        }
        
        public Builder sender(final UUID sender) {
            this.sender = sender;
            return this;
        }
        
        public abstract ChatMessage build();
    }
}
