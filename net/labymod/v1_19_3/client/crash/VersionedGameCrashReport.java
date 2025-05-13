// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.crash;

import net.labymod.api.client.crash.GameCrashReport;

public class VersionedGameCrashReport implements GameCrashReport
{
    private final n crashReport;
    
    public VersionedGameCrashReport(final String title, final Throwable throwable) {
        this.crashReport = n.a(throwable, title);
    }
    
    @Override
    public Category addCategory(final String name) {
        final o category = this.crashReport.a(name);
        return new VersionedGameCrashReportCategory(category);
    }
    
    @Override
    public <T> T crashReport() {
        return (T)this.crashReport;
    }
}
