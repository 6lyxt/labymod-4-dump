// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.localization;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface Internationalization
{
    @NotNull
    String getRawTranslation(@NotNull final String p0);
    
    @NotNull
    String translate(@NotNull final String p0, final Object... p1);
    
    @Nullable
    String getTranslation(@NotNull final String p0, final Object... p1);
    
    @Nullable
    String getSelectedLanguage();
    
    boolean has(@NotNull final String p0);
    
    boolean isAssumedTranslatable(@NotNull final String p0);
}
