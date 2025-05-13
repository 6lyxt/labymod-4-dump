// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect;

import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.item.AbstractItem;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.model.ModelPart;

public abstract class GeometryEffect
{
    private final String name;
    private final Type type;
    protected final ModelPart modelPart;
    protected final String[] arguments;
    private final int parameterCount;
    
    protected GeometryEffect(final String effectArgument, final ModelPart modelPart, final Type type, final int parameterCount) {
        this.arguments = effectArgument.split("_");
        this.modelPart = modelPart;
        this.type = type;
        this.name = this.getClass().getSimpleName();
        this.parameterCount = parameterCount;
    }
    
    @NotNull
    public String getName() {
        return this.name;
    }
    
    public GeometryEffect get() {
        return (this.arguments.length >= this.parameterCount) ? (this.processParameters() ? this : null) : null;
    }
    
    protected abstract boolean processParameters();
    
    public abstract void apply(final AbstractItem p0, final Player p1, final PlayerModel p2, final ItemMetadata p3, final ItemEffect.EffectData p4);
    
    protected String getParameter(final int index) {
        return this.arguments[index + 1];
    }
    
    protected String getParameter(final int index, final int length) {
        final String argument = this.arguments[index + 1];
        return (argument.length() == length) ? argument : null;
    }
    
    public ModelPart getModelPart() {
        return this.modelPart;
    }
    
    public String[] getArguments() {
        return this.arguments;
    }
    
    protected boolean hasParameter(final int index) {
        return this.arguments.length > index + 1;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public enum Type
    {
        BUFFER_CREATION, 
        PHYSIC, 
        POST_PROCESSING, 
        METADATA;
        
        public static Type[] VALUES;
        
        static {
            Type.VALUES = values();
        }
    }
}
