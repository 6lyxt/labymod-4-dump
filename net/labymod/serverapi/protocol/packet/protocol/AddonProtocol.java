// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.packet.protocol;

import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;

@Deprecated(forRemoval = true, since = "4.2.24")
public class AddonProtocol extends Protocol
{
    private final String addonId;
    
    public AddonProtocol(final String addonId) {
        this(addonId, null);
    }
    
    public AddonProtocol(final String addonId, final PayloadChannelIdentifier legacyId) {
        super(PayloadChannelIdentifier.create("labymod", "neo/addons/" + addonId), legacyId);
        this.addonId = addonId;
    }
    
    public String getAddonId() {
        return this.addonId;
    }
}
