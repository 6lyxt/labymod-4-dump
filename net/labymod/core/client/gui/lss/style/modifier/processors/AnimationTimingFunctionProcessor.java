// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.core.client.gui.lss.style.function.parameter.FixedElement;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;

public class AnimationTimingFunctionProcessor implements PostProcessor
{
    @Override
    public boolean requiresPostProcessing(final String key, final Element element, final Class<?> type) {
        if (element instanceof final Function function) {
            return function.getName().equals("cubic-bezier");
        }
        return CubicBezier.of(element.getRawValue()) != null;
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Element element) throws Exception {
        if (element instanceof FixedElement) {
            final String raw = element.getRawValue();
            final CubicBezier cubicBezier = CubicBezier.of(raw);
            if (cubicBezier != null) {
                return cubicBezier;
            }
            throw new IllegalArgumentException("Unknown cubic-bezier: " + raw);
        }
        else {
            if (element instanceof final Function function) {
                return parse(function, function.firstValues(widget, key));
            }
            return null;
        }
    }
    
    @Override
    public int getPriority() {
        return 2;
    }
    
    public static CubicBezier parse(final Function function, final ProcessedObject[] values) {
        if (!function.getName().equals("cubic-bezier")) {
            return null;
        }
        final double p0 = (double)values[0].value();
        final double p2 = (double)values[1].value();
        final double p3 = (double)values[2].value();
        final double p4 = (double)values[3].value();
        return new CubicBezier(p0, p2, p3, p4);
    }
}
