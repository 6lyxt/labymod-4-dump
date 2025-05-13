// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget;

import net.labymod.api.util.math.vector.Matrix4;
import java.util.function.BiConsumer;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface OverlappingTranslator
{
    void translateOverlappingElements(final BiConsumer<Object, Matrix4> p0, final Runnable p1);
    
    void translate(final Object p0, final Matrix4 p1);
    
    boolean isTranslated();
}
