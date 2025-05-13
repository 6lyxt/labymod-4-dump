// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.minecraft;

import net.labymod.core.main.user.shop.item.positionprovider.HideItemPositionProvider;
import net.labymod.api.client.world.item.VanillaItem;
import java.util.function.Consumer;
import java.util.HashMap;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import net.labymod.core.main.user.shop.item.positionprovider.MinecraftItemPositionProvider;

public final class MinecraftItemRegistry
{
    private static final MinecraftItemPositionProvider FALLBACK_PROVIDER;
    private final Map<ResourceLocation, MinecraftItemPositionProvider> providers;
    
    private MinecraftItemRegistry() {
        this.providers = new HashMap<ResourceLocation, MinecraftItemPositionProvider>();
    }
    
    public static MinecraftItemRegistry construct(final Consumer<MinecraftItemRegistry> registryConsumer) {
        final MinecraftItemRegistry registry = new MinecraftItemRegistry();
        registryConsumer.accept(registry);
        return registry;
    }
    
    public void register(final VanillaItem item, final MinecraftItemPositionProvider provider) {
        this.register(item.identifier(), provider);
    }
    
    public void register(final ResourceLocation id, final MinecraftItemPositionProvider provider) {
        this.providers.put(id, provider);
    }
    
    public MinecraftItemPositionProvider findProvider(final VanillaItem item) {
        return this.findProvider(item.identifier());
    }
    
    public MinecraftItemPositionProvider findProvider(final ResourceLocation id) {
        return this.providers.getOrDefault(id, MinecraftItemRegistry.FALLBACK_PROVIDER);
    }
    
    static {
        FALLBACK_PROVIDER = new HideItemPositionProvider();
    }
}
