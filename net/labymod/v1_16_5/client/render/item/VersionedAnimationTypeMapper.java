// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.render.item;

import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;

@Singleton
@Implements(RenderFirstPersonItemInHandEvent.AnimationTypeMapper.class)
public class VersionedAnimationTypeMapper implements RenderFirstPersonItemInHandEvent.AnimationTypeMapper
{
    @Inject
    public VersionedAnimationTypeMapper() {
    }
    
    @Override
    public RenderFirstPersonItemInHandEvent.AnimationType fromMinecraft(final Object gameType) {
        if (gameType instanceof final bnn useAnim) {
            return switch (useAnim) {
                default -> throw new MatchException(null, null);
                case a -> RenderFirstPersonItemInHandEvent.AnimationType.NONE;
                case b -> RenderFirstPersonItemInHandEvent.AnimationType.EAT;
                case c -> RenderFirstPersonItemInHandEvent.AnimationType.DRINK;
                case d -> RenderFirstPersonItemInHandEvent.AnimationType.BLOCK;
                case e -> RenderFirstPersonItemInHandEvent.AnimationType.BOW;
                case f -> RenderFirstPersonItemInHandEvent.AnimationType.SPEAR;
                case g -> RenderFirstPersonItemInHandEvent.AnimationType.CROSSBOW;
            };
        }
        throw new IllegalStateException(gameType.getClass().getName() + " is not an instance of " + bnn.class.getName());
    }
    
    @Override
    public Object toMinecraft(final RenderFirstPersonItemInHandEvent.AnimationType type) {
        return switch (type) {
            case NONE -> bnn.a;
            case EAT -> bnn.b;
            case DRINK -> bnn.c;
            case BLOCK -> bnn.d;
            case BOW -> bnn.e;
            case SPEAR -> bnn.f;
            case CROSSBOW -> bnn.g;
            default -> throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        };
    }
}
