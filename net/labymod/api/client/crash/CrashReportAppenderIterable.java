// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.crash;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface CrashReportAppenderIterable extends Iterable<CrashReportAppender>
{
    void addAppender(final CrashReportAppender p0);
}
