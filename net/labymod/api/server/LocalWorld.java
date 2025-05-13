// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.server;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import net.labymod.api.client.entity.player.GameMode;

public class LocalWorld
{
    private final String worldName;
    private final String folderName;
    private final GameMode gameMode;
    private final boolean allowCheats;
    private final int port;
    private final Path screenshotFile;
    
    public LocalWorld(@NotNull final String worldName, @NotNull final String folderName, @NotNull final GameMode gameMode, final boolean allowCheats, final int port, @Nullable final Path screenshotFile) {
        this.worldName = worldName;
        this.folderName = folderName;
        this.gameMode = gameMode;
        this.allowCheats = allowCheats;
        this.port = port;
        this.screenshotFile = screenshotFile;
    }
    
    @NotNull
    public String worldName() {
        return this.worldName;
    }
    
    @NotNull
    public String folderName() {
        return this.folderName;
    }
    
    @NotNull
    public GameMode gameMode() {
        return this.gameMode;
    }
    
    public boolean allowCheats() {
        return this.allowCheats;
    }
    
    public int port() {
        return this.port;
    }
    
    public boolean isOpen() {
        return this.port != -1;
    }
    
    @NotNull
    public Optional<Path> getScreenshotFile() {
        return Optional.ofNullable(this.screenshotFile);
    }
}
