// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.gui.screen.key.mapper;

import org.lwjgl.glfw.GLFW;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKey;

public class VersionedKey extends DefaultKey
{
    public VersionedKey(final Key key, final int keyCode, final int legacyKeyCode) {
        super(key, keyCode, legacyKeyCode);
    }
    
    @Override
    protected String getKeyName(final int keyCode) {
        return GLFW.glfwGetKeyName(keyCode, -1);
    }
}
