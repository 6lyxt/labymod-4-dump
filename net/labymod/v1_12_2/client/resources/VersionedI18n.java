// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.resources;

public class VersionedI18n
{
    private static cfb locale;
    
    public static cfb getLocale() {
        return VersionedI18n.locale;
    }
    
    public static void setLocale(final cfb locale) {
        VersionedI18n.locale = locale;
    }
}
