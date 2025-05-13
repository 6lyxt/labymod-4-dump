// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.resources;

import org.spongepowered.asm.mixin.Overwrite;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import com.google.common.collect.Lists;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.io.File;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ceu.class })
public class MixinResourcePackRepository
{
    @Shadow
    @Final
    private File h;
    @Shadow
    @Final
    private static Logger c;
    
    @Overwrite
    private void m() {
        if (!this.h.isDirectory()) {
            return;
        }
        try {
            final List<File> files = Lists.newArrayList((Iterable)FileUtils.listFiles(this.h, TrueFileFilter.TRUE, (IOFileFilter)null));
            Collections.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            int tries = 0;
            for (final File file : files) {
                if (tries++ >= 10) {
                    MixinResourcePackRepository.c.info("Deleting old server resource pack {}", (Object)file.getName());
                    FileUtils.deleteQuietly(file);
                }
            }
        }
        catch (final IllegalArgumentException exception) {
            MixinResourcePackRepository.c.error("Error while deleting old server resource pack : {}", (Object)exception.getMessage());
        }
    }
}
