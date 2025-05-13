// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.screen;

import java.util.Locale;
import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.v1_20_6.client.gui.screen.VersionedScreenWrapper;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_20_6.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.client.chat.ChatClearEvent;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import java.util.ListIterator;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.v1_20_6.client.chat.VersionedChatMessageCharSequence;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.chat.ChatTrustLevel;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.LabyAPI;
import net.labymod.v1_20_6.client.chat.VersionedChatMessageComponent;
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
import java.util.HashMap;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.v1_20_6.client.chat.ChatMessageMeta;
import java.util.Map;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_6.client.chat.VersionedChatComponent;

@Mixin({ fhh.class })
public abstract class MixinChatComponent implements VersionedChatComponent
{
    private final ffb labyMod$invalidMessage;
    private final ChatScreenUpdateEvent labyMod$refreshScreenChatScreenUpdateEvent;
    private final Map<xp, ChatMessageMeta> labyMod$chatMessageMeta;
    @Shadow
    @Final
    private ffh i;
    @Shadow
    @Final
    private List<ffb> k;
    @Shadow
    @Final
    private List<ffb.a> l;
    @Unique
    @Nullable
    private ffb labyMod$cachedGuiMessage;
    
    public MixinChatComponent() {
        this.labyMod$invalidMessage = new ffb(0, (xp)xp.i(), (yb)null, (ffc)null);
        this.labyMod$refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
        this.labyMod$chatMessageMeta = new HashMap<xp, ChatMessageMeta>();
    }
    
    @Shadow
    public abstract int f();
    
    @Shadow
    public abstract double h();
    
    @Shadow
    protected abstract ffb d(final ffb p0);
    
    @Inject(method = { "render" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$cancelChatRender(final fgt $$0, final int $$1, final int $$2, final int $$3, final boolean $$4, final CallbackInfo ci) {
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
        final fgr font = ffh.Q().h;
        if (font instanceof final FontHeightAccessor fontHeightAccessor) {
            return MathHelper.ceil(fontHeightAccessor.getHeight());
        }
        Objects.requireNonNull(font);
        return 9;
    }
    
    @Redirect(method = { "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V" }, at = @At(value = "NEW", target = "(ILnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)Lnet/minecraft/client/GuiMessage;"))
    private ffb labyMod$addMessage(final int ticks, xp component, final yb signature, final ffc tag) {
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
            this.labyMod$cachedGuiMessage = this.labyMod$invalidMessage;
        }
        else {
            component = (xp)new VersionedChatMessageComponent(message, (xp)mapper.toMinecraftComponent(message.component()));
            this.labyMod$cachedGuiMessage = new ffb(ticks, component, signature, tag);
        }
        return this.labyMod$cachedGuiMessage;
    }
    
    @Redirect(method = { "addMessageToDisplayQueue" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ComponentRenderUtils;wrapComponents(Lnet/minecraft/network/chat/FormattedText;ILnet/minecraft/client/gui/Font;)Ljava/util/List;"))
    private List<ayl> labyMod$injectChatMessages(final xu text, final int maxWidth, final fgr font) {
        final List<ayl> lines = fhl.a(text, maxWidth, font);
        if (!(text instanceof VersionedChatMessageComponent)) {
            return lines;
        }
        final ChatMessage message = ((VersionedChatMessageComponent)text).message();
        lines.replaceAll(wrapped -> new VersionedChatMessageCharSequence(message, wrapped));
        return lines;
    }
    
    @Inject(method = { "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V" }, at = { @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/gui/components/ChatComponent;logChatMessage(Lnet/minecraft/client/GuiMessage;)V") }, cancellable = true)
    private void labyMod$preventNullMessageAdd(final xp $$0, final yb $$1, final ffc $$2, final CallbackInfo ci) {
        if (this.labyMod$cachedGuiMessage == this.labyMod$invalidMessage) {
            ci.cancel();
        }
        this.labyMod$cachedGuiMessage = this.labyMod$invalidMessage;
    }
    
    @Inject(method = { "deleteMessageOrDelay(Lnet/minecraft/network/chat/MessageSignature;)Lnet/minecraft/client/gui/components/ChatComponent$DelayedMessageDeletion;" }, at = { @At(value = "INVOKE", target = "Ljava/util/ListIterator;set(Ljava/lang/Object;)V", shift = At.Shift.BEFORE) }, locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void labyMod$deleteMessage(final yb messageSignature, final CallbackInfoReturnable<Object> cir, final int guiTicks, final ListIterator<ffb> iterator, final ffb message) {
        final xp c = message.c();
        if (c instanceof final VersionedChatMessageComponent messageComponent) {
            final ComponentMapper mapper = Laby.labyAPI().minecraft().componentMapper();
            final ffb deletedMarker = this.d(message);
            final Component mappedDeletedMarker = mapper.fromMinecraftComponent(deletedMarker.c());
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
        final fnf y = this.i.y;
        if (y instanceof final LabyScreenRenderer screenRenderer) {
            cir.setReturnValue(screenRenderer.screen() instanceof ChatInputOverlay);
        }
    }
    
    @Insert(method = { "rescaleChat()V" }, at = @At("TAIL"))
    private void labyMod$chatUpdated(final InsertInfo callback) {
        Laby.fireEvent(this.labyMod$refreshScreenChatScreenUpdateEvent);
    }
    
    @Inject(method = { "storeState" }, at = { @At("HEAD") })
    private void labyMod$storeState(final CallbackInfoReturnable<fhh.b> cir) {
        Laby.references().chatController().storeState();
    }
    
    @Inject(method = { "restoreState" }, at = { @At("TAIL") })
    private void labyMod$restoreState(final fhh.b $$0, final CallbackInfo ci) {
        Laby.references().chatController().restoreState();
    }
    
    @Overwrite
    public boolean e() {
        final fnf y = this.i.y;
        if (!(y instanceof LabyScreenRenderer)) {
            return false;
        }
        final LabyScreenRenderer screenRenderer = (LabyScreenRenderer)y;
        final LabyScreen screen = screenRenderer.screen();
        if (!(screen instanceof ChatInputOverlay)) {
            return false;
        }
        final ChatInputOverlay overlay = (ChatInputOverlay)screen;
        final ScreenInstance mostInnerScreenInstance = overlay.mostInnerScreenInstance();
        if (mostInnerScreenInstance instanceof final VersionedScreenWrapper screenWrapper) {
            final Object versionedScreen = screenWrapper.getVersionedScreen();
            boolean b;
            if (versionedScreen instanceof final flv chatScreen) {
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
    public List<ffb> getMessages() {
        return this.k;
    }
    
    @Override
    public List<ffb.a> getFormattedMessages() {
        return this.l;
    }
    
    @Override
    public void injectFormattedMessages(final int index, final xp component, final ffb message) {
        final ffc tag = message.e();
        int maxWidth = ayz.a(this.f() / this.h());
        if (tag != null && tag.f() != null) {
            maxWidth -= tag.f().c + 4 + 2;
        }
        final List<ayl> formatted = this.labyMod$injectChatMessages((xu)component, maxWidth, this.i.h);
        for (int i = 0; i < formatted.size(); ++i) {
            final ayl sequence = formatted.get(i);
            final boolean end = i == formatted.size() - 1;
            this.l.add(index, new ffb.a(message.b(), sequence, tag, end));
        }
    }
    
    private ChatTrustLevel labyMod$evaluateTrustLevel(final ffc messageTag) {
        if (messageTag == null || messageTag.h() == null) {
            return ChatTrustLevel.SECURE;
        }
        final String lowerCase = messageTag.h().toLowerCase(Locale.ROOT);
        return switch (lowerCase) {
            case "system" -> ChatTrustLevel.SYSTEM;
            case "not secure" -> ChatTrustLevel.NOT_SECURE;
            case "filtered" -> ChatTrustLevel.FILTERED;
            case "modified" -> ChatTrustLevel.MODIFIED;
            default -> ChatTrustLevel.SECURE;
        };
    }
    
    @Override
    public void setMessageMeta(final xp component, final ChatMessageMeta meta) {
        this.labyMod$chatMessageMeta.put(component, meta);
    }
    
    @Override
    public void clearMessageMeta(final xp component) {
        this.labyMod$chatMessageMeta.remove(component);
    }
}
