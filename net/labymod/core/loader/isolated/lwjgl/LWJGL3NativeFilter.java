// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.isolated.lwjgl;

import net.labymod.api.models.OperatingSystem;
import net.labymod.core.loader.isolated.IsolatedLibrary;
import net.labymod.core.loader.isolated.IsolatedLibraryPredicate;

public class LWJGL3NativeFilter implements IsolatedLibraryPredicate
{
    @Override
    public boolean test(final IsolatedLibrary isolatedLibrary) {
        if (!isolatedLibrary.getGroup().equals("org.lwjgl")) {
            return false;
        }
        final String classifier = isolatedLibrary.getClassifier();
        if (classifier == null) {
            return false;
        }
        final OperatingSystem platform = OperatingSystem.getPlatform();
        final String arch = platform.getArch();
        switch (platform) {
            case LINUX:
            case SOLARIS: {
                if (arch.startsWith("arm") || arch.startsWith("aarch64")) {
                    return !classifier.equals("natives-linux" + ((arch.contains("64") || arch.startsWith("armv8")) ? "-arm64" : "-arm32"));
                }
                return !classifier.equals("natives-linux");
            }
            case WINDOWS: {
                if (arch.contains("64")) {
                    return !classifier.equals("natives-windows" + (arch.startsWith("aarch64") ? "-arm64" : ""));
                }
                return !classifier.equals("natives-windows-x86");
            }
            case MACOS: {
                return !classifier.equals("natives-macos" + (arch.startsWith("aarch64") ? "-arm64" : ""));
            }
            case UNKNOWN: {
                throw new IllegalStateException("Unrecognized or unsupported platform.");
            }
            default: {
                return false;
            }
        }
    }
}
