// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.component.mapper;

import net.labymod.api.client.component.Component;
import java.util.function.Consumer;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.component.flattener.ComplexMapper;

public class ScoreComponentMapper implements ComplexMapper<ScoreComponent>
{
    @Override
    public void map(final ScoreComponent value, final Consumer<Component> consumer) {
        consumer.accept(value.value());
    }
}
