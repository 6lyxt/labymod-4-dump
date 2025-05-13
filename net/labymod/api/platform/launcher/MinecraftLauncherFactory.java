// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.platform.launcher;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface MinecraftLauncherFactory
{
    @Nullable
    MinecraftLauncher create(final LauncherVendorType p0);
}
