// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.render.lighting;

import net.labymod.api.client.gfx.shader.ShaderTextures;
import org.lwjgl.opengl.GL11;
import java.util.Arrays;

public class DummyLightTexture
{
    private static DummyLightTexture instance;
    private static final int LIGHTMAP_SIZE = 16;
    private final ave minecraft;
    private final blz lightmapTexture;
    private final jy lightmapTextureLocation;
    
    private DummyLightTexture(final ave minecraft) {
        this.minecraft = minecraft;
        this.lightmapTexture = new blz(16, 16);
        this.lightmapTextureLocation = minecraft.P().a("lightMap", this.lightmapTexture);
        final int[] data = this.lightmapTexture.e();
        Arrays.fill(data, -1);
        this.lightmapTexture.d();
    }
    
    public static DummyLightTexture getInstance() {
        if (DummyLightTexture.instance == null) {
            DummyLightTexture.instance = new DummyLightTexture(ave.A());
        }
        return DummyLightTexture.instance;
    }
    
    public void apply() {
        bfl.g(bqs.r);
        bfl.n(5890);
        bfl.D();
        final float scale = 0.00390625f;
        bfl.a(scale, scale, scale);
        bfl.b(8.0f, 8.0f, 8.0f);
        bfl.n(5888);
        this.minecraft.P().a(this.lightmapTextureLocation);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 10496);
        GL11.glTexParameteri(3553, 10243, 10496);
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        bfl.w();
        bfl.g(bqs.q);
        ShaderTextures.setShaderTexture(1, this.lightmapTexture.b());
    }
    
    public void clear() {
        bfl.g(bqs.r);
        bfl.x();
        bfl.g(bqs.q);
        ShaderTextures.setShaderTexture(1, 0);
    }
}
