// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget;

import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.util.math.vector.Matrix4;
import java.util.function.BiConsumer;
import java.util.List;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.widget.OverlappingTranslator;

@Singleton
@Implements(OverlappingTranslator.class)
public class DefaultOverlappingTranslator implements OverlappingTranslator
{
    private final List<BiConsumer<Object, Matrix4>> translators;
    
    public DefaultOverlappingTranslator() {
        this.translators = new ArrayList<BiConsumer<Object, Matrix4>>();
    }
    
    @Override
    public void translateOverlappingElements(final BiConsumer<Object, Matrix4> translator, final Runnable context) {
        this.translators.add(translator);
        context.run();
        this.translators.remove(translator);
    }
    
    @Override
    public void translate(final Object translated, final Matrix4 matrix) {
        for (final BiConsumer<Object, Matrix4> translator : this.translators) {
            translator.accept(translated, matrix);
        }
    }
    
    @Override
    public boolean isTranslated() {
        return !this.translators.isEmpty();
    }
}
