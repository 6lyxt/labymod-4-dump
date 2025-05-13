// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font.text;

import java.util.Iterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import java.util.ArrayList;
import it.unimi.dsi.fastutil.floats.Float2ObjectOpenHashMap;
import java.util.List;
import net.labymod.api.client.render.font.RenderableComponent;

public class RenderableComponentFormatter
{
    public static List<FormattedRenderableComponent> format(final RenderableComponent component) {
        final Float2ObjectMap<List<RenderableComponent>> texts = (Float2ObjectMap<List<RenderableComponent>>)new Float2ObjectOpenHashMap();
        formatInternal(texts, component);
        final List<FormattedRenderableComponent> components = new ArrayList<FormattedRenderableComponent>();
        for (final Float2ObjectMap.Entry<List<RenderableComponent>> entry : texts.float2ObjectEntrySet()) {
            components.add(FormattedRenderableComponent.composite(entry.getFloatKey(), (List<RenderableComponent>)entry.getValue()));
        }
        return components;
    }
    
    private static void formatInternal(final Float2ObjectMap<List<RenderableComponent>> texts, final RenderableComponent component) {
        List<RenderableComponent> renderableComponent = (List<RenderableComponent>)texts.get(component.getYOffset());
        if (renderableComponent == null) {
            renderableComponent = new ArrayList<RenderableComponent>();
            texts.put(component.getYOffset(), (Object)renderableComponent);
        }
        renderableComponent.add(component);
        for (final RenderableComponent child : component.getChildren()) {
            formatInternal(texts, child);
        }
    }
}
