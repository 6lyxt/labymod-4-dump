// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.matrix;

import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.vector.FloatMatrix3;
import java.util.function.Supplier;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.GameMathMapper;

public abstract class AbstractStackProvider<S, M3, M4> implements StackProvider
{
    protected static final GameMathMapper MAPPER;
    protected static final FloatVector3 ROTATION_VECTOR;
    protected final S stack;
    private final Supplier<M3> normalSupplier;
    private final Supplier<M4> positionSupplier;
    
    protected AbstractStackProvider(final S stack, final Supplier<M3> normalSupplier, final Supplier<M4> positionSupplier) {
        this.stack = stack;
        this.normalSupplier = normalSupplier;
        this.positionSupplier = positionSupplier;
    }
    
    @Override
    public FloatMatrix3 getNormal() {
        return AbstractStackProvider.MAPPER.fromMatrix3f(this.normalSupplier.get());
    }
    
    @Override
    public FloatMatrix4 getPosition() {
        return AbstractStackProvider.MAPPER.fromMatrix4f(this.positionSupplier.get());
    }
    
    @Nullable
    @Override
    public Object getPoseStack() {
        return this.stack;
    }
    
    static {
        MAPPER = MathHelper.mapper();
        ROTATION_VECTOR = new FloatVector3();
    }
}
