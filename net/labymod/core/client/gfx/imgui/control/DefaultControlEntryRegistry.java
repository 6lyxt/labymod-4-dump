// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.imgui.control;

import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import java.util.function.Function;
import java.util.Map;
import net.labymod.core.main.LabyMod;
import net.labymod.api.util.KeyValue;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.imgui.control.ControlEntryRegistry;
import net.labymod.api.client.gfx.imgui.control.ControlEntry;
import net.labymod.api.service.DefaultRegistry;

@Singleton
@Implements(ControlEntryRegistry.class)
public final class DefaultControlEntryRegistry extends DefaultRegistry<ControlEntry> implements ControlEntryRegistry
{
    private static final Logging LOGGER;
    private final AdvancedConfig config;
    
    public DefaultControlEntryRegistry() {
        this.config = Laby.labyAPI().config().other().advanced();
    }
    
    @Override
    protected void onRegister(final KeyValue<ControlEntry> value) {
        super.onRegister(value);
        final String key = value.getKey();
        DefaultControlEntryRegistry.LOGGER.debug("A new control entry has been assigned to {}", key);
        final Map<String, Boolean> debugWindows = this.config.debugWindows();
        Boolean currentState = debugWindows.get(key);
        if (currentState == null) {
            currentState = value.getValue().getVisible().get();
            debugWindows.put(key, currentState);
            LabyMod.getInstance().getLabyConfig().save();
        }
        value.getValue().getVisible().set(currentState);
    }
    
    @Override
    public void registerEntry(final boolean defaultOpen, final Function<ImGuiBooleanType, ImGuiWindow> windowFactory) {
        this.register(new ControlEntry(windowFactory.apply(LabyImGui.booleanType(defaultOpen))));
    }
    
    public void setState(final ImGuiWindow window) {
        final String id = this.findId(window);
        if (id == null) {
            return;
        }
        final ImGuiBooleanType visible = window.getVisible();
        final Map<String, Boolean> debugWindows = this.config.debugWindows();
        final Boolean currentState = debugWindows.get(id);
        if (currentState == visible.get()) {
            return;
        }
        debugWindows.put(id, visible.get());
        LabyMod.getInstance().getLabyConfig().save();
    }
    
    @Nullable
    public String findId(final ImGuiWindow window) {
        for (final KeyValue<ControlEntry> element : this.getElements()) {
            final ControlEntry entry = element.getValue();
            if (entry.getWindow() == window) {
                return entry.getId();
            }
        }
        return null;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
