// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.isolated;

import java.util.ArrayList;
import java.util.List;

public class IsolatedLibraryManifest
{
    private final List<IsolatedLibrary> libraries;
    
    public IsolatedLibraryManifest() {
        this.libraries = new ArrayList<IsolatedLibrary>();
    }
    
    public List<IsolatedLibrary> getLibraries() {
        return this.libraries;
    }
}
