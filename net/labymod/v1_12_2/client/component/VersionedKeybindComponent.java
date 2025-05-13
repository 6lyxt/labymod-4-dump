// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.component;

import java.util.Iterator;
import net.labymod.api.client.component.Component;
import java.util.function.Supplier;
import java.util.function.Function;

public class VersionedKeybindComponent extends ho
{
    private static Function<String, Supplier<Component>> keyResolver;
    
    public VersionedKeybindComponent(final String keybind) {
        super(keybind);
    }
    
    public static Function<String, Supplier<Component>> getKeyResolver() {
        return VersionedKeybindComponent.keyResolver;
    }
    
    public static void setKeyResolver(final Function<String, Supplier<Component>> keyResolver) {
        VersionedKeybindComponent.keyResolver = keyResolver;
    }
    
    public ho h() {
        final VersionedKeybindComponent component = new VersionedKeybindComponent(this.g());
        component.a(this.b().m());
        for (final hh sibling : this.a()) {
            component.a(sibling.f());
        }
        return component;
    }
    
    public String toString() {
        return "KeybindComponent{keybind='" + this.g() + "', siblings=" + String.valueOf(this.a) + ", style=" + String.valueOf(this.b());
    }
    
    static {
        VersionedKeybindComponent.keyResolver = (Function<String, Supplier<Component>>)(keybind -> () -> Component.text(keybind));
    }
}
