// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.crash;

import net.labymod.api.client.crash.GameCrashReport;

public class VersionedGameCrashReport implements GameCrashReport
{
    private final q crashReport;
    
    public VersionedGameCrashReport(final String title, final Throwable throwable) {
        this.crashReport = q.a(throwable, title);
    }
    
    @Override
    public Category addCategory(final String name) {
        final r category = this.crashReport.a(name);
        return new VersionedGameCrashReportCategory(category);
    }
    
    @Override
    public <T> T crashReport() {
        return (T)this.crashReport;
    }
}
