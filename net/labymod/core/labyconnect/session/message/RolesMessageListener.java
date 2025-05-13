// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.message;

import java.util.Iterator;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import net.labymod.api.labyconnect.protocol.model.User;
import it.unimi.dsi.fastutil.ints.IntList;

public class RolesMessageListener implements MessageListener
{
    private final IntList roles;
    private final User self;
    
    public RolesMessageListener(final IntList roles, final User self) {
        this.roles = roles;
        this.self = self;
    }
    
    @Override
    public void listen(final String message) {
        this.roles.clear();
        final JsonArray roles = (JsonArray)RolesMessageListener.GSON.fromJson(message, (Class)JsonArray.class);
        for (final JsonElement role : roles) {
            this.roles.add(role.getAsInt());
        }
        final DefaultUser user = (DefaultUser)this.self;
        user.setLabyPlusOverride(this.roles.contains(10));
    }
}
