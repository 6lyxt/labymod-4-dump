// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.version.comparison;

import java.util.Locale;
import java.util.Objects;
import net.labymod.api.util.version.exception.VersionException;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;
import java.util.function.BiConsumer;
import net.labymod.api.models.version.VersionComparison;

public class VersionRangeComparison<T extends VersionComparison<T>> implements VersionComparison<T>
{
    private final T from;
    private final T to;
    @Nullable
    private final BiConsumer<T, T> rangeValidator;
    
    private VersionRangeComparison(@Nullable final T from, @Nullable final T to, @Nullable final BiConsumer<T, T> rangeValidator) {
        this.from = from;
        this.to = to;
        this.rangeValidator = rangeValidator;
        this.validateRange();
    }
    
    public static <T extends VersionComparison<T>> VersionRangeComparison<T> parse(final String value, final Function<String, T> comparisonFactory) {
        return parse(value, comparisonFactory, null);
    }
    
    public static <T extends VersionComparison<T>> VersionRangeComparison<T> parse(final String value, final Function<String, T> comparisonFactory, @Nullable final BiConsumer<T, T> rangeValidator) {
        final String[] entries = value.split("<");
        final T from = fromVersion(entries[0], comparisonFactory);
        final T to = (entries.length == 1) ? from : fromVersion(entries[1], comparisonFactory);
        return new VersionRangeComparison<T>(from, to, rangeValidator);
    }
    
    private static <T extends VersionComparison<T>> T fromVersion(final String version, final Function<String, T> comparisonFactory) {
        return (T)(version.equals("*") ? null : ((T)comparisonFactory.apply(version)));
    }
    
    private void validateRange() {
        if (this.from == null || this.to == null) {
            return;
        }
        if (this.rangeValidator != null) {
            this.rangeValidator.accept(this.from, this.to);
        }
        if (this.from.isGreaterThan(this.to)) {
            throw new VersionException("Range from " + String.valueOf(this.from) + " is greater than to " + String.valueOf(this.to));
        }
    }
    
    @Override
    public boolean isCompatible(final T version) {
        return (this.from != null && this.from.isCompatible(version)) || (this.to != null && this.to.isCompatible(version)) || ((this.from == null || version.isGreaterThan(this.from)) && (this.to == null || version.isLowerThan(this.to)));
    }
    
    @Override
    public boolean isGreaterThan(final T version) {
        return this.from != null && this.from.isGreaterThan(version);
    }
    
    @Override
    public boolean isLowerThan(final T version) {
        return this.to != null && this.to.isLowerThan(version);
    }
    
    @Override
    public String toString() {
        return Objects.equals(this.from, this.to) ? ((this.from == null) ? "*" : this.from.toString()) : String.format(Locale.ROOT, "%s<%s", (this.from == null) ? "*" : this.from, (this.to == null) ? "*" : this.to);
    }
}
