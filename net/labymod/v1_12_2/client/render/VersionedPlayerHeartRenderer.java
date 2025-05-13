// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.render;

import net.labymod.v1_12_2.client.player.VersionedNetworkPlayerInfo;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.event.EventBus;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.PlayerHeartRenderer;

@Singleton
@Implements(PlayerHeartRenderer.class)
public class VersionedPlayerHeartRenderer extends bir implements PlayerHeartRenderer
{
    private final long lastTimeOpened;
    
    @Inject
    public VersionedPlayerHeartRenderer(final EventBus eventBus) {
        this.lastTimeOpened = TimeUtil.getMillis();
        eventBus.registerListener(this);
    }
    
    public void renderHearts(final Stack stack, final float x, final float y, final int heartSize, final NetworkPlayerInfo info) {
        final biq guiIngame = bib.z().q;
        final bsc mcInfo = ((VersionedNetworkPlayerInfo)info).getWrapped();
        final int health = info.getHealth();
        bib.z().N().a(VersionedPlayerHeartRenderer.d);
        if (this.lastTimeOpened == mcInfo.q()) {
            if (health < mcInfo.m()) {
                mcInfo.a(bib.I());
                mcInfo.b((long)(guiIngame.e() + 20));
            }
            else if (health > mcInfo.m()) {
                mcInfo.a(bib.I());
                mcInfo.b((long)(guiIngame.e() + 10));
            }
        }
        if (bib.I() - mcInfo.o() > 1000L || this.lastTimeOpened != mcInfo.q()) {
            mcInfo.b(health);
            mcInfo.c(health);
            mcInfo.a(bib.I());
        }
        mcInfo.c(this.lastTimeOpened);
        mcInfo.a((long)health);
        final int availableHearts = rk.f(Math.max(health, mcInfo.n()) / 2.0f);
        final int heartAmount = Math.max(rk.f((float)(health / 2)), Math.max(rk.f((float)(mcInfo.n() / 2)), 10));
        final boolean blinkLight = mcInfo.p() > guiIngame.e() && (mcInfo.p() - guiIngame.e()) / 3L % 2L == 1L;
        if (availableHearts > 0) {
            final int rightX = (int)(x + heartAmount * heartSize);
            final float heartSpace = Math.min((rightX - x - 4.0f) / heartAmount, 9.0f);
            if (heartSpace > 3.0f) {
                for (int i = availableHearts; i < heartAmount; ++i) {
                    this.a(x + i * heartSpace, y, blinkLight ? 25 : 16, 0, 9, 9);
                }
                for (int i = 0; i < availableHearts; ++i) {
                    this.a(x + i * heartSpace, y, blinkLight ? 25 : 16, 0, 9, 9);
                    if (blinkLight) {
                        if (i * 2 + 1 < mcInfo.n()) {
                            this.a(x + i * heartSpace, y, 70, 0, 9, 9);
                        }
                        if (i * 2 + 1 == mcInfo.n()) {
                            this.a(x + i * heartSpace, y, 79, 0, 9, 9);
                        }
                    }
                    if (i * 2 + 1 < health) {
                        this.a(x + i * heartSpace, y, (i >= 10) ? 160 : 52, 0, 9, 9);
                    }
                    if (i * 2 + 1 == health) {
                        this.a(x + i * heartSpace, y, (i >= 10) ? 169 : 61, 0, 9, 9);
                    }
                }
            }
            else {
                final float healthPercentage = rk.a(health / 20.0f, 0.0f, 1.0f);
                final int color = (int)((1.0f - healthPercentage) * 255.0f) << 16 | (int)(healthPercentage * 255.0f) << 8;
                String text = "" + health / 2.0f;
                final bip fontRenderer = bib.z().k;
                if (rightX - fontRenderer.a(text + "hp") >= x) {
                    text += "hp";
                }
                fontRenderer.a(text, (rightX + x) / 2.0f - fontRenderer.a(text) / 2.0f, y, color);
            }
        }
    }
}
