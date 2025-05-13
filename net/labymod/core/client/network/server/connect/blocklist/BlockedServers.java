// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server.connect.blocklist;

import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import net.labymod.api.util.HashUtil;
import java.util.Locale;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import java.util.function.Function;
import java.util.Set;
import java.nio.charset.Charset;
import java.util.function.Predicate;

public class BlockedServers implements Predicate<String>
{
    public static final Charset HASH_CHARSET;
    public static final BlockedServers INSTANCE;
    private final Set<String> blockedServers;
    private static final String SRV_PREFIX = "_minecraft._tcp.";
    private static final Function<Collection<String>, String> DOT_JOINER;
    
    public BlockedServers(final Collection<String> blockedServers) {
        this.blockedServers = Set.copyOf((Collection<? extends String>)blockedServers);
    }
    
    @Override
    public boolean test(@Nullable String server) {
        if (server == null || server.isEmpty()) {
            return false;
        }
        if (server.startsWith("_minecraft._tcp.")) {
            server = server.substring("_minecraft._tcp.".length());
        }
        while (server.charAt(server.length() - 1) == '.') {
            server = server.substring(0, server.length() - 1);
        }
        if (this.isBlockedServerHostName(server)) {
            return true;
        }
        final List<String> parts = new ArrayList<String>(List.of(server.split("\\.")));
        final boolean isIp = isIp(parts);
        if (!isIp && this.isBlockedServerHostName("*." + server)) {
            return true;
        }
        if (parts.size() <= 1) {
            return false;
        }
        parts.remove(isIp ? (parts.size() - 1) : 0);
        for (String starredPart = isIp ? ((String)BlockedServers.DOT_JOINER.apply(parts) + ".*") : ("*." + (String)BlockedServers.DOT_JOINER.apply(parts)); !this.isBlockedServerHostName(starredPart); starredPart = (isIp ? ((String)BlockedServers.DOT_JOINER.apply(parts) + ".*") : ("*." + (String)BlockedServers.DOT_JOINER.apply(parts)))) {
            if (parts.size() <= 1) {
                return false;
            }
            parts.remove(isIp ? (parts.size() - 1) : 0);
        }
        return true;
    }
    
    private static boolean isIp(final List<String> address) {
        if (address.size() != 4) {
            return false;
        }
        for (final String segment : address) {
            try {
                final int part = Integer.parseInt(segment);
                if (part >= 0 && part <= 255) {
                    continue;
                }
                return false;
            }
            catch (final NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isBlockedServerHostName(final String server) {
        return this.blockedServers.contains(new String(HashUtil.sha1(server.toLowerCase(Locale.ROOT).getBytes(BlockedServers.HASH_CHARSET))));
    }
    
    private static BlockedServers create() {
        try {
            final URLConnection connection = new URL("https://sessionserver.mojang.com/blockedservers").openConnection();
            final InputStream inputStream = connection.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, BlockedServers.HASH_CHARSET));
            final BlockedServers blockedServers = new BlockedServers((Collection<String>)reader.lines().collect((Collector<? super String, ?, List<? super String>>)Collectors.toList()));
            inputStream.close();
            return blockedServers;
        }
        catch (final Throwable e) {
            e.printStackTrace();
            return new BlockedServers((Collection<String>)Collections.emptyList());
        }
    }
    
    static {
        HASH_CHARSET = StandardCharsets.ISO_8859_1;
        INSTANCE = create();
        DOT_JOINER = (s -> String.join(".", s));
    }
}
