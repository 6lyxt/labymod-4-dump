// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.pack;

import java.util.Collection;

record IncompatibleResourcePack(String name, Collection<String> ignoredFiles, Collection<String> unsupportedFiles) {
    public boolean isCompatible() {
        return this.unsupportedFiles.isEmpty();
    }
    
    public boolean isIncompatible() {
        return !this.isCompatible();
    }
}
