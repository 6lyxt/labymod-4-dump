// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.crash;

import net.labymod.api.util.function.ThrowableSupplier;

public abstract class CrashReportAppender
{
    private StringBuilder builder;
    
    public final void appendCrashReport(final StringBuilder builder) {
        this.append(this.builder = builder);
    }
    
    public abstract void append(final StringBuilder p0);
    
    public void setSubtitle(final String subtitle) {
        if (this.builder == null) {
            return;
        }
        this.builder.append(subtitle).append(":").append("\n");
    }
    
    public void setDetail(final String key, final Object value) {
        if (value == null) {
            return;
        }
        this.setDetail(key, value.toString());
    }
    
    public void setDetail(final String key, final String value) {
        if (this.builder == null) {
            return;
        }
        this.builder.append("\t").append(key).append(": ").append(value).append("\n");
    }
    
    public void setDetail(final String key, final ThrowableSupplier<CharSequence, Throwable> value, final String def) {
        if (this.builder == null) {
            return;
        }
        CharSequence finalValue = def;
        try {
            finalValue = value.get();
        }
        catch (final Throwable t) {}
        this.builder.append("\t").append(key).append(": ").append(finalValue).append("\n");
    }
    
    protected void appendCrashReportDetails(final CrashReportDetails details) {
        details.appendDetails(this);
    }
    
    protected void appendHeader(final String name) {
        if (this.builder == null) {
            return;
        }
        this.builder.append("\n").append("-- ").append(name).append(" Details").append(" --").append("\n");
    }
}
