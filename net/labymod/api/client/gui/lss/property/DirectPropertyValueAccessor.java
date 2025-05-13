// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.property;

import org.jetbrains.annotations.Nullable;

public interface DirectPropertyValueAccessor
{
    @Nullable
    PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String p0);
    
    boolean hasPropertyValueAccessor(final String p0);
    
    @Nullable
    LssPropertyResetter getPropertyResetter();
}
