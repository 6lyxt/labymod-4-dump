// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.util;

import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.ImageWriteParam;
import java.util.List;
import java.awt.image.RenderedImage;
import javax.imageio.IIOImage;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import java.io.OutputStream;
import java.util.Iterator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.IIOException;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.ImageReader;
import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import net.labymod.api.util.io.IOUtil;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.Map;

public class ImageCodec
{
    private final Map<String, String> metaData;
    private SourceImage sourceImage;
    private ImagePostProcessor imagePostProcessor;
    
    public ImageCodec(final File file) throws IOException {
        this(file.toPath());
        this.sourceImage = new SourceImage(file);
    }
    
    public ImageCodec(final Path path) throws IOException {
        this(IOUtil.newInputStream(path));
        this.sourceImage = new SourceImage(path);
    }
    
    public ImageCodec(final BufferedImage image) throws IOException {
        this.metaData = new HashMap<String, String>();
        this.sourceImage = new SourceImage(image);
    }
    
    public ImageCodec(final byte[] data) throws IOException {
        this(new ByteArrayInputStream(data));
        this.sourceImage = new SourceImage(data);
    }
    
    public ImageCodec(final InputStream inputStream) throws IOException {
        this.metaData = new HashMap<String, String>();
        ImageIO.setUseCache(false);
        try {
            final ImageInputStream stream = ImageIO.createImageInputStream(inputStream);
            final ImageReader reader = ImageIO.getImageReadersByFormatName("png").next();
            reader.setInput(stream);
            final IIOMetadata metadata = reader.getImageMetadata(0);
            final IIOMetadataNode root = (IIOMetadataNode)metadata.getAsTree("javax_imageio_1.0");
            final NodeList entries = root.getElementsByTagName("TextEntry");
            for (int i = 0; i < entries.getLength(); ++i) {
                final IIOMetadataNode node = (IIOMetadataNode)entries.item(i);
                final String keyword = node.getAttribute("keyword");
                final String value = node.getAttribute("value");
                this.metaData.put(keyword, value);
            }
        }
        catch (final IIOException e) {
            System.out.println("Failed to read meta data from png file: " + e.getMessage());
        }
        ImageIO.setUseCache(true);
    }
    
    private IIOMetadataNode compileNode() {
        final IIOMetadataNode imageNode = new IIOMetadataNode("javax_imageio_png_1.0");
        final IIOMetadataNode textNode = new IIOMetadataNode("tEXt");
        for (final Map.Entry<String, String> entry : this.metaData.entrySet()) {
            final IIOMetadataNode textEntry = new IIOMetadataNode("tEXtEntry");
            textEntry.setAttribute("keyword", entry.getKey());
            textEntry.setAttribute("value", entry.getValue());
            textNode.appendChild(textEntry);
        }
        imageNode.appendChild(textNode);
        return imageNode;
    }
    
    public void compileTo(final OutputStream out) throws IOException {
        final ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
        final ImageWriteParam writeParam = writer.getDefaultWriteParam();
        final ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(1);
        final IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
        if (metadata != null) {
            metadata.mergeTree("javax_imageio_png_1.0", this.compileNode());
        }
        try (final ImageOutputStream stream = ImageIO.createImageOutputStream(out)) {
            writer.setOutput(stream);
            writer.write(metadata, new IIOImage(this.readImage(), null, metadata), writeParam);
        }
    }
    
    public byte[] compileWithMeta() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.compileTo(baos);
        return baos.toByteArray();
    }
    
    public byte[] compile(final String format) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(this.readImage(), format, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    
    public void compileToFileWithMeta(final File file) throws IOException {
        try (final FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(this.compileWithMeta());
        }
    }
    
    public void compileToFile(final File file, final String format) throws IOException {
        try (final FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(this.compile(format));
        }
    }
    
    public BufferedImage readImage() throws IOException {
        final BufferedImage image = this.sourceImage.get();
        return (this.imagePostProcessor == null) ? image : this.imagePostProcessor.process(image);
    }
    
    public void setImagePostProcessor(final ImagePostProcessor imagePostProcessor) {
        this.imagePostProcessor = imagePostProcessor;
    }
    
    public void set(final String key, final String value) {
        this.metaData.put(key, value);
    }
    
    public void remove(final String key) {
        this.metaData.remove(key);
    }
    
    public boolean has(final String key) {
        return this.metaData.containsKey(key);
    }
    
    public String get(final String key) {
        return this.metaData.get(key);
    }
    
    public Map<String, String> map() {
        return this.metaData;
    }
    
    public static String getAvailableFormat(final String... formats) {
        for (final String format : formats) {
            Label_0071: {
                if (ImageIO.getImageWritersByFormatName(format).hasNext()) {
                    try {
                        final BufferedImage testImage = new BufferedImage(1, 1, 2);
                        ImageIO.write(testImage, format, new ByteArrayOutputStream());
                    }
                    catch (final Throwable e) {
                        break Label_0071;
                    }
                    return format;
                }
            }
        }
        return null;
    }
    
    private static class SourceImage
    {
        private final Object object;
        
        public SourceImage(final Object object) {
            this.object = object;
        }
        
        public BufferedImage get() throws IOException {
            if (this.object instanceof final Path path) {
                return ImageIO.read(IOUtil.toFile(path));
            }
            if (this.object instanceof final File file) {
                return ImageIO.read(file);
            }
            if (this.object instanceof final byte[]array) {
                return ImageIO.read(new ByteArrayInputStream(array));
            }
            if (this.object instanceof final InputStream inputStream) {
                return ImageIO.read(inputStream);
            }
            if (this.object instanceof final BufferedImage bufferedImage) {
                return bufferedImage;
            }
            return null;
        }
    }
    
    public interface ImagePostProcessor
    {
        BufferedImage process(final BufferedImage p0);
    }
}
