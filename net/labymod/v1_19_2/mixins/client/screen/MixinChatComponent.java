// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.screen;

import java.util.Locale;
import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.v1_19_2.client.gui.screen.VersionedScreenWrapper;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_19_2.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.api.event.client.chat.ChatClearEvent;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.Iterator;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.v1_19_2.client.chat.VersionedChatMessageCharSequence;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.chat.ChatTrustLevel;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.LabyAPI;
import net.labymod.v1_19_2.client.chat.VersionedChatMessageComponent;
import net.labymod.api.client.chat.ChatMessage;
import java.util.Objects;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.font.FontHeightAccessor;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.v1_19_2.client.chat.ChatMessageMeta;
import java.util.Map;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_2.client.chat.VersionedChatComponent;

@Mixin({ ehq.class })
public abstract class MixinChatComponent implements VersionedChatComponent
{
    private final ChatScreenUpdateEvent labyMod$refreshScreenChatScreenUpdateEvent;
    private final Map<rq, ChatMessageMeta> labyMod$chatMessageMeta;
    @Shadow
    @Final
    private efu f;
    @Shadow
    @Final
    private List<efp> k;
    @Shadow
    @Final
    private List<efp.a> l;
    
    public MixinChatComponent() {
        this.labyMod$refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
        this.labyMod$chatMessageMeta = new HashMap<rq, ChatMessageMeta>();
    }
    
    @Shadow
    protected abstract void a(final rq p0, @Nullable final rz p1, final int p2, @Nullable final efq p3, final boolean p4);
    
    @Shadow
    public abstract int e();
    
    @Shadow
    public abstract double g();
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;I)V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$cancelChatRender(final eaq stack, final int i, final InsertInfo callback) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            callback.cancel();
        }
    }
    
    @ModifyConstant(method = { "getLineHeight" }, constant = { @Constant(doubleValue = 9.0) })
    private double labyMod$modifyChatTextHeight(final double value) {
        return this.labyMod$getFontHeight();
    }
    
    @ModifyConstant(method = { "render" }, constant = { @Constant(doubleValue = -8.0) })
    private double labyMod$modifyChatTextHeight0(final double value) {
        return -(this.labyMod$getFontHeight() - 1.0);
    }
    
    @ModifyConstant(method = { "render" }, constant = { @Constant(intValue = 9) })
    private int labyMod$modifyChatBackgroundHeight(final int value) {
        return this.labyMod$getFontHeight();
    }
    
    private int labyMod$getFontHeight() {
        final eha font = efu.I().h;
        if (font instanceof final FontHeightAccessor fontHeightAccessor) {
            return MathHelper.ceil(fontHeightAccessor.getHeight());
        }
        Objects.requireNonNull(font);
        return 9;
    }
    
    @Redirect(method = { "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V"))
    public void labyMod$addMessage(final ehq instance, rq component, final rz signature, final int ticks, final efq tag, final boolean v) {
        ChatMessageMeta meta = this.labyMod$chatMessageMeta.remove(component);
        if (meta == null) {
            meta = ChatMessageMeta.system();
        }
        final LabyAPI labyAPI = Laby.labyAPI();
        final ComponentMapper mapper = labyAPI.minecraft().componentMapper();
        final Component mapped = mapper.fromMinecraftComponent(component);
        final ChatTrustLevel trustLevel = this.labyMod$evaluateTrustLevel(tag);
        final ChatMessage message = labyAPI.chatProvider().chatController().addMessage(ChatMessage.builder().component(mapped).visibility(meta.visibility()).trustLevel(trustLevel).sender(meta.getSender()));
        if (message == null) {
            return;
        }
        component = (rq)new VersionedChatMessageComponent(message, (rq)mapper.toMinecraftComponent(message.component()));
        this.a(component, signature, ticks, tag, v);
    }
    
    @Redirect(method = { "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ComponentRenderUtils;wrapComponents(Lnet/minecraft/network/chat/FormattedText;ILnet/minecraft/client/gui/Font;)Ljava/util/List;"))
    private List<alu> labyMod$injectChatMessages(final ru text, final int maxWidth, final eha font) {
        final List<alu> lines = eht.a(text, maxWidth, font);
        if (!(text instanceof VersionedChatMessageComponent)) {
            return lines;
        }
        final ChatMessage message = ((VersionedChatMessageComponent)text).message();
        lines.replaceAll(wrapped -> new VersionedChatMessageCharSequence(message, wrapped));
        return lines;
    }
    
    @Insert(method = { "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V" }, at = @At(value = "HEAD", shift = At.Shift.AFTER), cancellable = true)
    private void labyMod$preventNullMessageAdd(final rq component, final rz signature, final int $$2, final efq messageTag, final boolean $$4, final InsertInfo callback) {
        if (component == null) {
            callback.cancel();
        }
    }
    
    @Inject(method = { "deleteMessage(Lnet/minecraft/network/chat/MessageSignature;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$deleteMessage(final rz messageSignature, final CallbackInfo callbackInfo) {
        efp guiMessage = null;
        for (final efp allMessage : this.k) {
            if (allMessage.c() != null && allMessage.c().equals((Object)messageSignature)) {
                guiMessage = allMessage;
                break;
            }
        }
        if (guiMessage != null) {
            final rq b = guiMessage.b();
            if (b instanceof final VersionedChatMessageComponent messageComponent) {
                final ChatMessage message = messageComponent.message();
                if (!message.wasDeleted()) {
                    message.delete();
                    callbackInfo.cancel();
                }
            }
        }
    }
    
    @Insert(method = { "clearMessages(Z)V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$clearMessages(final boolean clearHistory, final InsertInfo callback) {
        if (Laby.fireEvent(new ChatClearEvent(clearHistory)).isCancelled()) {
            callback.cancel();
        }
    }
    
    @Insert(method = { "isChatFocused()Z" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$activityChatFocused(final InsertInfoReturnable<Boolean> cir) {
        final elm z = this.f.z;
        if (z instanceof final LabyScreenRenderer screenRenderer) {
            cir.setReturnValue(screenRenderer.screen() instanceof ChatInputOverlay);
        }
    }
    
    @Insert(method = { "rescaleChat()V" }, at = @At("TAIL"))
    private void labyMod$chatUpdated(final InsertInfo callback) {
        Laby.fireEvent(this.labyMod$refreshScreenChatScreenUpdateEvent);
    }
    
    @Overwrite
    @Nullable
    public ekg d() {
        final elm z = this.f.z;
        if (!(z instanceof LabyScreenRenderer)) {
            return null;
        }
        final LabyScreenRenderer screenRenderer = (LabyScreenRenderer)z;
        final LabyScreen screen = screenRenderer.screen();
        if (!(screen instanceof ChatInputOverlay)) {
            return null;
        }
        final ChatInputOverlay overlay = (ChatInputOverlay)screen;
        final ScreenInstance mostInnerScreenInstance = overlay.mostInnerScreenInstance();
        if (!(mostInnerScreenInstance instanceof VersionedScreenWrapper)) {
            return null;
        }
        final VersionedScreenWrapper screenWrapper = (VersionedScreenWrapper)mostInnerScreenInstance;
        final Object versionedScreen = screenWrapper.getVersionedScreen();
        if (versionedScreen instanceof final ekg chatScreen) {
            return chatScreen;
        }
        return null;
    }
    
    @Override
    public List<efp> getMessages() {
        return this.k;
    }
    
    @Override
    public List<efp.a> getFormattedMessages() {
        return this.l;
    }
    
    @Override
    public void injectFormattedMessages(final int index, final rq component, final efp message) {
        final efq tag = message.d();
        int maxWidth = ami.b(this.e() / this.g());
        if (tag != null && tag.e() != null) {
            maxWidth -= tag.e().e + 4 + 2;
        }
        final List<alu> formatted = this.labyMod$injectChatMessages((ru)component, maxWidth, this.f.h);
        for (int i = 0; i < formatted.size(); ++i) {
            final alu sequence = formatted.get(i);
            final boolean end = i == formatted.size() - 1;
            this.l.add(index, new efp.a(message.a(), sequence, tag, end));
        }
    }
    
    private ChatTrustLevel labyMod$evaluateTrustLevel(final efq messageTag) {
        if (messageTag == null || messageTag.g() == null) {
            return ChatTrustLevel.SECURE;
        }
        final String lowerCase = messageTag.g().toLowerCase(Locale.ROOT);
        return switch (lowerCase) {
            case "system" -> ChatTrustLevel.SYSTEM;
            case "not secure" -> ChatTrustLevel.NOT_SECURE;
            case "filtered" -> ChatTrustLevel.FILTERED;
            case "modified" -> ChatTrustLevel.MODIFIED;
            default -> ChatTrustLevel.SECURE;
        };
    }
    
    @Override
    public void setMessageMeta(final rq component, final ChatMessageMeta meta) {
        this.labyMod$chatMessageMeta.put(component, meta);
    }
    
    @Override
    public void clearMessageMeta(final rq component) {
        this.labyMod$chatMessageMeta.remove(component);
    }
}
