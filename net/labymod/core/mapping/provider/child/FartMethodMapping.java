// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.provider.child;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.mapping.provider.child.MethodMapping;
import net.minecraftforge.srgutils.IMappingFile;

public class FartMethodMapping extends FartMapping<IMappingFile.IMethod> implements MethodMapping
{
    public FartMethodMapping(final IMappingFile.IMethod method) {
        super((IMappingFile.INode)method);
    }
    
    @NotNull
    @Override
    public String getDescriptor() {
        return ((IMappingFile.IMethod)super.delegate).getDescriptor();
    }
    
    @NotNull
    @Override
    public String getMappedDescriptor() {
        return ((IMappingFile.IMethod)super.delegate).getMappedDescriptor();
    }
}
