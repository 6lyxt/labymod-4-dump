// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.screen;

import java.util.Iterator;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_16_5.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.api.event.client.chat.ChatClearEvent;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_16_5.client.chat.VersionedChatMessageCharSequence;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.LabyAPI;
import net.labymod.v1_16_5.client.chat.VersionedChatMessageComponent;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.options.ChatVisibility;
import java.util.Objects;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.font.FontHeightAccessor;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import java.util.HashSet;
import java.util.Collection;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_16_5.client.chat.VersionedChatComponent;

@Mixin({ dlk.class })
public abstract class MixinChatComponent implements VersionedChatComponent
{
    @Shadow
    @Final
    private djz b;
    @Shadow
    @Final
    private List<dju<nr>> d;
    @Shadow
    @Final
    private List<dju<afa>> e;
    private final ChatScreenUpdateEvent refreshScreenChatScreenUpdateEvent;
    private final Collection<nr> chatVisibilityMessages;
    
    public MixinChatComponent() {
        this.refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
        this.chatVisibilityMessages = new HashSet<nr>();
    }
    
    @Shadow
    public abstract int d();
    
    @Shadow
    public abstract double f();
    
    @Insert(method = { "enqueueMessage" }, at = @At("HEAD"))
    private void labyMod$enqueueMessage(final nr message, final InsertInfo ci) {
        this.chatVisibilityMessages.add(message);
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;I)V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$cancelChatRender(final dfm stack, final int i, final InsertInfo callback) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            callback.cancel();
        }
    }
    
    @ModifyConstant(method = { "render" }, constant = { @Constant(doubleValue = 9.0) })
    private double labyMod$modifyChatTextHeight(final double value) {
        return this.getFontHeight();
    }
    
    @ModifyConstant(method = { "render" }, constant = { @Constant(doubleValue = -8.0) })
    private double labyMod$modifyChatTextHeight0(final double value) {
        return -(this.getFontHeight() - 1.0);
    }
    
    @ModifyConstant(method = { "render" }, constant = { @Constant(intValue = 9) })
    private int labyMod$modifyChatBackgroundHeight(final int value) {
        return this.getFontHeight();
    }
    
    private int getFontHeight() {
        final dku font = djz.C().g;
        if (font instanceof final FontHeightAccessor fontHeightAccessor) {
            return MathHelper.ceil(fontHeightAccessor.getHeight());
        }
        Objects.requireNonNull(font);
        return 9;
    }
    
    @ModifyVariable(method = { "addMessage(Lnet/minecraft/network/chat/Component;I)V" }, at = @At("HEAD"), argsOnly = true)
    private nr labyMod$addMessage(final nr component) {
        final ChatVisibility visibility = this.chatVisibilityMessages.remove(component) ? ChatVisibility.SHOWN : ChatVisibility.COMMANDS_ONLY;
        final LabyAPI labyAPI = Laby.labyAPI();
        final ComponentMapper mapper = labyAPI.minecraft().componentMapper();
        final Component mapped = mapper.fromMinecraftComponent(component);
        final ChatMessage message = labyAPI.chatProvider().chatController().addMessage(ChatMessage.builder().component(mapped).visibility(visibility));
        if (message != null) {
            return (nr)new VersionedChatMessageComponent(message, (nr)mapper.toMinecraftComponent(message.component()));
        }
        return null;
    }
    
    @Redirect(method = { "addMessage(Lnet/minecraft/network/chat/Component;IIZ)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ComponentRenderUtils;wrapComponents(Lnet/minecraft/network/chat/FormattedText;ILnet/minecraft/client/gui/Font;)Ljava/util/List;"))
    private List<afa> labyMod$injectChatMessages(final nu text, final int maxWidth, final dku font) {
        final List<afa> lines = dln.a(text, maxWidth, font);
        if (!(text instanceof VersionedChatMessageComponent)) {
            return lines;
        }
        final ChatMessage message = ((VersionedChatMessageComponent)text).message();
        lines.replaceAll(wrapped -> new VersionedChatMessageCharSequence(message, wrapped));
        return lines;
    }
    
    @Insert(method = { "addMessage(Lnet/minecraft/network/chat/Component;I)V" }, at = @At(value = "HEAD", shift = At.Shift.AFTER), cancellable = true)
    private void labyMod$preventNullMessageAdd(final nr component, final int index, final InsertInfo callback) {
        if (component == null) {
            callback.cancel();
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
        final dot screen = this.b.y;
        if (screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            cir.setReturnValue(labyScreenRenderer.screen() instanceof ChatInputOverlay);
        }
    }
    
    @Insert(method = { "rescaleChat()V" }, at = @At("TAIL"))
    private void labyMod$chatUpdated(final InsertInfo callback) {
        Laby.fireEvent(this.refreshScreenChatScreenUpdateEvent);
    }
    
    @Override
    public List<dju<nr>> getMessages() {
        return this.d;
    }
    
    @Override
    public List<dju<afa>> getFormattedMessages() {
        return this.e;
    }
    
    @Override
    public void injectFormattedMessages(final int index, final nr component, final dju<nr> message) {
        final int maxWidth = afm.c(this.d() / this.f());
        final List<afa> formatted = this.labyMod$injectChatMessages((nu)component, maxWidth, this.b.g);
        for (final afa sequence : formatted) {
            this.e.add(index, (dju<afa>)new dju(message.b(), (Object)sequence, message.c()));
        }
    }
}
