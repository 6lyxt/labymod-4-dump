// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gui.background;

import org.lwjgl.opengl.GL11;
import net.labymod.api.util.math.MathHelper;
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
    private static final nf[] RESOURCES;
    private final bib minecraft;
    private final nf backgroundTexture;
    private float panoramaTimer;
    private float width;
    private float height;
    
    @Inject
    public VersionedPanoramaRenderer(final LabyAPI labyAPI) {
        super(labyAPI);
        this.minecraft = bib.z();
        this.backgroundTexture = this.minecraft.N().a("background", new cdg(256, 256));
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
    
    private void renderSkybox(final int mouseX, final int mouseY, final float partialTicks) {
        this.minecraft.b().e();
        bus.b(0, 0, 256, 256);
        this.drawPanorama(mouseX, mouseY, partialTicks);
        this.rotateAndBlurSkybox(partialTicks);
        this.rotateAndBlurSkybox(partialTicks);
        this.rotateAndBlurSkybox(partialTicks);
        this.rotateAndBlurSkybox(partialTicks);
        this.rotateAndBlurSkybox(partialTicks);
        this.rotateAndBlurSkybox(partialTicks);
        this.rotateAndBlurSkybox(partialTicks);
        this.minecraft.b().a(true);
        bus.b(0, 0, this.minecraft.d, this.minecraft.e);
        final float scale = (this.width > this.height) ? (120.0f / this.width) : (120.0f / this.height);
        final float height = this.height * scale / 256.0f;
        final float width = this.width * scale / 256.0f;
        final float width2 = this.width;
        final float height2 = this.height;
        final bve tessellator = bve.a();
        final buk buffer = tessellator.c();
        buffer.a(7, cdy.i);
        buffer.b(0.0, (double)height2, 0.0).a((double)(0.5f - height), (double)(0.5f + width)).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        buffer.b((double)width2, (double)height2, 0.0).a((double)(0.5f - height), (double)(0.5f - width)).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        buffer.b((double)width2, 0.0, 0.0).a((double)(0.5f + height), (double)(0.5f - width)).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        buffer.b(0.0, 0.0, 0.0).a((double)(0.5f + height), (double)(0.5f + width)).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        tessellator.b();
    }
    
    private void drawPanorama(final int mouseX, final int mouseY, final float partialTicks) {
        final bve tessellator = bve.a();
        final buk buffer = tessellator.c();
        bus.n(5889);
        bus.G();
        bus.F();
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        bus.n(5888);
        bus.G();
        bus.F();
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        bus.b(180.0f, 1.0f, 0.0f, 0.0f);
        bus.b(90.0f, 0.0f, 0.0f, 1.0f);
        bus.m();
        bus.d();
        bus.r();
        bus.a(false);
        bus.a(770, 771, 1, 0);
        for (int i = 8, j = 0; j < i * i; ++j) {
            bus.G();
            final float x = (j % i / (float)i - 0.5f) / 64.0f;
            final float y = (j / i / (float)i - 0.5f) / 64.0f;
            bus.c(x, y, 0.0f);
            bus.b(MathHelper.sin((this.panoramaTimer + partialTicks) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            bus.b(-(this.panoramaTimer + partialTicks) * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int side = 0; side < 6; ++side) {
                bus.G();
                if (side == 1) {
                    bus.b(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (side == 2) {
                    bus.b(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (side == 3) {
                    bus.b(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (side == 4) {
                    bus.b(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (side == 5) {
                    bus.b(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                this.minecraft.N().a(VersionedPanoramaRenderer.RESOURCES[side]);
                buffer.a(7, cdy.i);
                final int alpha = 255 / (j + 1);
                buffer.b(-1.0, -1.0, 1.0).a(0.0, 0.0).b(255, 255, 255, alpha).d();
                buffer.b(1.0, -1.0, 1.0).a(1.0, 0.0).b(255, 255, 255, alpha).d();
                buffer.b(1.0, 1.0, 1.0).a(1.0, 1.0).b(255, 255, 255, alpha).d();
                buffer.b(-1.0, 1.0, 1.0).a(0.0, 1.0).b(255, 255, 255, alpha).d();
                tessellator.b();
                bus.H();
            }
            bus.H();
            bus.a(true, true, true, false);
        }
        buffer.c(0.0, 0.0, 0.0);
        bus.a(true, true, true, true);
        bus.n(5889);
        bus.H();
        bus.n(5888);
        bus.H();
        bus.a(true);
        bus.q();
        bus.k();
    }
    
    private void rotateAndBlurSkybox(final float partialTicks) {
        this.minecraft.N().a(this.backgroundTexture);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        bus.m();
        bus.a(770, 771, 1, 0);
        bus.a(true, true, true, false);
        final bve tessellator = bve.a();
        final buk buffer = tessellator.c();
        buffer.a(7, cdy.i);
        bus.d();
        for (int i = 3, j = 0; j < i; ++j) {
            final float alpha = 1.0f / (j + 1);
            final float width = this.width;
            final float height = this.height;
            final float offset = (j - i / 2) / 256.0f;
            buffer.b((double)width, (double)height, 0.0).a((double)(0.0f + offset), 1.0).a(1.0f, 1.0f, 1.0f, alpha).d();
            buffer.b((double)width, 0.0, 0.0).a((double)(1.0f + offset), 1.0).a(1.0f, 1.0f, 1.0f, alpha).d();
            buffer.b(0.0, 0.0, 0.0).a((double)(1.0f + offset), 0.0).a(1.0f, 1.0f, 1.0f, alpha).d();
            buffer.b(0.0, (double)height, 0.0).a((double)(0.0f + offset), 0.0).a(1.0f, 1.0f, 1.0f, alpha).d();
        }
        tessellator.b();
        bus.e();
        bus.a(true, true, true, true);
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
        bus.z();
        bus.m();
        bus.d();
        bus.a(770, 771, 1, 0);
        bus.j(7425);
        final bve tessellator = bve.a();
        final buk buffer = tessellator.c();
        buffer.a(7, cdy.f);
        buffer.b((double)right, (double)top, 0.0).a(f2, f3, f4, f).d();
        buffer.b((double)left, (double)top, 0.0).a(f2, f3, f4, f).d();
        buffer.b((double)left, (double)bottom, 0.0).a(f6, f7, f8, f5).d();
        buffer.b((double)right, (double)bottom, 0.0).a(f6, f7, f8, f5).d();
        tessellator.b();
        bus.j(7424);
        bus.l();
        bus.e();
        bus.y();
    }
    
    static {
        RESOURCES = new nf[] { new nf("textures/gui/title/background/panorama_0.png"), new nf("textures/gui/title/background/panorama_1.png"), new nf("textures/gui/title/background/panorama_2.png"), new nf("textures/gui/title/background/panorama_3.png"), new nf("textures/gui/title/background/panorama_4.png"), new nf("textures/gui/title/background/panorama_5.png") };
    }
}
