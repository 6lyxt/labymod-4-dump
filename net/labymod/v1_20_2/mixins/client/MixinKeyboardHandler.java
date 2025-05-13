// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client;

import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.api.event.client.input.CharacterTypedEvent;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.core.client.input.KeyboardBridge;
import net.labymod.core.generated.DefaultReferenceStorage;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ equ.class })
public class MixinKeyboardHandler
{
    private final ChatScreenUpdateEvent tooltipChatScreenUpdateEvent;
    private Key labyMod$cancelledCharKey;
    @Shadow
    @Final
    private eqv b;
    private Key labyMod$pseudoUnpressedKey;
    
    public MixinKeyboardHandler() {
        this.tooltipChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.ITEM_TOOLTIPS);
    }
    
    @Insert(method = { "keyPress(JIIII)V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$handleKeyPress(final long windowHandle, final int keyCode, final int scancode, final int action, final int mods, final InsertInfo callback) {
        final KeyEvent.State state = switch (action) {
            case 1 -> KeyEvent.State.PRESS;
            case 2 -> KeyEvent.State.HOLDING;
            default -> KeyEvent.State.UNPRESSED;
        };
        final boolean press = state != KeyEvent.State.UNPRESSED;
        Key key;
        if (press) {
            key = DefaultKeyMapper.pressKey(keyCode);
            DefaultKeyMapper.setLastPressed(key);
        }
        else {
            key = DefaultKeyMapper.releaseKey(keyCode);
            DefaultKeyMapper.setLastReleased(key);
        }
        if (key == null || key == Key.NONE) {
            return;
        }
        final InputType inputType = KeyMapper.getInputType(key);
        final KeyEvent keyEvent = Laby.fireEvent(new KeyEvent(state, key));
        if (keyEvent.inputType() == InputType.CHARACTER) {
            if (press && keyEvent.isCancelled()) {
                this.labyMod$cancelledCharKey = key;
            }
            else {
                this.labyMod$cancelledCharKey = null;
            }
        }
        if (keyEvent.isCancelled()) {
            callback.cancel();
            return;
        }
        if (!this.labyMod$handToKeyboardBridge(windowHandle)) {
            return;
        }
        final KeyboardBridge bridge = ((DefaultReferenceStorage)Laby.references()).keyboardBridge();
        if (press) {
            if (!bridge.onKeyPress(key, inputType)) {
                return;
            }
        }
        else if (!bridge.onKeyRelease(key, inputType)) {
            return;
        }
        if (inputType == InputType.CHARACTER) {
            this.labyMod$cancelledCharKey = key;
        }
        callback.cancel();
    }
    
    @Insert(method = { "charTyped(JII)V" }, at = @At("TAIL"), cancellable = true)
    private void labyMod$resetPseudoUnpressedKey(final long windowHandle, final int codepoint, final int mods, final InsertInfo callback) {
        if (this.labyMod$pseudoUnpressedKey != null) {
            DefaultKeyMapper.setLastPressed((this.labyMod$pseudoUnpressedKey == Key.NONE) ? null : this.labyMod$pseudoUnpressedKey);
            this.labyMod$pseudoUnpressedKey = null;
        }
    }
    
    @Insert(method = { "charTyped(JII)V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$handleCharTyped(final long windowHandle, final int codepoint, final int mods, final InsertInfo callback) {
        Key key = DefaultKeyMapper.lastPressed();
        if (key == null || KeyMapper.getInputType(key) != InputType.CHARACTER) {
            this.labyMod$pseudoUnpressedKey = key;
            DefaultKeyMapper.setLastPressed(Key.NONE);
            key = Key.NONE;
        }
        if (key == this.labyMod$cancelledCharKey) {
            this.labyMod$cancelledCharKey = null;
            callback.cancel();
            return;
        }
        final boolean handToBridge = this.labyMod$handToKeyboardBridge(windowHandle);
        final KeyboardBridge keyboardBridge = ((DefaultReferenceStorage)Laby.references()).keyboardBridge();
        if (Character.charCount(codepoint) == 1) {
            final char character = (char)codepoint;
            if (Laby.fireEvent(new CharacterTypedEvent(key, character)).isCancelled()) {
                callback.cancel();
                return;
            }
            if (handToBridge && keyboardBridge.onCharTyped(key, character)) {
                callback.cancel();
            }
        }
        else {
            boolean consumed = false;
            for (final char character2 : Character.toChars(codepoint)) {
                if (Laby.fireEvent(new CharacterTypedEvent(key, character2)).isCancelled()) {
                    consumed = true;
                }
                else if (handToBridge) {
                    consumed |= keyboardBridge.onCharTyped(key, character2);
                }
            }
            if (consumed) {
                callback.cancel();
            }
        }
    }
    
    @Inject(method = { "handleDebugKeys" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/Options;advancedItemTooltips:Z", shift = At.Shift.AFTER, ordinal = 2) })
    private void labyMod$refreshChatForAdvancedItemTooltips(final int keycode, final CallbackInfoReturnable<Boolean> cir) {
        Laby.fireEvent(this.tooltipChatScreenUpdateEvent);
    }
    
    @Redirect(method = { "keyPress" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;hasControlDown()Z"))
    private boolean labyMod$disableNarratorHotkey() {
        return !Laby.labyAPI().config().hotkeys().disableNarratorHotkey().get() && eyk.p();
    }
    
    private boolean labyMod$handToKeyboardBridge(final long windowHandle) {
        return Laby.labyAPI().minecraft().isIngame() && windowHandle == this.b.aM().i() && this.b.y != null;
    }
}
