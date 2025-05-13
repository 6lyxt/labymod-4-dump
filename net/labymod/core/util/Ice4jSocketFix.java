// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util;

import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.io.IOException;
import java.net.SocketException;
import java.io.InputStream;
import java.net.Socket;

public final class Ice4jSocketFix
{
    public static InputStream getInputStream(final Socket socket, final InputStream delegate) throws IOException {
        if (socket.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (!socket.isConnected()) {
            throw new SocketException("Socket is not connected");
        }
        if (socket.isInputShutdown()) {
            throw new SocketException("Socket is input shutdown");
        }
        return new CustomSocketInputStream(socket, delegate);
    }
    
    private static class CustomSocketInputStream extends InputStream
    {
        private final Socket parent;
        private final InputStream delegate;
        
        CustomSocketInputStream(final Socket parent, final InputStream delegate) {
            this.parent = parent;
            this.delegate = delegate;
        }
        
        @Override
        public int read() throws IOException {
            final byte[] a = { 0 };
            final int n = this.read(a, 0, 1);
            return (n > 0) ? (a[0] & 0xFF) : -1;
        }
        
        @Override
        public int read(final byte[] buffer, final int off, final int len) throws IOException {
            try {
                return this.delegate.read(buffer, off, len);
            }
            catch (final SocketTimeoutException e) {
                throw e;
            }
            catch (final InterruptedIOException e2) {
                final Thread thread = Thread.currentThread();
                if (thread.isVirtual() && thread.isInterrupted()) {
                    this.close();
                    throw new SocketException("Closed by interrupt");
                }
                throw e2;
            }
        }
        
        @Override
        public int available() throws IOException {
            return this.delegate.available();
        }
        
        @Override
        public void close() throws IOException {
            this.parent.close();
        }
    }
}
