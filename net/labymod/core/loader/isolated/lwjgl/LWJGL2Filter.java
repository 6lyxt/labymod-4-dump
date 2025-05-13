// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.isolated.lwjgl;

import net.labymod.core.loader.isolated.IsolatedLibrary;
import net.labymod.core.loader.isolated.IsolatedLibraryPredicate;

public class LWJGL2Filter implements IsolatedLibraryPredicate
{
    @Override
    public boolean test(final IsolatedLibrary isolatedLibrary) {
        return !isolatedLibrary.getGroup().equals("org.lwjgl.lwjgl");
    }
}
