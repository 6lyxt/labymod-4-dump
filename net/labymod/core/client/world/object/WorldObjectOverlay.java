// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.object;

import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import java.util.HashMap;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.world.object.WorldObject;
import java.util.Map;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;

@AutoActivity
public class WorldObjectOverlay extends IngameOverlayActivity
{
    private static final ModifyReason WORLD_OBJECT_POSITION;
    private final Map<WorldObject, Widget> objects;
    
    public WorldObjectOverlay() {
        this.objects = new HashMap<WorldObject, Widget>();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        for (final Widget widget : this.objects.values()) {
            ((Document)this.document).addChild(widget);
        }
    }
    
    @Override
    public void render(final ScreenContext context) {
        for (final Map.Entry<WorldObject, Widget> entry : this.objects.entrySet()) {
            this.updateState(entry.getKey(), entry.getValue());
        }
        super.render(context);
    }
    
    private void updateState(@NotNull final WorldObject object, @NotNull final Widget widget) {
        widget.setVisible(object.shouldRender() && object.shouldRenderInOverlay());
        final Entity cam = this.labyAPI.minecraft().getCameraEntity();
        if (cam == null) {
            return;
        }
        final float yaw = this.getYaw(cam, object);
        final float clientYaw = MathHelper.wrapDegrees(cam.getRotationYaw());
        final float yawOffset = MathHelper.wrapDegrees(clientYaw - yaw + 180.0f);
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        final Bounds windowBounds = window.bounds();
        final float x = windowBounds.getWidth() / 2.0f + windowBounds.getWidth() / 180.0f * yawOffset;
        final float y = windowBounds.getHeight() - widget.bounds().getHeight(BoundsType.OUTER);
        widget.bounds().setOuterPosition(x, y, WorldObjectOverlay.WORLD_OBJECT_POSITION);
    }
    
    public float getYaw(final Entity cam, final WorldObject object) {
        final DoubleVector3 objectLocation = object.position();
        final DoubleVector3 previousObjectLocation = object.previousPosition();
        final double objectX = MathHelper.lerp(objectLocation.getX(), previousObjectLocation.getX());
        final double objectZ = MathHelper.lerp(objectLocation.getZ(), previousObjectLocation.getZ());
        final Position position = cam.position();
        final Position previousPosition = cam.previousPosition();
        final double playerX = MathHelper.lerp(position.getX(), previousPosition.getX());
        final double playerZ = MathHelper.lerp(position.getZ(), previousPosition.getZ());
        final double xDiff = objectX - playerX;
        final double zDiff = objectZ - playerZ;
        final double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)Math.toDegrees(Math.acos((distanceXZ == 0.0) ? 0.0 : (xDiff / distanceXZ)));
        if (zDiff < 0.0) {
            yaw += Math.abs(180.0f - yaw) * 2.0f;
        }
        yaw -= 90.0f;
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return yaw;
    }
    
    public void addObject(@NotNull final WorldObject object) {
        final Widget widget = object.createWidget();
        if (widget == null) {
            return;
        }
        ((Document)this.document).addChildInitialized(widget);
        this.objects.put(object, widget);
        this.updateState(object, widget);
    }
    
    public void removeObject(@NotNull final WorldObject object) {
        final Widget widget = this.objects.remove(object);
        if (widget != null) {
            ((Document)this.document).removeChild(widget);
        }
    }
    
    public Map<WorldObject, Widget> getObjects() {
        return this.objects;
    }
    
    @Override
    public boolean isVisible() {
        return !this.objects.isEmpty();
    }
    
    static {
        WORLD_OBJECT_POSITION = ModifyReason.of("WorldObjectPosition");
    }
}
