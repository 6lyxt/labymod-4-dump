// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gui.screen;

import java.util.function.Consumer;

public class VersionedFunctionalConfirmScreen extends blk
{
    private final int buttonId;
    private final Consumer<Boolean> consumer;
    
    public VersionedFunctionalConfirmScreen(final int buttonId, final Consumer<Boolean> consumer) {
        this.buttonId = buttonId;
        this.consumer = consumer;
    }
    
    public void a(final boolean open, final int buttonId) {
        if (buttonId == this.buttonId) {
            this.consumer.accept(open);
        }
    }
}
