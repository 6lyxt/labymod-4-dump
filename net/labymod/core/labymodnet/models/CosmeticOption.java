// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.models;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class CosmeticOption
{
    private final List<CosmeticOptionEntry> options;
    private Type type;
    
    public CosmeticOption() {
        this.options = new ArrayList<CosmeticOptionEntry>();
        this.type = Type.HIDDEN;
    }
    
    public CosmeticOptionEntry first() {
        return this.options.get(0);
    }
    
    public Collection<CosmeticOptionEntry> options() {
        return this.options;
    }
    
    public Type type() {
        return this.type;
    }
    
    public void push(final CosmeticOptionEntry entry) {
        this.options.add(entry);
        final int length = this.options.size();
        final String name = entry.getName();
        if (length == 2 && name != null && (name.equals("on") || name.equals("off"))) {
            this.type = Type.CHECKBOX;
        }
        else if (length >= 2) {
            this.type = Type.DROPDOWN;
        }
    }
    
    public enum Type
    {
        HIDDEN, 
        DROPDOWN, 
        CHECKBOX;
    }
}
