// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.chat;

import java.util.List;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.chat.ChatTrustLevel;
import net.labymod.api.client.options.ChatVisibility;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.metadata.MetadataExtension;

public class AdvancedChatMessage implements MetadataExtension
{
    private final ChatMessage chatMessage;
    private Metadata metadata;
    private Component component;
    private boolean hidden;
    private RenderableComponent[] cachedRenderableComponents;
    private int cachedWidth;
    
    private AdvancedChatMessage(final ChatMessage chatMessage) {
        this.metadata = Metadata.create();
        this.chatMessage = chatMessage;
    }
    
    private AdvancedChatMessage(final Component component) {
        this(ChatMessage.builder().component(component).visibility(ChatVisibility.COMMANDS_ONLY).build());
    }
    
    public static AdvancedChatMessage component(final Component component) {
        return new AdvancedChatMessage(component);
    }
    
    public static AdvancedChatMessage text(final String text) {
        return new AdvancedChatMessage(Component.text(text));
    }
    
    public static AdvancedChatMessage chat(final ChatMessage chatMessage) {
        return new AdvancedChatMessage(chatMessage);
    }
    
    public long timestamp() {
        return this.chatMessage.timestamp();
    }
    
    public Component component() {
        return (this.component == null) ? this.chatMessage.component() : this.component;
    }
    
    public Component originalComponent() {
        return this.chatMessage.component();
    }
    
    public ChatVisibility visibility() {
        return this.chatMessage.visibility();
    }
    
    public ChatTrustLevel trustLevel() {
        return this.chatMessage.trustLevel();
    }
    
    @NotNull
    public ChatMessage chatMessage() {
        return this.chatMessage;
    }
    
    public void updateComponent(@NotNull final Component component) {
        this.component = component;
        this.invalidateCache();
    }
    
    public void invalidateCache() {
        this.cachedRenderableComponents = null;
    }
    
    public void hideMessage() {
        this.hidden = true;
    }
    
    public boolean isVisible() {
        return !this.hidden && Laby.labyAPI().minecraft().options().chatVisibility().isMessageVisible(this.visibility());
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.metadata = metadata;
    }
    
    @Override
    public Metadata metadata() {
        return this.metadata;
    }
    
    public RenderableComponent[] getRenderableComponents(final int width) {
        if (this.cachedRenderableComponents != null && this.cachedWidth == width) {
            return this.cachedRenderableComponents;
        }
        final List<Component> lines = Laby.labyAPI().renderPipeline().componentRenderer().split(this.component(), (float)width);
        final int size = lines.size();
        final RenderableComponent[] renderableComponents = new RenderableComponent[size];
        for (int index = 0; index < size; ++index) {
            renderableComponents[index] = RenderableComponent.of(lines.get(index));
        }
        this.cachedRenderableComponents = renderableComponents;
        this.cachedWidth = width;
        return renderableComponents;
    }
}
