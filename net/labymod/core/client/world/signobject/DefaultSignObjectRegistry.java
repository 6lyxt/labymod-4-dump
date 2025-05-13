// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.signobject;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.world.block.BlockPosition;
import java.util.Iterator;
import java.util.Objects;
import java.util.ArrayList;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.world.signobject.PlacedSignObject;
import java.util.Collection;
import net.labymod.core.client.world.canvas.ActivityCanvasRenderer;
import net.labymod.api.client.gui.screen.activity.Activity;
import java.util.function.Supplier;
import net.labymod.api.client.world.canvas.Canvas;
import java.util.function.Consumer;
import net.labymod.api.client.world.signobject.template.SignObjectFactory;
import net.labymod.api.client.resources.IllegalResourceLocationException;
import java.util.Arrays;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.world.canvas.CanvasRenderer;
import net.labymod.core.client.world.canvas.DebugCanvasRenderer;
import net.labymod.api.client.world.canvas.template.CanvasTemplate;
import net.labymod.api.Laby;
import java.util.HashMap;
import net.labymod.api.client.world.signobject.template.SignObjectTemplate;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;
import net.labymod.api.client.world.signobject.object.SignObject;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.world.signobject.SignObjectRegistry;

@Singleton
@Implements(SignObjectRegistry.class)
public class DefaultSignObjectRegistry implements SignObjectRegistry
{
    private final ResourceLocationFactory resourceLocationFactory;
    private final SignObjectDiscovery discovery;
    private final Map<ResourceLocation, RegisteredFactory<? extends SignObject<? extends SignObjectMeta<? extends SignObjectTemplate>>, ? extends SignObjectMeta<? extends SignObjectTemplate>, ? extends SignObjectTemplate>> factories;
    
    public DefaultSignObjectRegistry(final ResourceLocationFactory resourceLocationFactory) {
        this.factories = new HashMap<ResourceLocation, RegisteredFactory<? extends SignObject<? extends SignObjectMeta<? extends SignObjectTemplate>>, ? extends SignObjectMeta<? extends SignObjectTemplate>, ? extends SignObjectTemplate>>();
        this.resourceLocationFactory = resourceLocationFactory;
        this.discovery = new SignObjectDiscovery(this);
        Laby.labyAPI().eventBus().registerListener(this.discovery);
        if (Laby.labyAPI().labyModLoader().isDevelopmentEnvironment()) {
            this.registerDebugCanvas();
        }
    }
    
    private void registerDebugCanvas() {
        this.registerCanvas(CanvasTemplate.builder(ResourceLocation.create("laby", "debug"), "labymod").build(), canvas -> {
            canvas.setRenderer(new DebugCanvasRenderer());
            canvas.addListener(object -> Laby.labyAPI().minecraft().chatExecutor().displayActionBar(Component.text("Debug canvas disposed: " + String.valueOf(object.position()))));
        });
    }
    
    @Override
    public <M extends SignObjectMeta<? extends SignObjectTemplate>> SignObject<M> createObject(final SignObjectPosition position, final String[] rawMeta) {
        final ResourceLocation location = this.parseLocation(rawMeta);
        if (location == null) {
            return null;
        }
        final RegisteredFactory factory = this.factories.get(location);
        if (factory == null) {
            return null;
        }
        final SignObjectMeta meta = factory.template.parseMeta(Arrays.copyOfRange(rawMeta, 1, rawMeta.length));
        if (meta == null) {
            return null;
        }
        final SignObject object = factory.factory.newObject((M)meta, position);
        if (object == null) {
            return null;
        }
        if (factory.postModifier != null) {
            factory.postModifier.accept((O)object);
        }
        return object;
    }
    
    private ResourceLocation parseLocation(final String[] meta) {
        if (meta.length == 0) {
            return null;
        }
        try {
            return this.resourceLocationFactory.parse(meta[0]);
        }
        catch (final IllegalResourceLocationException ignored) {
            return null;
        }
    }
    
    @Override
    public <O extends SignObject<M>, M extends SignObjectMeta<T>, T extends SignObjectTemplate> void registerFactory(final T template, final SignObjectFactory<O, M, T> factory, final Consumer<O> postModifier) {
        final ResourceLocation location = template.location();
        if (this.factories.containsKey(location)) {
            throw new IllegalArgumentException("Sign object " + String.valueOf(location) + " is already registered");
        }
        this.factories.put(location, new RegisteredFactory<SignObject<? extends SignObjectMeta<? extends SignObjectTemplate>>, SignObjectMeta<? extends SignObjectTemplate>, SignObjectTemplate>(template, (SignObjectFactory<SignObject<? extends SignObjectMeta<? extends SignObjectTemplate>>, SignObjectMeta<? extends SignObjectTemplate>, SignObjectTemplate>)factory, (Consumer<SignObject<? extends SignObjectMeta<? extends SignObjectTemplate>>>)postModifier));
        this.discovery.rediscoverAllSigns();
    }
    
    @Override
    public void registerCanvas(final CanvasTemplate template, final Consumer<Canvas> postModifier) {
        this.registerFactory(template, Canvas::createCanvas, postModifier);
    }
    
    @Override
    public void registerCanvas(final CanvasTemplate template, final Supplier<Activity> activitySupplier) {
        this.registerCanvas(template, canvas -> canvas.setRenderer(new ActivityCanvasRenderer(activitySupplier.get())));
    }
    
    @Override
    public void registerDummy(final SignObjectTemplate template, final Consumer<SignObject<SignObjectMeta<SignObjectTemplate>>> postModifier) {
        this.registerFactory(template, (meta, pos) -> SignObject.createDummy(meta, pos), postModifier);
    }
    
    @NotNull
    @Override
    public Collection<PlacedSignObject> getObjectsInWorld() {
        return this.discovery.trackedEntities().values();
    }
    
    @Override
    public Collection<SignObject<?>> getObjectsInWorld(@NotNull final String namespace) {
        return this.getObjectsInWorld(object -> object.meta().template().location().getNamespace().equals(namespace));
    }
    
    @Override
    public Collection<SignObject<?>> getObjectsInWorld(@NotNull final ResourceLocation location) {
        return this.getObjectsInWorld(object -> object.meta().template().location().equals(location));
    }
    
    @Override
    public Collection<SignObject<?>> getObjectsInWorld(@NotNull final Predicate<SignObject<?>> predicate) {
        final ArrayList obj;
        final Collection<SignObject<?>> objects = obj = new ArrayList();
        Objects.requireNonNull(obj);
        this.getObjectsInWorld(predicate, (Consumer<SignObject<?>>)obj::add);
        return objects;
    }
    
    @Override
    public void getObjectsInWorld(@NotNull final String namespace, @NotNull final Consumer<SignObject<?>> consumer) {
        this.getObjectsInWorld(object -> object.meta().template().location().getNamespace().equals(namespace), consumer);
    }
    
    @Override
    public void getObjectsInWorld(@NotNull final ResourceLocation location, @NotNull final Consumer<SignObject<?>> consumer) {
        this.getObjectsInWorld(object -> object.meta().template().location().equals(location), consumer);
    }
    
    @Override
    public void getObjectsInWorld(@NotNull final Predicate<SignObject<?>> predicate, @NotNull final Consumer<SignObject<?>> consumer) {
        for (final PlacedSignObject placed : this.getObjectsInWorld()) {
            for (final SignObject<?> object : placed.objects()) {
                if (object != null && predicate.test(object)) {
                    consumer.accept(object);
                }
            }
        }
    }
    
    @Nullable
    @Override
    public PlacedSignObject getObjectInWorld(@NotNull final BlockPosition position) {
        return this.discovery.trackedEntities().get(position);
    }
    
    record RegisteredFactory<O extends SignObject<M>, M extends SignObjectMeta<T>, T extends SignObjectTemplate>(T template, SignObjectFactory<O, M, T> factory, Consumer<O> postModifier) {}
}
