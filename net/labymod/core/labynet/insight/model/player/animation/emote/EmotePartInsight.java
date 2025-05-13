// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.model.player.animation.emote;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.labynet.insight.Insight;

public class EmotePartInsight implements Insight
{
    private FloatVector3 position;
    private FloatVector3 rotation;
    private FloatVector3 scale;
    
    public EmotePartInsight(final JsonObject object) {
        this.fromJsonObject(object);
    }
    
    public EmotePartInsight(final FloatVector3 position, final FloatVector3 rotation, final FloatVector3 scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }
    
    public boolean isPositionDefault() {
        return this.position.getX() == 0.0f && this.position.getY() == 0.0f && this.position.getZ() == 0.0f;
    }
    
    public boolean isRotationDefault() {
        return this.rotation.getX() == 0.0f && this.rotation.getY() == 0.0f && this.rotation.getZ() == 0.0f;
    }
    
    public boolean isScaleDefault() {
        return this.scale.getX() == 1.0f && this.scale.getY() == 1.0f && this.scale.getZ() == 1.0f;
    }
    
    public boolean isDefault() {
        return this.isPositionDefault() && this.isRotationDefault() && this.isScaleDefault();
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        if (!this.isRotationDefault()) {
            final JsonObject rotation = new JsonObject();
            rotation.addProperty("x", (Number)this.rotation.getX());
            rotation.addProperty("y", (Number)this.rotation.getY());
            rotation.addProperty("z", (Number)this.rotation.getZ());
            jsonObject.add("rotation", (JsonElement)rotation);
        }
        if (!this.isPositionDefault()) {
            final JsonObject position = new JsonObject();
            position.addProperty("x", (Number)this.position.getX());
            position.addProperty("y", (Number)this.position.getY());
            position.addProperty("z", (Number)this.position.getZ());
            jsonObject.add("position", (JsonElement)position);
        }
        if (!this.isScaleDefault()) {
            final JsonObject scale = new JsonObject();
            scale.addProperty("x", (Number)this.scale.getX());
            scale.addProperty("y", (Number)this.scale.getY());
            scale.addProperty("z", (Number)this.scale.getZ());
            jsonObject.add("scale", (JsonElement)scale);
        }
        return jsonObject;
    }
    
    @Override
    public void fromJsonObject(final JsonObject object) {
        if (object.has("rotation")) {
            final JsonObject rotation = object.getAsJsonObject("rotation");
            this.rotation = new FloatVector3(rotation.get("x").getAsFloat(), rotation.get("y").getAsFloat(), rotation.get("z").getAsFloat());
        }
        else {
            this.rotation = new FloatVector3(0.0f, 0.0f, 0.0f);
        }
        if (object.has("position")) {
            final JsonObject position = object.getAsJsonObject("position");
            this.position = new FloatVector3(position.get("x").getAsFloat(), position.get("y").getAsFloat(), position.get("z").getAsFloat());
        }
        else {
            this.position = new FloatVector3(0.0f, 0.0f, 0.0f);
        }
        if (object.has("scale")) {
            final JsonObject scale = object.getAsJsonObject("scale");
            this.scale = new FloatVector3(scale.get("x").getAsFloat(), scale.get("y").getAsFloat(), scale.get("z").getAsFloat());
        }
        else {
            this.scale = new FloatVector3(1.0f, 1.0f, 1.0f);
        }
    }
}
