// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web;

import java.io.IOException;
import java.io.InputStream;

public class WebInputStream extends InputStream
{
    private final InputStream inputStream;
    private final int contentLength;
    
    public WebInputStream(final InputStream inputStream) {
        this(inputStream, 0);
    }
    
    public WebInputStream(final InputStream inputStream, final int contentLength) {
        this.inputStream = inputStream;
        this.contentLength = contentLength;
    }
    
    @Override
    public int read() throws IOException {
        return this.inputStream.read();
    }
    
    public InputStream getInputStream() {
        return this.inputStream;
    }
    
    public int getContentLength() {
        return this.contentLength;
    }
}
