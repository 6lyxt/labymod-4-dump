// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.model.display;

import com.google.gson.JsonElement;
import java.util.UUID;

@Deprecated(forRemoval = true, since = "4.2.24")
public class Subtitle
{
    private UUID uniqueId;
    private double size;
    private JsonElement text;
    
    public Subtitle(final UUID uniqueId, final double size, final JsonElement text) {
        this.uniqueId = uniqueId;
        this.setSize(size);
        this.text = text;
    }
    
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    public void setUniqueId(final UUID uniqueId) {
        this.uniqueId = uniqueId;
    }
    
    public double getSize() {
        return this.size;
    }
    
    public void setSize(double size) {
        if (size < 0.25) {
            size = 0.25;
        }
        if (size > 1.0) {
            size = 1.0;
        }
        this.size = size;
    }
    
    public JsonElement getText() {
        return this.text;
    }
    
    public void setText(final JsonElement text) {
        this.text = text;
    }
}
