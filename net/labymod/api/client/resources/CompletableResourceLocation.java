// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources;

import net.labymod.api.util.concurrent.AbstractCompletable;

public class CompletableResourceLocation extends AbstractCompletable<ResourceLocation>
{
    public CompletableResourceLocation(final ResourceLocation fallback) {
        super(fallback);
    }
    
    public CompletableResourceLocation(final ResourceLocation completed, final boolean isCompleted) {
        super(completed, isCompleted);
    }
}
