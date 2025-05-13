// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.loader.serial;

import net.labymod.api.event.Subscribe;
import com.google.gson.GsonBuilder;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.Component;
import net.labymod.api.util.gson.PathTypeAdapter;
import java.nio.file.Path;
import net.labymod.api.util.gson.ResourceLocationTypeAdapter;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.gson.NBTTagTypeAdapter;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.util.gson.VersionTypeAdapter;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.gson.ItemStackTypeAdapter;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.util.gson.RootChatTabConfigTypeAdapter;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.util.gson.TagCollectionTypeAdapter;
import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.util.gson.KeyTypeAdapter;
import net.labymod.api.client.gui.screen.key.Key;
import java.lang.reflect.Type;
import net.labymod.api.util.gson.ColorTypeAdapter;
import net.labymod.api.util.Color;
import net.labymod.api.event.labymod.config.JsonConfigLoaderInitializeEvent;

public class JsonConfigLoaderInitializeListener
{
    @Subscribe
    public void onJsonConfigLoaderInitialize(final JsonConfigLoaderInitializeEvent event) {
        final GsonBuilder builder = event.getGsonBuilder();
        builder.registerTypeAdapter((Type)Color.class, (Object)new ColorTypeAdapter());
        builder.registerTypeAdapter((Type)Key.class, (Object)new KeyTypeAdapter());
        builder.registerTypeAdapter((Type)MouseButton.class, (Object)new KeyTypeAdapter());
        builder.registerTypeAdapter((Type)TagInputWidget.TagCollection.class, (Object)new TagCollectionTypeAdapter());
        builder.registerTypeAdapter((Type)RootChatTabConfig.Type.class, (Object)new RootChatTabConfigTypeAdapter());
        builder.registerTypeHierarchyAdapter((Class)ItemStack.class, (Object)new ItemStackTypeAdapter());
        builder.registerTypeHierarchyAdapter((Class)Version.class, (Object)new VersionTypeAdapter());
        builder.registerTypeHierarchyAdapter((Class)NBTTag.class, (Object)new NBTTagTypeAdapter());
        builder.registerTypeHierarchyAdapter((Class)ResourceLocation.class, (Object)new ResourceLocationTypeAdapter());
        builder.registerTypeHierarchyAdapter((Class)Path.class, (Object)new PathTypeAdapter());
        builder.registerTypeHierarchyAdapter((Class)Component.class, (Object)ComponentService.getComponentSerializer());
        if (!MinecraftVersions.V23w40a.orNewer()) {
            builder.registerTypeHierarchyAdapter((Class)Style.class, (Object)ComponentService.getStyleSerializer());
        }
    }
}
