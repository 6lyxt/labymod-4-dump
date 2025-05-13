// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_16_5.client.resources.texture.NativeGameImage;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.core.client.gfx.texture.overlay.DynamicOverlayTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.gfx.texture.overlay.GameOverlayTexture;

@Mixin({ ejw.class })
public class MixinOverlayTexture implements GameOverlayTexture
{
    @Shadow
    @Final
    private ejs b;
    private DynamicOverlayTexture labyMod$dynamicOverlayTexture;
    private det labyMod$overlayNativeImage;
    private GameImage labyMod$overlayImage;
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$getImage(final CallbackInfo ci) {
        this.labyMod$overlayNativeImage = this.b.e();
        this.labyMod$overlayImage = new NativeGameImage(this.labyMod$overlayNativeImage);
        this.labyMod$dynamicOverlayTexture = new DynamicOverlayTexture(this);
    }
    
    @Override
    public GameImage image() {
        return this.labyMod$overlayImage;
    }
    
    @Override
    public void upload() {
        final det image = this.labyMod$overlayNativeImage;
        RenderSystem.activeTexture(33985);
        this.b.d();
        RenderSystem.matrixMode(5890);
        RenderSystem.loadIdentity();
        RenderSystem.scalef(0.6666667f, 0.6666667f, 0.6666667f);
        RenderSystem.matrixMode(5888);
        this.b.d();
        image.a(0, 0, 0, 0, 0, image.a(), image.b(), false, true, false, false);
        RenderSystem.activeTexture(33984);
    }
    
    @Override
    public DynamicOverlayTexture dynamicTexture() {
        return this.labyMod$dynamicOverlayTexture;
    }
}
