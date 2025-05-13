// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.command;

import org.jetbrains.annotations.ApiStatus;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public abstract class SubCommand extends Command
{
    private Command parent;
    
    protected SubCommand(@NotNull final String subPrefix, @NotNull final String... aliases) {
        super(subPrefix, aliases);
    }
    
    @NotNull
    public final Command parent() {
        if (this.parent == null) {
            throw new NullPointerException("Parent is null. Did you register this sub command via Command#withSubCommand(SubCommand)?");
        }
        return this.parent;
    }
    
    @Deprecated
    @Nullable
    public final Command getParent() {
        return this.parent;
    }
    
    @ApiStatus.Internal
    protected final SubCommand withParent(@NotNull final Command parent) {
        Objects.requireNonNull(parent, "parent");
        if (this.parent != null) {
            throw new IllegalStateException("Parent already set");
        }
        this.parent = parent;
        return this;
    }
}
