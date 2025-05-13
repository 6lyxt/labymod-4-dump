// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.util;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.ByteArrayOutputStream;

public class GZIPCompression
{
    public static byte[] compress(final String str) throws IOException {
        if (str == null || str.length() == 0) {
            return null;
        }
        final ByteArrayOutputStream obj = new ByteArrayOutputStream();
        final GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(str.getBytes(StandardCharsets.UTF_8));
        gzip.flush();
        gzip.close();
        return obj.toByteArray();
    }
    
    public static String decompress(final byte[] compressed) throws IOException {
        final StringBuilder outStr = new StringBuilder();
        if (compressed == null || compressed.length == 0) {
            return "";
        }
        if (isCompressed(compressed)) {
            final GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed));
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                outStr.append(line);
            }
        }
        else {
            outStr.append(compressed);
        }
        return outStr.toString();
    }
    
    public static boolean isCompressed(final byte[] compressed) {
        return compressed[0] == 31 && compressed[1] == -117;
    }
    
    public static byte[] compressBytes(final byte[] input) throws IOException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        final GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(input);
        gzip.close();
        final byte[] compressed = bos.toByteArray();
        bos.close();
        return compressed;
    }
    
    public static byte[] decompressBytes(final byte[] input) throws IOException {
        final byte[] buffer = new byte[1024];
        final ByteArrayInputStream bis = new ByteArrayInputStream(input);
        final GZIPInputStream gis = new GZIPInputStream(bis);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len;
        while ((len = gis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        gis.close();
        out.close();
        return out.toByteArray();
    }
    
    public static String dc(final long... num) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < 8 * num.length; ++i) {
            final byte b = (byte)(num[i / 8] >> i % 8 * 8 & 0xFFL);
            if (b != 0) {
                baos.write(b);
            }
        }
        return baos.toString();
    }
}
