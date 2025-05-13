// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect;

import org.jetbrains.annotations.Nullable;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.metadata.MinecraftItemGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.physic.CurrentTimeGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.physic.OrientationGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.layer.ExtrudeGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.layer.LayerGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.color.ColorGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.physic.HeadPhysicGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.physic.PhysicGeometryEffect;
import java.util.HashMap;
import net.labymod.api.client.render.model.ModelPart;
import java.util.function.BiFunction;
import java.util.Map;

public class GeometryEffectRegistry
{
    private final Map<String, BiFunction<String, ModelPart, GeometryEffect>> effects;
    
    public GeometryEffectRegistry() {
        this.effects = new HashMap<String, BiFunction<String, ModelPart, GeometryEffect>>();
        this.register();
    }
    
    private void register() {
        this.effects.put("physics", (BiFunction<String, ModelPart, GeometryEffect>)PhysicGeometryEffect::new);
        this.effects.put("headgravity", (BiFunction<String, ModelPart, GeometryEffect>)HeadPhysicGeometryEffect::new);
        this.effects.put("color", (BiFunction<String, ModelPart, GeometryEffect>)ColorGeometryEffect::new);
        this.effects.put("layer", (BiFunction<String, ModelPart, GeometryEffect>)LayerGeometryEffect::new);
        this.effects.put("extrude", (BiFunction<String, ModelPart, GeometryEffect>)ExtrudeGeometryEffect::new);
        this.effects.put("orientation", (BiFunction<String, ModelPart, GeometryEffect>)OrientationGeometryEffect::new);
        this.effects.put("currenttime", (BiFunction<String, ModelPart, GeometryEffect>)CurrentTimeGeometryEffect::new);
        this.effects.put("item", (BiFunction<String, ModelPart, GeometryEffect>)MinecraftItemGeometryEffect::new);
    }
    
    @Nullable
    public <T extends GeometryEffect> T create(final String effectArgument, final ModelPart model) {
        if (!effectArgument.contains("_")) {
            return null;
        }
        final String effectName = effectArgument.substring(0, effectArgument.indexOf("_"));
        final BiFunction<String, ModelPart, GeometryEffect> constructor = this.effects.get(effectName);
        if (constructor == null) {
            return null;
        }
        final GeometryEffect effect = constructor.apply(effectArgument, model);
        if (effect == null) {
            return null;
        }
        return (T)effect.get();
    }
}
