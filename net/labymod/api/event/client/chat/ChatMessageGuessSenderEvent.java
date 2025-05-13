// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.chat;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.mojang.GameProfile;
import java.util.UUID;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.Event;

public class ChatMessageGuessSenderEvent implements Event
{
    private final Component component;
    private final String message;
    private UUID senderUniqueId;
    private GameProfile senderProfile;
    
    public ChatMessageGuessSenderEvent(@NotNull final Component component, final String message, final UUID senderUniqueId) {
        this.component = component;
        this.message = message;
        this.senderUniqueId = senderUniqueId;
    }
    
    @NotNull
    public Component component() {
        return this.component;
    }
    
    @NotNull
    public String getMessage() {
        return this.message;
    }
    
    @Nullable
    public UUID getSenderUniqueId() {
        return this.senderUniqueId;
    }
    
    @Nullable
    public GameProfile getSenderProfile() {
        return this.senderProfile;
    }
    
    public void setSenderProfile(final GameProfile senderProfile) {
        this.senderProfile = senderProfile;
        this.senderUniqueId = senderProfile.getUniqueId();
    }
}
