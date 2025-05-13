// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.render.item;

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
        if (gameType instanceof final chn useAnim) {
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
        throw new IllegalStateException(gameType.getClass().getName() + " is not an instance of " + chn.class.getName());
    }
    
    @Override
    public Object toMinecraft(final RenderFirstPersonItemInHandEvent.AnimationType type) {
        return switch (type) {
            case NONE -> chn.a;
            case EAT -> chn.b;
            case DRINK -> chn.c;
            case BLOCK -> chn.d;
            case BOW -> chn.e;
            case SPEAR -> chn.f;
            case CROSSBOW -> chn.g;
            case SPYGLASS -> chn.h;
            case TOOT_HORN -> chn.i;
            case BRUSH -> chn.j;
            default -> throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        };
    }
}
