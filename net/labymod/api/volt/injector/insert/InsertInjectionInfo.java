// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.injector.insert;

import net.labymod.api.volt.callback.JumpStatement;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import net.labymod.api.volt.annotation.Insert;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

@InjectionInfo.AnnotationType(Insert.class)
@InjectionInfo.HandlerPrefix("insert")
public class InsertInjectionInfo extends InjectionInfo
{
    public InsertInjectionInfo(final MixinTargetContext mixin, final MethodNode method, final AnnotationNode annotation) {
        super(mixin, method, annotation);
    }
    
    protected Injector parseInjector(final AnnotationNode injectAnnotation) {
        final boolean cancellable = (boolean)Annotations.getValue(injectAnnotation, "cancellable", (Object)Boolean.FALSE);
        final JumpStatement jumpStatement = (JumpStatement)Annotations.getValue(injectAnnotation, "jumpStatement", (Class)JumpStatement.class, (Enum)JumpStatement.RETURN);
        return new InsertInjector(this, cancellable, jumpStatement);
    }
}
