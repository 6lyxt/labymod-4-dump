// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.platform;

import net.labymod.core.loader.vanilla.launchwrapper.transformer.accesswidener.AccessWidenerUtil;
import net.labymod.api.loader.platform.PlatformAccessWidener;

public class LaunchWrapperPlatformAccessWidener implements PlatformAccessWidener
{
    @Override
    public void findAndCreateAccessWidener(final ClassLoader classLoader, final String addonId, final String runningVersion) {
        AccessWidenerUtil.findAndCreateAccessWidener(classLoader, addonId, runningVersion);
    }
}
