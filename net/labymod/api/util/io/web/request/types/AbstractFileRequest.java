// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.request.types;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.OpenOption;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Response;
import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;
import java.nio.file.Path;
import net.labymod.api.util.io.web.request.AbstractRequest;

public abstract class AbstractFileRequest<T, R extends AbstractFileRequest<T, R>> extends AbstractRequest<T, R>
{
    protected Path path;
    protected Consumer<Double> percentageConsumer;
    protected Consumer<Path> preDownloadConsumer;
    
    protected AbstractFileRequest(@Nullable final Path path, @Nullable final Consumer<Double> percentageConsumer) {
        this.path = path;
        this.percentageConsumer = percentageConsumer;
    }
    
    public R preDownloadConsumer(final Consumer<Path> preDownloadConsumer) {
        this.preDownloadConsumer = preDownloadConsumer;
        return (R)this;
    }
    
    protected Path download(final Response<T> response, final WebInputStream inputStream) throws IOException {
        if (this.path == null) {
            throw new UnsupportedOperationException("Cannot download file without a path!");
        }
        if (!this.continueDownload()) {
            return this.path;
        }
        Path downloadPath = this.path;
        if (IOUtil.isDirectory(downloadPath)) {
            String header = response.getHeaders().get("Content-Disposition");
            if (header == null) {
                header = response.getHeaders().get("content-disposition");
            }
            final String fileName = this.parseFileNameFromHeader(header);
            if (fileName == null) {
                throw new UnsupportedOperationException("Cannot download file as a directory was provided and no filename was provided in the response headers!");
            }
            downloadPath = downloadPath.resolve(fileName);
            this.path = downloadPath;
        }
        downloadPath = downloadPath.resolveSibling(String.valueOf(downloadPath.getFileName()) + ".tmp");
        if (this.preDownloadConsumer != null) {
            try {
                this.preDownloadConsumer.accept(downloadPath);
            }
            catch (final Exception exception) {
                throw exception;
            }
        }
        if (IOUtil.exists(downloadPath)) {
            IOUtil.delete(downloadPath);
        }
        try (final InputStream fileInputStream = inputStream.getInputStream();
             final OutputStream fileOutputStream = Files.newOutputStream(downloadPath, StandardOpenOption.CREATE_NEW)) {
            int totalLength = 0;
            final byte[] buffer = new byte[512];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, length);
                totalLength += length;
                if (this.percentageConsumer != null) {
                    this.percentageConsumer.accept(100.0 / inputStream.getContentLength() * totalLength);
                }
            }
        }
        IOUtil.copyAndDelete(downloadPath, this.path, StandardCopyOption.REPLACE_EXISTING);
        return this.path;
    }
    
    protected boolean continueDownload() throws IOException {
        return true;
    }
    
    protected R applyRequestDataTo(final AbstractFileRequest<T, R> request) {
        request.path = this.path;
        request.percentageConsumer = this.percentageConsumer;
        return (R)request;
    }
    
    private String parseFileNameFromHeader(final String header) {
        final int utf8NameIndex = header.indexOf("filename*=utf-8''");
        if (utf8NameIndex != -1) {
            final String encodedFileName = header.substring(utf8NameIndex + 17);
            final StringBuilder decodedFileName = new StringBuilder();
            for (int length = encodedFileName.length(), i = 0; i < length; ++i) {
                final char c = encodedFileName.charAt(i);
                if (c == '%') {
                    if (encodedFileName.charAt(i + 3) != '%' || !encodedFileName.startsWith("A7", i + 4)) {
                        final String hexCode = encodedFileName.substring(i + 1, i + 3);
                        final int codePoint = Integer.parseInt(hexCode, 16);
                        decodedFileName.append((char)codePoint);
                    }
                    i += 2;
                }
                else {
                    decodedFileName.append(c);
                }
            }
            return decodedFileName.toString();
        }
        final int fileHeaderIndex = header.indexOf("filename=");
        if (fileHeaderIndex == -1) {
            return null;
        }
        final int startIndex = fileHeaderIndex + 9;
        int endIndex = header.length();
        if (header.indexOf(59, startIndex) != -1) {
            endIndex = header.indexOf(59, startIndex);
        }
        String fileName = header.substring(startIndex, endIndex);
        if (fileName.startsWith("\"") && fileName.endsWith("\"")) {
            fileName = fileName.substring(1, fileName.length() - 1);
        }
        return fileName;
    }
}
