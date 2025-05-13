// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.converter.addon;

import java.io.IOException;
import java.util.Collection;
import net.labymod.api.configuration.converter.LegacyAddonConverter;

public interface LegacyAddonResolver
{
    void resolveAddons(final LegacyAddonConverter.Version p0, final Collection<LegacyAddon> p1) throws IOException;
}
