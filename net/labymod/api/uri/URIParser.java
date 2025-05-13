// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.uri;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.net.URI;
import java.util.List;

public class URIParser
{
    public static List<URI> parse(final String text) {
        final List<URI> foundURIs = new ArrayList<URI>();
        for (final String word : text.split(" ")) {
            final URI uri = parseSegment(word);
            if (uri != null) {
                foundURIs.add(uri);
            }
        }
        return foundURIs;
    }
    
    public static URI parseSegment(final String text) {
        String protocol = null;
        String host = null;
        String path = null;
        int index = 0;
        final int protocolIndex = text.indexOf("://");
        if (protocolIndex != -1) {
            protocol = text.substring(index, protocolIndex);
            index = protocolIndex + 3;
        }
        final int hostIndex = text.indexOf(47, index);
        if (hostIndex != -1) {
            host = text.substring(index, hostIndex);
            path = text.substring(hostIndex);
        }
        else if (protocol != null) {
            host = text.substring(index);
        }
        if (protocol == null && path == null) {
            final int dotIndex = text.indexOf(46, index);
            if (dotIndex == -1 || dotIndex == text.length() - 1) {
                return null;
            }
            host = text.substring(index);
        }
        if (path != null && path.endsWith(".")) {
            path = path.substring(0, path.length() - 1);
        }
        if (host.endsWith(".")) {
            host = host.substring(0, host.length() - 1);
        }
        final StringBuilder urlBuilder = new StringBuilder();
        if (protocol != null) {
            urlBuilder.append(protocol).append("://");
        }
        urlBuilder.append(host);
        if (path != null) {
            urlBuilder.append(path);
        }
        try {
            return new URI(urlBuilder.toString());
        }
        catch (final URISyntaxException e) {
            return null;
        }
    }
}
