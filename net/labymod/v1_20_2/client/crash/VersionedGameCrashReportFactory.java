// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.crash;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.crash.GameCrashReport;

@Singleton
@Implements(GameCrashReport.Factory.class)
public class VersionedGameCrashReportFactory implements GameCrashReport.Factory
{
    @Inject
    public VersionedGameCrashReportFactory() {
    }
    
    @Override
    public GameCrashReport create(@NotNull final String title, final Throwable throwable) {
        Objects.requireNonNull(title);
        return new VersionedGameCrashReport(title, throwable);
    }
}
