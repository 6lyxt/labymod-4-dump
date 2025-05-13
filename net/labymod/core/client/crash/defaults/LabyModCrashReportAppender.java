// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.crash.defaults;

import net.labymod.api.addon.LoadedAddon;
import java.util.Collection;
import net.labymod.api.LabyAPI;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.labymod.api.util.CharSequences;
import net.labymod.api.BuildData;
import net.labymod.api.Laby;
import net.labymod.api.client.crash.CrashReportAppender;

public class LabyModCrashReportAppender extends CrashReportAppender
{
    @Override
    public void append(final StringBuilder builder) {
        final LabyAPI labyAPI = Laby.labyAPI();
        if (labyAPI == null) {
            return;
        }
        this.appendHeader("LabyMod");
        this.setDetail("Version", labyAPI.getVersion());
        this.setDetail("Commit Reference", BuildData.commitReference());
        this.setDetail("Theme", () -> CharSequences.capitalize(labyAPI.config().appearance().theme().getOrDefault("Unknown")), "Unknown");
        final Collection<LoadedAddon> loadedAddons = labyAPI.addonService().getLoadedAddons();
        if (!loadedAddons.isEmpty()) {
            this.setDetail("Loaded Addons", loadedAddons.stream().map(addon -> addon.info().getNamespace() + "(" + addon.info().getVersion()).collect((Collector<? super Object, ?, String>)Collectors.joining(", ")));
        }
    }
}
