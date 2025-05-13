// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint.downloader;

import java.util.Locale;

public enum DownloadState
{
    PREPARE, 
    DOWNLOADING, 
    FAILED, 
    LOADING, 
    REQUIRES_RESTART, 
    FINISHED;
    
    @Override
    public String toString() {
        if (this.name().contains("_")) {
            final String[] words = this.name().split("_");
            String camelCase = "";
            for (int i = 0; i < words.length; ++i) {
                final String word = words[i];
                if (i == 0) {
                    camelCase += word.toLowerCase(Locale.ROOT);
                }
                else {
                    camelCase = camelCase + word.charAt(0) + word.substring(1).toLowerCase(Locale.ROOT);
                }
            }
            return camelCase;
        }
        return this.name().toLowerCase(Locale.ROOT);
    }
}
