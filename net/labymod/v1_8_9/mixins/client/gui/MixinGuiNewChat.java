// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_8_9.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.api.event.client.chat.ChatClearEvent;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.ChatMessage;
import java.util.Iterator;
import net.labymod.v1_8_9.client.chat.VersionedChatMessageComponent;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import java.util.HashMap;
import net.labymod.api.client.options.ChatVisibility;
import java.util.Map;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.chat.VersionedGuiNewChat;

@Mixin({ avt.class })
public abstract class MixinGuiNewChat implements VersionedGuiNewChat
{
    @Shadow
    @Final
    private ave f;
    @Shadow
    @Final
    private List<ava> h;
    @Shadow
    @Final
    private List<ava> i;
    private final ChatScreenUpdateEvent refreshScreenChatScreenUpdateEvent;
    private final Map<eu, ChatVisibility> chatVisibilityMessages;
    
    public MixinGuiNewChat() {
        this.refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
        this.chatVisibilityMessages = new HashMap<eu, ChatVisibility>();
    }
    
    @Shadow
    public abstract int f();
    
    @Shadow
    public abstract float h();
    
    @Insert(method = { "drawChat(I)V" }, at = @At("HEAD"), cancellable = true)
    public void cancelChatRender(final int index, final InsertInfo callback) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            callback.cancel();
        }
    }
    
    @Insert(method = { "deleteChatLine" }, at = @At("HEAD"))
    private void labyMod$deleteChatMessage(final int index, final InsertInfo callback) {
        for (final ava chatLine : this.h) {
            if (chatLine.c() == index) {
                final eu lineComponent = chatLine.a();
                if (!(lineComponent instanceof VersionedChatMessageComponent)) {
                    continue;
                }
                final ChatMessage chatMessage = ((VersionedChatMessageComponent)lineComponent).message();
                if (!chatMessage.wasDeleted()) {
                    chatMessage.delete();
                    break;
                }
                break;
            }
        }
    }
    
    @ModifyVariable(method = { "printChatMessageWithOptionalDeletion(Lnet/minecraft/util/IChatComponent;I)V" }, at = @At("HEAD"), argsOnly = true)
    private eu addMessage(final eu component) {
        ChatVisibility visibility = this.chatVisibilityMessages.remove(component);
        if (visibility == null) {
            visibility = ChatVisibility.COMMANDS_ONLY;
        }
        final LabyAPI labyAPI = Laby.labyAPI();
        final ComponentMapper mapper = labyAPI.minecraft().componentMapper();
        final Component mapped = mapper.fromMinecraftComponent(component);
        final ChatMessage message = labyAPI.chatProvider().chatController().addMessage(ChatMessage.builder().component(mapped).visibility(visibility));
        if (message != null) {
            return (eu)new VersionedChatMessageComponent(message, (eu)mapper.toMinecraftComponent(message.component()));
        }
        return null;
    }
    
    @Redirect(method = { "setChatLine" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiUtilRenderComponents;splitText(Lnet/minecraft/util/IChatComponent;ILnet/minecraft/client/gui/FontRenderer;ZZ)Ljava/util/List;"))
    private List<eu> labyMod$injectChatMessages(final eu component, final int maxWidth, final avn fontRenderer, final boolean b1, final boolean b2) {
        final List<eu> lines = avu.a(component, maxWidth, fontRenderer, b1, b2);
        if (!(component instanceof VersionedChatMessageComponent)) {
            return lines;
        }
        final ChatMessage message = ((VersionedChatMessageComponent)component).message();
        lines.replaceAll(wrapped -> new VersionedChatMessageComponent(message, wrapped));
        return lines;
    }
    
    @Insert(method = { "printChatMessageWithOptionalDeletion(Lnet/minecraft/util/IChatComponent;I)V" }, at = @At("HEAD"), cancellable = true)
    public void preventNullMessageAdd(final eu component, final int index, final InsertInfo callback) {
        if (component == null) {
            callback.cancel();
        }
    }
    
    @Insert(method = { "clearChatMessages()V" }, at = @At("HEAD"), cancellable = true)
    private void clearChatMessages(final InsertInfo callback) {
        if (Laby.fireEvent(new ChatClearEvent(true)).isCancelled()) {
            callback.cancel();
        }
    }
    
    @Insert(method = { "getChatOpen()Z" }, at = @At("HEAD"), cancellable = true)
    private void activityChatFocused(final InsertInfoReturnable<Boolean> cir) {
        if (this.f.m instanceof final LabyScreenRenderer screenRenderer) {
            cir.setReturnValue(screenRenderer.screen() instanceof ChatInputOverlay);
        }
    }
    
    @Insert(method = { "refreshChat()V" }, at = @At("TAIL"))
    private void labyMod$chatUpdated(final InsertInfo callback) {
        Laby.fireEvent(this.refreshScreenChatScreenUpdateEvent);
    }
    
    @Override
    public List<ava> getMessages() {
        return this.h;
    }
    
    @Override
    public List<ava> getFormattedMessages() {
        return this.i;
    }
    
    @Override
    public void injectFormattedMessages(final int index, final eu component, final ava message) {
        final int maxWidth = ns.d(this.f() / this.h());
        final List<eu> formatted = this.labyMod$injectChatMessages(component, maxWidth, this.f.k, false, false);
        for (final eu line : formatted) {
            this.i.add(index, new ava(message.b(), line, message.c()));
        }
    }
    
    @Override
    public void setChatVisibility(final eu component, final wn.b visibility) {
        ChatVisibility mapped = null;
        switch (visibility) {
            case a: {
                mapped = ChatVisibility.SHOWN;
                break;
            }
            case b: {
                mapped = ChatVisibility.COMMANDS_ONLY;
                break;
            }
            case c: {
                mapped = ChatVisibility.HIDDEN;
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(visibility));
            }
        }
        this.chatVisibilityMessages.put(component, mapped);
    }
    
    @Override
    public void clearChatVisibility(final eu component) {
        this.chatVisibilityMessages.remove(component);
    }
}
