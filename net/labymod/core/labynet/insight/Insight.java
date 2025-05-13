// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight;

import com.google.gson.JsonObject;

public interface Insight
{
    JsonObject toJsonObject();
    
    void fromJsonObject(final JsonObject p0);
}
