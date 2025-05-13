// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.resources.pack;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Set;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.pack.ResourcePack;

public class ModifiedPackResources implements amh
{
    private final ResourcePack resourcePack;
    private final Resources resources;
    
    public ModifiedPackResources(final ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.resources = LabyMod.getInstance().renderPipeline().resources();
    }
    
    @Nullable
    public anl<InputStream> a(final String... var1) {
        return (anl<InputStream>)(() -> ModifiedPackResources.class.getResourceAsStream(String.join("/", (CharSequence[])var1)));
    }
    
    public anl<InputStream> a(final ami type, final aew location) {
        final ResourceLocation resourceLocation = this.resources.resourceLocationFactory().create(location.b(), location.a());
        final boolean isServerData = type == ami.b;
        if (!this.resourcePack.hasResource(isServerData, resourceLocation)) {
            return null;
        }
        return (anl<InputStream>)(() -> this.resourcePack.getResource(isServerData, resourceLocation));
    }
    
    public void a(@NotNull final ami packType, @NotNull final String namespace, @NotNull final String path, @NotNull final amh.a output) {
        this.resourcePack.listResources(packType == ami.b, namespace, path, (location, inputStreamSupplier) -> {
            final aew aew = location.getMinecraftLocation();
            Objects.requireNonNull(inputStreamSupplier);
            output.accept((Object)aew, (Object)inputStreamSupplier::get);
        });
    }
    
    public Set<String> a(final ami type) {
        return (type == ami.b) ? this.resourcePack.getDataNamespaces() : this.resourcePack.getClientNamespaces();
    }
    
    @Nullable
    public <T> T a(final amt<T> metadataSectionSerializer) {
        final JsonObject object = new JsonObject();
        final JsonObject packObject = new JsonObject();
        packObject.addProperty("description", this.resourcePack.getName());
        packObject.addProperty("pack_format", (Number)11);
        object.add("pack", (JsonElement)packObject);
        return (T)amb.a((amt)metadataSectionSerializer, (InputStream)new ByteArrayInputStream(object.toString().getBytes(StandardCharsets.UTF_8)));
    }
    
    public boolean b() {
        return true;
    }
    
    public String a() {
        return this.resourcePack.getName();
    }
    
    public void close() {
    }
}
