// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.resources.pack;

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

public class ModifiedPackResources implements atc
{
    private final ResourcePack resourcePack;
    private final Resources resources;
    private final atb packLocationInfo;
    
    public ModifiedPackResources(final ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.resources = LabyMod.getInstance().renderPipeline().resources();
        this.packLocationInfo = new atb(resourcePack.getName(), (wp)wp.b(resourcePack.getName()), aub.c, (Optional)Optional.empty());
    }
    
    @Nullable
    public auh<InputStream> a(final String... var1) {
        return (auh<InputStream>)(() -> ModifiedPackResources.class.getResourceAsStream(String.join("/", (CharSequence[])var1)));
    }
    
    public auh<InputStream> a(final ate type, final akv location) {
        final ResourceLocation resourceLocation = this.resources.resourceLocationFactory().create(location.b(), location.a());
        final boolean isServerData = type == ate.b;
        if (!this.resourcePack.hasResource(isServerData, resourceLocation)) {
            return null;
        }
        return (auh<InputStream>)(() -> this.resourcePack.getResource(isServerData, resourceLocation));
    }
    
    public void a(@NotNull final ate packType, @NotNull final String namespace, @NotNull final String path, @NotNull final atc.a output) {
        this.resourcePack.listResources(packType == ate.b, namespace, path, (location, inputStreamSupplier) -> {
            final akv akv = location.getMinecraftLocation();
            Objects.requireNonNull(inputStreamSupplier);
            output.accept((Object)akv, (Object)inputStreamSupplier::get);
        });
    }
    
    public Set<String> a(final ate type) {
        return (type == ate.b) ? this.resourcePack.getDataNamespaces() : this.resourcePack.getClientNamespaces();
    }
    
    @Nullable
    public <T> T a(@NotNull final atp<T> type) throws IOException {
        final JsonObject object = new JsonObject();
        final JsonObject packObject = new JsonObject();
        packObject.addProperty("description", this.resourcePack.getName());
        packObject.addProperty("pack_format", (Number)11);
        object.add("pack", (JsonElement)packObject);
        final byte[] buffer = object.toString().getBytes(StandardCharsets.UTF_8);
        try (final ByteArrayInputStream stream = new ByteArrayInputStream(buffer)) {
            final Object a = ast.a((atp)type, (InputStream)stream);
            return (T)a;
        }
    }
    
    public atb a() {
        return this.packLocationInfo;
    }
    
    public String b() {
        return this.resourcePack.getName();
    }
    
    public void close() {
    }
}
