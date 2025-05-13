// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render;

import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.render.AtlasRenderer;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.Minecraft;
import net.labymod.api.Laby;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.client.render.RenderMode;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.ExperienceBarRenderer;

@Singleton
@Implements(ExperienceBarRenderer.class)
public class DefaultExperienceBarRenderer implements ExperienceBarRenderer
{
    private static final ResourceLocation EXPERIENCE_BAR_BACKGROUND_SPRITE;
    private static final ResourceLocation EXPERIENCE_BAR_PROGRESS_SPRITE;
    private static final float EXPERIENCE_BAR_WIDTH = 182.0f;
    private static final float EXPERIENCE_BAR_HEIGHT = 5.0f;
    private final LabyAPI labyAPI;
    private RenderMode renderMode;
    private float x;
    private float y;
    private int experienceNeededForNextLevel;
    private float progress;
    private int level;
    
    @Inject
    public DefaultExperienceBarRenderer(final LabyAPI labyAPI) {
        this.renderMode = RenderMode.REAL;
        this.experienceNeededForNextLevel = 155;
        this.progress = 0.55f;
        this.level = 13;
        this.labyAPI = labyAPI;
    }
    
    @Override
    public ExperienceBarRenderer mode(final RenderMode mode) {
        this.renderMode = mode;
        return this;
    }
    
    @Override
    public ExperienceBarRenderer pos(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    @Override
    public ExperienceBarRenderer experienceNeededForNextLevel(final int level) {
        this.experienceNeededForNextLevel = level;
        return this;
    }
    
    @Override
    public ExperienceBarRenderer experienceProgress(final float progress) {
        this.progress = progress;
        return this;
    }
    
    @Override
    public ExperienceBarRenderer experienceLevel(final int level) {
        this.level = level;
        return this;
    }
    
    @Override
    public void render(final Stack stack) {
        final Minecraft minecraft = this.labyAPI.minecraft();
        final ClientPlayer clientPlayer = minecraft.getClientPlayer();
        float experienceProgress = this.progress;
        int experienceLevel = this.level;
        int experienceNeededForNextLevel = this.experienceNeededForNextLevel;
        if (clientPlayer != null && this.renderMode == RenderMode.REAL) {
            experienceLevel = clientPlayer.getExperienceLevel();
            experienceProgress = clientPlayer.getExperienceProgress();
            experienceNeededForNextLevel = clientPlayer.getExperienceNeededForNextLevel();
        }
        if (experienceNeededForNextLevel > 0) {
            final float progress = experienceProgress * 183.0f;
            final ResourceLocation iconsTextureLocation = minecraft.textures().iconsTexture();
            final ResourceRenderContext resourceRenderContext = this.labyAPI.renderPipeline().renderContexts().resourceRenderContext();
            resourceRenderContext.begin(stack, iconsTextureLocation);
            final AtlasRenderer renderer = ResourceRenderContext.ATLAS_RENDERER;
            final TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(iconsTextureLocation);
            renderer.blitSprite(resourceRenderContext, atlas, DefaultExperienceBarRenderer.EXPERIENCE_BAR_BACKGROUND_SPRITE, (int)this.x, (int)this.y, 182, 5, -1);
            if (progress > 0.0f) {
                renderer.blitSprite(resourceRenderContext, atlas, DefaultExperienceBarRenderer.EXPERIENCE_BAR_PROGRESS_SPRITE, 182, 5, 0, 0, (int)this.x, (int)this.y, (int)progress, 5, -1);
            }
            resourceRenderContext.uploadToBuffer();
        }
        if (experienceLevel > 0) {
            final TextRenderer textRenderer = this.labyAPI.renderPipeline().textRenderer();
            final float textY = this.y - textRenderer.height() / 2.0f;
            final String levelText = String.valueOf(experienceLevel);
            textRenderer.beginBatch(stack);
            final float width = 91.0f - textRenderer.width(levelText) / 2.0f;
            this.renderText(stack, textRenderer, this.x + width + 1.0f, textY, levelText, -16777216);
            this.renderText(stack, textRenderer, this.x + width - 1.0f, textY, levelText, -16777216);
            this.renderText(stack, textRenderer, this.x + width, textY + 1.0f, levelText, -16777216);
            this.renderText(stack, textRenderer, this.x + width, textY - 1.0f, levelText, -16777216);
            stack.push();
            stack.translate(0.0f, 0.0f, 1.0f);
            this.renderText(stack, textRenderer, this.x + width, textY, levelText, -8323296);
            stack.pop();
            textRenderer.endBatch(stack);
        }
    }
    
    private void renderText(final Stack stack, final TextRenderer textRenderer, final float x, final float textY, final String levelText, final int color) {
        textRenderer.pos(x, textY).shadow(false).text(levelText).color(color).renderBatch(stack);
    }
    
    @Override
    public float getWidth() {
        return 182.0f;
    }
    
    @Override
    public float getHeight() {
        return 5.0f;
    }
    
    static {
        EXPERIENCE_BAR_BACKGROUND_SPRITE = ResourceLocation.create("minecraft", "hud/experience_bar_background");
        EXPERIENCE_BAR_PROGRESS_SPRITE = ResourceLocation.create("minecraft", "hud/experience_bar_progress");
    }
}
