// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader;

import net.labymod.api.BuildData;
import java.util.Locale;
import net.labymod.api.addon.exception.UnsupportedAddonException;
import net.labymod.api.addon.exception.AddonLoadException;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.Set;
import java.util.regex.Pattern;

public class AddonValidator
{
    private static final Pattern NAMESPACE_PATTERN;
    private static final Set<String> RESERVED_NAMESPACES;
    
    public static void validateAddonInfo(final InstalledAddonInfo addonInfo) {
        if (addonInfo == null) {
            throw new AddonLoadException("No addon.json found");
        }
        String errorText = null;
        if (addonInfo.getNamespace() == null || addonInfo.getNamespace().isEmpty()) {
            errorText = "namespace";
        }
        else if (addonInfo.getAuthor() == null) {
            errorText = "author";
        }
        else if (addonInfo.getDisplayName() == null) {
            errorText = "displayName";
        }
        else if (addonInfo.getVersion() == null) {
            errorText = "version";
        }
        else if (addonInfo.getCompatibleMinecraftVersions() == null) {
            errorText = "compatibleMinecraftVersions";
        }
        else if (addonInfo.meta() == null) {
            errorText = "meta";
        }
        if (errorText != null) {
            throw new UnsupportedAddonException("Not a valid LabyMod 4 Addon (" + errorText + " is missing)");
        }
        if (!isMatchingNamespaceFormat(addonInfo.getNamespace())) {
            throw new UnsupportedAddonException(String.format(Locale.ROOT, "%s uses an invalid namespace \"%s\" (Only lowercase letters, numbers, underscores and hyphens are allowed)", addonInfo.getFileName(), addonInfo.getNamespace()));
        }
        if (AddonValidator.RESERVED_NAMESPACES.contains(addonInfo.getNamespace())) {
            throw new UnsupportedAddonException(String.format(Locale.ROOT, "%s uses the reserved namespace \"%s\" (Reserved namespaces are: %s)", addonInfo.getFileName(), addonInfo.getNamespace(), String.join(", ", AddonValidator.RESERVED_NAMESPACES)));
        }
    }
    
    public static boolean isMatchingNamespaceFormat(final String namespace) {
        return AddonValidator.NAMESPACE_PATTERN.matcher(namespace).matches();
    }
    
    public static boolean isBuildNumberGreater(final InstalledAddonInfo addonInfo) {
        return addonInfo.getRequiredLabyModBuild() > BuildData.getBuildNumber();
    }
    
    static {
        NAMESPACE_PATTERN = Pattern.compile("[a-z0-9_\\-]*");
        RESERVED_NAMESPACES = Set.of("minecraft", "reamls", "labymod");
    }
}
