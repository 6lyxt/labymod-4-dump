// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.autotext;

import java.util.List;
import java.util.function.Predicate;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface AutoTextService
{
    void addEntry(final AutoTextEntry p0);
    
    boolean removeEntry(final AutoTextEntry p0);
    
    boolean removeEntry(final Predicate<AutoTextEntry> p0);
    
    List<AutoTextEntry> getEntries();
}
