// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.Constants;
import net.labymod.api.event.client.misc.CaptureScreenshotEvent;
import net.labymod.api.LabyAPI;

public class ScreenshotListener
{
    private final LabyAPI labyAPI;
    
    public ScreenshotListener(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    @Subscribe(126)
    public void onCaptureScreenshot(final CaptureScreenshotEvent event) {
        if (this.labyAPI.config().notifications().screenshotSound().get()) {
            this.labyAPI.minecraft().sounds().playSound(Constants.Resources.SOUND_SCREENSHOT_CAPTURE, 1.0f, 1.0f);
        }
    }
}
