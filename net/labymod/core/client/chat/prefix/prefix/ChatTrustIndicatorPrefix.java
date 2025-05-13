// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.prefix.prefix;

import net.labymod.api.client.chat.ChatData;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.client.chat.ChatTrustLevel;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.prefix.ChatPrefix;

public class ChatTrustIndicatorPrefix implements ChatPrefix
{
    private final LabyAPI api;
    
    public ChatTrustIndicatorPrefix() {
        this.api = Laby.labyAPI();
    }
    
    @Override
    public void render(final Stack stack, final float x, final float y, final AdvancedChatMessage message, final RenderableComponent[] renderableComponents, final int index, final int subIndex, final int lineHeight, final float textOffset, final double scale, final int alpha) {
        final ChatTrustLevel chatTrustLevel = message.trustLevel();
        this.api.renderPipeline().rectangleRenderer().pos(x - 2.0f, y).size(2.0f, (float)lineHeight).color(ColorFormat.ARGB32.pack(chatTrustLevel.getHexColor(), alpha)).render(stack);
    }
    
    @Override
    public boolean isVisible(final ChatTab chatTab, final AdvancedChatMessage message) {
        return ChatData.isChatTrustEnabled() && chatTab.config().chatTrust().get();
    }
    
    @Override
    public int getWidth(final AdvancedChatMessage message, final double scale) {
        return 0;
    }
}
