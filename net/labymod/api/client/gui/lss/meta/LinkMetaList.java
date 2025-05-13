// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.meta;

import java.util.ArrayList;
import java.util.List;

public class LinkMetaList
{
    private final List<LinkReference> links;
    
    public LinkMetaList() {
        this.links = new ArrayList<LinkReference>();
    }
    
    public List<LinkReference> getLinks() {
        return this.links;
    }
}
