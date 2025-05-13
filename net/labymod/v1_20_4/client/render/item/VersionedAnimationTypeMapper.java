// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.render.item;

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
        if (gameType instanceof final cos useAnim) {
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
                case i -> RenderFirstPersonItemInHandEvent.AnimationType.TOOT_HORN;
                case j -> RenderFirstPersonItemInHandEvent.AnimationType.BRUSH;
            };
        }
        throw new IllegalStateException(gameType.getClass().getName() + " is not an instance of " + cos.class.getName());
    }
    
    @Override
    public Object toMinecraft(final RenderFirstPersonItemInHandEvent.AnimationType type) {
        return switch (type) {
            case NONE -> cos.a;
            case EAT -> cos.b;
            case DRINK -> cos.c;
            case BLOCK -> cos.d;
            case BOW -> cos.e;
            case SPEAR -> cos.f;
            case CROSSBOW -> cos.g;
            case SPYGLASS -> cos.h;
            case TOOT_HORN -> cos.i;
            case BRUSH -> cos.j;
            default -> throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        };
    }
}
