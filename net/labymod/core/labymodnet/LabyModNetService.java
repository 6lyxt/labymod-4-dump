// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet;

import net.labymod.core.labymodnet.models.ChangeResponse;
import java.util.function.Consumer;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.labymodnet.models.CosmeticOptions;
import net.labymod.core.labymodnet.models.UserItems;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface LabyModNetService
{
    void reload();
    
    State getState();
    
    UserItems getUserItems();
    
    CosmeticOptions getCosmeticOptions();
    
    void toggleCosmetic(final Cosmetic p0, final boolean p1, final Consumer<ChangeResponse> p2);
    
    void updateCosmetic(final Cosmetic p0, final Consumer<ChangeResponse> p1);
    
    void updateEmotes(final Consumer<ChangeResponse> p0);
    
    public enum State
    {
        LOADING, 
        NOT_PREMIUM, 
        NOT_CONNECTED, 
        ERROR, 
        ACCOUNT_CHANGED, 
        OK;
    }
}
