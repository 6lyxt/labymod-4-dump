// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter.models;

import java.util.List;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.chat.autotext.AutoTextServerConfig;
import net.labymod.api.client.chat.autotext.AutoTextEntry;

public class LegacyAutoText
{
    private String message;
    private boolean keyShift;
    private boolean keyCtrl;
    private boolean keyAlt;
    private int keyCode;
    private boolean sendNotInstantly;
    private boolean serverBound;
    private String serverAddress;
    private boolean available;
    
    public AutoTextEntry convert() {
        if (!this.available) {
            return null;
        }
        final Key[] keys = this.convertKeys();
        if (keys.length == 0) {
            return null;
        }
        return new AutoTextEntry(this.message, this.message, false, !this.sendNotInstantly, new AutoTextServerConfig(this.serverBound, this.serverAddress), keys);
    }
    
    private Key[] convertKeys() {
        final Key key = Key.get(this.keyCode);
        if (key.isUnknown()) {
            return new Key[0];
        }
        final List<Key> keys = new ArrayList<Key>();
        if (this.keyShift) {
            keys.add(Key.L_SHIFT);
        }
        if (this.keyCtrl) {
            keys.add(Key.L_CONTROL);
        }
        if (this.keyAlt) {
            keys.add(Key.L_ALT);
        }
        keys.add(key);
        return keys.toArray(new Key[0]);
    }
}
