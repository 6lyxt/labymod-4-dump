// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.prefix;

import net.labymod.core.client.chat.prefix.prefix.ChatPlayerHeadPrefix;
import net.labymod.core.client.chat.prefix.prefix.ChatTrustIndicatorPrefix;
import net.labymod.api.client.chat.prefix.ChatPrefixRegistry;
import net.labymod.api.client.chat.prefix.ChatPrefix;
import net.labymod.api.service.DefaultRegistry;

public class DefaultChatPrefixRegistry extends DefaultRegistry<ChatPrefix> implements ChatPrefixRegistry
{
    public DefaultChatPrefixRegistry() {
        ((DefaultRegistry<ChatTrustIndicatorPrefix>)this).register("trust_indicator", new ChatTrustIndicatorPrefix());
        ((DefaultRegistry<ChatPlayerHeadPrefix>)this).register("player_head", new ChatPlayerHeadPrefix());
    }
}
