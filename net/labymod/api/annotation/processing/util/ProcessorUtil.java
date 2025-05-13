// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.annotation.processing.util;

import javax.tools.Diagnostic;
import java.util.function.BooleanSupplier;
import java.nio.file.Path;
import javax.annotation.processing.Messager;

public final class ProcessorUtil
{
    public static boolean shouldProcess(final Object processor, final Messager messager, final Path path) {
        return shouldProcess(processor, messager, () -> path == null);
    }
    
    public static boolean shouldProcess(final Object processor, final Messager messager, final BooleanSupplier condition) {
        if (condition.getAsBoolean()) {
            messager.printMessage(Diagnostic.Kind.WARNING, "Processor \"" + String.valueOf(processor) + "\" could not be executed because of Single HotSwap. (If you should not use Single HotSwap, please report the error.)");
            return false;
        }
        return true;
    }
}
