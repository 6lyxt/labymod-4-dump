// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.world.block.BlockPosition;
import java.util.function.Predicate;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import net.labymod.api.client.gui.screen.activity.Activity;
import java.util.function.Supplier;
import net.labymod.api.client.world.canvas.Canvas;
import net.labymod.api.client.world.canvas.template.CanvasTemplate;
import java.util.function.Consumer;
import net.labymod.api.client.world.signobject.template.SignObjectFactory;
import net.labymod.api.client.world.signobject.object.SignObject;
import net.labymod.api.client.world.signobject.template.SignObjectTemplate;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface SignObjectRegistry
{
     <M extends SignObjectMeta<? extends SignObjectTemplate>> SignObject<M> createObject(final SignObjectPosition p0, final String[] p1);
    
    default <O extends SignObject<M>, M extends SignObjectMeta<T>, T extends SignObjectTemplate> void registerFactory(final T template, final SignObjectFactory<O, M, T> factory) {
        this.registerFactory(template, factory, null);
    }
    
     <O extends SignObject<M>, M extends SignObjectMeta<T>, T extends SignObjectTemplate> void registerFactory(final T p0, final SignObjectFactory<O, M, T> p1, final Consumer<O> p2);
    
    void registerCanvas(final CanvasTemplate p0, final Consumer<Canvas> p1);
    
    void registerCanvas(final CanvasTemplate p0, final Supplier<Activity> p1);
    
    void registerDummy(final SignObjectTemplate p0, final Consumer<SignObject<SignObjectMeta<SignObjectTemplate>>> p1);
    
    @NotNull
    Collection<PlacedSignObject> getObjectsInWorld();
    
    Collection<SignObject<?>> getObjectsInWorld(@NotNull final String p0);
    
    Collection<SignObject<?>> getObjectsInWorld(@NotNull final ResourceLocation p0);
    
    Collection<SignObject<?>> getObjectsInWorld(@NotNull final Predicate<SignObject<?>> p0);
    
    void getObjectsInWorld(@NotNull final String p0, @NotNull final Consumer<SignObject<?>> p1);
    
    void getObjectsInWorld(@NotNull final ResourceLocation p0, @NotNull final Consumer<SignObject<?>> p1);
    
    void getObjectsInWorld(@NotNull final Predicate<SignObject<?>> p0, @NotNull final Consumer<SignObject<?>> p1);
    
    @Nullable
    PlacedSignObject getObjectInWorld(@NotNull final BlockPosition p0);
}
