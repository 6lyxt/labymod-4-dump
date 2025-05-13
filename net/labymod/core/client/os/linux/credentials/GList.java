// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.linux.credentials;

import java.util.Arrays;
import java.util.List;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class GList extends Structure
{
    public Pointer data;
    public Pointer next;
    
    public GList(final Pointer p) {
        super(p);
        this.read();
    }
    
    protected List<String> getFieldOrder() {
        return Arrays.asList("data", "next");
    }
}
