// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.render.item;

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
        if (gameType instanceof final dam animation) {
            return switch (animation) {
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
                case k -> RenderFirstPersonItemInHandEvent.AnimationType.BUNDLE;
            };
        }
        throw new IllegalStateException(gameType.getClass().getName() + " is not an instance of " + gib.a.class.getName());
    }
    
    @Override
    public Object toMinecraft(final RenderFirstPersonItemInHandEvent.AnimationType type) {
        return switch (type) {
            default -> throw new MatchException(null, null);
            case NONE -> dam.a;
            case EAT -> dam.b;
            case DRINK -> dam.c;
            case BLOCK -> dam.d;
            case BOW -> dam.e;
            case SPEAR -> dam.f;
            case CROSSBOW -> dam.g;
            case SPYGLASS -> dam.h;
            case TOOT_HORN -> dam.i;
            case BRUSH -> dam.j;
            case BUNDLE -> dam.k;
        };
    }
}
