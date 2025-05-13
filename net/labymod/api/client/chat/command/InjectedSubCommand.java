// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.command;

import java.util.Locale;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public abstract class InjectedSubCommand extends Command
{
    private final String injectionPrefix;
    
    protected InjectedSubCommand(@NotNull final String injectionPrefix, @NotNull final String prefix, @NotNull final String... aliases) {
        super(prefix, aliases);
        Objects.requireNonNull(injectionPrefix, "injectionPrefix");
        this.injectionPrefix = injectionPrefix.toLowerCase(Locale.ROOT);
    }
    
    @NotNull
    public String getInjectionPrefix() {
        return this.injectionPrefix;
    }
}
