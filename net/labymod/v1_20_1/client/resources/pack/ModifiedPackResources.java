// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.resources.pack;

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

public class ModifiedPackResources implements ajl
{
    private final ResourcePack resourcePack;
    private final Resources resources;
    
    public ModifiedPackResources(final ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.resources = LabyMod.getInstance().renderPipeline().resources();
    }
    
    @Nullable
    public akp<InputStream> a(final String... var1) {
        return (akp<InputStream>)(() -> ModifiedPackResources.class.getResourceAsStream(String.join("/", (CharSequence[])var1)));
    }
    
    public akp<InputStream> a(final ajm type, final acq location) {
        final ResourceLocation resourceLocation = this.resources.resourceLocationFactory().create(location.b(), location.a());
        final boolean isServerData = type == ajm.b;
        if (!this.resourcePack.hasResource(isServerData, resourceLocation)) {
            return null;
        }
        return (akp<InputStream>)(() -> this.resourcePack.getResource(isServerData, resourceLocation));
    }
    
    public void a(@NotNull final ajm packType, @NotNull final String namespace, @NotNull final String path, @NotNull final ajl.a output) {
        this.resourcePack.listResources(packType == ajm.b, namespace, path, (location, inputStreamSupplier) -> {
            final acq acq = location.getMinecraftLocation();
            Objects.requireNonNull(inputStreamSupplier);
            output.accept((Object)acq, (Object)inputStreamSupplier::get);
        });
    }
    
    public Set<String> a(final ajm type) {
        return (type == ajm.b) ? this.resourcePack.getDataNamespaces() : this.resourcePack.getClientNamespaces();
    }
    
    @Nullable
    public <T> T a(final ajx<T> metadataSectionSerializer) {
        final JsonObject object = new JsonObject();
        final JsonObject packObject = new JsonObject();
        packObject.addProperty("description", this.resourcePack.getName());
        packObject.addProperty("pack_format", (Number)11);
        object.add("pack", (JsonElement)packObject);
        return (T)ajh.a((ajx)metadataSectionSerializer, (InputStream)new ByteArrayInputStream(object.toString().getBytes(StandardCharsets.UTF_8)));
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
