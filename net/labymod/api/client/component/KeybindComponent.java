// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.client.options.MinecraftInputMapping;

public interface KeybindComponent extends BaseComponent<KeybindComponent>
{
    default Builder builder() {
        return new Builder();
    }
    
    String getKeybind();
    
    @Nullable
    default MinecraftInputMapping getInputMapping() {
        return Laby.labyAPI().minecraft().options().getInputMapping(this.getKeybind());
    }
    
    KeybindComponent plainCopy();
    
    KeybindComponent keybind(final String p0);
    
    default KeybindComponent keybind(final MinecraftInputMapping keybind) {
        return this.keybind(keybind.getName());
    }
    
    Component resolveKeybind();
    
    default Builder toBuilder() {
        return new Builder(this);
    }
    
    public static class Builder extends Component.Builder<KeybindComponent, Builder>
    {
        protected String keybind;
        
        protected Builder() {
            this.keybind = "";
        }
        
        protected Builder(final KeybindComponent component) {
            super(component);
            this.keybind = "";
            this.keybind = component.getKeybind();
        }
        
        public Builder keybind(final String keybind) {
            this.keybind = keybind;
            return this;
        }
        
        public Builder keybind(final MinecraftInputMapping keybind) {
            return this.keybind(keybind.getName());
        }
        
        @Override
        public KeybindComponent build() {
            Objects.requireNonNull(this.keybind, "Keybind cannot be null!");
            return ComponentService.keybindComponent(this.keybind, this.isEmpty() ? null : this.buildStyle(), this.children);
        }
    }
}
