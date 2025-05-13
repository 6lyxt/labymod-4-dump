// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.render.matrix;

import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.matrix.StackProviderFactory;

@Singleton
@Implements(StackProviderFactory.class)
public class VersionedStackProviderFactory implements StackProviderFactory
{
    @Inject
    public VersionedStackProviderFactory() {
    }
    
    @Override
    public Stack create() {
        return VersionedStackProvider.DEFAULT_STACK;
    }
    
    @Override
    public Stack create(final Object poseStack) {
        return VersionedStackProvider.DEFAULT_STACK;
    }
    
    @Override
    public Stack create(final Object poseStack, final Object multiBufferSource) {
        return VersionedStackProvider.DEFAULT_STACK;
    }
}
