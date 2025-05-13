// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.resources.pack;

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

public class ModifiedPackResources implements asq
{
    private final ResourcePack resourcePack;
    private final Resources resources;
    private final asp packLocationInfo;
    
    public ModifiedPackResources(final ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.resources = LabyMod.getInstance().renderPipeline().resources();
        this.packLocationInfo = new asp(resourcePack.getName(), (wz)wz.b(resourcePack.getName()), atq.c, (Optional)Optional.empty());
    }
    
    @Nullable
    public atw<InputStream> a(final String... var1) {
        return (atw<InputStream>)(() -> ModifiedPackResources.class.getResourceAsStream(String.join("/", (CharSequence[])var1)));
    }
    
    public atw<InputStream> a(final ass type, final akr location) {
        final ResourceLocation resourceLocation = this.resources.resourceLocationFactory().create(location.b(), location.a());
        final boolean isServerData = type == ass.b;
        if (!this.resourcePack.hasResource(isServerData, resourceLocation)) {
            return null;
        }
        return (atw<InputStream>)(() -> this.resourcePack.getResource(isServerData, resourceLocation));
    }
    
    public void a(@NotNull final ass packType, @NotNull final String namespace, @NotNull final String path, @NotNull final asq.a output) {
        this.resourcePack.listResources(packType == ass.b, namespace, path, (location, inputStreamSupplier) -> {
            final akr akr = location.getMinecraftLocation();
            Objects.requireNonNull(inputStreamSupplier);
            output.accept((Object)akr, (Object)inputStreamSupplier::get);
        });
    }
    
    public Set<String> a(final ass type) {
        return (type == ass.b) ? this.resourcePack.getDataNamespaces() : this.resourcePack.getClientNamespaces();
    }
    
    @Nullable
    public <T> T a(final atd<T> metadataSectionSerializer) {
        final JsonObject object = new JsonObject();
        final JsonObject packObject = new JsonObject();
        packObject.addProperty("description", this.resourcePack.getName());
        packObject.addProperty("pack_format", (Number)11);
        object.add("pack", (JsonElement)packObject);
        return (T)ash.a((atd)metadataSectionSerializer, (InputStream)new ByteArrayInputStream(object.toString().getBytes(StandardCharsets.UTF_8)));
    }
    
    public asp a() {
        return this.packLocationInfo;
    }
    
    public String b() {
        return this.resourcePack.getName();
    }
    
    public void close() {
    }
}
