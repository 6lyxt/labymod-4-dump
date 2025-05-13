// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.options;

public enum ChatVisibility
{
    HIDDEN, 
    SHOWN, 
    COMMANDS_ONLY;
    
    public boolean isMessageVisible(final ChatVisibility messageVisibility) {
        switch (this.ordinal()) {
            case 0: {
                return false;
            }
            case 1: {
                return true;
            }
            case 2: {
                return messageVisibility == ChatVisibility.COMMANDS_ONLY;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this));
            }
        }
    }
}
