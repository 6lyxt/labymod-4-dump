// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.input;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class CharacterTypedEvent extends DefaultCancellable implements Event
{
    private final Key key;
    private final char character;
    
    public CharacterTypedEvent(final Key key, final char character) {
        this.key = key;
        this.character = character;
    }
    
    @NotNull
    public Key key() {
        return this.key;
    }
    
    public char getCharacter() {
        return this.character;
    }
}
