// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.crash;

import net.labymod.api.client.crash.GameCrashReport;

public class VersionedGameCrashReport implements GameCrashReport
{
    private final p crashReport;
    
    public VersionedGameCrashReport(final String title, final Throwable throwable) {
        this.crashReport = p.a(throwable, title);
    }
    
    @Override
    public Category addCategory(final String name) {
        final q category = this.crashReport.a(name);
        return new VersionedGameCrashReportCategory(category);
    }
    
    @Override
    public <T> T crashReport() {
        return (T)this.crashReport;
    }
}
