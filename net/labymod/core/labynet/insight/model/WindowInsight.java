// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.model;

import com.google.gson.JsonObject;
import net.labymod.core.labynet.insight.Insight;

public class WindowInsight implements Insight
{
    private int width;
    private int height;
    
    public WindowInsight(final JsonObject object) {
        this.fromJsonObject(object);
    }
    
    public WindowInsight(final int width, final int height) {
        this.width = width;
        this.height = height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject window = new JsonObject();
        window.addProperty("width", (Number)this.width);
        window.addProperty("height", (Number)this.height);
        return window;
    }
    
    @Override
    public void fromJsonObject(final JsonObject object) {
        this.width = object.get("width").getAsInt();
        this.height = object.get("height").getAsInt();
    }
}
