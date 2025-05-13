// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gui.background;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.LabyAPI;
import net.labymod.core.client.gui.background.panorama.PanoramaRenderer;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.gui.background.panorama.AbstractPanoramaRenderer;

@Singleton
@Implements(PanoramaRenderer.class)
public class VersionedPanoramaRenderer extends AbstractPanoramaRenderer
{
    private static final jy[] RESOURCES;
    private final ave minecraft;
    private final jy backgroundTexture;
    private float panoramaTimer;
    private float width;
    private float height;
    
    @Inject
    public VersionedPanoramaRenderer(final LabyAPI labyAPI) {
        super(labyAPI);
        this.minecraft = ave.A();
        this.backgroundTexture = this.minecraft.P().a("background", new blz(256, 256));
    }
    
    @Override
    public void render(final Stack stack, final float left, final float top, final float right, final float bottom, final float tickDelta) {
        this.width = right - left;
        this.height = bottom - top;
        this.renderSkybox(0, 0, tickDelta);
        this.drawGradientRect(0.0f, 0.0f, this.width, this.height, -2130706433, 16777215);
        this.drawGradientRect(0.0f, 0.0f, this.width, this.height, 0, Integer.MIN_VALUE);
        this.panoramaTimer += tickDelta;
    }
    
    private void renderSkybox(final int mouseX, final int mouseY, final float tickDelta) {
        this.minecraft.b().e();
        bfl.b(0, 0, 256, 256);
        this.drawPanorama(mouseX, mouseY, tickDelta);
        this.rotateAndBlurSkybox(tickDelta);
        this.rotateAndBlurSkybox(tickDelta);
        this.rotateAndBlurSkybox(tickDelta);
        this.rotateAndBlurSkybox(tickDelta);
        this.rotateAndBlurSkybox(tickDelta);
        this.rotateAndBlurSkybox(tickDelta);
        this.rotateAndBlurSkybox(tickDelta);
        this.minecraft.b().a(true);
        bfl.b(0, 0, this.minecraft.d, this.minecraft.e);
        final float scale = (this.width > this.height) ? (120.0f / this.width) : (120.0f / this.height);
        final float height = this.height * scale / 256.0f;
        final float width = this.width * scale / 256.0f;
        final float width2 = this.width;
        final float height2 = this.height;
        final bfx tessellator = bfx.a();
        final bfd worldrenderer = tessellator.c();
        worldrenderer.a(7, bms.i);
        worldrenderer.b(0.0, (double)height2, 0.0).a((double)(0.5f - height), (double)(0.5f + width)).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        worldrenderer.b((double)width2, (double)height2, 0.0).a((double)(0.5f - height), (double)(0.5f - width)).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        worldrenderer.b((double)width2, 0.0, 0.0).a((double)(0.5f + height), (double)(0.5f - width)).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        worldrenderer.b(0.0, 0.0, 0.0).a((double)(0.5f + height), (double)(0.5f + width)).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        tessellator.b();
    }
    
    private void drawPanorama(final int mouseX, final int mouseY, final float partialTicks) {
        final bfx tessellator = bfx.a();
        final bfd renderer = tessellator.c();
        bfl.n(5889);
        bfl.E();
        bfl.D();
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        bfl.n(5888);
        bfl.E();
        bfl.D();
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        bfl.b(180.0f, 1.0f, 0.0f, 0.0f);
        bfl.b(90.0f, 0.0f, 0.0f, 1.0f);
        bfl.l();
        bfl.c();
        bfl.p();
        bfl.a(false);
        bfl.a(770, 771, 1, 0);
        for (int i = 8, j = 0; j < i * i; ++j) {
            bfl.E();
            final float x = (j % i / (float)i - 0.5f) / 64.0f;
            final float y = (j / i / (float)i - 0.5f) / 64.0f;
            bfl.b(x, y, 0.0f);
            bfl.b(ns.a((this.panoramaTimer + partialTicks) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            bfl.b(-(this.panoramaTimer + partialTicks) * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int side = 0; side < 6; ++side) {
                bfl.E();
                if (side == 1) {
                    bfl.b(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (side == 2) {
                    bfl.b(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (side == 3) {
                    bfl.b(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (side == 4) {
                    bfl.b(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (side == 5) {
                    bfl.b(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                this.minecraft.P().a(VersionedPanoramaRenderer.RESOURCES[side]);
                renderer.a(7, bms.i);
                final int alpha = 255 / (j + 1);
                renderer.b(-1.0, -1.0, 1.0).a(0.0, 0.0).b(255, 255, 255, alpha).d();
                renderer.b(1.0, -1.0, 1.0).a(1.0, 0.0).b(255, 255, 255, alpha).d();
                renderer.b(1.0, 1.0, 1.0).a(1.0, 1.0).b(255, 255, 255, alpha).d();
                renderer.b(-1.0, 1.0, 1.0).a(0.0, 1.0).b(255, 255, 255, alpha).d();
                tessellator.b();
                bfl.F();
            }
            bfl.F();
            bfl.a(true, true, true, false);
        }
        renderer.c(0.0, 0.0, 0.0);
        bfl.a(true, true, true, true);
        bfl.n(5889);
        bfl.F();
        bfl.n(5888);
        bfl.F();
        bfl.a(true);
        bfl.o();
        bfl.j();
    }
    
    private void rotateAndBlurSkybox(final float tickDelta) {
        this.minecraft.P().a(this.backgroundTexture);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        bfl.l();
        bfl.a(770, 771, 1, 0);
        bfl.a(true, true, true, false);
        final bfx tessellator = bfx.a();
        final bfd renderer = tessellator.c();
        renderer.a(7, bms.i);
        bfl.c();
        for (int i = 3, j = 0; j < i; ++j) {
            final float alpha = 1.0f / (j + 1);
            final float width = this.width;
            final float height = this.height;
            final float offset = (j - i / 2) / 256.0f;
            renderer.b((double)width, (double)height, 0.0).a((double)(0.0f + offset), 1.0).a(1.0f, 1.0f, 1.0f, alpha).d();
            renderer.b((double)width, 0.0, 0.0).a((double)(1.0f + offset), 1.0).a(1.0f, 1.0f, 1.0f, alpha).d();
            renderer.b(0.0, 0.0, 0.0).a((double)(1.0f + offset), 0.0).a(1.0f, 1.0f, 1.0f, alpha).d();
            renderer.b(0.0, (double)height, 0.0).a((double)(0.0f + offset), 0.0).a(1.0f, 1.0f, 1.0f, alpha).d();
        }
        tessellator.b();
        bfl.d();
        bfl.a(true, true, true, true);
    }
    
    protected void drawGradientRect(final float left, final float top, final float right, final float bottom, final int startColor, final int endColor) {
        final float f = (startColor >> 24 & 0xFF) / 255.0f;
        final float f2 = (startColor >> 16 & 0xFF) / 255.0f;
        final float f3 = (startColor >> 8 & 0xFF) / 255.0f;
        final float f4 = (startColor & 0xFF) / 255.0f;
        final float f5 = (endColor >> 24 & 0xFF) / 255.0f;
        final float f6 = (endColor >> 16 & 0xFF) / 255.0f;
        final float f7 = (endColor >> 8 & 0xFF) / 255.0f;
        final float f8 = (endColor & 0xFF) / 255.0f;
        bfl.x();
        bfl.l();
        bfl.c();
        bfl.a(770, 771, 1, 0);
        bfl.j(7425);
        final bfx tessellator = bfx.a();
        final bfd worldrenderer = tessellator.c();
        worldrenderer.a(7, bms.f);
        worldrenderer.b((double)right, (double)top, 0.0).a(f2, f3, f4, f).d();
        worldrenderer.b((double)left, (double)top, 0.0).a(f2, f3, f4, f).d();
        worldrenderer.b((double)left, (double)bottom, 0.0).a(f6, f7, f8, f5).d();
        worldrenderer.b((double)right, (double)bottom, 0.0).a(f6, f7, f8, f5).d();
        tessellator.b();
        bfl.j(7424);
        bfl.k();
        bfl.d();
        bfl.w();
    }
    
    static {
        RESOURCES = new jy[] { new jy("textures/gui/title/background/panorama_0.png"), new jy("textures/gui/title/background/panorama_1.png"), new jy("textures/gui/title/background/panorama_2.png"), new jy("textures/gui/title/background/panorama_3.png"), new jy("textures/gui/title/background/panorama_4.png"), new jy("textures/gui/title/background/panorama_5.png") };
    }
}
