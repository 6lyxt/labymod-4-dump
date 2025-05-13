// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.interaction.autotext;

import net.labymod.core.main.LabyMod;
import net.labymod.api.LabyAPI;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.core.client.entity.player.interaction.DefaultInteractionMenuRegistry;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;

public class AutoTextBulletPoint extends AbstractBulletPoint
{
    private static final DefaultInteractionMenuRegistry REGISTRY;
    private final AutoTextEntry entry;
    
    public AutoTextBulletPoint(final AutoTextEntry entry) {
        super(Component.text(entry.displayName().get()));
        this.entry = entry;
    }
    
    @Override
    public void execute(final Player player) {
        final LabyAPI labyAPI = Laby.labyAPI();
        final String autoTextMessage = AutoTextBulletPoint.REGISTRY.replacePlaceholders(this.entry.message().get(), player);
        if (this.entry.sendImmediately().get()) {
            labyAPI.minecraft().chatExecutor().chat(autoTextMessage, false);
            return;
        }
        labyAPI.minecraft().openChat(autoTextMessage);
    }
    
    static {
        REGISTRY = (DefaultInteractionMenuRegistry)LabyMod.references().interactionMenuRegistry();
    }
}
