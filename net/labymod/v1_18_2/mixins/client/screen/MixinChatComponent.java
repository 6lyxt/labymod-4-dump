// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.screen;

import java.util.Iterator;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_18_2.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.api.event.client.chat.ChatClearEvent;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_18_2.client.chat.VersionedChatMessageCharSequence;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.LabyAPI;
import net.labymod.v1_18_2.client.chat.VersionedChatMessageComponent;
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
import net.labymod.v1_18_2.client.chat.VersionedChatComponent;

@Mixin({ eaf.class })
public abstract class MixinChatComponent implements VersionedChatComponent
{
    @Shadow
    @Final
    private dyr c;
    @Shadow
    @Final
    private List<dym<qk>> e;
    @Shadow
    @Final
    private List<dym<aiz>> i;
    private final ChatScreenUpdateEvent refreshScreenChatScreenUpdateEvent;
    private final Collection<qk> chatVisibilityMessages;
    
    public MixinChatComponent() {
        this.refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
        this.chatVisibilityMessages = new HashSet<qk>();
    }
    
    @Shadow
    public abstract int d();
    
    @Shadow
    public abstract double f();
    
    @Insert(method = { "enqueueMessage" }, at = @At("HEAD"))
    private void labyMod$enqueueMessage(final qk message, final InsertInfo ci) {
        this.chatVisibilityMessages.add(message);
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;I)V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$cancelChatRender(final dtm stack, final int i, final InsertInfo callback) {
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
        final dzp font = dyr.D().h;
        if (font instanceof final FontHeightAccessor fontHeightAccessor) {
            return MathHelper.ceil(fontHeightAccessor.getHeight());
        }
        Objects.requireNonNull(font);
        return 9;
    }
    
    @ModifyVariable(method = { "addMessage(Lnet/minecraft/network/chat/Component;I)V" }, at = @At("HEAD"), argsOnly = true)
    private qk labyMod$addMessage(final qk component) {
        final ChatVisibility visibility = this.chatVisibilityMessages.remove(component) ? ChatVisibility.SHOWN : ChatVisibility.COMMANDS_ONLY;
        final LabyAPI labyAPI = Laby.labyAPI();
        final ComponentMapper mapper = labyAPI.minecraft().componentMapper();
        final Component mapped = mapper.fromMinecraftComponent(component);
        final ChatMessage message = labyAPI.chatProvider().chatController().addMessage(ChatMessage.builder().component(mapped).visibility(visibility));
        if (message != null) {
            return (qk)new VersionedChatMessageComponent(message, (qk)mapper.toMinecraftComponent(message.component()));
        }
        return null;
    }
    
    @Redirect(method = { "addMessage(Lnet/minecraft/network/chat/Component;IIZ)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ComponentRenderUtils;wrapComponents(Lnet/minecraft/network/chat/FormattedText;ILnet/minecraft/client/gui/Font;)Ljava/util/List;"))
    private List<aiz> labyMod$injectChatMessages(final qn text, final int maxWidth, final dzp font) {
        final List<aiz> lines = eai.a(text, maxWidth, font);
        if (!(text instanceof VersionedChatMessageComponent)) {
            return lines;
        }
        final ChatMessage message = ((VersionedChatMessageComponent)text).message();
        lines.replaceAll(wrapped -> new VersionedChatMessageCharSequence(message, wrapped));
        return lines;
    }
    
    @Insert(method = { "addMessage(Lnet/minecraft/network/chat/Component;I)V" }, at = @At(value = "HEAD", shift = At.Shift.AFTER), cancellable = true)
    private void labyMod$preventNullMessageAdd(final qk component, final int index, final InsertInfo callback) {
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
        final edw y = this.c.y;
        if (y instanceof final LabyScreenRenderer screenRenderer) {
            cir.setReturnValue(screenRenderer.screen() instanceof ChatInputOverlay);
        }
    }
    
    @Insert(method = { "rescaleChat()V" }, at = @At("TAIL"))
    private void labyMod$chatUpdated(final InsertInfo callback) {
        Laby.fireEvent(this.refreshScreenChatScreenUpdateEvent);
    }
    
    @Override
    public List<dym<qk>> getMessages() {
        return this.e;
    }
    
    @Override
    public List<dym<aiz>> getFormattedMessages() {
        return this.i;
    }
    
    @Override
    public void injectFormattedMessages(final int index, final qk component, final dym<qk> message) {
        final int maxWidth = ajl.b(this.d() / this.f());
        final List<aiz> formatted = this.labyMod$injectChatMessages((qn)component, maxWidth, this.c.h);
        for (final aiz sequence : formatted) {
            this.i.add(index, (dym<aiz>)new dym(message.b(), (Object)sequence, message.c()));
        }
    }
}
