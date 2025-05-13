// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.meta;

import org.jetbrains.annotations.NotNull;

public class LinkMeta implements Comparable<LinkMeta>
{
    private final String path;
    private final int priority;
    private final Class<?> definer;
    
    public LinkMeta(final String path, final int priority, final Class<?> definer) {
        this.path = path;
        this.priority = priority;
        this.definer = definer;
    }
    
    public LinkMeta(final String path, final Class<?> definer) {
        this(path, 0, definer);
    }
    
    public String getPath() {
        return this.path;
    }
    
    public Class<?> getDefiner() {
        return this.definer;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    @Override
    public int compareTo(@NotNull final LinkMeta o) {
        return Integer.compare(this.priority, o.priority);
    }
}
