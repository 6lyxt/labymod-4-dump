// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.gui.screen.widget;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.api.client.options.MinecraftKeyEntry;

public class KeyEntryCustomWidgetListener implements ezb
{
    private final MinecraftKeyEntry keyEntry;
    private boolean focused;
    
    public KeyEntryCustomWidgetListener(final MinecraftKeyEntry keyEntry) {
        this.keyEntry = keyEntry;
    }
    
    public void a(final boolean focused) {
        this.focused = focused;
    }
    
    public boolean aI_() {
        return this.focused;
    }
    
    public boolean c(final double mouseX, final double mouseY) {
        return this.keyEntry.getCustomWidget().isHovered();
    }
    
    public boolean a(final double mouseX, final double mouseY, final int mouseButton) {
        final Widget widget = this.keyEntry.getCustomWidget();
        if (!widget.isHovered()) {
            return false;
        }
        final MouseButton button = DefaultKeyMapper.pressMouse(mouseButton);
        return button != null && Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> widget.mouseClicked(mouse, button));
    }
    
    public boolean b(final double mouseX, final double mouseY, final int mouseButton) {
        final Widget widget = this.keyEntry.getCustomWidget();
        if (!widget.isHovered()) {
            return false;
        }
        final MouseButton button = KeyMapper.getMouseButton(mouseButton);
        return button != null && Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> widget.mouseReleased(mouse, button));
    }
}
