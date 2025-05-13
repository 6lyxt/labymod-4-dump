// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

public class PrettyStringBuilder
{
    private final StringBuilder builder;
    
    public PrettyStringBuilder() {
        this(new StringBuilder());
    }
    
    public PrettyStringBuilder(final StringBuilder builder) {
        this.builder = builder;
    }
    
    public PrettyStringBuilder append(final Object obj) {
        this.builder.append(obj);
        return this;
    }
    
    public PrettyStringBuilder append(final String str) {
        this.builder.append(str);
        return this;
    }
    
    public PrettyStringBuilder append(final StringBuffer sb) {
        this.builder.append(sb);
        return this;
    }
    
    public PrettyStringBuilder append(final CharSequence s) {
        this.builder.append(s);
        return this;
    }
    
    public PrettyStringBuilder append(final CharSequence s, final int start, final int end) {
        this.builder.append(s, start, end);
        return this;
    }
    
    public PrettyStringBuilder append(final char[] str) {
        this.builder.append(str);
        return this;
    }
    
    public PrettyStringBuilder append(final char[] str, final int offset, final int len) {
        this.builder.append(str, offset, len);
        return this;
    }
    
    public PrettyStringBuilder append(final boolean b) {
        this.builder.append(b);
        return this;
    }
    
    public PrettyStringBuilder append(final char c) {
        this.builder.append(c);
        return this;
    }
    
    public PrettyStringBuilder append(final int i) {
        this.builder.append(i);
        return this;
    }
    
    public PrettyStringBuilder append(final long lng) {
        this.builder.append(lng);
        return this;
    }
    
    public PrettyStringBuilder append(final float f) {
        this.builder.append(f);
        return this;
    }
    
    public PrettyStringBuilder append(final double d) {
        this.builder.append(d);
        return this;
    }
    
    public PrettyStringBuilder append(final CharSequence s, final int indent) {
        for (int i = 0; i < indent; ++i) {
            this.append("\t");
        }
        this.append(s);
        return this;
    }
    
    public PrettyStringBuilder appendKeyValue(final CharSequence key, final Object value) {
        return this.appendKeyValue(key, value, 0);
    }
    
    public PrettyStringBuilder appendKeyValue(final CharSequence key, final Object value, final int indent) {
        for (int i = 0; i < indent; ++i) {
            this.append("\t");
        }
        this.append(key).append(": ").append(value).newLine();
        return this;
    }
    
    public PrettyStringBuilder newLine() {
        this.builder.append(System.lineSeparator());
        return this;
    }
    
    @Override
    public String toString() {
        return this.builder.toString();
    }
}
