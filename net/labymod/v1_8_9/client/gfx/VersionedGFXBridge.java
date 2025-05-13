// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gfx;

import net.labymod.api.client.gui.window.Window;
import org.lwjgl.input.Mouse;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.target.stencil.StencilOperation;
import org.lwjgl.opengl.GL11;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.resources.ResourceLocation;
import javax.inject.Inject;
import net.labymod.v1_8_9.client.gfx.pipeline.blaze3d.buffer.VersionedBlaze3DBufferSource;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.state.BooleanState;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.gfx_lwjgl3.client.gfx.LWJGL3GFXBridge;

@Singleton
@Implements(value = GFXBridge.class, key = "gfx_bridge_production")
public class VersionedGFXBridge extends LWJGL3GFXBridge
{
    private static final BooleanState SCISSOR_STATE;
    
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
        final bmj textureManager = ave.A().P();
        final jy minecraftLocation = location.getMinecraftLocation();
        bmk texture = textureManager.b(minecraftLocation);
        if (texture == null) {
            textureManager.a(minecraftLocation);
            texture = textureManager.b(minecraftLocation);
        }
        this.bindTexture(TextureTarget.TEXTURE_2D, texture.b());
        texture.b(blur, mipmap);
        this.onTextureBind(location);
    }
    
    @Override
    public void stencilFunc(final int func, final int ref, final int mask) {
        GL11.glStencilFunc(func, ref, mask);
    }
    
    @Override
    public void stencilMask(final int mask) {
        GL11.glStencilMask(mask);
    }
    
    @Override
    public void stencilOp(final StencilOperation sfail, final StencilOperation dpfail, final StencilOperation dppass) {
        GL11.glStencilOp(sfail.getId(), dpfail.getId(), dppass.getId());
    }
    
    @Override
    public void enableScissor() {
        VersionedGFXBridge.SCISSOR_STATE.enable();
    }
    
    @Override
    public void scissor(final int x, final int y, final int width, final int height) {
        GL11.glScissor(x, y, width, height);
    }
    
    @Override
    public void disableScissor() {
        VersionedGFXBridge.SCISSOR_STATE.disable();
    }
    
    @Override
    public boolean isScissorActive() {
        return VersionedGFXBridge.SCISSOR_STATE.isEnabled();
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
            y = window.getRawHeight() - y * scale;
        }
        Mouse.setCursorPosition((int)x, (int)y);
    }
    
    @Override
    public void linesSmooth() {
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
    }
    
    @Override
    public void polygonMode(final int face, final int mode) {
        GL11.glPolygonMode(face, mode);
    }
    
    static {
        SCISSOR_STATE = new BooleanState(3089);
    }
}
