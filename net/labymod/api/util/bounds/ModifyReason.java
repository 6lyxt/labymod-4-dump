// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.bounds;

import java.util.Set;
import java.util.EnumSet;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.util.ide.IdeUtil;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.addon.LoadedAddon;

public class ModifyReason
{
    private static final StackWalker WALKER;
    private final Class<?> source;
    private final String reason;
    private boolean addonLoaded;
    private LoadedAddon sourceAddon;
    private boolean renderOnly;
    
    private ModifyReason(@NotNull final Class<?> source, @NotNull final String reason) {
        this.source = source;
        this.reason = reason;
    }
    
    public static ModifyReason of(@NotNull final Class<?> source, @NotNull final String reason) {
        return new ModifyReason(source, reason);
    }
    
    private static ModifyReason ofInternal(@NotNull final String reason, final Class<?> callerClass) {
        if (IdeUtil.RUNNING_IN_IDE) {
            final StackWalker.StackFrame walk = ModifyReason.WALKER.walk(stream -> stream.skip(2L).findFirst().orElse(null));
            final StackTraceElement sourceElement = walk.toStackTraceElement();
            if (!sourceElement.getMethodName().equals("<clinit>")) {
                throw new IllegalStateException("ModifyReason.of(reason) may only be used for constants as this is very performance intensive");
            }
        }
        return of(callerClass, reason);
    }
    
    public static ModifyReason of(@NotNull final String reason) {
        return ofInternal(reason, ModifyReason.WALKER.getCallerClass());
    }
    
    public static ModifyReason renderOnly(@NotNull final String reason) {
        return ofInternal(reason, ModifyReason.WALKER.getCallerClass()).setRenderOnly();
    }
    
    @Nullable
    public LoadedAddon sourceAddon() {
        if (!this.addonLoaded) {
            Laby.labyAPI().addonService().getAddon(this.source).ifPresent(addon -> this.sourceAddon = addon);
            this.addonLoaded = true;
        }
        return this.sourceAddon;
    }
    
    @NotNull
    public Class<?> source() {
        return this.source;
    }
    
    @NotNull
    public String reason() {
        return this.reason;
    }
    
    public boolean isRenderOnly() {
        return this.renderOnly;
    }
    
    public ModifyReason setRenderOnly() {
        this.renderOnly = true;
        return this;
    }
    
    static {
        WALKER = StackWalker.getInstance(EnumSet.of(StackWalker.Option.RETAIN_CLASS_REFERENCE, StackWalker.Option.SHOW_REFLECT_FRAMES));
    }
}
