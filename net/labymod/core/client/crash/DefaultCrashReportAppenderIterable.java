// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.crash;

import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import net.labymod.api.Laby;
import javax.inject.Inject;
import net.labymod.core.client.crash.defaults.OpenGLCrashReportAppender;
import net.labymod.core.client.crash.defaults.LabyModCrashReportAppender;
import java.util.ArrayList;
import net.labymod.api.client.crash.CrashReportAppender;
import java.util.List;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.crash.CrashReportAppenderIterable;

@Singleton
@Implements(CrashReportAppenderIterable.class)
public final class DefaultCrashReportAppenderIterable implements CrashReportAppenderIterable
{
    private final List<CrashReportAppender> appenders;
    
    @Inject
    public DefaultCrashReportAppenderIterable() {
        this.appenders = new ArrayList<CrashReportAppender>();
        this.addAppender(new LabyModCrashReportAppender());
        this.addAppender(new OpenGLCrashReportAppender());
    }
    
    @Override
    public void addAppender(final CrashReportAppender appender) {
        this.appenders.add(appender);
    }
    
    public void append(final StringBuilder builder) {
        if (!Laby.isInitialized()) {
            return;
        }
        for (int size = this.appenders.size(), i = 0; i < size; ++i) {
            final CrashReportAppender appender = this.appenders.get(i);
            if (appender != null) {
                if (i != size - 1) {
                    builder.append("\n");
                }
                appender.appendCrashReport(builder);
            }
        }
    }
    
    @NotNull
    @Override
    public Iterator<CrashReportAppender> iterator() {
        return this.appenders.iterator();
    }
}
