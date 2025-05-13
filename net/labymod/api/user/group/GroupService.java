// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.user.group;

import java.util.Collection;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface GroupService
{
    Group getGroup(final int p0);
    
    Collection<Group> getGroups();
}
