// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.prefix;

import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.client.render.matrix.Stack;

public interface ChatPrefix
{
    void render(final Stack p0, final float p1, final float p2, final AdvancedChatMessage p3, final RenderableComponent[] p4, final int p5, final int p6, final int p7, final float p8, final double p9, final int p10);
    
    boolean isVisible(final ChatTab p0, final AdvancedChatMessage p1);
    
    int getWidth(final AdvancedChatMessage p0, final double p1);
}
