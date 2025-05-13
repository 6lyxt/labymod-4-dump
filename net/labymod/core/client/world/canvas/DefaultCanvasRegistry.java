// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.canvas;

import net.labymod.api.client.world.canvas.Canvas;
import net.labymod.api.client.gui.screen.activity.Activity;
import java.util.function.Supplier;
import net.labymod.api.client.world.canvas.CanvasMeta;
import net.labymod.api.client.world.canvas.template.CanvasTemplate;
import net.labymod.api.client.world.canvas.CanvasRendererFactory;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.signobject.SignObjectRegistry;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.world.canvas.CanvasRegistry;

@Singleton
@Implements(CanvasRegistry.class)
public class DefaultCanvasRegistry implements CanvasRegistry
{
    private final SignObjectRegistry registry;
    
    public DefaultCanvasRegistry(final SignObjectRegistry registry) {
        this.registry = registry;
    }
    
    @Override
    public void registerRenderer(@NotNull final ResourceLocation identifier, @NotNull final CanvasRendererFactory factory) {
        this.registry.registerCanvas(CanvasTemplate.simple(identifier), canvas -> canvas.setRenderer(factory.createRenderer(canvas.meta())));
    }
    
    @Override
    public void registerActivity(@NotNull final ResourceLocation identifier, @NotNull final Supplier<Activity> supplier) {
        this.registry.registerCanvas(CanvasTemplate.simple(identifier), supplier);
    }
}
