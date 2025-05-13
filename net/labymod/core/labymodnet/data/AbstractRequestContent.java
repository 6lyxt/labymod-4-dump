// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.data;

import net.labymod.core.labymodnet.models.ChangeResponse;
import java.util.function.Consumer;

public abstract class AbstractRequestContent implements RequestContent
{
    protected final CosmeticRequestType type;
    protected final Consumer<ChangeResponse> changeResponseConsumer;
    
    public AbstractRequestContent(final CosmeticRequestType type, final Consumer<ChangeResponse> changeResponseConsumer) {
        this.type = type;
        this.changeResponseConsumer = changeResponseConsumer;
    }
    
    @Override
    public CosmeticRequestType getType() {
        return this.type;
    }
    
    @Override
    public Consumer<ChangeResponse> getChangeResponseConsumer() {
        return this.changeResponseConsumer;
    }
}
