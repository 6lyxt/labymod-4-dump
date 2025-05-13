// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.file;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.util.function.IoSupplier;
import java.net.URI;

record AddonResource(String name, URI uri, IoSupplier<InputStream> stream) {
    public InputStream openStream() throws IOException {
        return this.stream.get();
    }
}
