// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.mouse;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.key.MouseButton;

public class MouseAction
{
    private long timestamp;
    private MouseButton button;
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    @Nullable
    public MouseButton getButton() {
        return this.button;
    }
    
    public boolean isDoubleClick() {
        return TimeUtil.getMillis() - this.timestamp < 200L;
    }
    
    public void update(@NotNull final MouseButton button) {
        this.timestamp = TimeUtil.getMillis();
        this.button = button;
    }
}
