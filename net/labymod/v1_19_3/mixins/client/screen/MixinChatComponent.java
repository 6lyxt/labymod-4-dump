// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.screen;

import java.util.Locale;
import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.v1_19_3.client.gui.screen.VersionedScreenWrapper;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_19_3.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.api.event.client.chat.ChatClearEvent;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import java.util.ListIterator;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_19_3.client.chat.VersionedChatMessageCharSequence;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.chat.ChatTrustLevel;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.LabyAPI;
import net.labymod.v1_19_3.client.chat.VersionedChatMessageComponent;
import net.labymod.api.client.chat.ChatMessage;
import java.util.Objects;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.font.FontHeightAccessor;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.v1_19_3.client.chat.ChatMessageMeta;
import java.util.Map;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_3.client.chat.VersionedChatComponent;

@Mixin({ ela.class })
public abstract class MixinChatComponent implements VersionedChatComponent
{
    private final ChatScreenUpdateEvent labyMod$refreshScreenChatScreenUpdateEvent;
    private final Map<ss, ChatMessageMeta> labyMod$chatMessageMeta;
    @Shadow
    @Final
    private ejf l;
    @Shadow
    @Final
    private List<eja> n;
    @Shadow
    @Final
    private List<eja.a> o;
    
    public MixinChatComponent() {
        this.labyMod$refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
        this.labyMod$chatMessageMeta = new HashMap<ss, ChatMessageMeta>();
    }
    
    @Shadow
    protected abstract void a(final ss p0, @Nullable final td p1, final int p2, @Nullable final ejb p3, final boolean p4);
    
    @Shadow
    public abstract int e();
    
    @Shadow
    public abstract double g();
    
    @Shadow
    protected abstract eja a(final eja p0);
    
    @Inject(method = { "render" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$cancelChatRender(final eed $$0, final int $$1, final int $$2, final int $$3, final CallbackInfo ci) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            ci.cancel();
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
        final ekm font = ejf.N().h;
        if (font instanceof final FontHeightAccessor fontHeightAccessor) {
            return MathHelper.ceil(fontHeightAccessor.getHeight());
        }
        Objects.requireNonNull(font);
        return 9;
    }
    
    @Redirect(method = { "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V"))
    public void labyMod$addMessage(final ela instance, ss component, final td signature, final int ticks, final ejb tag, final boolean v) {
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
        component = (ss)new VersionedChatMessageComponent(message, (ss)mapper.toMinecraftComponent(message.component()));
        this.a(component, signature, ticks, tag, v);
    }
    
    @Redirect(method = { "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ComponentRenderUtils;wrapComponents(Lnet/minecraft/network/chat/FormattedText;ILnet/minecraft/client/gui/Font;)Ljava/util/List;"))
    private List<ano> labyMod$injectChatMessages(final sw text, final int maxWidth, final ekm font) {
        final List<ano> lines = eld.a(text, maxWidth, font);
        if (!(text instanceof VersionedChatMessageComponent)) {
            return lines;
        }
        final ChatMessage message = ((VersionedChatMessageComponent)text).message();
        lines.replaceAll(wrapped -> new VersionedChatMessageCharSequence(message, wrapped));
        return lines;
    }
    
    @Insert(method = { "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V" }, at = @At(value = "HEAD", shift = At.Shift.AFTER), cancellable = true)
    private void labyMod$preventNullMessageAdd(final ss component, final td signature, final int $$2, final ejb messageTag, final boolean $$4, final InsertInfo callback) {
        if (component == null) {
            callback.cancel();
        }
    }
    
    @Inject(method = { "deleteMessageOrDelay(Lnet/minecraft/network/chat/MessageSignature;)Lnet/minecraft/client/gui/components/ChatComponent$DelayedMessageDeletion;" }, at = { @At(value = "INVOKE", target = "Ljava/util/ListIterator;set(Ljava/lang/Object;)V", shift = At.Shift.BEFORE) }, locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void labyMod$deleteMessage(final td messageSignature, final CallbackInfoReturnable<Object> cir, final int guiTicks, final ListIterator<eja> iterator, final eja message) {
        final ss b = message.b();
        if (b instanceof final VersionedChatMessageComponent messageComponent) {
            final ComponentMapper mapper = Laby.labyAPI().minecraft().componentMapper();
            final eja deletedMarker = this.a(message);
            final Component mappedDeletedMarker = mapper.fromMinecraftComponent(deletedMarker.b());
            messageComponent.message().edit(mappedDeletedMarker);
            cir.setReturnValue((Object)null);
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
        final epb z = this.l.z;
        if (z instanceof final LabyScreenRenderer screenRenderer) {
            cir.setReturnValue(screenRenderer.screen() instanceof ChatInputOverlay);
        }
    }
    
    @Insert(method = { "rescaleChat()V" }, at = @At("TAIL"))
    private void labyMod$chatUpdated(final InsertInfo callback) {
        Laby.fireEvent(this.labyMod$refreshScreenChatScreenUpdateEvent);
    }
    
    @Overwrite
    private boolean o() {
        final epb z = this.l.z;
        if (!(z instanceof LabyScreenRenderer)) {
            return false;
        }
        final LabyScreenRenderer screenRenderer = (LabyScreenRenderer)z;
        final LabyScreen screen = screenRenderer.screen();
        if (!(screen instanceof ChatInputOverlay)) {
            return false;
        }
        final ChatInputOverlay overlay = (ChatInputOverlay)screen;
        final ScreenInstance mostInnerScreenInstance = overlay.mostInnerScreenInstance();
        if (mostInnerScreenInstance instanceof final VersionedScreenWrapper screenWrapper) {
            final Object versionedScreen = screenWrapper.getVersionedScreen();
            boolean b;
            if (versionedScreen instanceof final env chatScreen) {
                b = true;
            }
            else {
                b = false;
            }
            return b;
        }
        return false;
    }
    
    @Override
    public List<eja> getMessages() {
        return this.n;
    }
    
    @Override
    public List<eja.a> getFormattedMessages() {
        return this.o;
    }
    
    @Override
    public void injectFormattedMessages(final int index, final ss component, final eja message) {
        final ejb tag = message.d();
        int maxWidth = aoc.b(this.e() / this.g());
        if (tag != null && tag.e() != null) {
            maxWidth -= tag.e().d + 4 + 2;
        }
        final List<ano> formatted = this.labyMod$injectChatMessages((sw)component, maxWidth, this.l.h);
        for (int i = 0; i < formatted.size(); ++i) {
            final ano sequence = formatted.get(i);
            final boolean end = i == formatted.size() - 1;
            this.o.add(index, new eja.a(message.a(), sequence, tag, end));
        }
    }
    
    private ChatTrustLevel labyMod$evaluateTrustLevel(final ejb messageTag) {
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
    public void setMessageMeta(final ss component, final ChatMessageMeta meta) {
        this.labyMod$chatMessageMeta.put(component, meta);
    }
    
    @Override
    public void clearMessageMeta(final ss component) {
        this.labyMod$chatMessageMeta.remove(component);
    }
}
