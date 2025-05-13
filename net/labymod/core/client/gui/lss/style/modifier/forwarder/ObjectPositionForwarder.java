// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.util.StringUtil;
import java.util.Iterator;
import java.util.Collection;
import net.labymod.api.client.gui.screen.widget.attributes.ObjectPosition;
import java.util.function.Supplier;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.HorizontalAlignment;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;

public class ObjectPositionForwarder implements Forwarder
{
    private final WidgetModifier widgetModifier;
    
    public ObjectPositionForwarder(final WidgetModifier widgetModifier) {
        this.widgetModifier = widgetModifier;
    }
    
    @Override
    public boolean requiresForwarding(final Widget widget, final String key) {
        return key.equals("objectPosition");
    }
    
    @Override
    public void forward(final Widget widget, final String key, final ProcessedObject object) throws Exception {
        if (widget instanceof final IconWidget icon) {
            final ObjectPosition position = icon.objectPosition();
            final String s = (String)object.value();
            final String[] parts = s.split(" ");
            final Collection<PositionEntry> entries = new ArrayList<PositionEntry>();
            for (int i = 0; i < parts.length; ++i) {
                Object value = this.parsePart(icon, null, parts[i]);
                Object addition = null;
                if (value instanceof HorizontalAlignment || value instanceof VerticalAlignment) {
                    if ((value == HorizontalAlignment.CENTER || value == VerticalAlignment.CENTER) && this.contains(entries, value.getClass())) {
                        value = ((value instanceof HorizontalAlignment) ? VerticalAlignment.CENTER : HorizontalAlignment.CENTER);
                    }
                    if (this.contains(entries, value.getClass())) {
                        throw new IllegalArgumentException("Duplicate horizontal/vertical alignment (" + String.valueOf(value) + ") found in object-position");
                    }
                    if (parts.length > 1 && i < parts.length - 1) {
                        addition = this.parsePart(icon, value, parts[i + 1]);
                    }
                }
                Supplier<Float> offset;
                if (value instanceof final Supplier supplier) {
                    offset = supplier;
                    value = ((i == 0) ? HorizontalAlignment.CENTER : VerticalAlignment.CENTER);
                }
                else if (addition instanceof Float) {
                    offset = (Supplier)addition;
                    ++i;
                }
                else {
                    offset = (() -> 0.0f);
                }
                if (parts.length == 1) {
                    if (value instanceof HorizontalAlignment) {
                        entries.add(new PositionEntry(VerticalAlignment.CENTER, () -> 0.0f));
                    }
                    if (value instanceof VerticalAlignment) {
                        entries.add(new PositionEntry(HorizontalAlignment.CENTER, () -> 0.0f));
                    }
                }
                entries.add(new PositionEntry(value, offset));
            }
            for (final PositionEntry entry : entries) {
                this.applyValue(position, entry.alignment, entry.offset);
            }
        }
    }
    
    private Object parsePart(final IconWidget widget, final Object value, final String part) throws Exception {
        final String uppercasePart = StringUtil.toUppercase(part);
        final HorizontalAlignment horizontalAlignment = HorizontalAlignment.of(uppercasePart);
        if (horizontalAlignment != null) {
            return horizontalAlignment;
        }
        final VerticalAlignment verticalAlignment = VerticalAlignment.of(uppercasePart);
        if (verticalAlignment != null) {
            return value;
        }
        if (!part.endsWith("%")) {
            final float parsed = Float.parseFloat(part);
            return () -> parsed;
        }
        final float percentage = Float.parseFloat(part.substring(0, part.length() - 1)) / 100.0f;
        final boolean horizontal = value instanceof HorizontalAlignment;
        return () -> {
            final float size = horizontal ? widget.iconBounds().getWidth() : widget.iconBounds().getHeight();
            return size * percentage;
        };
    }
    
    private void applyValue(final ObjectPosition position, final Object alignment, final Supplier<Float> offset) {
        if (alignment instanceof final HorizontalAlignment horizontalAlignment) {
            position.setHorizontalAlignment(horizontalAlignment);
            position.setHorizontalOffset(offset);
        }
        if (alignment instanceof final VerticalAlignment verticalAlignment) {
            position.setVerticalAlignment(verticalAlignment);
            position.setVerticalOffset(offset);
        }
    }
    
    @Override
    public Class<?> getType(final Widget widget, final String key, final String value) {
        return String.class;
    }
    
    @Override
    public int getPriority() {
        return -1;
    }
    
    private boolean contains(final Collection<PositionEntry> entries, final Class<?> alignmentClass) {
        for (final PositionEntry entry : entries) {
            if (entry.alignment.getClass() == alignmentClass) {
                return true;
            }
        }
        return false;
    }
    
    private class PositionEntry
    {
        private final Object alignment;
        private final Supplier<Float> offset;
        
        public PositionEntry(final ObjectPositionForwarder objectPositionForwarder, final Object alignment, final Supplier<Float> offset) {
            this.alignment = alignment;
            this.offset = offset;
        }
    }
}
