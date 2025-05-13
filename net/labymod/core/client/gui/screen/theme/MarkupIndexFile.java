// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme;

import java.util.ArrayList;
import java.util.List;

public class MarkupIndexFile
{
    private List<String> markups;
    
    public MarkupIndexFile() {
        this.markups = new ArrayList<String>();
    }
    
    public List<String> markups() {
        return this.markups;
    }
    
    public void markups(final List<String> markups) {
        this.markups = markups;
    }
}
