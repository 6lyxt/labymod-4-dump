// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.gui.listener;

import net.labymod.api.client.gui.screen.widget.widgets.activity.chat.ChatButtonWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.user.permission.ClientPermission;
import java.util.Objects;
import net.labymod.api.event.labymod.serverapi.PermissionStateChangeEvent;
import net.labymod.api.client.chat.input.ChatInputRegistry;

public class ChatButtonPermissionListener
{
    private final ChatInputRegistry registry;
    
    public ChatButtonPermissionListener(final ChatInputRegistry registry) {
        this.registry = registry;
    }
    
    @Subscribe
    public void onPermissionStateChange(final PermissionStateChangeEvent event) {
        final ClientPermission permission = event.permission();
        this.registry.forEach(widget -> {
            final String permissionId = widget.getPermissionId();
            if (permissionId != null) {
                if (Objects.equals(permission.getIdentifier(), permissionId)) {
                    final PermissionStateChangeEvent.State state = event.state();
                    widget.setEnabled(state == PermissionStateChangeEvent.State.ALLOWED);
                }
            }
        });
    }
}
