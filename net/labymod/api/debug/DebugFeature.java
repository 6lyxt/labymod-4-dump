// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.debug;

import java.util.Iterator;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import java.util.ArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.List;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.gui.screen.key.Key;

public class DebugFeature
{
    private final String name;
    private final Key key;
    private final BooleanSupplier permissionCheck;
    private final ImGuiBooleanType state;
    private final List<BooleanConsumer> listeners;
    
    public DebugFeature(final String name, final Key key) {
        this(name, key, () -> true);
    }
    
    public DebugFeature(final String name, final Key key, final BooleanSupplier permissionCheck) {
        this.listeners = new ArrayList<BooleanConsumer>();
        this.name = name;
        this.key = key;
        this.permissionCheck = permissionCheck;
        this.state = LabyImGui.booleanType();
    }
    
    public void addListener(final BooleanConsumer listener) {
        this.listeners.add(listener);
    }
    
    public void toggleState(final Key key) {
        if (!this.permissionCheck.getAsBoolean()) {
            return;
        }
        if (key == this.key) {
            this._toggleState();
        }
    }
    
    public void toggleState() {
        if (!this.permissionCheck.getAsBoolean()) {
            return;
        }
        this._toggleState();
    }
    
    private void _toggleState() {
        this.state.set(!this.state.get());
        for (final BooleanConsumer listener : this.listeners) {
            listener.accept(this.state.get());
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isEnabled() {
        return this.permissionCheck.getAsBoolean() && this.state.get();
    }
    
    public ImGuiBooleanType getState() {
        return this.state;
    }
}
