// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.resources.pack;

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

public class ModifiedPackResources implements aua
{
    private final ResourcePack resourcePack;
    private final Resources resources;
    private final atz packLocationInfo;
    
    public ModifiedPackResources(final ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.resources = LabyMod.getInstance().renderPipeline().resources();
        this.packLocationInfo = new atz(resourcePack.getName(), (xg)xg.b(resourcePack.getName()), ava.c, (Optional)Optional.empty());
    }
    
    @Nullable
    public avg<InputStream> a(final String... var1) {
        return (avg<InputStream>)(() -> ModifiedPackResources.class.getResourceAsStream(String.join("/", (CharSequence[])var1)));
    }
    
    public avg<InputStream> a(final auc type, final alr location) {
        final ResourceLocation resourceLocation = this.resources.resourceLocationFactory().create(location.b(), location.a());
        final boolean isServerData = type == auc.b;
        if (!this.resourcePack.hasResource(isServerData, resourceLocation)) {
            return null;
        }
        return (avg<InputStream>)(() -> this.resourcePack.getResource(isServerData, resourceLocation));
    }
    
    public void a(@NotNull final auc packType, @NotNull final String namespace, @NotNull final String path, @NotNull final aua.a output) {
        this.resourcePack.listResources(packType == auc.b, namespace, path, (location, inputStreamSupplier) -> {
            final alr alr = location.getMinecraftLocation();
            Objects.requireNonNull(inputStreamSupplier);
            output.accept((Object)alr, (Object)inputStreamSupplier::get);
        });
    }
    
    public Set<String> a(final auc type) {
        return (type == auc.b) ? this.resourcePack.getDataNamespaces() : this.resourcePack.getClientNamespaces();
    }
    
    @Nullable
    public <T> T a(@NotNull final aun<T> type) throws IOException {
        final JsonObject object = new JsonObject();
        final JsonObject packObject = new JsonObject();
        packObject.addProperty("description", this.resourcePack.getName());
        packObject.addProperty("pack_format", (Number)11);
        object.add("pack", (JsonElement)packObject);
        final byte[] buffer = object.toString().getBytes(StandardCharsets.UTF_8);
        try (final ByteArrayInputStream stream = new ByteArrayInputStream(buffer)) {
            final Object a = atr.a((aun)type, (InputStream)stream);
            return (T)a;
        }
    }
    
    public atz a() {
        return this.packLocationInfo;
    }
    
    public String b() {
        return this.resourcePack.getName();
    }
    
    public void close() {
    }
}
