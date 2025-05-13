// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server.lan;

import org.jetbrains.annotations.NotNull;
import java.net.InetAddress;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface LanServerDetector
{
    @NotNull
    InetAddress broadCastAddress();
    
    void startDetectionTask(@NotNull final LanServerCallback p0);
    
    void terminateDetectionTask();
    
    void reset();
    
    void close();
}
