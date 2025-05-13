// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.spray.model;

import java.util.ArrayList;
import java.util.UUID;
import net.labymod.api.util.Lazy;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Spray
{
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("tags")
    private final List<String> tags;
    private final transient Lazy<UUID> uuid;
    
    public Spray() {
        this.tags = new ArrayList<String>();
        this.uuid = Lazy.of(() -> new UUID(this.id, 0L));
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<String> getTags() {
        return this.tags;
    }
    
    public UUID getUuid() {
        return this.uuid.get();
    }
    
    @Override
    public String toString() {
        return "Spray[id=" + this.id + ",name=" + this.name + ",tags=" + String.valueOf(this.tags);
    }
}
