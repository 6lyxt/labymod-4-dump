// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.version;

import org.jetbrains.annotations.NotNull;
import java.util.Locale;

public class CalendarVersion extends SemanticVersion
{
    public CalendarVersion(final String version) throws NumberFormatException {
        final String[] parts = version.split("w");
        final String year = parts[0];
        final String weak = parts[1];
        this.major = Integer.parseInt(year);
        this.minor = Integer.parseInt(weak.substring(0, weak.length() - 1));
        this.patch = weak.charAt(weak.length() - 1) - 'a';
    }
    
    public CalendarVersion(final int year, final int week, final char patch) {
        super(year, week, patch - 'a');
    }
    
    public int getYear() {
        return this.major;
    }
    
    public int getWeek() {
        return this.minor;
    }
    
    public char getPatchCharacter() {
        return (char)(this.patch + 97);
    }
    
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%sw%s%s", this.major, (this.minor < 10) ? ("0" + this.minor) : Integer.valueOf(this.minor), this.getPatchCharacter());
    }
    
    public static boolean isFormat(@NotNull final String version) {
        final int length = version.length();
        return length == 6 && Character.isDigit(version.charAt(0)) && version.contains("w") && !version.contains(".") && Character.isLetter(version.charAt(version.length() - 1));
    }
}
