// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.misc;

import java.io.IOException;
import java.io.FileOutputStream;
import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.misc.WriteScreenshotEvent;
import java.io.File;

public class WriteScreenshotEventCaller
{
    public static void call(byte[] imageData, final File destination) throws IOException {
        final WriteScreenshotEvent event = Laby.fireEvent(new WriteScreenshotEvent(Phase.PRE, imageData, destination));
        if (!event.isCancelled()) {
            imageData = event.getImage();
            try (final FileOutputStream outputStream = new FileOutputStream(event.getDestination())) {
                outputStream.write(imageData);
            }
        }
        Laby.fireEvent(new WriteScreenshotEvent(Phase.POST, imageData, destination));
    }
}
