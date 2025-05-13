// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.provider.child;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.mapping.provider.child.FieldMapping;
import net.minecraftforge.srgutils.IMappingFile;

public class FartFieldMapping extends FartMapping<IMappingFile.IField> implements FieldMapping
{
    public FartFieldMapping(final IMappingFile.IField delegate) {
        super((IMappingFile.INode)delegate);
    }
    
    @Nullable
    @Override
    public String getDescriptor() {
        return ((IMappingFile.IField)super.delegate).getDescriptor();
    }
    
    @Nullable
    @Override
    public String getMappedDescriptor() {
        return ((IMappingFile.IField)super.delegate).getMappedDescriptor();
    }
}
