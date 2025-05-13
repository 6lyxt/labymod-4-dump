// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.tag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.function.BiFunction;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class TagRegistry
{
    static final AtomicInteger ID_COUNTER;
    private static final Map<String, Tag> TAGS;
    
    static Tag getOrRegister(final String namespace, final String name, final BiFunction<String, String, Tag> tagFactory) {
        final String key = namespace + ":" + name;
        Tag tag = TagRegistry.TAGS.get(key);
        if (tag == null) {
            final Tag newTag = tagFactory.apply(namespace, name);
            TagRegistry.TAGS.put(key, newTag);
            tag = newTag;
        }
        return tag;
    }
    
    static List<Tag> getTags(final IntList ids) {
        final List<Tag> tags = new ArrayList<Tag>();
        for (int index = 0; index < ids.size(); ++index) {
            final int tagId = ids.getInt(index);
            final Tag tag = findTag(tagId);
            if (tag != null) {
                tags.add(tag);
            }
        }
        return tags;
    }
    
    private static Tag findTag(final int id) {
        for (final Tag tag : TagRegistry.TAGS.values()) {
            if (tag.getId() == id) {
                return tag;
            }
        }
        return null;
    }
    
    static {
        ID_COUNTER = new AtomicInteger(0);
        TAGS = new HashMap<String, Tag>();
    }
}
