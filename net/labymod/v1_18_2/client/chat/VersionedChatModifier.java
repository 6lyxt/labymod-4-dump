// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.chat;

import net.labymod.api.util.CollectionHelper;
import net.labymod.api.client.component.Component;
import java.util.function.Predicate;
import net.labymod.api.client.chat.ChatMessage;
import javax.inject.Inject;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.chat.InternalChatModifier;

@Singleton
@Implements(InternalChatModifier.class)
public class VersionedChatModifier implements InternalChatModifier
{
    private static final Logging LOGGER;
    private final ComponentMapper componentMapper;
    
    @Inject
    public VersionedChatModifier(final ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }
    
    @Override
    public void fireChatClear(final boolean clearHistory) {
        dyr.D().k.c().a(clearHistory);
    }
    
    @Override
    public void deleteMessage(final ChatMessage message) {
        final VersionedChatComponent accessor = (VersionedChatComponent)dyr.D().k.c();
        accessor.getMessages().removeIf(this.messageFilter(message));
        accessor.getFormattedMessages().removeIf(this.formattedFilter(message));
    }
    
    @Override
    public void editMessage(final ChatMessage message, final Component component) {
        final VersionedChatComponent accessor = (VersionedChatComponent)dyr.D().k.c();
        final int index = CollectionHelper.indexOf(accessor.getMessages(), this.messageFilter(message));
        if (index == -1) {
            VersionedChatModifier.LOGGER.warn("Tried to edit unknown message", new Object[0]);
            return;
        }
        final dym<qk> prevMessage = accessor.getMessages().get(index);
        final qk mapped = (qk)new VersionedChatMessageComponent(message, (qk)this.componentMapper.toMinecraftComponent(component));
        accessor.getMessages().set(index, (dym<qk>)new dym(prevMessage.b(), (Object)mapped, prevMessage.c()));
        final Predicate<dym<aiz>> predicate = this.formattedFilter(message);
        int first = -1;
        for (int i = 0; i < accessor.getFormattedMessages().size(); ++i) {
            if (predicate.test(accessor.getFormattedMessages().get(i))) {
                first = i;
                break;
            }
        }
        if (first != -1) {
            accessor.getFormattedMessages().removeIf(this.formattedFilter(message));
            accessor.injectFormattedMessages(first, mapped, prevMessage);
        }
    }
    
    private Predicate<dym<qk>> messageFilter(final ChatMessage message) {
        return m -> m.a() instanceof VersionedChatMessageComponent && ((VersionedChatMessageComponent)m.a()).message().messageId().equals(message.messageId());
    }
    
    private Predicate<dym<aiz>> formattedFilter(final ChatMessage message) {
        return m -> m.a() instanceof VersionedChatMessageCharSequence && ((VersionedChatMessageCharSequence)m.a()).message().messageId().equals(message.messageId());
    }
    
    static {
        LOGGER = Logging.create(VersionedChatModifier.class);
    }
}
