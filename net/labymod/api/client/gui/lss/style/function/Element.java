// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.function;

import java.util.function.Consumer;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.Widget;

public interface Element
{
    @NotNull
    ProcessedObject[] computeValue(@NotNull final Widget p0, @NotNull final String p1, @NotNull final Class<?> p2) throws Exception;
    
    String getRawValue();
    
    void forEach(final Consumer<Element> p0);
}
