// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.client.entity.player.FieldOfViewModifierEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import javax.inject.Singleton;

@Singleton
public class FieldOfViewModifierListener
{
    private static final float NORMAL_WALKING_SPEED = 0.1f;
    private static final float NORMAL_SPRINTING_SPEED = 0.13000001f;
    private final LabyAPI labyAPI;
    
    @Inject
    public FieldOfViewModifierListener() {
        this.labyAPI = Laby.labyAPI();
    }
    
    @Subscribe(-64)
    public void modifyFieldOfView(final FieldOfViewModifierEvent event) {
        if (!this.labyAPI.config().ingame().disableSpeedFOV().get()) {
            return;
        }
        final ClientPlayer clientPlayer = this.labyAPI.minecraft().getClientPlayer();
        float fieldOfView = 1.0f;
        if (clientPlayer.isAbilitiesFlying()) {
            fieldOfView *= 1.1f;
        }
        final float walkingSpeed = clientPlayer.getAbilitiesWalkingSpeed();
        float speed = 0.1f;
        if (clientPlayer.isSprinting()) {
            speed = 0.13000001f;
        }
        fieldOfView *= (float)((speed / walkingSpeed + 1.0) / 2.0);
        if (walkingSpeed == 0.0f || Float.isNaN(fieldOfView) || Float.isInfinite(fieldOfView)) {
            fieldOfView = 1.0f;
        }
        if (clientPlayer.isHandActive() && clientPlayer.isUsingBow()) {
            float modifiedFieldOfView = clientPlayer.getItemUseDurationTicks() / 20.0f;
            if (modifiedFieldOfView > 1.0f) {
                modifiedFieldOfView = 1.0f;
            }
            else {
                modifiedFieldOfView *= modifiedFieldOfView;
            }
            fieldOfView *= 1.0f - modifiedFieldOfView * 0.15f;
        }
        event.setFieldOfView(fieldOfView);
    }
}
