// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.unix.ns;

import java.util.Iterator;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import ca.weblite.objc.Client;
import ca.weblite.objc.Proxy;

public final class ClientUtil
{
    public static Proxy newArray(final Proxy... objects) {
        return newArray(Client.getInstance(), objects);
    }
    
    public static Proxy newArray(final Client client, final Proxy... objets) {
        final Proxy arrayProxy = client.sendProxy("NSMutableArray", "array", new Object[0]);
        for (final Proxy objet : objets) {
            arrayProxy.send("addObject:", new Object[] { objet });
        }
        return arrayProxy;
    }
    
    public static Proxy newNSImageInitWithData(final Proxy nsData) {
        return newNSImageInitWithData(Client.getInstance(), nsData);
    }
    
    public static Proxy newNSImageInitWithData(final Client client, final Proxy nsData) {
        return client.sendProxy("NSImage", "alloc", new Object[0]).sendProxy("initWithData:", new Object[] { nsData });
    }
    
    public static Proxy newNSData(final byte[] buffer) {
        return newNSData(Client.getInstance(), buffer);
    }
    
    public static Proxy newNSData(final Client client, final byte[] buffer) {
        return client.sendProxy("NSData", "dataWithBytes:length:", new Object[] { buffer, buffer.length });
    }
    
    public static Proxy allocNSData() {
        return allocNSData(Client.getInstance());
    }
    
    public static Proxy allocNSData(final Client client) {
        return client.sendProxy("NSData", "alloc", new Object[0]);
    }
    
    public static Proxy allocNSImage() {
        return allocNSImage(Client.getInstance());
    }
    
    public static Proxy allocNSImage(final Client client) {
        return client.sendProxy("NSImage", "alloc", new Object[0]);
    }
    
    public static Proxy objectAtIndex(final Proxy proxy, final int index) {
        return proxy.sendProxy("objectAtIndex:", new Object[] { index });
    }
    
    public static <T> Collection<T> forEach(final Proxy object, final String selector, final Function<Proxy, T> mappingFunction) {
        final Proxy result = object.sendProxy(selector, new Object[0]);
        final int count = result.sendInt("count", new Object[0]);
        final List<T> entries = new ArrayList<T>(count);
        for (int index = 0; index < count; ++index) {
            entries.add(mappingFunction.apply(objectAtIndex(result, index)));
        }
        return entries;
    }
    
    public static <T> Optional<Proxy> findFirst(final Collection<T> entries, final Proxy object, final String selector) {
        Proxy finalResult = null;
        for (final T entry : entries) {
            finalResult = object.sendProxy(selector, new Object[] { entry });
            if (finalResult != null) {
                break;
            }
        }
        return Optional.ofNullable(finalResult);
    }
}
