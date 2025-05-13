// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.object;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.vector.DoubleVector3;

public abstract class AbstractWorldObject implements WorldObject
{
    protected final DoubleVector3 position;
    
    protected AbstractWorldObject() {
        this(new FloatVector3(0.0f, 0.0f, 0.0f));
    }
    
    protected AbstractWorldObject(final FloatVector3 location) {
        this(new DoubleVector3(location));
    }
    
    protected AbstractWorldObject(final DoubleVector3 position) {
        this.position = position;
    }
    
    @NotNull
    @Override
    public DoubleVector3 position() {
        return this.position;
    }
    
    @Nullable
    @Override
    public Widget createWidget() {
        return null;
    }
}
