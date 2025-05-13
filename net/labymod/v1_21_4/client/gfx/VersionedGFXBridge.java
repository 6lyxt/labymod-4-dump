// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.gfx;

import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.api.client.gui.window.Window;
import org.lwjgl.glfw.GLFW;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.resources.ResourceLocation;
import javax.inject.Inject;
import net.labymod.v1_21_4.client.gfx.buffer.VersionedBlaze3DBufferSource;
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
        final hev textureManager = flk.Q().aa();
        final hee texture = textureManager.b((akv)location.getMinecraftLocation());
        texture.a(blur, mipmap);
        this.bindTexture(TextureTarget.TEXTURE_2D, texture.a());
        this.onTextureBind(location);
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
        GLFW.glfwSetCursorPos(flk.Q().aO().h(), Math.max(Math.min(x, window.getRawWidth()), 0.0), Math.max(Math.min(y, window.getRawHeight()), 0.0));
    }
    
    @Override
    public void polygonMode(final int face, final int mode) {
        GlStateManager._polygonMode(face, mode);
    }
}
