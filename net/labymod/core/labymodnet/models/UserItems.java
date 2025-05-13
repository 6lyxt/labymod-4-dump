// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserItems
{
    private final List<Cosmetic> cosmetics;
    private final List<Emote> emotes;
    
    public UserItems() {
        this.cosmetics = Collections.emptyList();
        this.emotes = new ArrayList<Emote>();
    }
    
    public List<Cosmetic> getCosmetics() {
        return this.cosmetics;
    }
    
    public List<Emote> getEmotes() {
        return this.emotes;
    }
}
