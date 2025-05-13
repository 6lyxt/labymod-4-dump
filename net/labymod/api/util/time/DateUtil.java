// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.time;

import java.time.DateTimeException;
import java.time.temporal.TemporalAccessor;
import java.util.function.BiFunction;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Instant;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class DateUtil
{
    public static final ZoneId DEFAULT_TIMEZONE;
    
    public static DateTimeFormatter ofPattern(final String pattern, final TemporalAccessorType type) {
        return ofPattern(pattern, DateUtil.DEFAULT_TIMEZONE, type);
    }
    
    public static DateTimeFormatter ofPattern(final String pattern, final ZoneId zoneId, final TemporalAccessorType type) {
        return ofPattern(pattern, Locale.ROOT, zoneId, type);
    }
    
    public static DateTimeFormatter ofPattern(final String pattern, final Locale locale, final TemporalAccessorType type) {
        return ofPattern(pattern, locale, DateUtil.DEFAULT_TIMEZONE, type);
    }
    
    public static DateTimeFormatter ofPattern(final String pattern, final Locale locale, final ZoneId zoneId, final TemporalAccessorType type) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
        formatNow(formatter, zoneId, type);
        return formatter;
    }
    
    public static String formatNow(final DateTimeFormatter formatter, final ZoneId zoneId, final TemporalAccessorType type) {
        return format(formatter, Instant.now(), zoneId, type);
    }
    
    private static String format(final DateTimeFormatter formatter, final Instant instant, final ZoneId zoneId, final TemporalAccessorType type) {
        final BiFunction<Instant, ZoneId, TemporalAccessor> factory = switch (type.ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> LocalTime::ofInstant;
            case 1 -> LocalDate::ofInstant;
        };
        return format(formatter, instant, zoneId, factory);
    }
    
    private static String format(final DateTimeFormatter formatter, final Instant instant, final ZoneId zoneId, final BiFunction<Instant, ZoneId, TemporalAccessor> temporalAccessorFactory) {
        try {
            return formatter.format(temporalAccessorFactory.apply(instant, zoneId));
        }
        catch (final DateTimeException exception) {
            throw new IllegalArgumentException(exception.getMessage(), exception);
        }
    }
    
    static {
        DEFAULT_TIMEZONE = ZoneId.systemDefault();
    }
    
    public enum TemporalAccessorType
    {
        TIME, 
        DATE;
    }
}
