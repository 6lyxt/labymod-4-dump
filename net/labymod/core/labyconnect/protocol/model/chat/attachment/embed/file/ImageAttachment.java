// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.widget.widgets.screenshot.viewer.ImageViewerWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImage;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import java.util.UUID;
import java.net.URI;
import net.labymod.api.client.gui.icon.Icon;

public class ImageAttachment extends FileAttachment
{
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 100;
    private Icon image;
    
    public ImageAttachment(final URI uri, final String type, final UUID identifier) {
        super(uri, type, identifier);
    }
    
    @Override
    public Icon getIcon() {
        if (this.image != null) {
            return this.image;
        }
        final byte[] fileData = this.getFileData();
        if (fileData == null) {
            return Textures.SpriteCommon.QUESTION_MARK;
        }
        try {
            final GameImage image = Laby.references().gameImageProvider().getImage(new ByteArrayInputStream(fileData));
            final ResourceLocation resourceLocation = Laby.references().resources().resourceLocationFactory().createLabyMod("labyconnect_file/" + String.valueOf(this.identifier));
            image.uploadTextureAt(resourceLocation);
            final Icon icon = Icon.texture(resourceLocation);
            icon.resolution(image.getWidth(), image.getHeight());
            return this.image = icon;
        }
        catch (final Exception e) {
            e.printStackTrace();
            return Textures.SpriteCommon.QUESTION_MARK;
        }
    }
    
    @Override
    public float getWidth() {
        if (this.image == null) {
            return 50.0f;
        }
        final float width = 100.0f * this.image.getAspectRatio();
        return (width > 200.0f) ? 200.0f : width;
    }
    
    @Override
    public float getHeight() {
        if (this.image == null) {
            return 50.0f;
        }
        final float width = 100.0f * this.image.getAspectRatio();
        return (width > 200.0f) ? (200.0f / this.image.getAspectRatio()) : 100.0f;
    }
    
    @Override
    public Component getTitle() {
        return null;
    }
    
    @Override
    public Component getDescription() {
        return null;
    }
    
    @Override
    public boolean isClickable() {
        return true;
    }
    
    @Override
    public void open() {
        if (this.image == null) {
            return;
        }
        final ImageViewerWidget imageViewerWidget = new ImageViewerWidget(this.image);
        imageViewerWidget.displayInOverlay(imageViewerWidget);
    }
    
    @NotNull
    @Override
    public Component toComponent() {
        return Component.text("Image Attachment" + ((this.image == null) ? "" : (" " + this.image.getResolutionWidth() + "x" + this.image.getResolutionHeight())));
    }
}
