// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.math.LssMath;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.PrimitiveHelper;
import net.labymod.core.client.gui.lss.style.function.parameter.FixedElement;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.TypeParser;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;

public class PercentagePostProcessor implements PostProcessor
{
    public final TypeParser typeParser;
    
    public PercentagePostProcessor(final TypeParser typeParser) {
        this.typeParser = typeParser;
    }
    
    @Override
    public boolean requiresPostProcessing(final String key, final Element element, final Class<?> type) {
        final String rawValue = element.getRawValue();
        return element instanceof FixedElement && PrimitiveHelper.isNumber(type) && (rawValue.endsWith("%") || rawValue.endsWith("vh") || rawValue.endsWith("vw")) && !rawValue.contains(" ") && (rawValue.charAt(0) == '-' || rawValue.charAt(0) == '+' || (rawValue.charAt(0) >= '0' && rawValue.charAt(0) <= '9'));
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Element element) throws Exception {
        final String rawValue = element.getRawValue();
        String rawNumber;
        if (rawValue.endsWith("%")) {
            rawNumber = rawValue.substring(0, rawValue.length() - 1);
        }
        else {
            if (!rawValue.endsWith("vh") && !rawValue.endsWith("vw")) {
                return 0.0f;
            }
            rawNumber = rawValue.substring(0, rawValue.length() - 2);
        }
        final float number = Float.parseFloat(rawNumber);
        final Parent parent = (rawValue.endsWith("vh") || rawValue.endsWith("vw")) ? widget.getRoot() : widget.getParent();
        if (parent == null) {
            return this.typeParser.parseValue(type, "0");
        }
        final float percentageValue = LssMath.getPercentageValue(parent, key, number);
        return this.typeParser.parseValue(type, String.valueOf(percentageValue));
    }
}
