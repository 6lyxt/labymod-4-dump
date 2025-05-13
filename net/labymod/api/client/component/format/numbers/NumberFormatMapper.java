// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.format.numbers;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

@Nullable
@Referenceable
public interface NumberFormatMapper
{
    NumberFormat fromMinecraft(final Object p0);
    
    Object toMinecraft(final NumberFormat p0);
}
