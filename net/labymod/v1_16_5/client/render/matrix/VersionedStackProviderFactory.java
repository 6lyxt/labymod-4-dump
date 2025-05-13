// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.render.matrix;

import java.util.Objects;
import net.labymod.api.client.render.matrix.StackProvider;
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
        return Stack.create(new VersionedStackProvider(new dfm()));
    }
    
    @Override
    public Stack create(final Object poseStack) {
        Objects.requireNonNull(poseStack, "poseStack must not be null");
        if (!(poseStack instanceof dfm)) {
            throw new IllegalStateException(poseStack.getClass().getName() + " is not an instance of " + String.valueOf(dfm.class));
        }
        return Stack.create(new VersionedStackProvider((dfm)poseStack));
    }
    
    @Override
    public Stack create(final Object poseStack, final Object multiBufferSource) {
        Objects.requireNonNull(poseStack, "poseStack must not be null");
        if (!(poseStack instanceof dfm)) {
            throw new IllegalStateException(poseStack.getClass().getName() + " is not an instance of " + String.valueOf(dfm.class));
        }
        return Stack.create(new VersionedStackProvider((dfm)poseStack)).multiBufferSource(multiBufferSource);
    }
}
