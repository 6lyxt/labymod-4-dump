// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.chat;

import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;

@Link(value = "activity/overlay/chat/settings.lss", priority = 64)
@AutoActivity
public class ChatSettingActivity extends SettingContentActivity
{
    private final Runnable saveCallback;
    
    public ChatSettingActivity(final Setting holder, final Runnable saveCallback) {
        super(holder);
        this.saveCallback = saveCallback;
    }
    
    public ChatSettingActivity(final Setting holder) {
        this(holder, null);
    }
    
    @Override
    public void onCloseScreen() {
        if (this.saveCallback != null) {
            this.saveCallback.run();
        }
        super.onCloseScreen();
    }
    
    @Override
    public boolean allowCustomFont() {
        return false;
    }
}
