// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.world.lighting;

import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.world.lighting.LightingLayerMapper;

@Singleton
@Implements(LightingLayerMapper.class)
public class VersionedLightingLayerMapper implements LightingLayerMapper
{
    @Override
    public LightType fromMinecraft(final Object value) {
        if (value instanceof final cqe lightLayer) {
            return switch (lightLayer) {
                default -> throw new MatchException(null, null);
                case a -> LightType.SKY;
                case b -> LightType.BLOCK;
            };
        }
        throw new IllegalArgumentException(value.getClass().getName() + " is not an instance of " + cqe.class.getName());
    }
    
    @Override
    public Object toMinecraft(final LightType value) {
        return switch (value) {
            default -> throw new MatchException(null, null);
            case SKY -> cqe.a;
            case BLOCK -> cqe.b;
        };
    }
}
