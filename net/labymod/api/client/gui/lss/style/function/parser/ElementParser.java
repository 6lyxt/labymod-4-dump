// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.function.parser;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.lss.style.function.Element;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ElementParser
{
    @Nullable
    Element parseElement(@NotNull final String p0) throws ElementParseException;
}
