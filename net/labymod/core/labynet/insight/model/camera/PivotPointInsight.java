// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.model.camera;

import com.google.gson.JsonObject;
import net.labymod.core.labynet.insight.Insight;

public class PivotPointInsight implements Insight
{
    private double x;
    private double y;
    private double z;
    
    public PivotPointInsight(final JsonObject object) {
        this.fromJsonObject(object);
    }
    
    public PivotPointInsight(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", (Number)this.x);
        jsonObject.addProperty("y", (Number)this.y);
        jsonObject.addProperty("z", (Number)this.z);
        return jsonObject;
    }
    
    @Override
    public void fromJsonObject(final JsonObject object) {
        this.x = object.get("x").getAsDouble();
        this.y = object.get("y").getAsDouble();
        this.z = object.get("z").getAsDouble();
    }
}
