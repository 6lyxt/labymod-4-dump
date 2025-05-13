// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.render.item;

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
        if (gameType instanceof final cfk useAnim) {
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
            };
        }
        throw new IllegalStateException(gameType.getClass().getName() + " is not an instance of " + cfk.class.getName());
    }
    
    @Override
    public Object toMinecraft(final RenderFirstPersonItemInHandEvent.AnimationType type) {
        return switch (type) {
            case NONE -> cfk.a;
            case EAT -> cfk.b;
            case DRINK -> cfk.c;
            case BLOCK -> cfk.d;
            case BOW -> cfk.e;
            case SPEAR -> cfk.f;
            case CROSSBOW -> cfk.g;
            case SPYGLASS -> cfk.h;
            case TOOT_HORN -> cfk.i;
            default -> throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        };
    }
}
