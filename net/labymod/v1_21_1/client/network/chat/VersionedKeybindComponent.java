// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.network.chat;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.v1_21_1.client.network.chat.contents.KeybindContentsAccessor;
import net.labymod.api.client.component.KeybindComponent;

public class VersionedKeybindComponent extends VersionedBaseComponent<KeybindComponent, xa> implements KeybindComponent
{
    public VersionedKeybindComponent(final xn holder) {
        super(holder);
    }
    
    @Override
    public String getKeybind() {
        final xa contents = ((VersionedBaseComponent<T, xa>)this).getContents();
        if (contents instanceof final yd keybind) {
            return keybind.b();
        }
        return contents.toString();
    }
    
    @Override
    public KeybindComponent keybind(final String keybind) {
        final KeybindContentsAccessor contents = ((VersionedBaseComponent<T, KeybindContentsAccessor>)this).getContents();
        if (contents instanceof KeybindContentsAccessor) {
            final KeybindContentsAccessor keybindContents = contents;
            keybindContents.setKeybind(keybind);
            return this;
        }
        return this;
    }
    
    @Override
    public KeybindComponent plainCopy() {
        return ComponentService.keybindComponent(this.getKeybind());
    }
    
    @Override
    public Component resolveKeybind() {
        final KeybindContentsAccessor contents = ((VersionedBaseComponent<T, KeybindContentsAccessor>)this).getContents();
        if (contents instanceof KeybindContentsAccessor) {
            final KeybindContentsAccessor keybindContents = contents;
            return keybindContents.resolveKeybind();
        }
        return Component.text(this.getKeybind());
    }
}
