// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.unix.ns;

import org.jetbrains.annotations.NotNull;
import ca.weblite.objc.Proxy;
import ca.weblite.objc.Client;

public class NSPasteboard
{
    private final Client client;
    private final Proxy nsPasteboardProxy;
    
    public NSPasteboard(final Client client, final Proxy nsPasteboardProxy) {
        this.client = client;
        this.nsPasteboardProxy = nsPasteboardProxy;
    }
    
    @NotNull
    public static NSPasteboard generalPasteboard() {
        return generalPasteboard(Client.getInstance());
    }
    
    @NotNull
    public static NSPasteboard generalPasteboard(final Client client) {
        return new NSPasteboard(client, client.sendProxy("NSPasteboard", "generalPasteboard", new Object[0]));
    }
    
    public Proxy getPasteboardItems() {
        final Proxy pasteboardItems = this.nsPasteboardProxy.sendProxy("pasteboardItems", new Object[0]);
        if (pasteboardItems == null || pasteboardItems.sendInt("count", new Object[0]) <= 0) {
            throw new IllegalStateException("No pasteboard items found");
        }
        return pasteboardItems;
    }
    
    public void clearContents() {
        this.nsPasteboardProxy.send("clearContents", new Object[0]);
    }
    
    public void writeObjects(final Proxy... objects) {
        this.nsPasteboardProxy.send("writeObjects:", new Object[] { ClientUtil.newArray(this.client, objects) });
    }
}
