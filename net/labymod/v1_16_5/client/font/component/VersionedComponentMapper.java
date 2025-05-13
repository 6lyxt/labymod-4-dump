// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.font.component;

import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.font.ComponentMapper;

@Singleton
@Implements(ComponentMapper.class)
public class VersionedComponentMapper implements ComponentMapper
{
    @Override
    public String getTranslationKeyOfComponent(final Object minecraftComponent) {
        if (minecraftComponent instanceof final of of) {
            return of.i();
        }
        return null;
    }
}
