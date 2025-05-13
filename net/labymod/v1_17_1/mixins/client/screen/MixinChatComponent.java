// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.screen;

import java.util.Iterator;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.v1_17_1.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.api.event.client.chat.ChatClearEvent;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_17_1.client.chat.VersionedChatMessageCharSequence;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.LabyAPI;
import net.labymod.v1_17_1.client.chat.VersionedChatMessageComponent;
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
import net.labymod.v1_17_1.client.chat.VersionedChatComponent;

@Mixin({ dxb.class })
public abstract class MixinChatComponent implements VersionedChatComponent
{
    @Shadow
    @Final
    private dvp c;
    @Shadow
    @Final
    private List<dvk<os>> e;
    @Shadow
    @Final
    private List<dvk<ags>> i;
    private final ChatScreenUpdateEvent refreshScreenChatScreenUpdateEvent;
    private final Collection<os> chatVisibilityMessages;
    
    public MixinChatComponent() {
        this.refreshScreenChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.REFRESH_SCREEN);
        this.chatVisibilityMessages = new HashSet<os>();
    }
    
    @Shadow
    public abstract int d();
    
    @Shadow
    public abstract double f();
    
    @Insert(method = { "enqueueMessage" }, at = @At("HEAD"))
    private void labyMod$enqueueMessage(final os message, final InsertInfo ci) {
        this.chatVisibilityMessages.add(message);
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;I)V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$cancelChatRender(final dql stack, final int i, final InsertInfo callback) {
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
        final dwl font = dvp.C().h;
        if (font instanceof final FontHeightAccessor fontHeightAccessor) {
            return MathHelper.ceil(fontHeightAccessor.getHeight());
        }
        Objects.requireNonNull(font);
        return 9;
    }
    
    @ModifyVariable(method = { "addMessage(Lnet/minecraft/network/chat/Component;I)V" }, at = @At("HEAD"), argsOnly = true)
    private os labyMod$addMessage(final os component) {
        final ChatVisibility visibility = this.chatVisibilityMessages.remove(component) ? ChatVisibility.SHOWN : ChatVisibility.COMMANDS_ONLY;
        final LabyAPI labyAPI = Laby.labyAPI();
        final ComponentMapper mapper = labyAPI.minecraft().componentMapper();
        final Component mapped = mapper.fromMinecraftComponent(component);
        final ChatMessage message = labyAPI.chatProvider().chatController().addMessage(ChatMessage.builder().component(mapped).visibility(visibility));
        if (message != null) {
            return (os)new VersionedChatMessageComponent(message, (os)mapper.toMinecraftComponent(message.component()));
        }
        return null;
    }
    
    @Redirect(method = { "addMessage(Lnet/minecraft/network/chat/Component;IIZ)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ComponentRenderUtils;wrapComponents(Lnet/minecraft/network/chat/FormattedText;ILnet/minecraft/client/gui/Font;)Ljava/util/List;"))
    private List<ags> labyMod$injectChatMessages(final ov text, final int maxWidth, final dwl font) {
        final List<ags> lines = dxe.a(text, maxWidth, font);
        if (!(text instanceof VersionedChatMessageComponent)) {
            return lines;
        }
        final ChatMessage message = ((VersionedChatMessageComponent)text).message();
        lines.replaceAll(wrapped -> new VersionedChatMessageCharSequence(message, wrapped));
        return lines;
    }
    
    @Insert(method = { "addMessage(Lnet/minecraft/network/chat/Component;I)V" }, at = @At(value = "HEAD", shift = At.Shift.AFTER), cancellable = true)
    private void labyMod$preventNullMessageAdd(final os component, final int index, final InsertInfo callback) {
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
        final eaq y = this.c.y;
        if (y instanceof final LabyScreenRenderer screenRenderer) {
            cir.setReturnValue(screenRenderer.screen() instanceof ChatInputOverlay);
        }
    }
    
    @Insert(method = { "rescaleChat()V" }, at = @At("TAIL"))
    private void labyMod$chatUpdated(final InsertInfo callback) {
        Laby.fireEvent(this.refreshScreenChatScreenUpdateEvent);
    }
    
    @Override
    public List<dvk<os>> getMessages() {
        return this.e;
    }
    
    @Override
    public List<dvk<ags>> getFormattedMessages() {
        return this.i;
    }
    
    @Override
    public void injectFormattedMessages(final int index, final os component, final dvk<os> message) {
        final int maxWidth = ahb.b(this.d() / this.f());
        final List<ags> formatted = this.labyMod$injectChatMessages((ov)component, maxWidth, this.c.h);
        for (final ags sequence : formatted) {
            this.i.add(index, (dvk<ags>)new dvk(message.b(), (Object)sequence, message.c()));
        }
    }
}
