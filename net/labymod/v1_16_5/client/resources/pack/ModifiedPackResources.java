// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.resources.pack;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import java.io.InputStream;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.pack.ResourcePack;

public class ModifiedPackResources implements abj
{
    private final ResourcePack resourcePack;
    private final Resources resources;
    
    public ModifiedPackResources(final ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.resources = LabyMod.getInstance().renderPipeline().resources();
    }
    
    @Nullable
    public InputStream b(final String path) throws IOException {
        return ModifiedPackResources.class.getResourceAsStream("/" + path);
    }
    
    public InputStream a(final abk type, final vk location) throws IOException {
        final ResourceLocation resourceLocation = this.resources.resourceLocationFactory().create(location.b(), location.a());
        return (type == abk.b) ? this.resourcePack.getDataResource(resourceLocation) : this.resourcePack.getClientResource(resourceLocation);
    }
    
    public Collection<vk> a(@NotNull final abk packType, @NotNull final String namespace, @NotNull final String path, final int var4, @NotNull final Predicate<String> filter) {
        final List<vk> locations = new ArrayList<vk>();
        this.resourcePack.listResources(packType == abk.b, namespace, path, (location, inputStreamSupplier) -> {
            locations.add(location.getMinecraftLocation());
            inputStreamSupplier.silentClose();
            return;
        });
        return locations;
    }
    
    public boolean b(final abk type, final vk location) {
        final ResourceLocation resourceLocation = this.resources.resourceLocationFactory().create(location.b(), location.a());
        return (type == abk.b) ? this.resourcePack.hasDataResource(resourceLocation) : this.resourcePack.hasClientResource(resourceLocation);
    }
    
    public Set<String> a(final abk type) {
        return (type == abk.b) ? this.resourcePack.getDataNamespaces() : this.resourcePack.getClientNamespaces();
    }
    
    @Nullable
    public <T> T a(final abn<T> metadataSectionSerializer) {
        final JsonObject object = new JsonObject();
        final JsonObject packObject = new JsonObject();
        packObject.addProperty("description", this.a());
        packObject.addProperty("pack_format", (Number)7);
        object.add("pack", (JsonElement)packObject);
        return (T)abg.a((abn)metadataSectionSerializer, (InputStream)new ByteArrayInputStream(object.toString().getBytes(StandardCharsets.UTF_8)));
    }
    
    public String a() {
        return this.resourcePack.getName();
    }
    
    public void close() {
    }
}
