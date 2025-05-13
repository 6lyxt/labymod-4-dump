// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon;

import java.util.Iterator;
import net.labymod.api.addon.transform.LoadedAddonClassTransformer;
import net.labymod.api.addon.LoadedAddon;

public class AddonTransformer
{
    public static byte[] transform(final String name, final String transformedName, byte[] classData) {
        final DefaultAddonService instance = DefaultAddonService.getInstance();
        if (instance == null) {
            return classData;
        }
        for (final LoadedAddon addon : instance.getLoadedAddons()) {
            for (final LoadedAddonClassTransformer transformer : addon.getTransformers()) {
                if (transformer.shouldTransform(name, transformedName)) {
                    classData = transformer.transform(name, transformedName, classData);
                }
            }
        }
        return classData;
    }
}
