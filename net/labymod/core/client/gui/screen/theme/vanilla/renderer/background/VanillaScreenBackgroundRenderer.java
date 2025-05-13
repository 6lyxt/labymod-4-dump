// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer.background;

import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.core.client.gui.screen.activity.activities.menu.MainMenuActivity;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.core.client.gui.screen.theme.vanilla.VanillaThemeConfig;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.api.client.Minecraft;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;

public class VanillaScreenBackgroundRenderer implements BackgroundRenderer
{
    protected final LabyAPI api;
    protected final Minecraft minecraft;
    protected final DynamicBackgroundController dynamicBackground;
    protected boolean canTick;
    
    public VanillaScreenBackgroundRenderer() {
        this.canTick = false;
        this.api = Laby.labyAPI();
        this.minecraft = this.api.minecraft();
        this.dynamicBackground = LabyMod.references().dynamicBackgroundController();
    }
    
    @Subscribe
    public void onTick(final GameTickEvent event) {
        if (!this.canTick) {
            return;
        }
        this.canTick = false;
        this.dynamicBackground.tick();
    }
    
    @Override
    public boolean renderBackground(final Stack stack, final Object screenInstance) {
        final boolean isIngame = this.minecraft.isIngame();
        final Window window = this.minecraft.minecraftWindow();
        final int scaledWidth = window.getScaledWidth();
        final int scaledHeight = window.getScaledHeight();
        if (isIngame) {
            return this.api.config().appearance().hideMenuBackground().get() || this.renderIngameBackground(stack, 0.0f, 0.0f, (float)scaledWidth, (float)scaledHeight, screenInstance);
        }
        if (DynamicBackgroundController.isEnabled() && this.hasScreenDynamicBackground(screenInstance)) {
            return this.renderDynamicBackground(stack, 0.0f, 0.0f, window.getRawWidth() / window.getScale(), window.getRawHeight() / window.getScale(), screenInstance);
        }
        return this.renderMenuBackground(stack, 0.0f, 0.0f, (float)scaledWidth, (float)scaledHeight, screenInstance);
    }
    
    public boolean hasScreenDynamicBackground(final Object screenInstance) {
        return this.isFreshUIEnabled() && !this.isMainMenuActivity(screenInstance);
    }
    
    public boolean renderDynamicBackground(final Stack stack, final float left, final float top, final float right, final float bottom, final Object screenInstance) {
        stack.push();
        stack.translate(left, top, 0.0f);
        final float tickDelta = this.minecraft.getTickDelta();
        this.dynamicBackground.renderTick(stack, left, top, right, bottom, tickDelta);
        this.dynamicBackground.render(stack, left, top, right, bottom, tickDelta);
        stack.pop();
        return this.canTick = true;
    }
    
    public boolean renderIngameBackground(final Stack stack, final float left, final float top, final float right, final float bottom, final Object screenInstance) {
        if (this.isFreshUIEnabled()) {
            return false;
        }
        this.api.renderPipeline().rectangleRenderer().setupGradient(stack, renderer -> renderer.renderBackgroundGradient(left, top, right, bottom));
        return true;
    }
    
    public boolean renderMenuBackground(final Stack stack, final float left, final float top, final float right, final float bottom, final Object screenInstance) {
        if (this.isFreshUIEnabled()) {
            return false;
        }
        final ResourceLocation backgroundTexture = this.minecraft.textures().screenMenuBackgroundTexture();
        final float brightness = PlatformEnvironment.isFreshUI() ? 255.0f : 64.0f;
        this.api.renderPipeline().resourceRenderer().texture(backgroundTexture).pos(left, top, right, bottom).sprite(0.0f, 0.0f, right * 8.0f, bottom * 8.0f).color(brightness / 255.0f, brightness / 255.0f, brightness / 255.0f, 1.0f).render(stack);
        return true;
    }
    
    public boolean isFreshUIEnabled() {
        final VanillaThemeConfig config = this.api.themeService().getThemeConfig(VanillaThemeConfig.class);
        return PlatformEnvironment.isFreshUI() && config != null && config.freshUI().get();
    }
    
    public boolean isMainMenuActivity(final Object screenInstance) {
        if (screenInstance instanceof final LabyScreenAccessor labyScreen) {
            return labyScreen.screen() instanceof MainMenuActivity;
        }
        return false;
    }
}
