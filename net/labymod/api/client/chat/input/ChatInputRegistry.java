// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.input;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.gui.screen.widget.widgets.activity.chat.ChatButtonWidget;
import net.labymod.api.service.Registry;

@Referenceable
public interface ChatInputRegistry extends Registry<ChatButtonWidget>
{
    float getButtonWidth();
}
