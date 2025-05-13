// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.chat;

import net.labymod.api.client.component.BaseComponent;
import java.nio.charset.StandardCharsets;
import net.labymod.api.client.gui.window.Window;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.LabyConnectActivity;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.navigation.elements.LabyConnectNavigationElement;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageDeleteEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageEvent;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.metadata.Metadata;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import net.labymod.api.client.screenshot.ScreenshotUtil;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.user.GameUser;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;
import java.util.List;
import java.util.UUID;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;

public class DefaultChat implements Chat
{
    private static final int MAX_IMAGE_SIZE;
    private final UUID id;
    private final List<ChatMessage> messages;
    private boolean typing;
    private final List<User> participants;
    private String inputMessage;
    private ChatMessage lastMessage;
    
    public DefaultChat(final UUID id) {
        this.messages = new ArrayList<ChatMessage>();
        this.typing = false;
        this.participants = new ArrayList<User>();
        this.inputMessage = "";
        this.id = id;
    }
    
    @Override
    public UUID getId() {
        return this.id;
    }
    
    @Override
    public List<ChatMessage> getMessages() {
        return this.messages;
    }
    
    @Override
    public List<User> getParticipants() {
        return this.participants;
    }
    
    @Override
    public void addParticipant(final User user) {
        this.participants.add(user);
    }
    
    @Override
    public void removeParticipant(final User user) {
        this.participants.remove(user);
    }
    
    @Override
    public boolean isTyping() {
        return this.typing;
    }
    
    @Override
    public int getUnreadCount() {
        int count = 0;
        for (final ChatMessage message : this.messages) {
            if (!message.isRead() && !message.sender().isSelf()) {
                ++count;
            }
        }
        return count;
    }
    
    @Override
    public Component title() {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return Component.translatable("labymod.activity.labyconnect.chat.title", new Component[0]);
        }
        final Friend friend = session.getFriend(this.id);
        if (friend == null) {
            return Component.translatable("labymod.activity.labyconnect.chat.title", new Component[0]);
        }
        final GameUser gameUser = Laby.labyAPI().gameUserService().gameUser(friend.getUniqueId());
        return ((BaseComponent<Component>)Component.text(friend.getName())).color(gameUser.displayColor());
    }
    
    @Override
    public Icon icon() {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return Textures.SpriteCommon.CHAT_BUBBLE;
        }
        final Friend friend = session.getFriend(this.id);
        if (friend == null) {
            return Textures.SpriteCommon.CHAT_BUBBLE;
        }
        return Icon.head(friend.getUniqueId());
    }
    
    @Override
    public void sendMessage(final String message) {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return;
        }
        final TextChatMessage chatMessage = new DefaultTextChatMessage(this, session.self(), TimeUtil.getCurrentTimeMillis(), message);
        this.addMessage(chatMessage);
        session.sendChatMessage(this.id, chatMessage);
    }
    
    @Override
    public void sendFile(final String type, final byte[] data) {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return;
        }
        session.sendChatFile(this.id, type, data);
    }
    
    @Override
    public void sendImage(final GameImage image) throws Exception {
        this.sendImage(image, 1920, 1080);
    }
    
    private void sendImage(GameImage image, final int newWidth, final int newHeight) throws Exception {
        image = ScreenshotUtil.maxSize(image, newWidth, newHeight);
        final BufferedImage bufferedImage = image.getImage();
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", out);
            final byte[] imageData = out.toByteArray();
            final int size = imageData.length;
            if (size > DefaultChat.MAX_IMAGE_SIZE) {
                this.sendImage(image, newWidth - 16, newHeight - 9);
                out.close();
                return;
            }
            this.sendFile("png", imageData);
        }
    }
    
    @Override
    public void addMessage(final User sender, final String message, final long timestamp, final Metadata metadata) {
        final ChatMessage chatMessage = new DefaultTextChatMessage(this, sender, timestamp, message, metadata);
        this.addMessage(chatMessage);
    }
    
    public void addMessage(final ChatMessage message) {
        ThreadSafe.ensureRenderThread();
        this.messages.add(message);
        this.lastMessage = message;
        Laby.fireEvent(new LabyConnectChatMessageEvent(Laby.references().labyConnect(), this, message));
    }
    
    public void removeMessage(final ChatMessage message) {
        if (!this.messages.remove(message)) {
            return;
        }
        if (this.lastMessage == message) {
            this.lastMessage = (this.messages.isEmpty() ? null : this.messages.get(this.messages.size() - 1));
        }
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> Laby.labyAPI().eventBus().fire(new LabyConnectChatMessageDeleteEvent(Laby.labyAPI().labyConnect(), this, message)));
    }
    
    public void updateTyping(final boolean typing) {
        this.typing = typing;
    }
    
    @Override
    public String getInputMessage() {
        return this.inputMessage;
    }
    
    @Override
    public void setInputMessage(final String inputMessage) {
        this.inputMessage = inputMessage;
    }
    
    @Nullable
    @Override
    public ChatMessage getLastMessage() {
        return this.lastMessage;
    }
    
    @Override
    public void openChat() {
        final LabyAPI labyAPI = Laby.labyAPI();
        final LabyConnectSession session = labyAPI.labyConnect().getSession();
        if (session == null) {
            return;
        }
        final LabyConnectActivity activity = LabyConnectNavigationElement.ACTIVITY;
        final Window window = labyAPI.minecraft().minecraftWindow();
        window.displayScreen(activity);
        labyAPI.minecraft().executeNextTick(() -> activity.desktopActivity().friendsActivity().openChat(this.id));
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Chat && ((Chat)obj).getId().equals(this.id);
    }
    
    static {
        MAX_IMAGE_SIZE = 2097135 - "labymod:file".getBytes(StandardCharsets.UTF_8).length;
    }
}
