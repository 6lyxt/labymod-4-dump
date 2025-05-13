// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.lanworld;

import net.labymod.labypeer.client.ice.credentials.IceCredentialsTransmitException;
import net.labymod.core.main.LabyMod;
import net.labymod.core.labyconnect.session.DefaultLabyConnectSession;
import java.util.UUID;
import net.labymod.labypeer.ice.IceCredentials;
import net.labymod.labypeer.client.ice.credentials.IceCredentialsHandler;
import net.labymod.labypeer.client.ice.credentials.IceCredentialsTransmitter;

public class LabyConnectIceCredentialsTransmitter implements IceCredentialsTransmitter
{
    private IceCredentialsHandler handler;
    
    public void credentialsReceived(final IceCredentials credentials) {
        if (this.handler != null) {
            this.handler.remoteCredentialsReceived(credentials);
        }
    }
    
    public void setHandler(final IceCredentialsHandler handler) {
        this.handler = handler;
    }
    
    public void sendCredentials(final UUID targetUser, final IceCredentials credentials) throws IceCredentialsTransmitException {
        final DefaultLabyConnectSession session = (DefaultLabyConnectSession)LabyMod.references().labyConnect().getSession();
        if (session != null) {
            session.sendIceCredentials(targetUser, credentials);
            return;
        }
        throw new IceCredentialsTransmitException("Not connected to the LabyMod chat");
    }
}
