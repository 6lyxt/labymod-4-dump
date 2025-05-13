// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.resources.pack;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;
import java.nio.file.Path;
import java.io.IOException;
import net.labymod.api.util.StringUtil;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.function.Consumer;
import java.io.File;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.resources.pack.ResourceLister;

@Mixin({ bnd.class })
public abstract class MixinFolderResourcePack extends bmx implements ResourceLister
{
    public MixinFolderResourcePack(final File lvt_1_1_) {
        super(lvt_1_1_);
    }
    
    public void listResources(final String namespace, final String baseDirectory, final Consumer<jy> locationConsumer) {
        final Path root = this.a.toPath();
        final Path assetsDirectory = root.resolve("assets").resolve(namespace);
        if (!Files.exists(assetsDirectory, new LinkOption[0])) {
            return;
        }
        try {
            final Path startPath = assetsDirectory.resolve(baseDirectory);
            try (final Stream<Path> files = Files.find(startPath, Integer.MAX_VALUE, (path, attributes) -> attributes.isRegularFile(), new FileVisitOption[0])) {
                files.forEach(resource -> {
                    final String path = StringUtil.join(assetsDirectory.relativize(resource), "/");
                    locationConsumer.accept(new jy(namespace, path));
                    return;
                });
            }
        }
        catch (final IOException ex) {}
    }
}
