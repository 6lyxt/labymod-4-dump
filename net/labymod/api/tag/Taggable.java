// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.tag;

public interface Taggable
{
    TaggedObject taggedObject();
    
    default void setTag(final Tag tag) {
        this.taggedObject().setTag(tag);
    }
    
    default void setTag(final Tag... tags) {
        final TaggedObject taggedObject = this.taggedObject();
        for (final Tag tag : tags) {
            taggedObject.setTag(tag);
        }
    }
    
    default boolean removeTag(final Tag tag) {
        return this.taggedObject().removeTag(tag);
    }
    
    default void removeTag(final Tag... tags) {
        final TaggedObject taggedObject = this.taggedObject();
        for (final Tag tag : tags) {
            taggedObject.removeTag(tag);
        }
    }
    
    default boolean hasTag(final Tag tag) {
        return this.taggedObject().hasTag(tag);
    }
    
    default void clearTags() {
        this.taggedObject().clearTags();
    }
}
