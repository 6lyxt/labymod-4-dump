// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.renderer;

import net.labymod.api.client.render.font.text.TextRenderer;
import java.util.Locale;
import net.labymod.api.util.math.MathHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.v1_18_2.client.player.VersionedNetworkPlayerInfo;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import javax.inject.Inject;
import net.labymod.api.event.EventBus;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.client.render.PlayerHeartRenderer;

@Implements(PlayerHeartRenderer.class)
public class VersionedPlayerHeartRenderer implements PlayerHeartRenderer
{
    private final ResourceLocation guiIconsLocation;
    private final ResourceRenderer resourceRender;
    private long visibilityId;
    private int tickCount;
    private final LabyAPI labyAPI;
    
    @Inject
    public VersionedPlayerHeartRenderer(final LabyAPI labyAPI, final EventBus eventBus, final ResourceRenderer resourceRenderer) {
        this.labyAPI = labyAPI;
        eventBus.registerListener(this);
        this.resourceRender = resourceRenderer;
        this.guiIconsLocation = (ResourceLocation)dzr.h;
    }
    
    @Subscribe
    public void tick(final GameTickEvent event) {
        ++this.tickCount;
    }
    
    @Override
    public void renderHearts(final Stack stack, final float rawX, final float rawY, final int heartSize, final NetworkPlayerInfo info) {
        final emw player = ((VersionedNetworkPlayerInfo)info).getWrapped();
        final int health = info.getHealth();
        RenderSystem.setShaderTexture(0, dzr.h);
        final long millis = ad.b();
        if (this.visibilityId == player.q()) {
            if (health < player.m()) {
                player.a(millis);
                player.b((long)(this.tickCount + 20));
            }
            else if (health > player.m()) {
                player.a(millis);
                player.b((long)(this.tickCount + 10));
            }
        }
        if (millis - player.o() > 1000L) {
            player.b(health);
            player.c(health);
            player.a(millis);
        }
        player.c(this.visibilityId);
        player.b(health);
        final int availableHearts = MathHelper.ceil(Math.max(health, player.n()) / 2.0f);
        final int heartAmount = Math.max(MathHelper.ceil((float)(health / 2)), Math.max(MathHelper.ceil((float)(player.n() / 2)), 10));
        final boolean blinkLight = player.p() > this.tickCount && (player.p() - this.tickCount) / 3L % 2L == 1L;
        final int x = (int)rawX;
        final int y = (int)rawY;
        final int rightX = (int)(rawX + heartAmount * heartSize);
        if (availableHearts > 0) {
            final int heartSpace = MathHelper.floor(Math.min((rightX - x - 4) / (float)heartAmount, (float)heartSize));
            if (heartSpace > 3) {
                for (int heart = availableHearts; heart < heartAmount; ++heart) {
                    this.resourceRender.texture(this.guiIconsLocation).pos((float)(x + heart * heartSpace), (float)y).sprite(blinkLight ? 25.0f : 16.0f, 0.0f, (float)heartSize, (float)heartSize).render(stack);
                }
                for (int heart = 0; heart < availableHearts; ++heart) {
                    this.resourceRender.texture(this.guiIconsLocation).pos((float)(x + heart * heartSpace), (float)y).sprite(blinkLight ? 25.0f : 16.0f, 0.0f, (float)heartSize, (float)heartSize).render(stack);
                    if (blinkLight) {
                        if (heart * 2 + 1 < player.n()) {
                            this.resourceRender.texture(this.guiIconsLocation).pos((float)(x + heart * heartSpace), (float)y).sprite(70.0f, 0.0f, (float)heartSize, (float)heartSize).render(stack);
                        }
                        if (heart * 2 + 1 == player.n()) {
                            this.resourceRender.texture(this.guiIconsLocation).pos((float)(x + heart * heartSpace), (float)y).sprite(70.0f, 0.0f, (float)heartSize, (float)heartSize).render(stack);
                            this.resourceRender.texture(this.guiIconsLocation).pos((float)(x + heart * heartSpace), (float)y).sprite(79.0f, 0.0f, (float)heartSize, (float)heartSize).render(stack);
                        }
                    }
                    if (heart * 2 + 1 < health) {
                        this.resourceRender.texture(this.guiIconsLocation).pos((float)(x + heart * heartSpace), (float)y).sprite((heart >= 10) ? 160.0f : 52.0f, 0.0f, (float)heartSize, (float)heartSize).render(stack);
                    }
                    if (heart * 2 + 1 == health) {
                        this.resourceRender.texture(this.guiIconsLocation).pos((float)(x + heart * heartSpace), (float)y).sprite((heart >= 10) ? 169.0f : 61.0f, 0.0f, (float)heartSize, (float)heartSize).render(stack);
                    }
                }
            }
            else {
                final float colorBase = MathHelper.clamp(health / 20.0f, 0.0f, 1.0f);
                final int color = (int)((1.0f - colorBase) * 255.0f) << 16 | (int)(colorBase * 255.0f) << 8;
                String text = String.format(Locale.ROOT, "%.1f", health / 2.0f);
                final TextRenderer textBuilder = this.labyAPI.renderPipeline().textRenderer();
                if (rightX - textBuilder.width(text + "hp") >= x) {
                    text += "hp";
                }
                textBuilder.text(text).pos((rightX + x) / 2.0f - textBuilder.width(text) / 2.0f, (float)y).color(color).render(stack);
            }
        }
    }
}
