// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.models;

import net.labymod.api.util.version.serial.VersionCompatibilityDeserializer;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.models.version.VersionCompatibility;

public class GameMode
{
    private String name;
    private String command;
    private String url;
    private String color;
    private String versions;
    private transient VersionCompatibility versionCompatibility;
    
    @Nullable
    public String getName() {
        return this.name;
    }
    
    @Nullable
    public String getCommand() {
        return this.command;
    }
    
    @Nullable
    public String getUrl() {
        return this.url;
    }
    
    @Nullable
    public String getColor() {
        return this.color;
    }
    
    @Nullable
    public VersionCompatibility getVersions() {
        if (this.versions == null) {
            return null;
        }
        if (this.versionCompatibility == null) {
            this.versionCompatibility = VersionCompatibilityDeserializer.from(this.versions);
        }
        return this.versionCompatibility;
    }
}
