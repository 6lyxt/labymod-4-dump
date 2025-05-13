// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.version.comparison;

import java.util.Iterator;
import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import net.labymod.api.models.version.VersionComparison;

public class VersionMultiRangeComparison<T extends VersionComparison<T>> implements VersionComparison<T>
{
    private final List<VersionComparison<T>> ranges;
    
    private VersionMultiRangeComparison(final List<VersionComparison<T>> ranges) {
        this.ranges = ranges;
    }
    
    public static <T extends VersionComparison<T>> VersionMultiRangeComparison<T> parse(@NotNull final String value, @NotNull final Function<String, T> comparisonFactory) {
        return parse(value, comparisonFactory, null);
    }
    
    public static <T extends VersionComparison<T>> VersionMultiRangeComparison<T> parse(@NotNull final String value, @NotNull final Function<String, T> comparisonFactory, @Nullable final BiConsumer<T, T> rangeValidator) {
        final String[] entries = value.split(",");
        final List<VersionComparison<T>> ranges = new ArrayList<VersionComparison<T>>(entries.length);
        for (final String entry : entries) {
            ranges.add(VersionRangeComparison.parse(entry, comparisonFactory, rangeValidator));
        }
        return new VersionMultiRangeComparison<T>(ranges);
    }
    
    @Override
    public boolean isCompatible(final T version) {
        for (final VersionComparison<T> range : this.ranges) {
            if (range.isCompatible(version)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isGreaterThan(final T version) {
        for (final VersionComparison<T> range : this.ranges) {
            if (range.isGreaterThan(version)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isLowerThan(final T version) {
        for (final VersionComparison<T> range : this.ranges) {
            if (range.isLowerThan(version)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.ranges.size(); ++i) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append(this.ranges.get(i).toString());
        }
        return builder.toString();
    }
}
