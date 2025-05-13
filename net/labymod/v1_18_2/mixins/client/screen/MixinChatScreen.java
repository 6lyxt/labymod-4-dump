// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.screen;

import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.core.client.accessor.gui.CommandSuggestionsAccessor;
import net.labymod.core.client.accessor.gui.AbstractWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;

@Mixin({ ecs.class })
public abstract class MixinChatScreen extends edw implements ChatScreenAccessor
{
    @Shadow
    protected eam b;
    @Shadow
    eah q;
    
    public MixinChatScreen(final qk param0) {
        super(param0);
    }
    
    @Shadow
    protected abstract void a(final String p0, final boolean p1);
    
    @Shadow
    protected abstract void b();
    
    @Redirect(method = { "render", "mouseClicked" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;getClickedComponentStyleAt(DD)Lnet/minecraft/network/chat/Style;"))
    public qu cancelHoveredComponent(final eaf chatComponent, final double x, final double y) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            return null;
        }
        return chatComponent.b(x, y);
    }
    
    @Inject(method = { "mouseScrolled" }, at = { @At(value = "RETURN", ordinal = 1) }, cancellable = true)
    public void cancelMouseScrolled(final double $$0, final double $$1, final double $$2, final CallbackInfoReturnable<Boolean> cir) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            cir.setReturnValue((Object)false);
        }
    }
    
    @Redirect(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/ChatScreen;fill(Lcom/mojang/blaze3d/vertex/PoseStack;IIIII)V"))
    private void labyMod$preventBackgroundRender(final dtm poseStack, final int left, final int top, final int right, final int bottom, final int color) {
    }
    
    @Inject(method = { "init" }, at = { @At("TAIL") })
    public void labyMod$modifyInputBounds(final CallbackInfo ci) {
        this.labyMod$updateInputBounds();
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    public void labyMod$modifyInputBounds(final dtm param0, final int param1, final int param2, final float param3, final CallbackInfo ci) {
        this.labyMod$updateInputBounds();
    }
    
    private void labyMod$updateInputBounds() {
        final LabyScreen screen = Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen();
        if (!(screen instanceof ChatInputOverlay)) {
            return;
        }
        final ChatInputOverlay overlay = (ChatInputOverlay)screen;
        final DivWidget widget = overlay.getInputUnderlay();
        if (widget == null) {
            return;
        }
        widget.backgroundColor().set(this.e.l.a(Integer.MIN_VALUE));
        final Rectangle rectangle = widget.bounds().rectangle(BoundsType.INNER);
        this.b.l = (int)rectangle.getX() + 2;
        this.b.m = (int)rectangle.getY() + 2;
        this.b.b((int)rectangle.getWidth());
        ((AbstractWidgetAccessor)this.b).setHeight((int)rectangle.getHeight());
        ((CommandSuggestionsAccessor)this.q).setBottomY((int)(rectangle.getY() - 2.0f));
    }
    
    public void insertChatText(final String text, final boolean override, final boolean skipInput) {
        this.a(text, override);
    }
    
    public String getChatText() {
        return this.b.b();
    }
}
