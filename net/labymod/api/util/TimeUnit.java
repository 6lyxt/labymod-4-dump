// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

public enum TimeUnit
{
    YEAR('y', 31536000000L), 
    MONTH('M', 2592000000L), 
    DAY('d', 86400000L), 
    HOUR('h', 3600000L), 
    MINUTE('m', 60000L), 
    SECOND('s', 1000L), 
    UNKNOWN('?', 0L);
    
    private static final TimeUnit[] VALUES;
    private final char suffix;
    private final long factor;
    
    private TimeUnit(final char suffix, final long factor) {
        this.suffix = suffix;
        this.factor = factor;
    }
    
    @NotNull
    public static TimeUnit from(final String input) {
        for (final TimeUnit unit : TimeUnit.VALUES) {
            if (unit != TimeUnit.UNKNOWN && input.endsWith(unit.getSuffix())) {
                return unit;
            }
        }
        return TimeUnit.UNKNOWN;
    }
    
    public static long parseToLong(final String input) {
        boolean canSeparate = false;
        final StringBuilder string = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            final char character = input.charAt(i);
            if (Character.isDigit(character)) {
                if (canSeparate) {
                    string.append(" ");
                    canSeparate = false;
                }
            }
            else {
                canSeparate = true;
            }
            if (character != ' ') {
                string.append(character);
            }
        }
        long duration = 0L;
        final String[] split;
        final String[] units = split = string.toString().split(" ");
        for (final String unit : split) {
            duration += from(unit).toLong(unit);
        }
        return duration;
    }
    
    @NotNull
    public static String parseToString(final long duration) {
        return parseToString(duration, false);
    }
    
    @NotNull
    public static String parseToString(final long duration, final boolean trimmed) {
        final StringBuilder string = new StringBuilder();
        for (final String unit : parseToList(duration)) {
            if (!string.isEmpty() && !trimmed) {
                string.append(" ");
            }
            string.append(unit);
        }
        return string.toString();
    }
    
    @NotNull
    public static List<String> parseToList(long duration) {
        final List<String> units = new ArrayList<String>();
        for (final TimeUnit unit : TimeUnit.VALUES) {
            if (unit != TimeUnit.UNKNOWN) {
                final long amount = duration / unit.getFactor();
                if (amount > 0L || (unit == TimeUnit.SECOND && units.isEmpty())) {
                    units.add(String.format(Locale.ROOT, "%s%s", amount, unit.getIdentifier()));
                    duration -= amount * unit.getFactor();
                }
            }
        }
        return units;
    }
    
    @NotNull
    public String getSuffix() {
        return String.valueOf(this.suffix);
    }
    
    public char getIdentifier() {
        return this.suffix;
    }
    
    public long getFactor() {
        return this.factor;
    }
    
    private long toLong(@NotNull final String input) {
        if (this.equals(TimeUnit.UNKNOWN)) {
            return 0L;
        }
        final String amount = input.substring(0, input.length() - this.getSuffix().length());
        return StringUtil.isNumeric(amount) ? (Long.parseLong(amount) * this.factor) : 0L;
    }
    
    static {
        VALUES = values();
    }
}
