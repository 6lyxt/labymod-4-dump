// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.resources.pack;

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
import java.util.Optional;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.pack.ResourcePack;

public class ModifiedPackResources implements aug
{
    private final ResourcePack resourcePack;
    private final Resources resources;
    private final auf packLocationInfo;
    
    public ModifiedPackResources(final ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.resources = LabyMod.getInstance().renderPipeline().resources();
        this.packLocationInfo = new auf(resourcePack.getName(), (xv)xv.b(resourcePack.getName()), avh.c, (Optional)Optional.empty());
    }
    
    @Nullable
    public avn<InputStream> a(final String... var1) {
        return (avn<InputStream>)(() -> ModifiedPackResources.class.getResourceAsStream(String.join("/", (CharSequence[])var1)));
    }
    
    public avn<InputStream> a(final aui type, final alz location) {
        final ResourceLocation resourceLocation = this.resources.resourceLocationFactory().create(location.b(), location.a());
        final boolean isServerData = type == aui.b;
        if (!this.resourcePack.hasResource(isServerData, resourceLocation)) {
            return null;
        }
        return (avn<InputStream>)(() -> this.resourcePack.getResource(isServerData, resourceLocation));
    }
    
    public void a(@NotNull final aui packType, @NotNull final String namespace, @NotNull final String path, @NotNull final aug.a output) {
        this.resourcePack.listResources(packType == aui.b, namespace, path, (location, inputStreamSupplier) -> {
            final alz alz = location.getMinecraftLocation();
            Objects.requireNonNull(inputStreamSupplier);
            output.accept((Object)alz, (Object)inputStreamSupplier::get);
        });
    }
    
    public Set<String> a(final aui type) {
        return (type == aui.b) ? this.resourcePack.getDataNamespaces() : this.resourcePack.getClientNamespaces();
    }
    
    @Nullable
    public <T> T a(final aut<T> metadataSectionSerializer) {
        final JsonObject object = new JsonObject();
        final JsonObject packObject = new JsonObject();
        packObject.addProperty("description", this.resourcePack.getName());
        packObject.addProperty("pack_format", (Number)11);
        object.add("pack", (JsonElement)packObject);
        return (T)atx.a((aut)metadataSectionSerializer, (InputStream)new ByteArrayInputStream(object.toString().getBytes(StandardCharsets.UTF_8)));
    }
    
    public auf a() {
        return this.packLocationInfo;
    }
    
    public String b() {
        return this.resourcePack.getName();
    }
    
    public void close() {
    }
}
