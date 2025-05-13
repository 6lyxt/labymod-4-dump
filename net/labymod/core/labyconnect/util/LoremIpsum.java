// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.util;

import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.util.ThreadSafe;
import net.labymod.core.labyconnect.protocol.model.chat.DefaultTextChatMessage;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.Laby;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;

public class LoremIpsum
{
    private static final String[] WORDS;
    
    public static void addMessages(final Chat chat) {
        ThreadSafe.executeOnRenderThread(() -> {
            final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
            if (session != null) {
                for (int i = 0; i < 600; ++i) {
                    final User sender = (Math.random() < 0.5) ? chat.getParticipants().get(0) : session.self();
                    final StringBuilder generatedText = new StringBuilder("[" + i + ".] ");
                    for (int wordsAmount = (int)(Math.random() * 50.0) + 1, m = 0; m < wordsAmount; ++m) {
                        generatedText.append(LoremIpsum.WORDS[(int)(Math.random() * LoremIpsum.WORDS.length)]);
                        if (Math.random() < 0.20000000298023224 && m < wordsAmount - 5) {
                            generatedText.append(",");
                        }
                        generatedText.append(" ");
                    }
                    generatedText.setCharAt();
                    generatedText.setLength();
                    generatedText.append(".");
                    final DefaultTextChatMessage message = new DefaultTextChatMessage(chat, sender, TimeUtil.getCurrentTimeMillis(), generatedText.toString());
                    message.markAsRead();
                    chat.getMessages().add(message);
                }
            }
        });
    }
    
    static {
        WORDS = new String[] { "Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit", "Sed", "non", "risus", "nisl", "nec", "diam", "eu", "nunc", "consequat", "viverra", "Suspendisse", "potenti", "augue", "mauris", "pellentesque", "quis", "ultricies", "interdum", "aliquet", "tempus", "fringilla", "vehicula", "lectus", "vitae", "varius", "orci", "tristique", "facilisis", "scelerisque", "eget", "sollicitudin", "lobortis", "porttitor", "mollis", "iaculis", "venenatis", "turpis", "imperdiet", "gravida", "tempor", "dictum", "fermentum", "quam", "eleifend", "tincidunt", "urna", "congue", "lobortis", "hendrerit", "euismod", "sapien" };
    }
}
