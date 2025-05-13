// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import net.labymod.api.client.render.shader.MojangShader;
import java.util.Iterator;
import net.labymod.api.client.gfx.shader.uniform.Uniform3F;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import net.labymod.api.util.ColorUtil;
import java.nio.FloatBuffer;
import net.labymod.api.util.Color;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.laby.ingame.DamageConfig;
import net.labymod.api.client.render.shader.MojangShaderRegistry;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class DamageOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "damage";
    private final MojangShaderRegistry mojangShaderRegistry;
    private final DamageConfig damageConfig;
    private int colorIndex;
    private float[] damageRGBA;
    
    public DamageOldAnimation() {
        super("damage");
        this.colorIndex = -1;
        this.damageRGBA = null;
        this.mojangShaderRegistry = Laby.references().mojangShaderRegistry();
        this.damageConfig = Laby.labyAPI().config().ingame().damage();
    }
    
    public boolean shouldCombineTextures() {
        return this.isEnabled();
    }
    
    public boolean isColored() {
        return this.damageConfig.damageColored().get();
    }
    
    public int getColor() {
        return this.damageConfig.damageColor().get().get();
    }
    
    public FloatBuffer updateBuffer(final FloatBuffer buffer, final float value) {
        if (this.colorIndex == 3) {
            this.colorIndex = -1;
            this.damageRGBA = null;
        }
        if (this.isColored()) {
            if (this.damageRGBA == null) {
                this.damageRGBA = ColorUtil.toRGBA(this.getColor());
            }
            ++this.colorIndex;
            return buffer.put(this.damageRGBA[this.colorIndex]);
        }
        return buffer.put(value);
    }
    
    public void updateCustomHitColorUniform(@NotNull final List<String> names, final Uniform3F armorCutoutNoCullUniform) {
        for (String name : names) {
            final MojangShader shader = this.mojangShaderRegistry.getShader("rendertype_entity_" + name);
            if (shader == null) {
                continue;
            }
            final Uniform3F customHitColorUniform = shader.getUniform("customHitColor");
            if (customHitColorUniform == null) {
                continue;
            }
            this.updateUniform(customHitColorUniform);
        }
        if (armorCutoutNoCullUniform != null) {
            this.updateUniform(armorCutoutNoCullUniform);
        }
    }
    
    private void updateUniform(final Uniform3F uniform) {
        if (this.isColored()) {
            final float[] rgba = ColorUtil.toRGBA(this.getColor());
            uniform.set(rgba[0], rgba[1], rgba[2]);
        }
        else {
            uniform.set(0.7f, 0.0f, 0.0f);
        }
    }
    
    @Override
    public boolean isEnabled() {
        return this.classicPvPConfig.oldDamageColor().get();
    }
}
