// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.render.item;

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
        if (gameType instanceof final bsf useAnim) {
            return switch (useAnim) {
                default -> throw new MatchException(null, null);
                case a -> RenderFirstPersonItemInHandEvent.AnimationType.NONE;
                case b -> RenderFirstPersonItemInHandEvent.AnimationType.EAT;
                case c -> RenderFirstPersonItemInHandEvent.AnimationType.DRINK;
                case d -> RenderFirstPersonItemInHandEvent.AnimationType.BLOCK;
                case e -> RenderFirstPersonItemInHandEvent.AnimationType.BOW;
                case f -> RenderFirstPersonItemInHandEvent.AnimationType.SPEAR;
                case g -> RenderFirstPersonItemInHandEvent.AnimationType.CROSSBOW;
                case h -> RenderFirstPersonItemInHandEvent.AnimationType.SPYGLASS;
            };
        }
        throw new IllegalStateException(gameType.getClass().getName() + " is not an instance of " + bsf.class.getName());
    }
    
    @Override
    public Object toMinecraft(final RenderFirstPersonItemInHandEvent.AnimationType type) {
        return switch (type) {
            case NONE -> bsf.a;
            case EAT -> bsf.b;
            case DRINK -> bsf.c;
            case BLOCK -> bsf.d;
            case BOW -> bsf.e;
            case SPEAR -> bsf.f;
            case CROSSBOW -> bsf.g;
            case SPYGLASS -> bsf.h;
            default -> throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        };
    }
}
