// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.metadata.MetadataExtension;

public interface ScreenWrapper extends ScreenInstance, MetadataExtension
{
    void onCloseScreen();
    
    boolean isPauseScreen();
    
    Object getVersionedScreen();
    
    boolean isActivity();
    
    Activity asActivity();
    
    @Referenceable
    public interface Factory
    {
        ScreenWrapper create(final Object p0);
    }
}
