// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.crash.defaults;

import net.labymod.api.client.gfx.GFXCapabilities;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.crash.CrashReportDetails;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import net.labymod.api.client.gfx.GFXVersion;
import net.labymod.api.Laby;
import net.labymod.api.client.crash.CrashReportAppender;

public class OpenGLCrashReportAppender extends CrashReportAppender
{
    @Override
    public void append(final StringBuilder builder) {
        this.appendHeader("OpenGL");
        final LabyAPI labyAPI = Laby.labyAPI();
        if (labyAPI == null) {
            this.onEarlyInitialization(builder);
            return;
        }
        final GFXRenderPipeline renderPipeline = labyAPI.gfxRenderPipeline();
        if (renderPipeline == null) {
            this.onEarlyInitialization(builder);
            return;
        }
        final GFXBridge gfx = renderPipeline.gfx();
        if (gfx == null) {
            this.onEarlyInitialization(builder);
            return;
        }
        GFXCapabilities capabilities;
        try {
            capabilities = gfx.capabilities();
        }
        catch (final Throwable ignored) {
            this.onEarlyInitialization(builder);
            return;
        }
        if (capabilities == null) {
            this.onEarlyInitialization(builder);
            return;
        }
        this.setDetail("Available versions", capabilities.getVersions().stream().filter(GFXVersion::isSupported).map((Function<? super Object, ?>)GFXVersion::toString).collect((Collector<? super Object, ?, String>)Collectors.joining(", ")));
        this.appendCrashReportDetails(capabilities);
    }
    
    private void onEarlyInitialization(final StringBuilder builder) {
        builder.append("No OpenGL specific information could be written to the crash report");
    }
}
