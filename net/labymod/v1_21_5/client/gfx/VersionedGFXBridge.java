// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.gfx;

import com.mojang.blaze3d.opengl.GlStateManager;
import net.labymod.api.client.gui.window.Window;
import org.lwjgl.glfw.GLFW;
import net.labymod.api.Laby;
import com.mojang.blaze3d.textures.GpuTexture;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.resources.ResourceLocation;
import javax.inject.Inject;
import net.labymod.v1_21_5.client.gfx.buffer.VersionedBlaze3DBufferSource;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.gfx_lwjgl3.client.gfx.LWJGL3GFXBridge;

@Singleton
@Implements(value = GFXBridge.class, key = "gfx_bridge_production")
public class VersionedGFXBridge extends LWJGL3GFXBridge
{
    @Inject
    public VersionedGFXBridge(final Blaze3DGlStatePipeline blaze3DGlStatePipeline) {
        super(blaze3DGlStatePipeline);
        this.blaze3DBufferSource = new VersionedBlaze3DBufferSource();
    }
    
    @Override
    public void bindResourceLocation(final ResourceLocation location, final boolean blur, final boolean mipmap) {
        if (!location.exists()) {
            return;
        }
        final hks textureManager = fqq.Q().aa();
        final hkb texture = textureManager.b((alr)location.getMinecraftLocation());
        texture.a(blur, mipmap);
        this.bindTexture(TextureTarget.TEXTURE_2D, TextureId.of(MinecraftUtil.getGpuTextureId(texture.a()), texture.a()));
        this.onTextureBind(location);
        this.flushModeChanges(texture);
    }
    
    private void flushModeChanges(final hkb texture) {
        final GpuTexture gpuTexture = texture.a();
        if (gpuTexture instanceof final fjl glTexture) {
            glTexture.a();
            return;
        }
        throw new IllegalStateException("OpenGL implementation was expected, but got " + gpuTexture.getClass().getName());
    }
    
    @Override
    public void setCursorPosition(double x, double y) {
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        final float scale = window.getScale();
        if (x == -1.0) {
            x = window.getRawWidth() / 2.0;
        }
        else {
            x *= scale;
        }
        if (y == -1.0) {
            y = window.getRawHeight() / 2.0;
        }
        else {
            y *= scale;
        }
        GLFW.glfwSetCursorPos(fqq.Q().aO().h(), Math.max(Math.min(x, window.getRawWidth()), 0.0), Math.max(Math.min(y, window.getRawHeight()), 0.0));
    }
    
    @Override
    public void polygonMode(final int face, final int mode) {
        GlStateManager._polygonMode(face, mode);
    }
}
