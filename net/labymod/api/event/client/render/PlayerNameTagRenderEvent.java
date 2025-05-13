// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render;

import net.labymod.api.client.component.format.NamedTextColor;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class PlayerNameTagRenderEvent extends DefaultCancellable implements Event
{
    public static final TextComponent EDITED_COMPONENT;
    private final Context context;
    private final NetworkPlayerInfo playerInfo;
    private final TagType tagType;
    private Component nameTagComponent;
    
    public PlayerNameTagRenderEvent(@NotNull final Context context, @Nullable final NetworkPlayerInfo playerInfo, @NotNull final Component nameTagComponent, @NotNull final TagType tagType) {
        this.context = context;
        this.playerInfo = playerInfo;
        this.nameTagComponent = nameTagComponent;
        this.tagType = tagType;
    }
    
    @NotNull
    public Context context() {
        return this.context;
    }
    
    @Nullable
    public NetworkPlayerInfo getPlayerInfo() {
        return this.playerInfo;
    }
    
    @Deprecated(forRemoval = true, since = "4.2.26")
    @Nullable
    public NetworkPlayerInfo playerInfo() {
        return this.playerInfo;
    }
    
    @NotNull
    public Component nameTag() {
        return this.nameTagComponent;
    }
    
    public TagType tagType() {
        return this.tagType;
    }
    
    public void setNameTag(@NotNull final Component nameTagComponent) {
        this.nameTagComponent = nameTagComponent;
    }
    
    static {
        EDITED_COMPONENT = Component.text(" \u270e", NamedTextColor.YELLOW);
    }
    
    public enum Context
    {
        PLAYER_RENDER, 
        TAB_LIST;
    }
}
