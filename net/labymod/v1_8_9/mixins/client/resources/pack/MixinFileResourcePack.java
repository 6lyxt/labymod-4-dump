// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.resources.pack;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.function.Consumer;
import org.spongepowered.asm.mixin.Shadow;
import java.io.IOException;
import java.util.zip.ZipFile;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.resources.pack.ResourceLister;

@Mixin({ bnc.class })
public abstract class MixinFileResourcePack implements ResourceLister
{
    @Shadow
    protected abstract ZipFile d() throws IOException;
    
    @Override
    public void listResources(final String namespace, final String baseDirectory, final Consumer<jy> locationConsumer) {
        ZipFile file;
        try {
            file = this.d();
        }
        catch (final IOException ignored) {
            return;
        }
        final String packPath = "assets/" + namespace;
        final String path = packPath + baseDirectory;
        final Enumeration<? extends ZipEntry> entries = file.entries();
        while (entries.hasMoreElements()) {
            final ZipEntry entry = (ZipEntry)entries.nextElement();
            if (entry.isDirectory()) {
                continue;
            }
            final String name = entry.getName();
            if (name.endsWith(".mcmeta") || !name.startsWith(path)) {
                continue;
            }
            final String locationPath = name.substring(packPath.length());
            locationConsumer.accept(new jy(namespace, locationPath));
        }
    }
}
