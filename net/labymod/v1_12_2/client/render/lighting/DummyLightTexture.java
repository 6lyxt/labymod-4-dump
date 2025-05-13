// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.render.lighting;

import net.labymod.api.client.gfx.shader.ShaderTextures;
import org.lwjgl.opengl.GL11;
import java.util.Arrays;

public class DummyLightTexture
{
    private static DummyLightTexture instance;
    private static final int LIGHTMAP_SIZE = 16;
    private final bib minecraft;
    private final cdg lightmapTexture;
    private final nf lightmapTextureLocation;
    
    private DummyLightTexture(final bib minecraft) {
        this.minecraft = minecraft;
        this.lightmapTexture = new cdg(16, 16);
        this.lightmapTextureLocation = minecraft.N().a("lightMap", this.lightmapTexture);
        final int[] data = this.lightmapTexture.e();
        Arrays.fill(data, -1);
        this.lightmapTexture.d();
    }
    
    public static DummyLightTexture getInstance() {
        if (DummyLightTexture.instance == null) {
            DummyLightTexture.instance = new DummyLightTexture(bib.z());
        }
        return DummyLightTexture.instance;
    }
    
    public void apply() {
        bus.g(cii.r);
        bus.n(5890);
        bus.F();
        final float scale = 0.00390625f;
        bus.b(scale, scale, scale);
        bus.c(8.0f, 8.0f, 8.0f);
        bus.n(5888);
        this.minecraft.N().a(this.lightmapTextureLocation);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 10496);
        GL11.glTexParameteri(3553, 10243, 10496);
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        bus.y();
        bus.g(cii.q);
        ShaderTextures.setShaderTexture(1, this.lightmapTexture.b());
    }
    
    public void clear() {
        bus.g(cii.r);
        bus.z();
        bus.g(cii.q);
        ShaderTextures.setShaderTexture(1, 0);
    }
}
