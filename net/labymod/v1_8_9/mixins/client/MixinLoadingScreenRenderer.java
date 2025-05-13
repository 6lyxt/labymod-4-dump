// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client;

import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gfx.GFXBridge;
import org.lwjgl.opengl.Display;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ avi.class })
public abstract class MixinLoadingScreenRenderer
{
    @Shadow
    private ave b;
    @Shadow
    private boolean e;
    @Shadow
    private long d;
    @Shadow
    private bfw g;
    @Shadow
    private String c;
    @Shadow
    private String a;
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$setFramebufferColor(final ave lvt_1_1_, final CallbackInfo ci) {
        this.g.a(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    @Overwrite
    public void a(final int loadingProgress) {
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        final long systemTime = ave.J();
        if (systemTime - this.d >= 100L) {
            this.d = systemTime;
            final avr resolution = new avr(this.b);
            final int scaleFactor = resolution.e();
            final int scaledWidth = resolution.a();
            final int scaledHeight = resolution.b();
            if (bqs.i()) {
                this.g.f();
            }
            else {
                bfl.m(256);
            }
            this.g.a(false);
            bfl.n(5889);
            bfl.D();
            bfl.a(0.0, resolution.c(), resolution.d(), 0.0, 100.0, 300.0);
            bfl.n(5888);
            bfl.D();
            bfl.b(0.0f, 0.0f, -200.0f);
            if (!bqs.i()) {
                bfl.m(16640);
            }
            final bfx tessellator = bfx.a();
            final bfd renderer = tessellator.c();
            final Theme currentTheme = Laby.labyAPI().themeService().currentTheme();
            final BackgroundRenderer backgroundRenderer = currentTheme.getBackgroundRenderer();
            if (backgroundRenderer == null || !backgroundRenderer.renderBackground(VersionedStackProvider.DEFAULT_STACK, null)) {
                this.labyMod$renderBackground(renderer, scaledWidth, scaledHeight);
            }
            if (loadingProgress >= 0) {
                final byte right = 100;
                final byte bottom = 2;
                final int left = scaledWidth / 2 - right / 2;
                final int top = scaledHeight / 2 + 16;
                bfl.x();
                renderer.a(7, bms.f);
                renderer.b((double)left, (double)top, 0.0).b(128, 128, 128, 255).d();
                renderer.b((double)left, (double)(top + bottom), 0.0).b(128, 128, 128, 255).d();
                renderer.b((double)(left + right), (double)(top + bottom), 0.0).b(128, 128, 128, 255).d();
                renderer.b((double)(left + right), (double)top, 0.0).b(128, 128, 128, 255).d();
                renderer.b((double)left, (double)top, 0.0).b(128, 255, 128, 255).d();
                renderer.b((double)left, (double)(top + bottom), 0.0).b(128, 255, 128, 255).d();
                renderer.b((double)(left + loadingProgress), (double)(top + bottom), 0.0).b(128, 255, 128, 255).d();
                renderer.b((double)(left + loadingProgress), (double)top, 0.0).b(128, 255, 128, 255).d();
                tessellator.b();
                bfl.w();
            }
            bfl.l();
            bfl.a(770, 771, 1, 0);
            this.b.k.a(this.c, (float)((scaledWidth - this.b.k.a(this.c)) / 2), (float)(scaledHeight / 2 - 4 - 16), 16777215);
            this.b.k.a(this.a, (float)((scaledWidth - this.b.k.a(this.a)) / 2), (float)(scaledHeight / 2 - 4 + 8), 16777215);
            this.g.e();
            if (bqs.i()) {
                this.g.c(Display.getFramebufferWidth(), Display.getFramebufferHeight());
            }
            this.b.h();
            try {
                Thread.yield();
            }
            catch (final Exception ex) {}
        }
        gfx.restoreBlaze3DStates();
    }
    
    @Unique
    private void labyMod$renderBackground(final bfd bufferBuilder, final int scaledWidth, final int scaledHeight) {
        this.b.P().a(avp.b);
        final float tileSize = 32.0f;
        final int tileBrightness = 64;
        bufferBuilder.a(7, bms.i);
        final float u = scaledWidth / tileSize;
        final float v = scaledHeight / tileSize;
        bufferBuilder.b(0.0, (double)scaledHeight, 0.0).a(0.0, (double)v).b(tileBrightness, tileBrightness, tileBrightness, 255).d();
        bufferBuilder.b((double)scaledWidth, (double)scaledHeight, 0.0).a((double)u, (double)v).b(tileBrightness, tileBrightness, tileBrightness, 255).d();
        bufferBuilder.b((double)scaledWidth, 0.0, 0.0).a((double)u, 0.0).b(tileBrightness, tileBrightness, tileBrightness, 255).d();
        bufferBuilder.b(0.0, 0.0, 0.0).a(0.0, 0.0).b(tileBrightness, tileBrightness, tileBrightness, 255).d();
        bfx.a().b();
    }
}
