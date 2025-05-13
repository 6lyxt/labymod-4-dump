// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.unix;

import net.labymod.core.client.os.unix.credentials.MacOSCredentialsAccessor;
import net.labymod.accountmanager.storage.credentials.CredentialsAccessor;
import java.util.Base64;
import java.util.Collection;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import com.sun.jna.Pointer;
import java.io.IOException;
import ca.weblite.objc.Proxy;
import net.labymod.core.client.os.unix.ns.NSPasteboard;
import net.labymod.core.client.os.unix.ns.ClientUtil;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import ca.weblite.objc.Client;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.os.AbstractOperatingSystemAccessor;

public class MacOSAccessor extends AbstractOperatingSystemAccessor
{
    private static final Logging LOGGER;
    
    @Override
    public void copyToClipboard(final GameImage image) {
        final Client client = Client.getInstance();
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            image.write("png", outputStream);
            outputStream.flush();
            final byte[] imageBuffer = outputStream.toByteArray();
            final Proxy dataProxy = ClientUtil.newNSData(client, imageBuffer);
            final Proxy imageProxy = ClientUtil.newNSImageInitWithData(client, dataProxy);
            final NSPasteboard nsPasteboard = NSPasteboard.generalPasteboard(client);
            nsPasteboard.clearContents();
            nsPasteboard.writeObjects(imageProxy);
        }
        catch (final IOException exception) {
            throw new IllegalStateException("Failed to copy image to Clipboard", exception);
        }
    }
    
    @Override
    public GameImage getImageInClipboard() {
        final Client client = Client.getInstance();
        final NSPasteboard nsPasteboard = NSPasteboard.generalPasteboard(client);
        final Proxy pasteboardItems = nsPasteboard.getPasteboardItems();
        final Proxy nsPasteboardItem = ClientUtil.objectAtIndex(pasteboardItems, 0);
        final Collection<String> entries = ClientUtil.forEach(nsPasteboardItem, "types", Proxy::toString);
        final Proxy imageData = ClientUtil.findFirst(entries, nsPasteboardItem, "dataForType:").orElseThrow(() -> new IllegalStateException("Failed to find image data"));
        final Object imageBytes = imageData.send("bytes", new Object[0]);
        final int length = imageData.sendInt("length", new Object[0]);
        byte[] imageBuffer;
        if (imageBytes instanceof final Pointer pointer) {
            imageBuffer = pointer.getByteArray(0L, length);
        }
        else {
            if (!(imageBytes instanceof byte[])) {
                throw new IllegalStateException("Unsupported image type: " + String.valueOf(imageBytes.getClass()));
            }
            final byte[] buffer = imageBuffer = (byte[])imageBytes;
        }
        try (final ByteArrayInputStream stream = new ByteArrayInputStream(imageBuffer, 0, length)) {
            return GameImage.IMAGE_PROVIDER.getImage(ImageIO.read(stream));
        }
        catch (final IOException exception) {
            throw new IllegalStateException("Failed to read image from Clipboard", exception);
        }
    }
    
    @Override
    public void setWindowIcon(final long handle, final InputStream stream) {
        try {
            final String encodedData = Base64.getEncoder().encodeToString(stream.readAllBytes());
            final Client client = Client.getInstance();
            final Object nsData = ClientUtil.allocNSData(client).send("initWithBase64Encoding:", new Object[] { encodedData });
            final Object nsImage = ClientUtil.allocNSImage(client).send("initWithData:", new Object[] { nsData });
            client.sendProxy("NSApplication", "sharedApplication", new Object[0]).send("setApplicationIconImage:", new Object[] { nsImage });
        }
        catch (final IOException exception) {
            MacOSAccessor.LOGGER.error("Failed to set window icon", exception);
        }
    }
    
    @Override
    public CredentialsAccessor credentialsAccessor() throws UnsupportedOperationException {
        return (CredentialsAccessor)new MacOSCredentialsAccessor();
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
