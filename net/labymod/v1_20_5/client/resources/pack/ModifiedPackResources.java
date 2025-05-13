// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.resources.pack;

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

public class ModifiedPackResources implements atb
{
    private final ResourcePack resourcePack;
    private final Resources resources;
    private final ata packLocationInfo;
    
    public ModifiedPackResources(final ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.resources = LabyMod.getInstance().renderPipeline().resources();
        this.packLocationInfo = new ata(resourcePack.getName(), (xp)xp.b(resourcePack.getName()), aub.c, (Optional)Optional.empty());
    }
    
    @Nullable
    public auh<InputStream> a(final String... var1) {
        return (auh<InputStream>)(() -> ModifiedPackResources.class.getResourceAsStream(String.join("/", (CharSequence[])var1)));
    }
    
    public auh<InputStream> a(final atd type, final alf location) {
        final ResourceLocation resourceLocation = this.resources.resourceLocationFactory().create(location.b(), location.a());
        final boolean isServerData = type == atd.b;
        if (!this.resourcePack.hasResource(isServerData, resourceLocation)) {
            return null;
        }
        return (auh<InputStream>)(() -> this.resourcePack.getResource(isServerData, resourceLocation));
    }
    
    public void a(@NotNull final atd packType, @NotNull final String namespace, @NotNull final String path, @NotNull final atb.a output) {
        this.resourcePack.listResources(packType == atd.b, namespace, path, (location, inputStreamSupplier) -> {
            final alf alf = location.getMinecraftLocation();
            Objects.requireNonNull(inputStreamSupplier);
            output.accept((Object)alf, (Object)inputStreamSupplier::get);
        });
    }
    
    public Set<String> a(final atd type) {
        return (type == atd.b) ? this.resourcePack.getDataNamespaces() : this.resourcePack.getClientNamespaces();
    }
    
    @Nullable
    public <T> T a(final ato<T> metadataSectionSerializer) {
        final JsonObject object = new JsonObject();
        final JsonObject packObject = new JsonObject();
        packObject.addProperty("description", this.resourcePack.getName());
        packObject.addProperty("pack_format", (Number)11);
        object.add("pack", (JsonElement)packObject);
        return (T)ass.a((ato)metadataSectionSerializer, (InputStream)new ByteArrayInputStream(object.toString().getBytes(StandardCharsets.UTF_8)));
    }
    
    public ata a() {
        return this.packLocationInfo;
    }
    
    public String b() {
        return this.resourcePack.getName();
    }
    
    public void close() {
    }
}
