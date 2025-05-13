// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform.launcher;

import java.io.IOException;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.platform.launcher.LauncherVendorType;
import java.io.File;
import net.labymod.api.platform.launcher.MinecraftLauncher;

public class MojangMinecraftLauncher implements MinecraftLauncher
{
    @Override
    public File getDirectory() {
        return this.getWorkingDirectory("minecraft");
    }
    
    @Override
    public LauncherVendorType currentType() {
        return LauncherVendorType.MOJANG;
    }
    
    @Override
    public boolean kill() throws IOException {
        final OperatingSystem currentPlatform = OperatingSystem.getPlatform();
        switch (currentPlatform) {
            case LINUX:
            case SOLARIS:
            case UNKNOWN: {
                return false;
            }
            case WINDOWS: {
                this.killOnWindows();
                return true;
            }
            case MACOS: {
                this.killOnMacOS();
                return true;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(currentPlatform));
            }
        }
    }
    
    private File getWorkingDirectory(final String applicationName) {
        final String userHome = System.getProperty("user.home", ".");
        File workingDirectory = null;
        switch (OperatingSystem.getPlatform()) {
            case LINUX: {
                workingDirectory = new File(userHome, "." + applicationName);
                break;
            }
            case UNKNOWN: {
                workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
                break;
            }
            case WINDOWS: {
                final String applicationData = System.getenv("APPDATA");
                if (applicationData != null) {
                    workingDirectory = new File(applicationData, "." + applicationName);
                    break;
                }
                workingDirectory = new File(userHome, "." + applicationName);
                break;
            }
            case MACOS: {
                workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
                break;
            }
            case SOLARIS: {
                final String applicationDataW = System.getenv("APPDATA");
                if (applicationDataW != null) {
                    workingDirectory = new File(applicationDataW, "." + applicationName);
                    break;
                }
                workingDirectory = new File(userHome, "." + applicationName);
                break;
            }
            default: {
                workingDirectory = new File(userHome, applicationName);
                break;
            }
        }
        if (!workingDirectory.exists() && !workingDirectory.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + String.valueOf(workingDirectory));
        }
        return workingDirectory;
    }
    
    private void killOnWindows() throws IOException {
        final Runtime runtime = Runtime.getRuntime();
        runtime.exec("taskkill /IM Minecraft.exe");
        runtime.exec("taskkill /IM MinecraftLauncher.exe");
    }
    
    private void killOnMacOS() throws IOException {
        Runtime.getRuntime().exec(new String[] { "osascript", "-e", "quit app \"Minecraft Launcher\"" });
    }
}
