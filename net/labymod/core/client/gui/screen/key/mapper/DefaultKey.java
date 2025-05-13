// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.key.mapper;

import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.gui.screen.key.Key;

public abstract class DefaultKey
{
    private final String keyName;
    private final int glfwKeyCode;
    private final int lwjglKeyCode;
    private final int keyCode;
    private boolean action;
    private char character;
    
    protected DefaultKey(final Key key, final int glfwKeyCode, final int lwjglKeyCode) {
        this.character = '\0';
        this.keyName = key.getActualName();
        this.glfwKeyCode = glfwKeyCode;
        this.lwjglKeyCode = lwjglKeyCode;
        this.keyCode = (PlatformEnvironment.isAncientOpenGL() ? lwjglKeyCode : glfwKeyCode);
    }
    
    public String getKeyName() {
        return this.keyName;
    }
    
    public int getKeyCode() {
        return this.keyCode;
    }
    
    public int getGlfwKeyCode() {
        return this.glfwKeyCode;
    }
    
    public int getLwjglKeyCode() {
        return this.lwjglKeyCode;
    }
    
    public char getCharacter() {
        if (this.action) {
            return this.character;
        }
        if (this.character == '\0') {
            final String keyName = this.getKeyName(this.keyCode);
            if (keyName == null || keyName.length() == 0) {
                this.action = true;
                return this.character;
            }
            this.character = keyName.charAt(0);
        }
        return this.character;
    }
    
    protected abstract String getKeyName(final int p0);
}
