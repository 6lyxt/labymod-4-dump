// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.v1_12_2.client.gui.GuiTextFieldAccessor;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.gui.ScrollableGuiScreen;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;

@Mixin({ bkn.class })
public abstract class MixinGuiChat extends blk implements ChatScreenAccessor, ScrollableGuiScreen
{
    @Shadow
    protected bje a;
    private int labyMod$scrollAmount;
    
    @Shadow
    protected abstract void a(final String p0, final boolean p1);
    
    @Shadow
    public abstract void b();
    
    @Redirect(method = { "drawScreen(IIF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;getChatComponent(II)Lnet/minecraft/util/text/ITextComponent;"))
    public hh labyMod$cancelHoveredComponent$drawScreen(final bjb gui, final int x, final int y) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            return null;
        }
        return gui.a(x, y);
    }
    
    @Redirect(method = { "mouseClicked(III)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;getChatComponent(II)Lnet/minecraft/util/text/ITextComponent;"))
    public hh labyMod$cancelHoveredComponent$mouseClicked(final bjb gui, final int x, final int y) {
        if (Laby.labyAPI().config().ingame().advancedChat().enabled().get()) {
            return null;
        }
        return gui.a(x, y);
    }
    
    @Redirect(method = { "drawScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiChat;drawRect(IIIII)V"))
    private void labyMod$preventBackgroundRender(final int left, final int top, final int right, final int bottom, final int color) {
    }
    
    @Inject(method = { "initGui" }, at = { @At("TAIL") })
    public void labyMod$modifyInputBounds(final CallbackInfo ci) {
        this.labyMod$updateInputBounds();
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("HEAD") })
    public void labyMod$modifyInputBounds(final int param1, final int param2, final float param3, final CallbackInfo ci) {
        this.labyMod$updateInputBounds();
    }
    
    private void labyMod$updateInputBounds() {
        final LabyScreen screen = Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen();
        if (!(screen instanceof ChatInputOverlay)) {
            return;
        }
        final DivWidget widget = ((ChatInputOverlay)screen).getInputUnderlay();
        if (widget == null) {
            return;
        }
        widget.backgroundColor().set(Integer.MIN_VALUE);
        final Rectangle rectangle = widget.bounds().rectangle(BoundsType.INNER);
        this.a.a = (int)rectangle.getX() + 2;
        this.a.f = (int)rectangle.getY() + 2;
        ((GuiTextFieldAccessor)this.a).setWidth((int)rectangle.getWidth());
        ((GuiTextFieldAccessor)this.a).setHeight((int)rectangle.getHeight());
    }
    
    public void insertChatText(final String text, final boolean override, final boolean skipInput) {
        this.a(text, override);
    }
    
    @Redirect(method = { "handleMouseInput" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventDWheel()I", remap = false))
    private int labyMod$customScrollHandling() {
        final int delta = this.labyMod$scrollAmount;
        this.labyMod$scrollAmount = 0;
        return delta;
    }
    
    public boolean mouseScrolled(final double delta) {
        this.labyMod$scrollAmount = (int)delta;
        return true;
    }
    
    public String getChatText() {
        return this.a.b();
    }
}
