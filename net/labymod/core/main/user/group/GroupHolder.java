// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.group;

import net.labymod.api.Laby;
import java.util.Objects;
import net.labymod.api.user.group.Group;

public class GroupHolder
{
    private int id;
    private Group visibleGroup;
    
    public GroupHolder() {
        this(DefaultGroups.DEFAULT_GROUP);
    }
    
    public GroupHolder(final Group visibleGroup) {
        this.id = visibleGroup.getIdentifier();
        this.visibleGroup = visibleGroup;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
        this.updateVisibleGroup();
    }
    
    public Group visibleGroup() {
        return this.visibleGroup;
    }
    
    public boolean isLegacy() {
        return this.visibleGroup == DefaultGroups.LEGACY_GROUP;
    }
    
    public void refresh() {
        this.updateVisibleGroup();
    }
    
    private void setVisibleGroup(final Group visibleGroup) {
        this.visibleGroup = Objects.requireNonNullElse(visibleGroup, DefaultGroups.DEFAULT_GROUP);
    }
    
    private void updateVisibleGroup() {
        this.setVisibleGroup(Laby.references().groupService().getGroup(this.id));
    }
}
