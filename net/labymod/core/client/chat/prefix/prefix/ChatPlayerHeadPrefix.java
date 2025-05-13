// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.prefix.prefix;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.PlayerHeadRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.prefix.ChatPrefix;

public class ChatPlayerHeadPrefix implements ChatPrefix
{
    private static final int HEAD_SIZE = 8;
    private final LabyAPI api;
    
    public ChatPlayerHeadPrefix() {
        this.api = Laby.labyAPI();
    }
    
    @Override
    public void render(final Stack stack, final float x, final float y, final AdvancedChatMessage message, final RenderableComponent[] renderableComponents, final int index, final int subIndex, final int lineHeight, final float textOffset, final double scale, final int alpha) {
        if (index != 0 || subIndex != 0) {
            return;
        }
        final GameProfile gameProfile = message.chatMessage().getSenderProfile();
        if (gameProfile == null) {
            return;
        }
        final double headSize = 8.0 * scale;
        final int margin = 1;
        this.api.renderPipeline().resourceRenderer().head().player(gameProfile).wearingHat(true).pos(x + margin, y + textOffset).size((float)headSize).color(ColorFormat.ARGB32.pack(255, 255, 255, alpha)).render(stack);
    }
    
    @Override
    public boolean isVisible(final ChatTab chatTab, final AdvancedChatMessage message) {
        return this.api.config().ingame().advancedChat().showPlayerHeads().get() && message.chatMessage().getSenderProfile() != null;
    }
    
    @Override
    public int getWidth(final AdvancedChatMessage message, final double scale) {
        final double headSize = 8.0 * scale;
        final int margin = 1;
        return MathHelper.ceil(headSize + margin);
    }
}
