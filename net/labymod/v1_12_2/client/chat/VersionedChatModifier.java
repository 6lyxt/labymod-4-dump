// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.chat;

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
        bib.z().q.d().a(clearHistory);
    }
    
    @Override
    public void deleteMessage(final ChatMessage message) {
        final VersionedGuiNewChat accessor = (VersionedGuiNewChat)bib.z().q.d();
        final Predicate<bhx> messageFilter = this.messageFilter(message);
        accessor.getMessages().removeIf(messageFilter);
        accessor.getFormattedMessages().removeIf(messageFilter);
    }
    
    @Override
    public void editMessage(final ChatMessage message, final Component component) {
        final VersionedGuiNewChat accessor = (VersionedGuiNewChat)bib.z().q.d();
        final Predicate<bhx> messageFilter = this.messageFilter(message);
        final int index = CollectionHelper.indexOf(accessor.getMessages(), messageFilter);
        if (index == -1) {
            VersionedChatModifier.LOGGER.warn("Tried to edit unknown message", new Object[0]);
            return;
        }
        final bhx prevMessage = accessor.getMessages().get(index);
        final hh mapped = (hh)new VersionedChatMessageComponent(message, (hh)this.componentMapper.toMinecraftComponent(component));
        accessor.getMessages().set(index, new bhx(prevMessage.b(), mapped, prevMessage.c()));
        int first = -1;
        for (int i = 0; i < accessor.getFormattedMessages().size(); ++i) {
            if (messageFilter.test(accessor.getFormattedMessages().get(i))) {
                first = i;
                break;
            }
        }
        if (first != -1) {
            accessor.getFormattedMessages().removeIf(messageFilter);
            accessor.injectFormattedMessages(first, mapped, prevMessage);
        }
    }
    
    private Predicate<bhx> messageFilter(final ChatMessage message) {
        return m -> m.a() instanceof VersionedChatMessageComponent && ((VersionedChatMessageComponent)m.a()).message().messageId().equals(message.messageId());
    }
    
    static {
        LOGGER = Logging.create(VersionedChatModifier.class);
    }
}
