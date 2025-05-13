// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.tag;

import java.util.List;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public class TaggedObject
{
    private final IntList tags;
    
    public TaggedObject() {
        this((IntList)new IntArrayList());
    }
    
    public TaggedObject(final IntList tags) {
        this.tags = tags;
    }
    
    public void setTag(final Tag tag) {
        final int id = tag.getId();
        if (this.tags.contains(id)) {
            return;
        }
        this.tags.add(id);
    }
    
    public boolean removeTag(final Tag tag) {
        return this.tags.contains(tag.getId()) && this.tags.rem(tag.getId());
    }
    
    public boolean hasTag(final Tag tag) {
        return this.tags.contains(tag.getId());
    }
    
    public IntList getTags() {
        return this.tags;
    }
    
    public void clearTags() {
        this.tags.clear();
    }
    
    public List<Tag> fetchTags() {
        return TagRegistry.getTags(this.tags);
    }
}
