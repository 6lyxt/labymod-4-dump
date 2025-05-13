// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.render;

import net.labymod.v1_8_9.client.player.VersionedNetworkPlayerInfo;
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
public class VersionedPlayerHeartRenderer extends avp implements PlayerHeartRenderer
{
    private final long lastTimeOpened;
    
    @Inject
    public VersionedPlayerHeartRenderer(final EventBus eventBus) {
        this.lastTimeOpened = TimeUtil.getMillis();
        eventBus.registerListener(this);
    }
    
    public void renderHearts(final Stack stack, final float x, final float y, final int heartSize, final NetworkPlayerInfo info) {
        final avo guiIngame = ave.A().q;
        final bdc mcInfo = ((VersionedNetworkPlayerInfo)info).getWrapped();
        final int health = info.getHealth();
        ave.A().P().a(VersionedPlayerHeartRenderer.d);
        if (this.lastTimeOpened == mcInfo.p()) {
            if (health < mcInfo.l()) {
                mcInfo.a(ave.J());
                mcInfo.b((long)(guiIngame.e() + 20));
            }
            else if (health > mcInfo.l()) {
                mcInfo.a(ave.J());
                mcInfo.b((long)(guiIngame.e() + 10));
            }
        }
        if (ave.J() - mcInfo.n() > 1000L || this.lastTimeOpened != mcInfo.p()) {
            mcInfo.b(health);
            mcInfo.c(health);
            mcInfo.a(ave.J());
        }
        mcInfo.c(this.lastTimeOpened);
        mcInfo.b(health);
        final int availableHearts = ns.f(Math.max(health, mcInfo.m()) / 2.0f);
        final int heartAmount = Math.max(ns.f((float)(health / 2)), Math.max(ns.f((float)(mcInfo.m() / 2)), 10));
        final boolean blinkLight = mcInfo.o() > guiIngame.e() && (mcInfo.o() - guiIngame.e()) / 3L % 2L == 1L;
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
                        if (i * 2 + 1 < mcInfo.m()) {
                            this.a(x + i * heartSpace, y, 70, 0, 9, 9);
                        }
                        if (i * 2 + 1 == mcInfo.m()) {
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
                final float healthPercentage = ns.a(health / 20.0f, 0.0f, 1.0f);
                final int color = (int)((1.0f - healthPercentage) * 255.0f) << 16 | (int)(healthPercentage * 255.0f) << 8;
                String text = "" + health / 2.0f;
                if (rightX - ave.A().k.a(text + "hp") >= x) {
                    text += "hp";
                }
                ave.A().k.a(text, (rightX + x) / 2.0f - ave.A().k.a(text) / 2.0f, y, color);
            }
        }
    }
}
