// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.network.chat;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.v1_19_3.client.network.chat.contents.KeybindContentsAccessor;
import net.labymod.api.client.component.KeybindComponent;

public class VersionedKeybindComponent extends VersionedBaseComponent<KeybindComponent, st> implements KeybindComponent
{
    public VersionedKeybindComponent(final tf holder) {
        super(holder);
    }
    
    @Override
    public String getKeybind() {
        final st contents = ((VersionedBaseComponent<T, st>)this).getContents();
        if (contents instanceof final tv keybind) {
            return keybind.a();
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
