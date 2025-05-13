// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform.launcher;

import net.labymod.api.platform.launcher.MinecraftLauncher;
import net.labymod.api.platform.launcher.LauncherVendorType;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.platform.launcher.MinecraftLauncherFactory;

@Singleton
@Implements(MinecraftLauncherFactory.class)
public class DefaultMinecraftLauncherFactory implements MinecraftLauncherFactory
{
    @Override
    public MinecraftLauncher create(final LauncherVendorType type) {
        switch (type) {
            case MOJANG: {
                return new MojangMinecraftLauncher();
            }
            case MULTIMC: {
                return null;
            }
            default: {
                return null;
            }
        }
    }
}
