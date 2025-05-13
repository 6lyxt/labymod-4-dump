// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.util;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.io.IOException;
import net.labymod.core.event.client.misc.WriteScreenshotEventCaller;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import net.labymod.api.Laby;
import net.labymod.api.event.client.misc.CaptureScreenshotEvent;
import java.io.File;
import java.awt.image.RenderedImage;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ avj.class })
public class MixinScreenShotHelper
{
    @Redirect(method = { "saveScreenshot(Ljava/io/File;Ljava/lang/String;IILnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/util/IChatComponent;" }, at = @At(value = "INVOKE", target = "Ljavax/imageio/ImageIO;write(Ljava/awt/image/RenderedImage;Ljava/lang/String;Ljava/io/File;)Z"))
    private static boolean labyMod$write(final RenderedImage im, final String formatName, final File output) throws IOException {
        Laby.fireEvent(new CaptureScreenshotEvent(output));
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(im, formatName, byteArrayOutputStream);
        final byte[] bytes = byteArrayOutputStream.toByteArray();
        WriteScreenshotEventCaller.call(bytes, output);
        return false;
    }
}
