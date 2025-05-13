// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class AbstractWidgetAnimationTimingFunctionPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, CubicBezier, CubicBezier>
{
    @Override
    public LssProperty getProperty(final AbstractWidget widget) {
        return widget.animationTimingFunction();
    }
    
    @Override
    public Class<?> type() {
        return CubicBezier.class;
    }
    
    @Override
    public CubicBezier[] toArray(final Object[] objects) {
        final CubicBezier[] array = new CubicBezier[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (CubicBezier)objects[i];
        }
        return array;
    }
    
    @Override
    public CubicBezier[] toArray(final Collection<CubicBezier> collection) {
        return collection.toArray(new CubicBezier[0]);
    }
}
