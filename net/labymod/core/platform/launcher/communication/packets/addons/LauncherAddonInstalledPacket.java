// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform.launcher.communication.packets.addons;

import net.labymod.core.platform.launcher.communication.LauncherPacketHandler;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.platform.launcher.communication.LauncherPacket;

public class LauncherAddonInstalledPacket implements LauncherPacket
{
    private String namespace;
    private String fileName;
    
    public LauncherAddonInstalledPacket() {
    }
    
    public LauncherAddonInstalledPacket(@NotNull final String namespace, @NotNull final String fileName) {
        Objects.requireNonNull(namespace, "namespace");
        Objects.requireNonNull(fileName, "fileName");
        this.namespace = namespace;
        this.fileName = fileName;
    }
    
    @Override
    public void read(final PayloadReader reader) {
        this.namespace = reader.readString();
        this.fileName = reader.readString();
    }
    
    @Override
    public void write(final PayloadWriter writer) {
        writer.writeString(this.namespace);
        writer.writeString(this.fileName);
    }
    
    @Override
    public void handle(final LauncherPacketHandler handler) {
        handler.handleAddonInstalledPacket(this);
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public String getFileName() {
        return this.fileName;
    }
}
