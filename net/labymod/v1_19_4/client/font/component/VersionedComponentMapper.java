// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.font.component;

import net.labymod.v1_19_4.client.network.chat.VersionedBaseComponent;
import net.labymod.v1_19_4.client.network.chat.MutableComponentAccessor;
import net.labymod.api.client.component.Component;
import net.labymod.api.models.Implements;
import net.labymod.api.client.render.font.ComponentMapper;

@Implements(ComponentMapper.class)
public class VersionedComponentMapper implements ComponentMapper
{
    @Override
    public Component fromMinecraftComponent(final Object component) {
        return (component == null) ? null : ((MutableComponentAccessor)component).getLabyComponent();
    }
    
    @Override
    public Object toMinecraftComponent(final Component component) {
        return (component == null) ? null : ((VersionedBaseComponent)component).getHolder();
    }
    
    @Override
    public String getTranslationKeyOfComponent(final Object minecraftComponent) {
        if (minecraftComponent instanceof final tj component) {
            final tk contents = component.b();
            if (contents instanceof final ut ut) {
                return ut.a();
            }
        }
        return null;
    }
}
