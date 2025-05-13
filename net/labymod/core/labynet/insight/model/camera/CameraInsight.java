// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.model.camera;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.labynet.insight.Insight;

public class CameraInsight implements Insight
{
    private DoubleVector3 position;
    private Quaternion rotation;
    private double fov;
    private PivotPointInsight pivotPoint;
    
    public CameraInsight(final JsonObject object) {
        this.fromJsonObject(object);
    }
    
    public CameraInsight(final DoubleVector3 position, final Quaternion rotation, final double fov) {
        this.position = position;
        this.rotation = rotation;
        this.fov = fov;
    }
    
    public void setPivotPoint(final PivotPointInsight pivotPoint) {
        this.pivotPoint = pivotPoint;
    }
    
    public Quaternion getRotation() {
        return this.rotation;
    }
    
    public DoubleVector3 getPosition() {
        return this.position;
    }
    
    public double getFov() {
        return this.fov;
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject camera = new JsonObject();
        final JsonObject rotation = new JsonObject();
        rotation.addProperty("x", (Number)this.rotation.getX());
        rotation.addProperty("y", (Number)this.rotation.getY());
        rotation.addProperty("z", (Number)this.rotation.getZ());
        rotation.addProperty("w", (Number)this.rotation.getW());
        camera.add("rotation", (JsonElement)rotation);
        final JsonObject position = new JsonObject();
        position.addProperty("x", (Number)this.position.getX());
        position.addProperty("y", (Number)this.position.getY());
        position.addProperty("z", (Number)this.position.getZ());
        camera.add("position", (JsonElement)position);
        camera.addProperty("fov", (Number)this.fov);
        if (this.pivotPoint != null) {
            camera.add("pivot_point", (JsonElement)this.pivotPoint.toJsonObject());
        }
        return camera;
    }
    
    @Override
    public void fromJsonObject(final JsonObject object) {
        final JsonObject rotation = object.get("rotation").getAsJsonObject();
        this.rotation = new Quaternion(rotation.get("x").getAsFloat(), rotation.get("y").getAsFloat(), rotation.get("z").getAsFloat(), rotation.get("w").getAsFloat());
        final JsonObject position = object.get("position").getAsJsonObject();
        this.position = new DoubleVector3(position.get("x").getAsDouble(), position.get("y").getAsDouble(), position.get("z").getAsDouble());
        this.fov = object.get("fov").getAsDouble();
        if (object.has("pivot_point")) {
            this.pivotPoint = new PivotPointInsight(object.get("pivot_point").getAsJsonObject());
        }
    }
}
