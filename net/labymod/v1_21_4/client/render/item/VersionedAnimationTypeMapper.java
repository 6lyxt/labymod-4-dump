// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.render.item;

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
        if (gameType instanceof final cws animation) {
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
        throw new IllegalStateException(gameType.getClass().getName() + " is not an instance of " + gcp.a.class.getName());
    }
    
    @Override
    public Object toMinecraft(final RenderFirstPersonItemInHandEvent.AnimationType type) {
        return switch (type) {
            default -> throw new MatchException(null, null);
            case NONE -> cws.a;
            case EAT -> cws.b;
            case DRINK -> cws.c;
            case BLOCK -> cws.d;
            case BOW -> cws.e;
            case SPEAR -> cws.f;
            case CROSSBOW -> cws.g;
            case SPYGLASS -> cws.h;
            case TOOT_HORN -> cws.i;
            case BRUSH -> cws.j;
            case BUNDLE -> cws.k;
        };
    }
}
